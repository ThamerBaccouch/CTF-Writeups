package org.apache.commons.p004io.input;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: org.apache.commons.io.input.NullInputStream */
public class NullInputStream extends InputStream {
    private boolean eof;
    private long mark;
    private final boolean markSupported;
    private long position;
    private long readlimit;
    private final long size;
    private final boolean throwEofException;

    public NullInputStream(long size2) {
        this(size2, true, false);
    }

    public NullInputStream(long size2, boolean markSupported2, boolean throwEofException2) {
        this.mark = -1;
        this.size = size2;
        this.markSupported = markSupported2;
        this.throwEofException = throwEofException2;
    }

    public long getPosition() {
        return this.position;
    }

    public long getSize() {
        return this.size;
    }

    public int available() {
        long avail = this.size - this.position;
        if (avail <= 0) {
            return 0;
        }
        if (avail > 2147483647L) {
            return ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        }
        return (int) avail;
    }

    public void close() throws IOException {
        this.eof = false;
        this.position = 0;
        this.mark = -1;
    }

    public synchronized void mark(int readlimit2) {
        if (this.markSupported) {
            this.mark = this.position;
            this.readlimit = (long) readlimit2;
        } else {
            throw new UnsupportedOperationException("Mark not supported");
        }
    }

    public boolean markSupported() {
        return this.markSupported;
    }

    public int read() throws IOException {
        if (!this.eof) {
            long j = this.position;
            if (j == this.size) {
                return doEndOfFile();
            }
            this.position = j + 1;
            return processByte();
        }
        throw new IOException("Read after end of file");
    }

    public int read(byte[] bytes) throws IOException {
        return read(bytes, 0, bytes.length);
    }

    public int read(byte[] bytes, int offset, int length) throws IOException {
        if (!this.eof) {
            long j = this.position;
            long j2 = this.size;
            if (j == j2) {
                return doEndOfFile();
            }
            long j3 = j + ((long) length);
            this.position = j3;
            int returnLength = length;
            if (j3 > j2) {
                returnLength = length - ((int) (j3 - j2));
                this.position = j2;
            }
            processBytes(bytes, offset, returnLength);
            return returnLength;
        }
        throw new IOException("Read after end of file");
    }

    public synchronized void reset() throws IOException {
        if (!this.markSupported) {
            throw new UnsupportedOperationException("Mark not supported");
        } else if (this.mark < 0) {
            throw new IOException("No position has been marked");
        } else if (this.position <= this.mark + this.readlimit) {
            this.position = this.mark;
            this.eof = false;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Marked position [");
            sb.append(this.mark);
            sb.append("] is no longer valid - passed the read limit [");
            sb.append(this.readlimit);
            sb.append("]");
            throw new IOException(sb.toString());
        }
    }

    public long skip(long numberOfBytes) throws IOException {
        if (!this.eof) {
            long j = this.position;
            long j2 = this.size;
            if (j == j2) {
                return (long) doEndOfFile();
            }
            long j3 = j + numberOfBytes;
            this.position = j3;
            long returnLength = numberOfBytes;
            if (j3 > j2) {
                returnLength = numberOfBytes - (j3 - j2);
                this.position = j2;
            }
            return returnLength;
        }
        throw new IOException("Skip after end of file");
    }

    /* access modifiers changed from: protected */
    public int processByte() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public void processBytes(byte[] bytes, int offset, int length) {
    }

    private int doEndOfFile() throws EOFException {
        this.eof = true;
        if (!this.throwEofException) {
            return -1;
        }
        throw new EOFException();
    }
}
