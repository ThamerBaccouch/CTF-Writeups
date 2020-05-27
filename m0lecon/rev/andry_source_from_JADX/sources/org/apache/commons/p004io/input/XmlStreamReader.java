package org.apache.commons.p004io.input;

import com.andry.BuildConfig;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.p004io.ByteOrderMark;

/* renamed from: org.apache.commons.io.input.XmlStreamReader */
public class XmlStreamReader extends Reader {
    private static final ByteOrderMark[] BOMS = {ByteOrderMark.UTF_8, ByteOrderMark.UTF_16BE, ByteOrderMark.UTF_16LE, ByteOrderMark.UTF_32BE, ByteOrderMark.UTF_32LE};
    private static final int BUFFER_SIZE = 4096;
    private static final Pattern CHARSET_PATTERN = Pattern.compile("charset=[\"']?([.[^; \"']]*)[\"']?");
    private static final String EBCDIC = "CP1047";
    public static final Pattern ENCODING_PATTERN = Pattern.compile("<\\?xml.*encoding[\\s]*=[\\s]*((?:\".[^\"]*\")|(?:'.[^']*'))", 8);
    private static final String HTTP_EX_1 = "Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], BOM must be NULL";
    private static final String HTTP_EX_2 = "Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], encoding mismatch";
    private static final String HTTP_EX_3 = "Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], Invalid MIME";
    private static final String RAW_EX_1 = "Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] encoding mismatch";
    private static final String RAW_EX_2 = "Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] unknown BOM";
    private static final String US_ASCII = "US-ASCII";
    private static final String UTF_16 = "UTF-16";
    private static final String UTF_16BE = "UTF-16BE";
    private static final String UTF_16LE = "UTF-16LE";
    private static final String UTF_32 = "UTF-32";
    private static final String UTF_32BE = "UTF-32BE";
    private static final String UTF_32LE = "UTF-32LE";
    private static final String UTF_8 = "UTF-8";
    private static final ByteOrderMark[] XML_GUESS_BYTES = {new ByteOrderMark(UTF_8, 60, 63, 120, 109), new ByteOrderMark(UTF_16BE, 0, 60, 0, 63), new ByteOrderMark(UTF_16LE, 60, 0, 63, 0), new ByteOrderMark(UTF_32BE, 0, 0, 0, 60, 0, 0, 0, 63, 0, 0, 0, 120, 0, 0, 0, 109), new ByteOrderMark(UTF_32LE, 60, 0, 0, 0, 63, 0, 0, 0, 120, 0, 0, 0, 109, 0, 0, 0), new ByteOrderMark(EBCDIC, 76, 111, 167, 148)};
    private final String defaultEncoding;
    private final String encoding;
    private final Reader reader;

    public String getDefaultEncoding() {
        return this.defaultEncoding;
    }

    public XmlStreamReader(File file) throws IOException {
        this((InputStream) new FileInputStream(file));
    }

    public XmlStreamReader(InputStream is) throws IOException {
        this(is, true);
    }

    public XmlStreamReader(InputStream is, boolean lenient) throws IOException {
        this(is, lenient, (String) null);
    }

    public XmlStreamReader(InputStream is, boolean lenient, String defaultEncoding2) throws IOException {
        this.defaultEncoding = defaultEncoding2;
        BOMInputStream bom = new BOMInputStream(new BufferedInputStream(is, 4096), false, BOMS);
        BOMInputStream pis = new BOMInputStream(bom, true, XML_GUESS_BYTES);
        this.encoding = doRawStream(bom, pis, lenient);
        this.reader = new InputStreamReader(pis, this.encoding);
    }

    public XmlStreamReader(URL url) throws IOException {
        this(url.openConnection(), (String) null);
    }

    public XmlStreamReader(URLConnection conn, String defaultEncoding2) throws IOException {
        this.defaultEncoding = defaultEncoding2;
        String contentType = conn.getContentType();
        BOMInputStream bom = new BOMInputStream(new BufferedInputStream(conn.getInputStream(), 4096), false, BOMS);
        BOMInputStream pis = new BOMInputStream(bom, true, XML_GUESS_BYTES);
        if ((conn instanceof HttpURLConnection) || contentType != null) {
            this.encoding = doHttpStream(bom, pis, contentType, true);
        } else {
            this.encoding = doRawStream(bom, pis, true);
        }
        this.reader = new InputStreamReader(pis, this.encoding);
    }

