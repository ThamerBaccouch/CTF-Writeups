package org.apache.commons.p004io.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.p004io.FileUtils;

/* renamed from: org.apache.commons.io.output.DeferredFileOutputStream */
public class DeferredFileOutputStream extends ThresholdingOutputStream {
    private boolean closed;
    private OutputStream currentOutputStream;
    private final File directory;
    private ByteArrayOutputStream memoryOutputStream;
    private File outputFile;
    private final String prefix;
    private final String suffix;

    public DeferredFileOutputStream(int threshold, File outputFile2) {
        this(threshold, outputFile2, null, null, null, 1024);
    }

    public DeferredFileOutputStream(int threshold, int initialBufferSize, File outputFile2) {
        this(threshold, outputFile2, null, null, null, initialBufferSize);
        if (initialBufferSize < 0) {
            throw new IllegalArgumentException("Initial buffer size must be atleast 0.");
        }
    }

    public DeferredFileOutputStream(int threshold, String prefix2, String suffix2, File directory2) {
        this(threshold, null, prefix2, suffix2, directory2, 1024);
        if (prefix2 == null) {
            throw new IllegalArgumentException("Temporary file prefix is missing");
        }
    }

    public DeferredFileOutputStream(int threshold, int initialBufferSize, String prefix2, String suffix2, File directory2) {
        this(threshold, null, prefix2, suffix2, directory2, initialBufferSize);
        if (prefix2 == null) {
            throw new IllegalArgumentException("Temporary file prefix is missing");
        } else if (initialBufferSize < 0) {
            throw new IllegalArgumentException("Initial buffer size must be atleast 0.");
        }
    }

    private DeferredFileOutputStream(int threshold, File outputFile2, String prefix2, String suffix2, File directory2, int initialBufferSize) {
        super(threshold);
        this.closed = false;
        this.outputFile = outputFile2;
        this.prefix = prefix2;
        this.suffix = suffix2;
        this.directory = directory2;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(initialBufferSize);
        this.memoryOutputStream = byteArrayOutputStream;
        this.currentOutputStream = byteArrayOutputStream;
    }

    /* access modifiers changed from: protected */
    public OutputStream getStream() throws IOException {
        return this.currentOutputStream;
    }

    /* access modifiers changed from: protected */
    public void thresholdReached() throws IOException {
        String str = this.prefix;
        if (str != null) {
            this.outputFile = File.createTempFile(str, this.suffix, this.directory);
        }
        FileUtils.forceMkdirParent(this.outputFile);
        FileOutputStream fos = new FileOutputStream(this.outputFile);
        try {
            this.memoryOutputStream.writeTo(fos);
            this.currentOutputStream = fos;
            this.memoryOutputStream = null;
        } catch (IOException e) {
            fos.close();
            throw e;
        }
    }

    public boolean isInMemory() {
        return !isThresholdExceeded();
    }

    public byte[] getData() {
        ByteArrayOutputStream byteArrayOutputStream = this.memoryOutputStream;
        if (byteArrayOutputStream != null) {
            return byteArrayOutputStream.toByteArray();
        }
        return null;
    }

    public File getFile() {
        return this.outputFile;
    }

    public void close() throws IOException {
        super.close();
        this.closed = true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0020, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0025, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0026, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0029, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void writeTo(java.io.OutputStream r5) throws java.io.IOException {
        /*
            r4 = this;
            boolean r0 = r4.closed
            if (r0 == 0) goto L_0x002a
            boolean r0 = r4.isInMemory()
            if (r0 == 0) goto L_0x0010
            org.apache.commons.io.output.ByteArrayOutputStream r0 = r4.memoryOutputStream
            r0.writeTo(r5)
            goto L_0x001d
        L_0x0010:
            java.io.FileInputStream r0 = new java.io.FileInputStream
            java.io.File r1 = r4.outputFile
            r0.<init>(r1)
            org.apache.commons.p004io.IOUtils.copy(r0, r5)     // Catch:{ all -> 0x001e }
            r0.close()
        L_0x001d:
            return
        L_0x001e:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0020 }
        L_0x0020:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x0025 }
            goto L_0x0029
        L_0x0025:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x0029:
            throw r2
        L_0x002a:
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r1 = "Stream not closed"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p004io.output.DeferredFileOutputStream.writeTo(java.io.OutputStream):void");
    }
}
