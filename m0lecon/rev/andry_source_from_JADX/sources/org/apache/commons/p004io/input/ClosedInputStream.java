package org.apache.commons.p004io.input;

import java.io.InputStream;

/* renamed from: org.apache.commons.io.input.ClosedInputStream */
public class ClosedInputStream extends InputStream {
    public static final ClosedInputStream CLOSED_INPUT_STREAM = new ClosedInputStream();

    public int read() {
        return -1;
    }
}
