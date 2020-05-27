package org.apache.commons.p004io.filefilter;

import java.io.Serializable;
import java.nio.charset.Charset;

/* renamed from: org.apache.commons.io.filefilter.MagicNumberFileFilter */
public class MagicNumberFileFilter extends AbstractFileFilter implements Serializable {
    private static final long serialVersionUID = -547733176983104172L;
    private final long byteOffset;
    private final byte[] magicNumbers;

    public MagicNumberFileFilter(byte[] magicNumber) {
        this(magicNumber, 0);
    }

    public MagicNumberFileFilter(String magicNumber) {
        this(magicNumber, 0);
    }

    public MagicNumberFileFilter(String magicNumber, long offset) {
        if (magicNumber == null) {
            throw new IllegalArgumentException("The magic number cannot be null");
        } else if (magicNumber.isEmpty()) {
            throw new IllegalArgumentException("The magic number must contain at least one byte");
        } else if (offset >= 0) {
            this.magicNumbers = magicNumber.getBytes(Charset.defaultCharset());
            this.byteOffset = offset;
        } else {
            throw new IllegalArgumentException("The offset cannot be negative");
        }
    }

    public MagicNumberFileFilter(byte[] magicNumber, long offset) {
        if (magicNumber == null) {
            throw new IllegalArgumentException("The magic number cannot be null");
        } else if (magicNumber.length == 0) {
            throw new IllegalArgumentException("The magic number must contain at least one byte");
        } else if (offset >= 0) {
            byte[] bArr = new byte[magicNumber.length];
            this.magicNumbers = bArr;
            System.arraycopy(magicNumber, 0, bArr, 0, magicNumber.length);
            this.byteOffset = offset;
        } else {
            throw new IllegalArgumentException("The offset cannot be negative");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003a, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0043, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean accept(java.io.File r6) {
        /*
            r5 = this;
            r0 = 0
            if (r6 == 0) goto L_0x0045
            boolean r1 = r6.isFile()
            if (r1 == 0) goto L_0x0045
            boolean r1 = r6.canRead()
            if (r1 == 0) goto L_0x0045
            java.io.RandomAccessFile r1 = new java.io.RandomAccessFile     // Catch:{ IOException -> 0x0044 }
            java.lang.String r2 = "r"
            r1.<init>(r6, r2)     // Catch:{ IOException -> 0x0044 }
            byte[] r2 = r5.magicNumbers     // Catch:{ all -> 0x0038 }
            int r2 = r2.length     // Catch:{ all -> 0x0038 }
            byte[] r2 = new byte[r2]     // Catch:{ all -> 0x0038 }
            long r3 = r5.byteOffset     // Catch:{ all -> 0x0038 }
            r1.seek(r3)     // Catch:{ all -> 0x0038 }
            int r3 = r1.read(r2)     // Catch:{ all -> 0x0038 }
            byte[] r4 = r5.magicNumbers     // Catch:{ all -> 0x0038 }
            int r4 = r4.length     // Catch:{ all -> 0x0038 }
            if (r3 == r4) goto L_0x002e
            r1.close()     // Catch:{ IOException -> 0x0044 }
            return r0
        L_0x002e:
            byte[] r4 = r5.magicNumbers     // Catch:{ all -> 0x0038 }
            boolean r4 = java.util.Arrays.equals(r4, r2)     // Catch:{ all -> 0x0038 }
            r1.close()     // Catch:{ IOException -> 0x0044 }
            return r4
        L_0x0038:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x003a }
        L_0x003a:
            r3 = move-exception
            r1.close()     // Catch:{ all -> 0x003f }
            goto L_0x0043
        L_0x003f:
            r4 = move-exception
            r2.addSuppressed(r4)     // Catch:{ IOException -> 0x0044 }
        L_0x0043:
            throw r3     // Catch:{ IOException -> 0x0044 }
        L_0x0044:
            r1 = move-exception
        L_0x0045:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p004io.filefilter.MagicNumberFileFilter.accept(java.io.File):boolean");
    }

    public String toString() {
        StringBuilder builder = new StringBuilder(super.toString());
        builder.append("(");
        builder.append(new String(this.magicNumbers, Charset.defaultCharset()));
        builder.append(",");
        builder.append(this.byteOffset);
        builder.append(")");
        return builder.toString();
    }
}