    public XmlStreamReader(InputStream is, String httpContentType) throws IOException {
        this(is, httpContentType, true);
    }

    public XmlStreamReader(InputStream is, String httpContentType, boolean lenient, String defaultEncoding2) throws IOException {
        this.defaultEncoding = defaultEncoding2;
        BOMInputStream bom = new BOMInputStream(new BufferedInputStream(is, 4096), false, BOMS);
        BOMInputStream pis = new BOMInputStream(bom, true, XML_GUESS_BYTES);
        this.encoding = doHttpStream(bom, pis, httpContentType, lenient);
        this.reader = new InputStreamReader(pis, this.encoding);
    }

    public XmlStreamReader(InputStream is, String httpContentType, boolean lenient) throws IOException {
        this(is, httpContentType, lenient, null);
    }

    public String getEncoding() {
        return this.encoding;
    }

    public int read(char[] buf, int offset, int len) throws IOException {
        return this.reader.read(buf, offset, len);
    }

    public void close() throws IOException {
        this.reader.close();
    }

    private String doRawStream(BOMInputStream bom, BOMInputStream pis, boolean lenient) throws IOException {
        String bomEnc = bom.getBOMCharsetName();
        String xmlGuessEnc = pis.getBOMCharsetName();
        try {
            return calculateRawEncoding(bomEnc, xmlGuessEnc, getXmlProlog(pis, xmlGuessEnc));
        } catch (XmlStreamReaderException ex) {
            if (lenient) {
                return doLenientDetection(null, ex);
            }
            throw ex;
        }
    }

    private String doHttpStream(BOMInputStream bom, BOMInputStream pis, String httpContentType, boolean lenient) throws IOException {
        String bomEnc = bom.getBOMCharsetName();
        String xmlGuessEnc = pis.getBOMCharsetName();
        try {
            return calculateHttpEncoding(httpContentType, bomEnc, xmlGuessEnc, getXmlProlog(pis, xmlGuessEnc), lenient);
        } catch (XmlStreamReaderException ex) {
            if (lenient) {
                return doLenientDetection(httpContentType, ex);
            }
            throw ex;
        }
    }

    private String doLenientDetection(String httpContentType, XmlStreamReaderException ex) throws IOException {
        if (httpContentType != null) {
            String str = "text/html";
            if (httpContentType.startsWith(str)) {
                String httpContentType2 = httpContentType.substring(str.length());
                StringBuilder sb = new StringBuilder();
                sb.append("text/xml");
                sb.append(httpContentType2);
                try {
                    return calculateHttpEncoding(sb.toString(), ex.getBomEncoding(), ex.getXmlGuessEncoding(), ex.getXmlEncoding(), true);
                } catch (XmlStreamReaderException ex2) {
                    ex = ex2;
                }
            }
        }
        String encoding2 = ex.getXmlEncoding();
        if (encoding2 == null) {
            encoding2 = ex.getContentTypeEncoding();
        }
        if (encoding2 == null) {
            String str2 = this.defaultEncoding;
            if (str2 == null) {
                str2 = UTF_8;
            }
            encoding2 = str2;
        }
        return encoding2;
    }

