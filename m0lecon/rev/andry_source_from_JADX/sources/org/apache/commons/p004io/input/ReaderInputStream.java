package org.apache.commons.p004io.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

/* renamed from: org.apache.commons.io.input.ReaderInputStream */
public class ReaderInputStream extends InputStream {
    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private final CharsetEncoder encoder;
    private final CharBuffer encoderIn;
    private final ByteBuffer encoderOut;
    private boolean endOfInput;
    private CoderResult lastCoderResult;
    private final Reader reader;

    public ReaderInputStream(Reader reader2, CharsetEncoder encoder2) {
        this(reader2, encoder2, 1024);
    }

    public ReaderInputStream(Reader reader2, CharsetEncoder encoder2, int bufferSize) {
        this.reader = reader2;
        this.encoder = encoder2;
        CharBuffer allocate = CharBuffer.allocate(bufferSize);
        this.encoderIn = allocate;
        allocate.flip();
        ByteBuffer allocate2 = ByteBuffer.allocate(128);
        this.encoderOut = allocate2;
        allocate2.flip();
    }

    public ReaderInputStream(Reader reader2, Charset charset, int bufferSize) {
        this(reader2, charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE), bufferSize);
    }

    public ReaderInputStream(Reader reader2, Charset charset) {
        this(reader2, charset, 1024);
    }

    public ReaderInputStream(Reader reader2, String charsetName, int bufferSize) {
        this(reader2, Charset.forName(charsetName), bufferSize);
    }

    public ReaderInputStream(Reader reader2, String charsetName) {
        this(reader2, charsetName, 1024);
    }

    @Deprecated
    public ReaderInputStream(Reader reader2) {
        this(reader2, Charset.defaultCharset());
    }

    private void fillBuffer() throws IOException {
        if (!this.endOfInput) {
            CoderResult coderResult = this.lastCoderResult;
            if (coderResult == null || coderResult.isUnderflow()) {
                this.encoderIn.compact();
                int position = this.encoderIn.position();
                int c = this.reader.read(this.encoderIn.array(), position, this.encoderIn.remaining());
                if (c == -1) {
                    this.endOfInput = true;
                } else {
                    this.encoderIn.position(position + c);
                }
                this.encoderIn.flip();
            }
        }
        this.encoderOut.compact();
        this.lastCoderResult = this.encoder.encode(this.encoderIn, this.encoderOut, this.endOfInput);
        this.encoderOut.flip();
    }

    public int read(byte[] b, int off, int len) throws IOException {
        if (b == null) {
            throw new NullPointerException("Byte array must not be null");
        } else if (len < 0 || off < 0 || off + len > b.length) {
            StringBuilder sb = new StringBuilder();
            sb.append("Array Size=");
            sb.append(b.length);
            sb.append(", offset=");
            sb.append(off);
            sb.append(", length=");
            sb.append(len);
            throw new IndexOutOfBoundsException(sb.toString());
        } else {
            int read = 0;
            if (len == 0) {
                return 0;
            }
            while (len > 0) {
                if (!this.encoderOut.hasRemaining()) {
                    fillBuffer();
                    if (this.endOfInput && !this.encoderOut.hasRemaining()) {
                        break;
                    }
                } else {
                    int c = Math.min(this.encoderOut.remaining(), len);
                    this.encoderOut.get(b, off, c);
                    off += c;
                    len -= c;
                    read += c;
                }
            }
            return (read != 0 || !this.endOfInput) ? read : -1;
        }
    }

    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    public int read() throws IOException {
        while (!this.encoderOut.hasRemaining()) {
            fillBuffer();
            if (this.endOfInput && !this.encoderOut.hasRemaining()) {
                return -1;
            }
        }
        return this.encoderOut.get() & 255;
    }

    public void close() throws IOException {
        this.reader.close();
    }
}
