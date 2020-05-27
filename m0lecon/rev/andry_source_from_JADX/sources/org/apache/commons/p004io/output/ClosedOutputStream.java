package org.apache.commons.p004io.output;

import java.io.IOException;
import java.io.OutputStream;

/* renamed from: org.apache.commons.io.output.ClosedOutputStream */
public class ClosedOutputStream extends OutputStream {
    public static final ClosedOutputStream CLOSED_OUTPUT_STREAM = new ClosedOutputStream();

    public void write(int b) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("write(");
        sb.append(b);
        sb.append(") failed: stream is closed");
        throw new IOException(sb.toString());
    }

    public void flush() throws IOException {
        throw new IOException("flush() failed: stream is closed");
    }
}