    /* access modifiers changed from: 0000 */
    public String calculateRawEncoding(String bomEnc, String xmlGuessEnc, String xmlEnc) throws IOException {
        String str = UTF_16;
        String str2 = UTF_16LE;
        String str3 = UTF_16BE;
        String str4 = UTF_8;
        if (bomEnc != null) {
            boolean equals = bomEnc.equals(str4);
            String str5 = RAW_EX_1;
            if (equals) {
                if (xmlGuessEnc != null && !xmlGuessEnc.equals(str4)) {
                    throw new XmlStreamReaderException(MessageFormat.format(str5, new Object[]{bomEnc, xmlGuessEnc, xmlEnc}), bomEnc, xmlGuessEnc, xmlEnc);
                } else if (xmlEnc == null || xmlEnc.equals(str4)) {
                    return bomEnc;
                } else {
                    throw new XmlStreamReaderException(MessageFormat.format(str5, new Object[]{bomEnc, xmlGuessEnc, xmlEnc}), bomEnc, xmlGuessEnc, xmlEnc);
                }
            } else if (bomEnc.equals(str3) || bomEnc.equals(str2)) {
                if (xmlGuessEnc != null && !xmlGuessEnc.equals(bomEnc)) {
                    throw new XmlStreamReaderException(MessageFormat.format(str5, new Object[]{bomEnc, xmlGuessEnc, xmlEnc}), bomEnc, xmlGuessEnc, xmlEnc);
                } else if (xmlEnc == null || xmlEnc.equals(str) || xmlEnc.equals(bomEnc)) {
                    return bomEnc;
                } else {
                    throw new XmlStreamReaderException(MessageFormat.format(str5, new Object[]{bomEnc, xmlGuessEnc, xmlEnc}), bomEnc, xmlGuessEnc, xmlEnc);
                }
            } else if (!bomEnc.equals(UTF_32BE) && !bomEnc.equals(UTF_32LE)) {
                throw new XmlStreamReaderException(MessageFormat.format(RAW_EX_2, new Object[]{bomEnc, xmlGuessEnc, xmlEnc}), bomEnc, xmlGuessEnc, xmlEnc);
            } else if (xmlGuessEnc != null && !xmlGuessEnc.equals(bomEnc)) {
                throw new XmlStreamReaderException(MessageFormat.format(str5, new Object[]{bomEnc, xmlGuessEnc, xmlEnc}), bomEnc, xmlGuessEnc, xmlEnc);
            } else if (xmlEnc == null || xmlEnc.equals(UTF_32) || xmlEnc.equals(bomEnc)) {
                return bomEnc;
            } else {
                throw new XmlStreamReaderException(MessageFormat.format(str5, new Object[]{bomEnc, xmlGuessEnc, xmlEnc}), bomEnc, xmlGuessEnc, xmlEnc);
            }
        } else if (xmlGuessEnc == null || xmlEnc == null) {
            String str6 = this.defaultEncoding;
            if (str6 != null) {
                str4 = str6;
            }
            return str4;
        } else if (!xmlEnc.equals(str) || (!xmlGuessEnc.equals(str3) && !xmlGuessEnc.equals(str2))) {
            return xmlEnc;
        } else {
            return xmlGuessEnc;
        }
    }

