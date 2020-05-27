package org.apache.commons.p004io.input;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: org.apache.commons.io.input.BrokenInputStream */
public class BrokenInputStream extends InputStream {
    private final IOException exception;

    public BrokenInputStream(IOException exception2) {
        this.exception = exception2;
    }

    public BrokenInputStream() {
        this(new IOException("Broken input stream"));
    }

    public int read() throws IOException {
        throw this.exception;
    }

    public int available() throws IOException {
        throw this.exception;
    }

    public long skip(long n) throws IOException {
        throw this.exception;
    }

    public synchronized void reset() throws IOException {
        throw this.exception;
    }

    public void close() throws IOException {
        throw this.exception;
    }
}
