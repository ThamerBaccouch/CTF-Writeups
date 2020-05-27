package org.apache.commons.p004io.output;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* renamed from: org.apache.commons.io.output.ProxyOutputStream */
public class ProxyOutputStream extends FilterOutputStream {
    public ProxyOutputStream(OutputStream proxy) {
        super(proxy);
    }

    public void write(int idx) throws IOException {
        try {
            beforeWrite(1);
            this.out.write(idx);
            afterWrite(1);
        } catch (IOException e) {
            handleIOException(e);
        }
    }

    public void write(byte[] bts) throws IOException {
        int len;
        if (bts != null) {
            try {
                len = bts.length;
            } catch (IOException e) {
                handleIOException(e);
                return;
            }
        } else {
            len = 0;
        }
        beforeWrite(len);
        this.out.write(bts);
        afterWrite(len);
    }

    public void write(byte[] bts, int st, int end) throws IOException {
        try {
            beforeWrite(end);
            this.out.write(bts, st, end);
            afterWrite(end);
        } catch (IOException e) {
            handleIOException(e);
        }
    }

    public void flush() throws IOException {
        try {
            this.out.flush();
        } catch (IOException e) {
            handleIOException(e);
        }
    }

    public void close() throws IOException {
        try {
            this.out.close();
        } catch (IOException e) {
            handleIOException(e);
        }
    }

    /* access modifiers changed from: protected */
    public void beforeWrite(int n) throws IOException {
    }

    /* access modifiers changed from: protected */
    public void afterWrite(int n) throws IOException {
    }

    /* access modifiers changed from: protected */
    public void handleIOException(IOException e) throws IOException {
        throw e;
    }
}
