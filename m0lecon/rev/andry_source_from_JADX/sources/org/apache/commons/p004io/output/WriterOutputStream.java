package org.apache.commons.p004io.output;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

/* renamed from: org.apache.commons.io.output.WriterOutputStream */
public class WriterOutputStream extends OutputStream {
    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private final CharsetDecoder decoder;
    private final ByteBuffer decoderIn;
    private final CharBuffer decoderOut;
    private final boolean writeImmediately;
    private final Writer writer;

    public WriterOutputStream(Writer writer2, CharsetDecoder decoder2) {
        this(writer2, decoder2, 1024, false);
    }

    public WriterOutputStream(Writer writer2, CharsetDecoder decoder2, int bufferSize, boolean writeImmediately2) {
        this.decoderIn = ByteBuffer.allocate(128);
        checkIbmJdkWithBrokenUTF16(decoder2.charset());
        this.writer = writer2;
        this.decoder = decoder2;
        this.writeImmediately = writeImmediately2;
        this.decoderOut = CharBuffer.allocate(bufferSize);
    }

    public WriterOutputStream(Writer writer2, Charset charset, int bufferSize, boolean writeImmediately2) {
        this(writer2, charset.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE).replaceWith("?"), bufferSize, writeImmediately2);
    }

    public WriterOutputStream(Writer writer2, Charset charset) {
        this(writer2, charset, 1024, false);
    }

    public WriterOutputStream(Writer writer2, String charsetName, int bufferSize, boolean writeImmediately2) {
        this(writer2, Charset.forName(charsetName), bufferSize, writeImmediately2);
    }

    public WriterOutputStream(Writer writer2, String charsetName) {
        this(writer2, charsetName, 1024, false);
    }

    @Deprecated
    public WriterOutputStream(Writer writer2) {
        this(writer2, Charset.defaultCharset(), 1024, false);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        while (len > 0) {
            int c = Math.min(len, this.decoderIn.remaining());
            this.decoderIn.put(b, off, c);
            processInput(false);
            len -= c;
            off += c;
        }
        if (this.writeImmediately) {
            flushOutput();
        }
    }

    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    public void write(int b) throws IOException {
        write(new byte[]{(byte) b}, 0, 1);
    }

    public void flush() throws IOException {
        flushOutput();
        this.writer.flush();
    }

    public void close() throws IOException {
        processInput(true);
        flushOutput();
        this.writer.close();
    }

    private void processInput(boolean endOfInput) throws IOException {
        CoderResult coderResult;
        this.decoderIn.flip();
        while (true) {
            coderResult = this.decoder.decode(this.decoderIn, this.decoderOut, endOfInput);
            if (!coderResult.isOverflow()) {
                break;
            }
            flushOutput();
        }
        if (coderResult.isUnderflow()) {
            this.decoderIn.compact();
            return;
        }
        throw new IOException("Unexpected coder result");
    }

    private void flushOutput() throws IOException {
        if (this.decoderOut.position() > 0) {
            this.writer.write(this.decoderOut.array(), 0, this.decoderOut.position());
            this.decoderOut.rewind();
        }
    }

    private static void checkIbmJdkWithBrokenUTF16(Charset charset) {
        String str;
        if ("UTF-16".equals(charset.name())) {
            String str2 = "vés";
            String str3 = "vés";
            byte[] bytes = str3.getBytes(charset);
            CharsetDecoder charsetDecoder2 = charset.newDecoder();
            ByteBuffer bb2 = ByteBuffer.allocate(16);
            CharBuffer cb2 = CharBuffer.allocate(str3.length());
            int len = bytes.length;
            int i = 0;
            while (true) {
                str = "UTF-16 requested when runninng on an IBM JDK with broken UTF-16 support. Please find a JDK that supports UTF-16 if you intend to use UF-16 with WriterOutputStream";
                if (i >= len) {
                    break;
                }
                bb2.put(bytes[i]);
                bb2.flip();
                try {
                    charsetDecoder2.decode(bb2, cb2, i == len + -1);
                    bb2.compact();
                    i++;
                } catch (IllegalArgumentException e) {
                    throw new UnsupportedOperationException(str);
                }
            }
            cb2.rewind();
            if (!str3.equals(cb2.toString())) {
                throw new UnsupportedOperationException(str);
            }
        }
    }
}
