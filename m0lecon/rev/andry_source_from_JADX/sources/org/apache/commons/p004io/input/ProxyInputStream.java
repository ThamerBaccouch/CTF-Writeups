package org.apache.commons.p004io.input;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: org.apache.commons.io.input.ProxyInputStream */
public abstract class ProxyInputStream extends FilterInputStream {
    public ProxyInputStream(InputStream proxy) {
        super(proxy);
    }

    public int read() throws IOException {
        int i = 1;
        try {
            beforeRead(1);
            int b = this.in.read();
            if (b == -1) {
                i = -1;
            }
            afterRead(i);
            return b;
        } catch (IOException e) {
            handleIOException(e);
            return -1;
        }
    }

    public int read(byte[] bts) throws IOException {
        int i;
        if (bts != null) {
            try {
                i = bts.length;
            } catch (IOException e) {
                handleIOException(e);
                return -1;
            }
        } else {
            i = 0;
        }
        beforeRead(i);
        int n = this.in.read(bts);
        afterRead(n);
        return n;
    }

    public int read(byte[] bts, int off, int len) throws IOException {
        try {
            beforeRead(len);
            int n = this.in.read(bts, off, len);
            afterRead(n);
            return n;
        } catch (IOException e) {
            handleIOException(e);
            return -1;
        }
    }

    public long skip(long ln) throws IOException {
        try {
            return this.in.skip(ln);
        } catch (IOException e) {
            handleIOException(e);
            return 0;
        }
    }

    public int available() throws IOException {
        try {
            return super.available();
        } catch (IOException e) {
            handleIOException(e);
            return 0;
        }
    }

    public void close() throws IOException {
        try {
            this.in.close();
        } catch (IOException e) {
            handleIOException(e);
        }
    }

    public synchronized void mark(int readlimit) {
        this.in.mark(readlimit);
    }

    public synchronized void reset() throws IOException {
        try {
            this.in.reset();
        } catch (IOException e) {
            handleIOException(e);
        }
        return;
    }

    public boolean markSupported() {
        return this.in.markSupported();
    }

    /* access modifiers changed from: protected */
    public void beforeRead(int n) throws IOException {
    }

    /* access modifiers changed from: protected */
    public void afterRead(int n) throws IOException {
    }

    /* access modifiers changed from: protected */
    public void handleIOException(IOException e) throws IOException {
        throw e;
    }
}
