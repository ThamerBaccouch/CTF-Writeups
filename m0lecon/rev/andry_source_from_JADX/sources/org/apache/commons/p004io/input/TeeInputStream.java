package org.apache.commons.p004io.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* renamed from: org.apache.commons.io.input.TeeInputStream */
public class TeeInputStream extends ProxyInputStream {
    private final OutputStream branch;
    private final boolean closeBranch;

    public TeeInputStream(InputStream input, OutputStream branch2) {
        this(input, branch2, false);
    }

    public TeeInputStream(InputStream input, OutputStream branch2, boolean closeBranch2) {
        super(input);
        this.branch = branch2;
        this.closeBranch = closeBranch2;
    }

    public void close() throws IOException {
        try {
            super.close();
        } finally {
            if (this.closeBranch) {
                this.branch.close();
            }
        }
    }

    public int read() throws IOException {
        int ch = super.read();
        if (ch != -1) {
            this.branch.write(ch);
        }
        return ch;
    }

    public int read(byte[] bts, int st, int end) throws IOException {
        int n = super.read(bts, st, end);
        if (n != -1) {
            this.branch.write(bts, st, n);
        }
        return n;
    }

    public int read(byte[] bts) throws IOException {
        int n = super.read(bts);
        if (n != -1) {
            this.branch.write(bts, 0, n);
        }
        return n;
    }
}
