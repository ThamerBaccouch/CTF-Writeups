package org.apache.commons.p004io.output;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.p004io.input.ClosedInputStream;

/* renamed from: org.apache.commons.io.output.ByteArrayOutputStream */
public class ByteArrayOutputStream extends OutputStream {
    static final int DEFAULT_SIZE = 1024;
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private final List<byte[]> buffers;
    private int count;
    private byte[] currentBuffer;
    private int currentBufferIndex;
    private int filledBufferSum;
    private boolean reuseBuffers;

    public ByteArrayOutputStream() {
        this(1024);
    }

    public ByteArrayOutputStream(int size) {
        this.buffers = new ArrayList();
        this.reuseBuffers = true;
        if (size >= 0) {
            synchronized (this) {
                needNewBuffer(size);
            }
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Negative initial size: ");
        sb.append(size);
        throw new IllegalArgumentException(sb.toString());
    }

    private void needNewBuffer(int newcount) {
        int newBufferSize;
        if (this.currentBufferIndex < this.buffers.size() - 1) {
            this.filledBufferSum += this.currentBuffer.length;
            int i = this.currentBufferIndex + 1;
            this.currentBufferIndex = i;
            this.currentBuffer = (byte[]) this.buffers.get(i);
            return;
        }
        byte[] bArr = this.currentBuffer;
        if (bArr == null) {
            newBufferSize = newcount;
            this.filledBufferSum = 0;
        } else {
            newBufferSize = Math.max(bArr.length << 1, newcount - this.filledBufferSum);
            this.filledBufferSum += this.currentBuffer.length;
        }
        this.currentBufferIndex++;
        byte[] bArr2 = new byte[newBufferSize];
        this.currentBuffer = bArr2;
        this.buffers.add(bArr2);
    }

    public void write(byte[] b, int off, int len) {
        if (off < 0 || off > b.length || len < 0 || off + len > b.length || off + len < 0) {
            throw new IndexOutOfBoundsException();
        } else if (len != 0) {
            synchronized (this) {
                int newcount = this.count + len;
                int remaining = len;
                int inBufferPos = this.count - this.filledBufferSum;
                while (remaining > 0) {
                    int part = Math.min(remaining, this.currentBuffer.length - inBufferPos);
                    System.arraycopy(b, (off + len) - remaining, this.currentBuffer, inBufferPos, part);
                    remaining -= part;
                    if (remaining > 0) {
                        needNewBuffer(newcount);
                        inBufferPos = 0;
                    }
                }
                this.count = newcount;
            }
        }
    }

    public synchronized void write(int b) {
        int inBufferPos = this.count - this.filledBufferSum;
        if (inBufferPos == this.currentBuffer.length) {
            needNewBuffer(this.count + 1);
            inBufferPos = 0;
        }
        this.currentBuffer[inBufferPos] = (byte) b;
        this.count++;
    }

    public synchronized int write(InputStream in) throws IOException {
        int readCount;
        readCount = 0;
        int inBufferPos = this.count - this.filledBufferSum;
        int n = in.read(this.currentBuffer, inBufferPos, this.currentBuffer.length - inBufferPos);
        while (n != -1) {
            readCount += n;
            inBufferPos += n;
            this.count += n;
            if (inBufferPos == this.currentBuffer.length) {
                needNewBuffer(this.currentBuffer.length);
                inBufferPos = 0;
            }
            n = in.read(this.currentBuffer, inBufferPos, this.currentBuffer.length - inBufferPos);
        }
        return readCount;
    }

    public synchronized int size() {
        return this.count;
    }

    public void close() throws IOException {
    }

    public synchronized void reset() {
        this.count = 0;
        this.filledBufferSum = 0;
        this.currentBufferIndex = 0;
        if (this.reuseBuffers) {
            this.currentBuffer = (byte[]) this.buffers.get(0);
        } else {
            this.currentBuffer = null;
            int size = ((byte[]) this.buffers.get(0)).length;
            this.buffers.clear();
            needNewBuffer(size);
            this.reuseBuffers = true;
        }
    }

    public synchronized void writeTo(OutputStream out) throws IOException {
        int remaining = this.count;
        for (byte[] buf : this.buffers) {
            int c = Math.min(buf.length, remaining);
            out.write(buf, 0, c);
            remaining -= c;
            if (remaining == 0) {
                break;
            }
        }
    }

    public static InputStream toBufferedInputStream(InputStream input) throws IOException {
        return toBufferedInputStream(input, 1024);
    }

    public static InputStream toBufferedInputStream(InputStream input, int size) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream(size);
        output.write(input);
        return output.toInputStream();
    }

    public synchronized InputStream toInputStream() {
        int remaining = this.count;
        if (remaining == 0) {
            return new ClosedInputStream();
        }
        List<ByteArrayInputStream> list = new ArrayList<>(this.buffers.size());
        Iterator it = this.buffers.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            byte[] buf = (byte[]) it.next();
            int c = Math.min(buf.length, remaining);
            list.add(new ByteArrayInputStream(buf, 0, c));
            remaining -= c;
            if (remaining == 0) {
                break;
            }
        }
        this.reuseBuffers = false;
        return new SequenceInputStream(Collections.enumeration(list));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002e, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized byte[] toByteArray() {
        /*
            r7 = this;
            monitor-enter(r7)
            int r0 = r7.count     // Catch:{ all -> 0x002f }
            if (r0 != 0) goto L_0x0009
            byte[] r1 = EMPTY_BYTE_ARRAY     // Catch:{ all -> 0x002f }
            monitor-exit(r7)
            return r1
        L_0x0009:
            byte[] r1 = new byte[r0]     // Catch:{ all -> 0x002f }
            r2 = 0
            java.util.List<byte[]> r3 = r7.buffers     // Catch:{ all -> 0x002f }
            java.util.Iterator r3 = r3.iterator()     // Catch:{ all -> 0x002f }
        L_0x0012:
            boolean r4 = r3.hasNext()     // Catch:{ all -> 0x002f }
            if (r4 == 0) goto L_0x002d
            java.lang.Object r4 = r3.next()     // Catch:{ all -> 0x002f }
            byte[] r4 = (byte[]) r4     // Catch:{ all -> 0x002f }
            int r5 = r4.length     // Catch:{ all -> 0x002f }
            int r5 = java.lang.Math.min(r5, r0)     // Catch:{ all -> 0x002f }
            r6 = 0
            java.lang.System.arraycopy(r4, r6, r1, r2, r5)     // Catch:{ all -> 0x002f }
            int r2 = r2 + r5
            int r0 = r0 - r5
            if (r0 != 0) goto L_0x002c
            goto L_0x002d
        L_0x002c:
            goto L_0x0012
        L_0x002d:
            monitor-exit(r7)
            return r1
        L_0x002f:
            r0 = move-exception
            monitor-exit(r7)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p004io.output.ByteArrayOutputStream.toByteArray():byte[]");
    }

    @Deprecated
    public String toString() {
        return new String(toByteArray(), Charset.defaultCharset());
    }

    public String toString(String enc) throws UnsupportedEncodingException {
        return new String(toByteArray(), enc);
    }

    public String toString(Charset charset) {
        return new String(toByteArray(), charset);
    }
}
