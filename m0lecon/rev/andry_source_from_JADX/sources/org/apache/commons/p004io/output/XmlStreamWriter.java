package org.apache.commons.p004io.output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.p004io.input.XmlStreamReader;

/* renamed from: org.apache.commons.io.output.XmlStreamWriter */
public class XmlStreamWriter extends Writer {
    private static final int BUFFER_SIZE = 4096;
    static final Pattern ENCODING_PATTERN = XmlStreamReader.ENCODING_PATTERN;
    private final String defaultEncoding;
    private String encoding;
    private final OutputStream out;
    private Writer writer;
    private StringWriter xmlPrologWriter;

    public XmlStreamWriter(OutputStream out2) {
        this(out2, (String) null);
    }

    public XmlStreamWriter(OutputStream out2, String defaultEncoding2) {
        this.xmlPrologWriter = new StringWriter(4096);
        this.out = out2;
        this.defaultEncoding = defaultEncoding2 != null ? defaultEncoding2 : "UTF-8";
    }

    public XmlStreamWriter(File file) throws FileNotFoundException {
        this(file, (String) null);
    }

    public XmlStreamWriter(File file, String defaultEncoding2) throws FileNotFoundException {
        this((OutputStream) new FileOutputStream(file), defaultEncoding2);
    }

    public String getEncoding() {
        return this.encoding;
    }

    public String getDefaultEncoding() {
        return this.defaultEncoding;
    }

    public void close() throws IOException {
        if (this.writer == null) {
            this.encoding = this.defaultEncoding;
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.out, this.encoding);
            this.writer = outputStreamWriter;
            outputStreamWriter.write(this.xmlPrologWriter.toString());
        }
        this.writer.close();
    }

    public void flush() throws IOException {
        Writer writer2 = this.writer;
        if (writer2 != null) {
            writer2.flush();
        }
    }

    private void detectEncoding(char[] cbuf, int off, int len) throws IOException {
        int size = len;
        StringBuffer xmlProlog = this.xmlPrologWriter.getBuffer();
        if (xmlProlog.length() + len > 4096) {
            size = 4096 - xmlProlog.length();
        }
        this.xmlPrologWriter.write(cbuf, off, size);
        if (xmlProlog.length() >= 5) {
            if (xmlProlog.substring(0, 5).equals("<?xml")) {
                int xmlPrologEnd = xmlProlog.indexOf("?>");
                if (xmlPrologEnd > 0) {
                    Matcher m = ENCODING_PATTERN.matcher(xmlProlog.substring(0, xmlPrologEnd));
                    if (m.find()) {
                        String upperCase = m.group(1).toUpperCase();
                        this.encoding = upperCase;
                        this.encoding = upperCase.substring(1, upperCase.length() - 1);
                    } else {
                        this.encoding = this.defaultEncoding;
                    }
                } else if (xmlProlog.length() >= 4096) {
                    this.encoding = this.defaultEncoding;
                }
            } else {
                this.encoding = this.defaultEncoding;
            }
            if (this.encoding != null) {
                this.xmlPrologWriter = null;
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.out, this.encoding);
                this.writer = outputStreamWriter;
                outputStreamWriter.write(xmlProlog.toString());
                if (len > size) {
                    this.writer.write(cbuf, off + size, len - size);
                }
            }
        }
    }

    public void write(char[] cbuf, int off, int len) throws IOException {
        if (this.xmlPrologWriter != null) {
            detectEncoding(cbuf, off, len);
        } else {
            this.writer.write(cbuf, off, len);
        }
    }
}
