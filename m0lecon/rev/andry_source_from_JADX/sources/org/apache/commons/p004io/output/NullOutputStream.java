package org.apache.commons.p004io.output;

import java.io.IOException;
import java.io.OutputStream;

/* renamed from: org.apache.commons.io.output.NullOutputStream */
public class NullOutputStream extends OutputStream {
    public static final NullOutputStream NULL_OUTPUT_STREAM = new NullOutputStream();

    public void write(byte[] b, int off, int len) {
    }

    public void write(int b) {
    }

    public void write(byte[] b) throws IOException {
    }
}
