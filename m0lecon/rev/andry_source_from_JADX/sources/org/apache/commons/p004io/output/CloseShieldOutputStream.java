package org.apache.commons.p004io.output;

import java.io.OutputStream;

/* renamed from: org.apache.commons.io.output.CloseShieldOutputStream */
public class CloseShieldOutputStream extends ProxyOutputStream {
    public CloseShieldOutputStream(OutputStream out) {
        super(out);
    }

    public void close() {
        this.out = new ClosedOutputStream();
    }
}
