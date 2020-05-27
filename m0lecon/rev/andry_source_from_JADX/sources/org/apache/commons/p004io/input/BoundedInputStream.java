package org.apache.commons.p004io.input;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: org.apache.commons.io.input.BoundedInputStream */
public class BoundedInputStream extends InputStream {

    /* renamed from: in */
    private final InputStream f38in;
    private long mark;
    private final long max;
    private long pos;
    private boolean propagateClose;

    public BoundedInputStream(InputStream in, long size) {
        this.pos = 0;
        this.mark = -1;
        this.propagateClose = true;
        this.max = size;
        this.f38in = in;
    }

    public BoundedInputStream(InputStream in) {
        this(in, -1);
    }

    public int read() throws IOException {
        long j = this.max;
        if (j >= 0 && this.pos >= j) {
            return -1;
        }
        int result = this.f38in.read();
        this.pos++;
        return result;
    }

    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    public int read(byte[] b, int off, int len) throws IOException {
        long j = this.max;
        if (j >= 0 && this.pos >= j) {
            return -1;
        }
        long j2 = this.max;
        int bytesRead = this.f38in.read(b, off, (int) (j2 >= 0 ? Math.min((long) len, j2 - this.pos) : (long) len));
        if (bytesRead == -1) {
            return -1;
        }
        this.pos += (long) bytesRead;
        return bytesRead;
    }

    public long skip(long n) throws IOException {
        long j = this.max;
        long skippedBytes = this.f38in.skip(j >= 0 ? Math.min(n, j - this.pos) : n);
        this.pos += skippedBytes;
        return skippedBytes;
    }

    public int available() throws IOException {
        long j = this.max;
        if (j < 0 || this.pos < j) {
            return this.f38in.available();
        }
        return 0;
    }

    public String toString() {
        return this.f38in.toString();
    }

    public void close() throws IOException {
        if (this.propagateClose) {
            this.f38in.close();
        }
    }

    public synchronized void reset() throws IOException {
        this.f38in.reset();
        this.pos = this.mark;
    }

    public synchronized void mark(int readlimit) {
        this.f38in.mark(readlimit);
        this.mark = this.pos;
    }

    public boolean markSupported() {
        return this.f38in.markSupported();
    }

    public boolean isPropagateClose() {
        return this.propagateClose;
    }

    public void setPropagateClose(boolean propagateClose2) {
        this.propagateClose = propagateClose2;
    }
}
