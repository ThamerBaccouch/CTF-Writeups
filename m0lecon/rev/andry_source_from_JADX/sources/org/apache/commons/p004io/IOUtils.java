package org.apache.commons.p004io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.p004io.output.ByteArrayOutputStream;

/* renamed from: org.apache.commons.io.IOUtils */
public class IOUtils {
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    public static final char DIR_SEPARATOR = File.separatorChar;
    public static final char DIR_SEPARATOR_UNIX = '/';
    public static final char DIR_SEPARATOR_WINDOWS = '\\';
    public static final int EOF = -1;
    public static final String LINE_SEPARATOR;
    public static final String LINE_SEPARATOR_UNIX = "\n";
    public static final String LINE_SEPARATOR_WINDOWS = "\r\n";
    private static final int SKIP_BUFFER_SIZE = 2048;
    private static byte[] SKIP_BYTE_BUFFER;
    private static char[] SKIP_CHAR_BUFFER;

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0022, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0027, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r2.addSuppressed(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002b, code lost:
        throw r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x002e, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0033, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0034, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0037, code lost:
        throw r2;
     */
    static {
        /*
            char r0 = java.io.File.separatorChar
            DIR_SEPARATOR = r0
            org.apache.commons.io.output.StringBuilderWriter r0 = new org.apache.commons.io.output.StringBuilderWriter
            r1 = 4
            r0.<init>(r1)
            java.io.PrintWriter r1 = new java.io.PrintWriter     // Catch:{ all -> 0x002c }
            r1.<init>(r0)     // Catch:{ all -> 0x002c }
            r1.println()     // Catch:{ all -> 0x0020 }
            java.lang.String r2 = r0.toString()     // Catch:{ all -> 0x0020 }
            LINE_SEPARATOR = r2     // Catch:{ all -> 0x0020 }
            r1.close()     // Catch:{ all -> 0x002c }
            r0.close()
            return
        L_0x0020:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0022 }
        L_0x0022:
            r3 = move-exception
            r1.close()     // Catch:{ all -> 0x0027 }
            goto L_0x002b
        L_0x0027:
            r4 = move-exception
            r2.addSuppressed(r4)     // Catch:{ all -> 0x002c }
        L_0x002b:
            throw r3     // Catch:{ all -> 0x002c }
        L_0x002c:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x002e }
        L_0x002e:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x0033 }
            goto L_0x0037
        L_0x0033:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x0037:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p004io.IOUtils.<clinit>():void");
    }

    public static void close(URLConnection conn) {
        if (conn instanceof HttpURLConnection) {
            ((HttpURLConnection) conn).disconnect();
        }
    }

    @Deprecated
    public static void closeQuietly(Reader input) {
        closeQuietly((Closeable) input);
    }

    @Deprecated
    public static void closeQuietly(Writer output) {
        closeQuietly((Closeable) output);
    }

    @Deprecated
    public static void closeQuietly(InputStream input) {
        closeQuietly((Closeable) input);
    }

    @Deprecated
    public static void closeQuietly(OutputStream output) {
        closeQuietly((Closeable) output);
    }

    @Deprecated
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    @Deprecated
    public static void closeQuietly(Closeable... closeables) {
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                closeQuietly(closeable);
            }
        }
    }

    @Deprecated
    public static void closeQuietly(Socket sock) {
        if (sock != null) {
            try {
                sock.close();
            } catch (IOException e) {
            }
        }
    }

    @Deprecated
    public static void closeQuietly(Selector selector) {
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
            }
        }
    }

    @Deprecated
    public static void closeQuietly(ServerSocket sock) {
        if (sock != null) {
            try {
                sock.close();
            } catch (IOException e) {
            }
        }
    }

    public static InputStream toBufferedInputStream(InputStream input) throws IOException {
        return ByteArrayOutputStream.toBufferedInputStream(input);
    }

    public static InputStream toBufferedInputStream(InputStream input, int size) throws IOException {
        return ByteArrayOutputStream.toBufferedInputStream(input, size);
    }

    public static BufferedReader toBufferedReader(Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }

    public static BufferedReader toBufferedReader(Reader reader, int size) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader, size);
    }

    public static BufferedReader buffer(Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }

    public static BufferedReader buffer(Reader reader, int size) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader, size);
    }

    public static BufferedWriter buffer(Writer writer) {
        return writer instanceof BufferedWriter ? (BufferedWriter) writer : new BufferedWriter(writer);
    }

    public static BufferedWriter buffer(Writer writer, int size) {
        return writer instanceof BufferedWriter ? (BufferedWriter) writer : new BufferedWriter(writer, size);
    }

    public static BufferedOutputStream buffer(OutputStream outputStream) {
        if (outputStream != null) {
            return outputStream instanceof BufferedOutputStream ? (BufferedOutputStream) outputStream : new BufferedOutputStream(outputStream);
        }
        throw null;
    }

    public static BufferedOutputStream buffer(OutputStream outputStream, int size) {
        if (outputStream != null) {
            return outputStream instanceof BufferedOutputStream ? (BufferedOutputStream) outputStream : new BufferedOutputStream(outputStream, size);
        }
        throw null;
    }

    public static BufferedInputStream buffer(InputStream inputStream) {
        if (inputStream != null) {
            return inputStream instanceof BufferedInputStream ? (BufferedInputStream) inputStream : new BufferedInputStream(inputStream);
        }
        throw null;
    }

    public static BufferedInputStream buffer(InputStream inputStream, int size) {
        if (inputStream != null) {
            return inputStream instanceof BufferedInputStream ? (BufferedInputStream) inputStream : new BufferedInputStream(inputStream, size);
        }
        throw null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0017, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0018, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001b, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0012, code lost:
        r2 = move-exception;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] toByteArray(java.io.InputStream r4) throws java.io.IOException {
        /*
            org.apache.commons.io.output.ByteArrayOutputStream r0 = new org.apache.commons.io.output.ByteArrayOutputStream
            r0.<init>()
            copy(r4, r0)     // Catch:{ all -> 0x0010 }
            byte[] r1 = r0.toByteArray()     // Catch:{ all -> 0x0010 }
            r0.close()
            return r1
        L_0x0010:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0012 }
        L_0x0012:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x0017 }
            goto L_0x001b
        L_0x0017:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x001b:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p004io.IOUtils.toByteArray(java.io.InputStream):byte[]");
    }

    public static byte[] toByteArray(InputStream input, long size) throws IOException {
        if (size <= 2147483647L) {
            return toByteArray(input, (int) size);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Size cannot be greater than Integer max value: ");
        sb.append(size);
        throw new IllegalArgumentException(sb.toString());
    }

    public static byte[] toByteArray(InputStream input, int size) throws IOException {
        if (size < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Size must be equal or greater than zero: ");
            sb.append(size);
            throw new IllegalArgumentException(sb.toString());
        } else if (size == 0) {
            return new byte[0];
        } else {
            byte[] data = new byte[size];
            int offset = 0;
            while (offset < size) {
                int read = input.read(data, offset, size - offset);
                int read2 = read;
                if (read == -1) {
                    break;
                }
                offset += read2;
            }
            if (offset == size) {
                return data;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Unexpected read size. current: ");
            sb2.append(offset);
            sb2.append(", expected: ");
            sb2.append(size);
            throw new IOException(sb2.toString());
        }
    }

    @Deprecated
    public static byte[] toByteArray(Reader input) throws IOException {
        return toByteArray(input, Charset.defaultCharset());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0017, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0018, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001b, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0012, code lost:
        r2 = move-exception;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] toByteArray(java.io.Reader r4, java.nio.charset.Charset r5) throws java.io.IOException {
        /*
            org.apache.commons.io.output.ByteArrayOutputStream r0 = new org.apache.commons.io.output.ByteArrayOutputStream
            r0.<init>()
            copy(r4, r0, r5)     // Catch:{ all -> 0x0010 }
            byte[] r1 = r0.toByteArray()     // Catch:{ all -> 0x0010 }
            r0.close()
            return r1
        L_0x0010:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0012 }
        L_0x0012:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x0017 }
            goto L_0x001b
        L_0x0017:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x001b:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p004io.IOUtils.toByteArray(java.io.Reader, java.nio.charset.Charset):byte[]");
    }

    public static byte[] toByteArray(Reader input, String encoding) throws IOException {
        return toByteArray(input, Charsets.toCharset(encoding));
    }

    @Deprecated
    public static byte[] toByteArray(String input) throws IOException {
        return input.getBytes(Charset.defaultCharset());
    }

    public static byte[] toByteArray(URI uri) throws IOException {
        return toByteArray(uri.toURL());
    }

    public static byte[] toByteArray(URL url) throws IOException {
        URLConnection conn = url.openConnection();
        try {
            return toByteArray(conn);
        } finally {
            close(conn);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0011, code lost:
        if (r0 != null) goto L_0x0013;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0017, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0018, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x001b, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0010, code lost:
        r2 = move-exception;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] toByteArray(java.net.URLConnection r4) throws java.io.IOException {
        /*
            java.io.InputStream r0 = r4.getInputStream()
            byte[] r1 = toByteArray(r0)     // Catch:{ all -> 0x000e }
            if (r0 == 0) goto L_0x000d
            r0.close()
        L_0x000d:
            return r1
        L_0x000e:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0010 }
        L_0x0010:
            r2 = move-exception
            if (r0 == 0) goto L_0x001b
            r0.close()     // Catch:{ all -> 0x0017 }
            goto L_0x001b
        L_0x0017:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x001b:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p004io.IOUtils.toByteArray(java.net.URLConnection):byte[]");
    }

    @Deprecated
    public static char[] toCharArray(InputStream is) throws IOException {
        return toCharArray(is, Charset.defaultCharset());
    }

    public static char[] toCharArray(InputStream is, Charset encoding) throws IOException {
        CharArrayWriter output = new CharArrayWriter();
        copy(is, (Writer) output, encoding);
        return output.toCharArray();
    }

    public static char[] toCharArray(InputStream is, String encoding) throws IOException {
        return toCharArray(is, Charsets.toCharset(encoding));
    }

    public static char[] toCharArray(Reader input) throws IOException {
        CharArrayWriter sw = new CharArrayWriter();
        copy(input, (Writer) sw);
        return sw.toCharArray();
    }

    @Deprecated
    public static String toString(InputStream input) throws IOException {
        return toString(input, Charset.defaultCharset());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0017, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0018, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001b, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0012, code lost:
        r2 = move-exception;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String toString(java.io.InputStream r4, java.nio.charset.Charset r5) throws java.io.IOException {
        /*
            org.apache.commons.io.output.StringBuilderWriter r0 = new org.apache.commons.io.output.StringBuilderWriter
            r0.<init>()
            copy(r4, r0, r5)     // Catch:{ all -> 0x0010 }
            java.lang.String r1 = r0.toString()     // Catch:{ all -> 0x0010 }
            r0.close()
            return r1
        L_0x0010:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0012 }
        L_0x0012:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x0017 }
            goto L_0x001b
        L_0x0017:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x001b:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p004io.IOUtils.toString(java.io.InputStream, java.nio.charset.Charset):java.lang.String");
    }

    public static String toString(InputStream input, String encoding) throws IOException {
        return toString(input, Charsets.toCharset(encoding));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0017, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0018, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001b, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0012, code lost:
        r2 = move-exception;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String toString(java.io.Reader r4) throws java.io.IOException {
        /*
            org.apache.commons.io.output.StringBuilderWriter r0 = new org.apache.commons.io.output.StringBuilderWriter
            r0.<init>()
            copy(r4, r0)     // Catch:{ all -> 0x0010 }
            java.lang.String r1 = r0.toString()     // Catch:{ all -> 0x0010 }
            r0.close()
            return r1
        L_0x0010:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0012 }
        L_0x0012:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x0017 }
            goto L_0x001b
        L_0x0017:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x001b:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p004io.IOUtils.toString(java.io.Reader):java.lang.String");
    }

    @Deprecated
    public static String toString(URI uri) throws IOException {
        return toString(uri, Charset.defaultCharset());
    }

    public static String toString(URI uri, Charset encoding) throws IOException {
        return toString(uri.toURL(), Charsets.toCharset(encoding));
    }

    public static String toString(URI uri, String encoding) throws IOException {
        return toString(uri, Charsets.toCharset(encoding));
    }

    @Deprecated
    public static String toString(URL url) throws IOException {
        return toString(url, Charset.defaultCharset());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0011, code lost:
        if (r0 != null) goto L_0x0013;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0017, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0018, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x001b, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0010, code lost:
        r2 = move-exception;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String toString(java.net.URL r4, java.nio.charset.Charset r5) throws java.io.IOException {
        /*
            java.io.InputStream r0 = r4.openStream()
            java.lang.String r1 = toString(r0, r5)     // Catch:{ all -> 0x000e }
            if (r0 == 0) goto L_0x000d
            r0.close()
        L_0x000d:
            return r1
        L_0x000e:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0010 }
        L_0x0010:
            r2 = move-exception
            if (r0 == 0) goto L_0x001b
            r0.close()     // Catch:{ all -> 0x0017 }
            goto L_0x001b
        L_0x0017:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x001b:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p004io.IOUtils.toString(java.net.URL, java.nio.charset.Charset):java.lang.String");
    }

    public static String toString(URL url, String encoding) throws IOException {
        return toString(url, Charsets.toCharset(encoding));
    }

    @Deprecated
    public static String toString(byte[] input) throws IOException {
        return new String(input, Charset.defaultCharset());
    }

    public static String toString(byte[] input, String encoding) throws IOException {
        return new String(input, Charsets.toCharset(encoding));
    }

    public static String resourceToString(String name, Charset encoding) throws IOException {
        return resourceToString(name, encoding, null);
    }

    public static String resourceToString(String name, Charset encoding, ClassLoader classLoader) throws IOException {
        return toString(resourceToURL(name, classLoader), encoding);
    }

    public static byte[] resourceToByteArray(String name) throws IOException {
        return resourceToByteArray(name, null);
    }

    public static byte[] resourceToByteArray(String name, ClassLoader classLoader) throws IOException {
        return toByteArray(resourceToURL(name, classLoader));
    }

    public static URL resourceToURL(String name) throws IOException {
        return resourceToURL(name, null);
    }

    public static URL resourceToURL(String name, ClassLoader classLoader) throws IOException {
        URL resource = classLoader == null ? IOUtils.class.getResource(name) : classLoader.getResource(name);
        if (resource != null) {
            return resource;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Resource not found: ");
        sb.append(name);
        throw new IOException(sb.toString());
    }

    @Deprecated
    public static List<String> readLines(InputStream input) throws IOException {
        return readLines(input, Charset.defaultCharset());
    }

    public static List<String> readLines(InputStream input, Charset encoding) throws IOException {
        return readLines((Reader) new InputStreamReader(input, Charsets.toCharset(encoding)));
    }

    public static List<String> readLines(InputStream input, String encoding) throws IOException {
        return readLines(input, Charsets.toCharset(encoding));
    }

    public static List<String> readLines(Reader input) throws IOException {
        BufferedReader reader = toBufferedReader(input);
        List<String> list = new ArrayList<>();
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            list.add(line);
        }
        return list;
    }

    public static LineIterator lineIterator(Reader reader) {
        return new LineIterator(reader);
    }

    public static LineIterator lineIterator(InputStream input, Charset encoding) throws IOException {
        return new LineIterator(new InputStreamReader(input, Charsets.toCharset(encoding)));
    }

    public static LineIterator lineIterator(InputStream input, String encoding) throws IOException {
        return lineIterator(input, Charsets.toCharset(encoding));
    }

    @Deprecated
    public static InputStream toInputStream(CharSequence input) {
        return toInputStream(input, Charset.defaultCharset());
    }

    public static InputStream toInputStream(CharSequence input, Charset encoding) {
        return toInputStream(input.toString(), encoding);
    }

    public static InputStream toInputStream(CharSequence input, String encoding) throws IOException {
        return toInputStream(input, Charsets.toCharset(encoding));
    }

    @Deprecated
    public static InputStream toInputStream(String input) {
        return toInputStream(input, Charset.defaultCharset());
    }

    public static InputStream toInputStream(String input, Charset encoding) {
        return new ByteArrayInputStream(input.getBytes(Charsets.toCharset(encoding)));
    }

    public static InputStream toInputStream(String input, String encoding) throws IOException {
        return new ByteArrayInputStream(input.getBytes(Charsets.toCharset(encoding)));
    }

    public static void write(byte[] data, OutputStream output) throws IOException {
        if (data != null) {
            output.write(data);
        }
    }

    public static void writeChunked(byte[] data, OutputStream output) throws IOException {
        if (data != null) {
            int bytes = data.length;
            int offset = 0;
            while (bytes > 0) {
                int chunk = Math.min(bytes, 4096);
                output.write(data, offset, chunk);
                bytes -= chunk;
                offset += chunk;
            }
        }
    }

    @Deprecated
    public static void write(byte[] data, Writer output) throws IOException {
        write(data, output, Charset.defaultCharset());
    }

    public static void write(byte[] data, Writer output, Charset encoding) throws IOException {
        if (data != null) {
            output.write(new String(data, Charsets.toCharset(encoding)));
        }
    }

    public static void write(byte[] data, Writer output, String encoding) throws IOException {
        write(data, output, Charsets.toCharset(encoding));
    }

    public static void write(char[] data, Writer output) throws IOException {
        if (data != null) {
            output.write(data);
        }
    }

    public static void writeChunked(char[] data, Writer output) throws IOException {
        if (data != null) {
            int bytes = data.length;
            int offset = 0;
            while (bytes > 0) {
                int chunk = Math.min(bytes, 4096);
                output.write(data, offset, chunk);
                bytes -= chunk;
                offset += chunk;
            }
        }
    }

    @Deprecated
    public static void write(char[] data, OutputStream output) throws IOException {
        write(data, output, Charset.defaultCharset());
    }

    public static void write(char[] data, OutputStream output, Charset encoding) throws IOException {
        if (data != null) {
            output.write(new String(data).getBytes(Charsets.toCharset(encoding)));
        }
    }

    public static void write(char[] data, OutputStream output, String encoding) throws IOException {
        write(data, output, Charsets.toCharset(encoding));
    }

    public static void write(CharSequence data, Writer output) throws IOException {
        if (data != null) {
            write(data.toString(), output);
        }
    }

    @Deprecated
    public static void write(CharSequence data, OutputStream output) throws IOException {
        write(data, output, Charset.defaultCharset());
    }

    public static void write(CharSequence data, OutputStream output, Charset encoding) throws IOException {
        if (data != null) {
            write(data.toString(), output, encoding);
        }
    }

    public static void write(CharSequence data, OutputStream output, String encoding) throws IOException {
        write(data, output, Charsets.toCharset(encoding));
    }

    public static void write(String data, Writer output) throws IOException {
        if (data != null) {
            output.write(data);
        }
    }

    @Deprecated
    public static void write(String data, OutputStream output) throws IOException {
        write(data, output, Charset.defaultCharset());
    }

    public static void write(String data, OutputStream output, Charset encoding) throws IOException {
        if (data != null) {
            output.write(data.getBytes(Charsets.toCharset(encoding)));
        }
    }

    public static void write(String data, OutputStream output, String encoding) throws IOException {
        write(data, output, Charsets.toCharset(encoding));
    }

    @Deprecated
    public static void write(StringBuffer data, Writer output) throws IOException {
        if (data != null) {
            output.write(data.toString());
        }
    }

    @Deprecated
    public static void write(StringBuffer data, OutputStream output) throws IOException {
        write(data, output, (String) null);
    }

    @Deprecated
    public static void write(StringBuffer data, OutputStream output, String encoding) throws IOException {
        if (data != null) {
            output.write(data.toString().getBytes(Charsets.toCharset(encoding)));
        }
    }

    @Deprecated
    public static void writeLines(Collection<?> lines, String lineEnding, OutputStream output) throws IOException {
        writeLines(lines, lineEnding, output, Charset.defaultCharset());
    }

    public static void writeLines(Collection<?> lines, String lineEnding, OutputStream output, Charset encoding) throws IOException {
        if (lines != null) {
            if (lineEnding == null) {
                lineEnding = LINE_SEPARATOR;
            }
            Charset cs = Charsets.toCharset(encoding);
            for (Object line : lines) {
                if (line != null) {
                    output.write(line.toString().getBytes(cs));
                }
                output.write(lineEnding.getBytes(cs));
            }
        }
    }

    public static void writeLines(Collection<?> lines, String lineEnding, OutputStream output, String encoding) throws IOException {
        writeLines(lines, lineEnding, output, Charsets.toCharset(encoding));
    }

    public static void writeLines(Collection<?> lines, String lineEnding, Writer writer) throws IOException {
        if (lines != null) {
            if (lineEnding == null) {
                lineEnding = LINE_SEPARATOR;
            }
            for (Object line : lines) {
                if (line != null) {
                    writer.write(line.toString());
                }
                writer.write(lineEnding);
            }
        }
    }

    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        if (count > 2147483647L) {
            return -1;
        }
        return (int) count;
    }

    public static long copy(InputStream input, OutputStream output, int bufferSize) throws IOException {
        return copyLarge(input, output, new byte[bufferSize]);
    }

    public static long copyLarge(InputStream input, OutputStream output) throws IOException {
        return copy(input, output, 4096);
    }

    public static long copyLarge(InputStream input, OutputStream output, byte[] buffer) throws IOException {
        long count = 0;
        while (true) {
            int read = input.read(buffer);
            int n = read;
            if (-1 == read) {
                return count;
            }
            output.write(buffer, 0, n);
            count += (long) n;
        }
    }

    public static long copyLarge(InputStream input, OutputStream output, long inputOffset, long length) throws IOException {
        return copyLarge(input, output, inputOffset, length, new byte[4096]);
    }

    public static long copyLarge(InputStream input, OutputStream output, long inputOffset, long length, byte[] buffer) throws IOException {
        InputStream inputStream = input;
        long j = inputOffset;
        long j2 = length;
        byte[] bArr = buffer;
        long j3 = 0;
        if (j > 0) {
            skipFully(inputStream, j);
        }
        if (j2 == 0) {
            return 0;
        }
        int bufferLength = bArr.length;
        int bytesToRead = bufferLength;
        if (j2 > 0 && j2 < ((long) bufferLength)) {
            bytesToRead = (int) j2;
        }
        long totalRead = 0;
        while (true) {
            if (bytesToRead <= 0) {
                OutputStream outputStream = output;
                break;
            }
            int read = inputStream.read(bArr, 0, bytesToRead);
            int read2 = read;
            if (-1 == read) {
                OutputStream outputStream2 = output;
                break;
            }
            output.write(bArr, 0, read2);
            totalRead += (long) read2;
            if (j2 > j3) {
                bytesToRead = (int) Math.min(j2 - totalRead, (long) bufferLength);
                j3 = 0;
            } else {
                j3 = 0;
            }
        }
        return totalRead;
    }

    @Deprecated
    public static void copy(InputStream input, Writer output) throws IOException {
        copy(input, output, Charset.defaultCharset());
    }

    public static void copy(InputStream input, Writer output, Charset inputEncoding) throws IOException {
        copy((Reader) new InputStreamReader(input, Charsets.toCharset(inputEncoding)), output);
    }

    public static void copy(InputStream input, Writer output, String inputEncoding) throws IOException {
        copy(input, output, Charsets.toCharset(inputEncoding));
    }

    public static int copy(Reader input, Writer output) throws IOException {
        long count = copyLarge(input, output);
        if (count > 2147483647L) {
            return -1;
        }
        return (int) count;
    }

    public static long copyLarge(Reader input, Writer output) throws IOException {
        return copyLarge(input, output, new char[4096]);
    }

    public static long copyLarge(Reader input, Writer output, char[] buffer) throws IOException {
        long count = 0;
        while (true) {
            int read = input.read(buffer);
            int n = read;
            if (-1 == read) {
                return count;
            }
            output.write(buffer, 0, n);
            count += (long) n;
        }
    }

    public static long copyLarge(Reader input, Writer output, long inputOffset, long length) throws IOException {
        return copyLarge(input, output, inputOffset, length, new char[4096]);
    }

    public static long copyLarge(Reader input, Writer output, long inputOffset, long length, char[] buffer) throws IOException {
        Reader reader = input;
        long j = inputOffset;
        long j2 = length;
        char[] cArr = buffer;
        long j3 = 0;
        if (j > 0) {
            skipFully(reader, j);
        }
        if (j2 == 0) {
            return 0;
        }
        int bytesToRead = cArr.length;
        if (j2 > 0 && j2 < ((long) cArr.length)) {
            bytesToRead = (int) j2;
        }
        long totalRead = 0;
        while (true) {
            if (bytesToRead <= 0) {
                Writer writer = output;
                break;
            }
            int read = reader.read(cArr, 0, bytesToRead);
            int read2 = read;
            if (-1 == read) {
                Writer writer2 = output;
                break;
            }
            output.write(cArr, 0, read2);
            totalRead += (long) read2;
            if (j2 > j3) {
                bytesToRead = (int) Math.min(j2 - totalRead, (long) cArr.length);
                j3 = 0;
            } else {
                j3 = 0;
            }
        }
        return totalRead;
    }

    @Deprecated
    public static void copy(Reader input, OutputStream output) throws IOException {
        copy(input, output, Charset.defaultCharset());
    }

    public static void copy(Reader input, OutputStream output, Charset outputEncoding) throws IOException {
        OutputStreamWriter out = new OutputStreamWriter(output, Charsets.toCharset(outputEncoding));
        copy(input, (Writer) out);
        out.flush();
    }

    public static void copy(Reader input, OutputStream output, String outputEncoding) throws IOException {
        copy(input, output, Charsets.toCharset(outputEncoding));
    }

    public static boolean contentEquals(InputStream input1, InputStream input2) throws IOException {
        boolean z = true;
        if (input1 == input2) {
            return true;
        }
        if (!(input1 instanceof BufferedInputStream)) {
            input1 = new BufferedInputStream(input1);
        }
        if (!(input2 instanceof BufferedInputStream)) {
            input2 = new BufferedInputStream(input2);
        }
        for (int ch = input1.read(); -1 != ch; ch = input1.read()) {
            if (ch != input2.read()) {
                return false;
            }
        }
        if (input2.read() != -1) {
            z = false;
        }
        return z;
    }

    public static boolean contentEquals(Reader input1, Reader input2) throws IOException {
        boolean z = true;
        if (input1 == input2) {
            return true;
        }
        Reader input12 = toBufferedReader(input1);
        Reader input22 = toBufferedReader(input2);
        for (int ch = input12.read(); -1 != ch; ch = input12.read()) {
            if (ch != input22.read()) {
                return false;
            }
        }
        if (input22.read() != -1) {
            z = false;
        }
        return z;
    }

    public static boolean contentEqualsIgnoreEOL(Reader input1, Reader input2) throws IOException {
        boolean z = true;
        if (input1 == input2) {
            return true;
        }
        BufferedReader br1 = toBufferedReader(input1);
        BufferedReader br2 = toBufferedReader(input2);
        String line1 = br1.readLine();
        String line2 = br2.readLine();
        while (line1 != null && line2 != null && line1.equals(line2)) {
            line1 = br1.readLine();
            line2 = br2.readLine();
        }
        if (line1 != null) {
            z = line1.equals(line2);
        } else if (line2 != null) {
            z = false;
        }
        return z;
    }

    public static long skip(InputStream input, long toSkip) throws IOException {
        if (toSkip >= 0) {
            if (SKIP_BYTE_BUFFER == null) {
                SKIP_BYTE_BUFFER = new byte[2048];
            }
            long remain = toSkip;
            while (remain > 0) {
                long n = (long) input.read(SKIP_BYTE_BUFFER, 0, (int) Math.min(remain, 2048));
                if (n < 0) {
                    break;
                }
                remain -= n;
            }
            return toSkip - remain;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Skip count must be non-negative, actual: ");
        sb.append(toSkip);
        throw new IllegalArgumentException(sb.toString());
    }

    public static long skip(ReadableByteChannel input, long toSkip) throws IOException {
        if (toSkip >= 0) {
            ByteBuffer skipByteBuffer = ByteBuffer.allocate((int) Math.min(toSkip, 2048));
            long remain = toSkip;
            while (remain > 0) {
                skipByteBuffer.position(0);
                skipByteBuffer.limit((int) Math.min(remain, 2048));
                int n = input.read(skipByteBuffer);
                if (n == -1) {
                    break;
                }
                remain -= (long) n;
            }
            return toSkip - remain;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Skip count must be non-negative, actual: ");
        sb.append(toSkip);
        throw new IllegalArgumentException(sb.toString());
    }

    public static long skip(Reader input, long toSkip) throws IOException {
        if (toSkip >= 0) {
            if (SKIP_CHAR_BUFFER == null) {
                SKIP_CHAR_BUFFER = new char[2048];
            }
            long remain = toSkip;
            while (remain > 0) {
                long n = (long) input.read(SKIP_CHAR_BUFFER, 0, (int) Math.min(remain, 2048));
                if (n < 0) {
                    break;
                }
                remain -= n;
            }
            return toSkip - remain;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Skip count must be non-negative, actual: ");
        sb.append(toSkip);
        throw new IllegalArgumentException(sb.toString());
    }

    public static void skipFully(InputStream input, long toSkip) throws IOException {
        if (toSkip >= 0) {
            long skipped = skip(input, toSkip);
            if (skipped != toSkip) {
                StringBuilder sb = new StringBuilder();
                sb.append("Bytes to skip: ");
                sb.append(toSkip);
                sb.append(" actual: ");
                sb.append(skipped);
                throw new EOFException(sb.toString());
            }
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Bytes to skip must not be negative: ");
        sb2.append(toSkip);
        throw new IllegalArgumentException(sb2.toString());
    }

    public static void skipFully(ReadableByteChannel input, long toSkip) throws IOException {
        if (toSkip >= 0) {
            long skipped = skip(input, toSkip);
            if (skipped != toSkip) {
                StringBuilder sb = new StringBuilder();
                sb.append("Bytes to skip: ");
                sb.append(toSkip);
                sb.append(" actual: ");
                sb.append(skipped);
                throw new EOFException(sb.toString());
            }
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Bytes to skip must not be negative: ");
        sb2.append(toSkip);
        throw new IllegalArgumentException(sb2.toString());
    }

    public static void skipFully(Reader input, long toSkip) throws IOException {
        long skipped = skip(input, toSkip);
        if (skipped != toSkip) {
            StringBuilder sb = new StringBuilder();
            sb.append("Chars to skip: ");
            sb.append(toSkip);
            sb.append(" actual: ");
            sb.append(skipped);
            throw new EOFException(sb.toString());
        }
    }

    public static int read(Reader input, char[] buffer, int offset, int length) throws IOException {
        if (length >= 0) {
            int remaining = length;
            while (remaining > 0) {
                int count = input.read(buffer, offset + (length - remaining), remaining);
                if (-1 == count) {
                    break;
                }
                remaining -= count;
            }
            return length - remaining;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Length must not be negative: ");
        sb.append(length);
        throw new IllegalArgumentException(sb.toString());
    }

    public static int read(Reader input, char[] buffer) throws IOException {
        return read(input, buffer, 0, buffer.length);
    }

    public static int read(InputStream input, byte[] buffer, int offset, int length) throws IOException {
        if (length >= 0) {
            int remaining = length;
            while (remaining > 0) {
                int count = input.read(buffer, offset + (length - remaining), remaining);
                if (-1 == count) {
                    break;
                }
                remaining -= count;
            }
            return length - remaining;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Length must not be negative: ");
        sb.append(length);
        throw new IllegalArgumentException(sb.toString());
    }

    public static int read(InputStream input, byte[] buffer) throws IOException {
        return read(input, buffer, 0, buffer.length);
    }

    public static int read(ReadableByteChannel input, ByteBuffer buffer) throws IOException {
        int length = buffer.remaining();
        while (buffer.remaining() > 0) {
            if (-1 == input.read(buffer)) {
                break;
            }
        }
        return length - buffer.remaining();
    }

    public static void readFully(Reader input, char[] buffer, int offset, int length) throws IOException {
        int actual = read(input, buffer, offset, length);
        if (actual != length) {
            StringBuilder sb = new StringBuilder();
            sb.append("Length to read: ");
            sb.append(length);
            sb.append(" actual: ");
            sb.append(actual);
            throw new EOFException(sb.toString());
        }
    }

    public static void readFully(Reader input, char[] buffer) throws IOException {
        readFully(input, buffer, 0, buffer.length);
    }

    public static void readFully(InputStream input, byte[] buffer, int offset, int length) throws IOException {
        int actual = read(input, buffer, offset, length);
        if (actual != length) {
            StringBuilder sb = new StringBuilder();
            sb.append("Length to read: ");
            sb.append(length);
            sb.append(" actual: ");
            sb.append(actual);
            throw new EOFException(sb.toString());
        }
    }

    public static void readFully(InputStream input, byte[] buffer) throws IOException {
        readFully(input, buffer, 0, buffer.length);
    }

    public static byte[] readFully(InputStream input, int length) throws IOException {
        byte[] buffer = new byte[length];
        readFully(input, buffer, 0, buffer.length);
        return buffer;
    }

    public static void readFully(ReadableByteChannel input, ByteBuffer buffer) throws IOException {
        int expected = buffer.remaining();
        int actual = read(input, buffer);
        if (actual != expected) {
            StringBuilder sb = new StringBuilder();
            sb.append("Length to read: ");
            sb.append(expected);
            sb.append(" actual: ");
            sb.append(actual);
            throw new EOFException(sb.toString());
        }
    }
}
