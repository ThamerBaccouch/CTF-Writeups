package org.apache.commons.p004io.input;

import java.io.InputStream;

/* renamed from: org.apache.commons.io.input.CloseShieldInputStream */
public class CloseShieldInputStream extends ProxyInputStream {
    public CloseShieldInputStream(InputStream in) {
        super(in);
    }

    public void close() {
        this.in = new ClosedInputStream();
    }
}
