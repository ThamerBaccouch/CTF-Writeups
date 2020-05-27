package org.apache.commons.p004io.output;

import java.io.OutputStream;

/* renamed from: org.apache.commons.io.output.CountingOutputStream */
public class CountingOutputStream extends ProxyOutputStream {
    private long count = 0;

    public CountingOutputStream(OutputStream out) {
        super(out);
    }

    /* access modifiers changed from: protected */
    public synchronized void beforeWrite(int n) {
        this.count += (long) n;
    }

    public int getCount() {
        long result = getByteCount();
        if (result <= 2147483647L) {
            return (int) result;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("The byte count ");
        sb.append(result);
        sb.append(" is too large to be converted to an int");
        throw new ArithmeticException(sb.toString());
    }

    public int resetCount() {
        long result = resetByteCount();
        if (result <= 2147483647L) {
            return (int) result;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("The byte count ");
        sb.append(result);
        sb.append(" is too large to be converted to an int");
        throw new ArithmeticException(sb.toString());
    }

    public synchronized long getByteCount() {
        return this.count;
    }

    public synchronized long resetByteCount() {
        long tmp;
        tmp = this.count;
        this.count = 0;
        return tmp;
    }
}
