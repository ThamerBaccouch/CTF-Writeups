package org.apache.commons.p004io.output;

import java.io.IOException;
import java.io.OutputStream;

/* renamed from: org.apache.commons.io.output.BrokenOutputStream */
public class BrokenOutputStream extends OutputStream {
    private final IOException exception;

    public BrokenOutputStream(IOException exception2) {
        this.exception = exception2;
    }

    public BrokenOutputStream() {
        this(new IOException("Broken output stream"));
    }

    public void write(int b) throws IOException {
        throw this.exception;
    }

    public void flush() throws IOException {
        throw this.exception;
    }

    public void close() throws IOException {
        throw this.exception;
    }
}