    /* access modifiers changed from: 0000 */
    public String calculateHttpEncoding(String httpContentType, String bomEnc, String xmlGuessEnc, String xmlEnc, boolean lenient) throws IOException {
        String str = bomEnc;
        String str2 = xmlGuessEnc;
        String str3 = xmlEnc;
        if (lenient && str3 != null) {
            return str3;
        }
        String cTMime = getContentTypeMime(httpContentType);
        String cTEnc = getContentTypeEncoding(httpContentType);
        boolean appXml = isAppXml(cTMime);
        boolean textXml = isTextXml(cTMime);
        if (!appXml && !textXml) {
            XmlStreamReaderException xmlStreamReaderException = new XmlStreamReaderException(MessageFormat.format(HTTP_EX_3, new Object[]{cTMime, cTEnc, str, str2, str3}), cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
            throw xmlStreamReaderException;
        } else if (cTEnc != null) {
            boolean equals = cTEnc.equals(UTF_16BE);
            String str4 = HTTP_EX_1;
            if (!equals && !cTEnc.equals(UTF_16LE)) {
                String str5 = UTF_16;
                boolean equals2 = cTEnc.equals(str5);
                String str6 = HTTP_EX_2;
                if (equals2) {
                    if (str != null && str.startsWith(str5)) {
                        return str;
                    }
                    XmlStreamReaderException xmlStreamReaderException2 = new XmlStreamReaderException(MessageFormat.format(str6, new Object[]{cTMime, cTEnc, str, str2, str3}), cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
                    throw xmlStreamReaderException2;
                } else if (!cTEnc.equals(UTF_32BE) && !cTEnc.equals(UTF_32LE)) {
                    String str7 = UTF_32;
                    if (!cTEnc.equals(str7)) {
                        return cTEnc;
                    }
                    if (str != null && str.startsWith(str7)) {
                        return str;
                    }
                    XmlStreamReaderException xmlStreamReaderException3 = new XmlStreamReaderException(MessageFormat.format(str6, new Object[]{cTMime, cTEnc, str, str2, str3}), cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
                    throw xmlStreamReaderException3;
                } else if (str == null) {
                    return cTEnc;
                } else {
                    XmlStreamReaderException xmlStreamReaderException4 = new XmlStreamReaderException(MessageFormat.format(str4, new Object[]{cTMime, cTEnc, str, str2, str3}), cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
                    throw xmlStreamReaderException4;
                }
            } else if (str == null) {
                return cTEnc;
            } else {
                XmlStreamReaderException xmlStreamReaderException5 = new XmlStreamReaderException(MessageFormat.format(str4, new Object[]{cTMime, cTEnc, str, str2, str3}), cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
                throw xmlStreamReaderException5;
            }
        } else if (appXml) {
            return calculateRawEncoding(str, str2, str3);
        } else {
            String str8 = this.defaultEncoding;
            if (str8 == null) {
                str8 = US_ASCII;
            }
            return str8;
        }
    }

    static String getContentTypeMime(String httpContentType) {
        String mime;
        if (httpContentType == null) {
            return null;
        }
        int i = httpContentType.indexOf(";");
        if (i >= 0) {
            mime = httpContentType.substring(0, i);
        } else {
            mime = httpContentType;
        }
        return mime.trim();
    }

    static String getContentTypeEncoding(String httpContentType) {
        if (httpContentType == null) {
            return null;
        }
        int i = httpContentType.indexOf(";");
        if (i <= -1) {
            return null;
        }
        Matcher m = CHARSET_PATTERN.matcher(httpContentType.substring(i + 1));
        String str = null;
        String encoding2 = m.find() ? m.group(1) : null;
        if (encoding2 != null) {
            str = encoding2.toUpperCase(Locale.US);
        }
        return str;
    }

    private static String getXmlProlog(InputStream is, String guessedEnc) throws IOException {
        if (guessedEnc == null) {
            return null;
        }
        byte[] bytes = new byte[4096];
        is.mark(4096);
        int offset = 0;
        int max = 4096;
        int c = is.read(bytes, 0, 4096);
        int firstGT = -1;
        String xmlProlog = BuildConfig.FLAVOR;
        while (c != -1 && firstGT == -1 && offset < 4096) {
            offset += c;
            max -= c;
            c = is.read(bytes, offset, max);
            xmlProlog = new String(bytes, 0, offset, guessedEnc);
            firstGT = xmlProlog.indexOf(62);
        }
        if (firstGT == -1) {
            if (c == -1) {
                throw new IOException("Unexpected end of XML stream");
            }
            StringBuilder sb = new StringBuilder();
            sb.append("XML prolog or ROOT element not found on first ");
            sb.append(offset);
            sb.append(" bytes");
            throw new IOException(sb.toString());
        } else if (offset <= 0) {
            return null;
        } else {
            is.reset();
            BufferedReader bReader = new BufferedReader(new StringReader(xmlProlog.substring(0, firstGT + 1)));
            StringBuffer prolog = new StringBuffer();
            for (String line = bReader.readLine(); line != null; line = bReader.readLine()) {
                prolog.append(line);
            }
            Matcher m = ENCODING_PATTERN.matcher(prolog);
            if (!m.find()) {
                return null;
            }
            String encoding2 = m.group(1).toUpperCase();
            return encoding2.substring(1, encoding2.length() - 1);
        }
    }

    static boolean isAppXml(String mime) {
        return mime != null && (mime.equals("application/xml") || mime.equals("application/xml-dtd") || mime.equals("application/xml-external-parsed-entity") || (mime.startsWith("application/") && mime.endsWith("+xml")));
    }

    static boolean isTextXml(String mime) {
        return mime != null && (mime.equals("text/xml") || mime.equals("text/xml-external-parsed-entity") || (mime.startsWith("text/") && mime.endsWith("+xml")));
    }
}
