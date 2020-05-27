package org.apache.commons.p004io.input;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import org.apache.commons.p004io.FileUtils;

/* renamed from: org.apache.commons.io.input.Tailer */
public class Tailer implements Runnable {
    private static final int DEFAULT_BUFSIZE = 4096;
    private static final Charset DEFAULT_CHARSET = Charset.defaultCharset();
    private static final int DEFAULT_DELAY_MILLIS = 1000;
    private static final String RAF_MODE = "r";
    private final Charset cset;
    private final long delayMillis;
    private final boolean end;
    private final File file;
    private final byte[] inbuf;
    private final TailerListener listener;
    private final boolean reOpen;
    private volatile boolean run;

    public Tailer(File file2, TailerListener listener2) {
        this(file2, listener2, 1000);
    }

    public Tailer(File file2, TailerListener listener2, long delayMillis2) {
        this(file2, listener2, delayMillis2, false);
    }

    public Tailer(File file2, TailerListener listener2, long delayMillis2, boolean end2) {
        this(file2, listener2, delayMillis2, end2, 4096);
    }

    public Tailer(File file2, TailerListener listener2, long delayMillis2, boolean end2, boolean reOpen2) {
        this(file2, listener2, delayMillis2, end2, reOpen2, 4096);
    }

    public Tailer(File file2, TailerListener listener2, long delayMillis2, boolean end2, int bufSize) {
        this(file2, listener2, delayMillis2, end2, false, bufSize);
    }

    public Tailer(File file2, TailerListener listener2, long delayMillis2, boolean end2, boolean reOpen2, int bufSize) {
        this(file2, DEFAULT_CHARSET, listener2, delayMillis2, end2, reOpen2, bufSize);
    }

    public Tailer(File file2, Charset cset2, TailerListener listener2, long delayMillis2, boolean end2, boolean reOpen2, int bufSize) {
        this.run = true;
        this.file = file2;
        this.delayMillis = delayMillis2;
        this.end = end2;
        this.inbuf = new byte[bufSize];
        this.listener = listener2;
        listener2.init(this);
        this.reOpen = reOpen2;
        this.cset = cset2;
    }

    public static Tailer create(File file2, TailerListener listener2, long delayMillis2, boolean end2, int bufSize) {
        return create(file2, listener2, delayMillis2, end2, false, bufSize);
    }

    public static Tailer create(File file2, TailerListener listener2, long delayMillis2, boolean end2, boolean reOpen2, int bufSize) {
        return create(file2, DEFAULT_CHARSET, listener2, delayMillis2, end2, reOpen2, bufSize);
    }

    public static Tailer create(File file2, Charset charset, TailerListener listener2, long delayMillis2, boolean end2, boolean reOpen2, int bufSize) {
        Tailer tailer = new Tailer(file2, charset, listener2, delayMillis2, end2, reOpen2, bufSize);
        Thread thread = new Thread(tailer);
        thread.setDaemon(true);
        thread.start();
        return tailer;
    }

    public static Tailer create(File file2, TailerListener listener2, long delayMillis2, boolean end2) {
        return create(file2, listener2, delayMillis2, end2, 4096);
    }

    public static Tailer create(File file2, TailerListener listener2, long delayMillis2, boolean end2, boolean reOpen2) {
        return create(file2, listener2, delayMillis2, end2, reOpen2, 4096);
    }

    public static Tailer create(File file2, TailerListener listener2, long delayMillis2) {
        return create(file2, listener2, delayMillis2, false);
    }

    public static Tailer create(File file2, TailerListener listener2) {
        return create(file2, listener2, 1000, false);
    }

    public File getFile() {
        return this.file;
    }

    /* access modifiers changed from: protected */
    public boolean getRun() {
        return this.run;
    }

    public long getDelay() {
        return this.delayMillis;
    }

    public void run() {
        String str;
        RandomAccessFile save;
        RandomAccessFile reader = null;
        long last = 0;
        long position = 0;
        while (true) {
            try {
                boolean run2 = getRun();
                str = RAF_MODE;
                if (run2 && reader == null) {
                    try {
                        reader = new RandomAccessFile(this.file, str);
                    } catch (FileNotFoundException e) {
                        this.listener.fileNotFound();
                    }
                    if (reader == null) {
                        Thread.sleep(this.delayMillis);
                    } else {
                        position = this.end ? this.file.length() : 0;
                        last = this.file.lastModified();
                        reader.seek(position);
                    }
                }
            } catch (InterruptedException e2) {
                Thread.currentThread().interrupt();
                this.listener.handle((Exception) e2);
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e3) {
                        e = e3;
                    }
                }
            } catch (Exception e4) {
                this.listener.handle(e4);
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e5) {
                        e = e5;
                    }
                }
            } catch (Throwable th) {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e6) {
                        this.listener.handle((Exception) e6);
                    }
                }
                stop();
                throw th;
            }
        }
        while (getRun()) {
            boolean newer = FileUtils.isFileNewer(this.file, last);
            long length = this.file.length();
            if (length < position) {
                this.listener.fileRotated();
                save = reader;
                reader = new RandomAccessFile(this.file, str);
                try {
                    readLines(save);
                } catch (IOException ioe) {
                    this.listener.handle((Exception) ioe);
                }
                position = 0;
                if (save != null) {
                    try {
                        save.close();
                    } catch (FileNotFoundException e7) {
                        this.listener.fileNotFound();
                        Thread.sleep(this.delayMillis);
                    } catch (Throwable th2) {
                        r10.addSuppressed(th2);
                    }
                }
            } else {
                if (length > position) {
                    position = readLines(reader);
                    last = this.file.lastModified();
                } else if (newer) {
                    reader.seek(0);
                    position = readLines(reader);
                    last = this.file.lastModified();
                }
                if (this.reOpen && reader != null) {
                    reader.close();
                }
                Thread.sleep(this.delayMillis);
                if (getRun() && this.reOpen) {
                    reader = new RandomAccessFile(this.file, str);
                    reader.seek(position);
                }
            }
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e8) {
                e = e8;
            }
        }
        stop();
        try {
        } catch (Throwable th3) {
            if (save != null) {
                save.close();
            }
            throw th3;
        }
        this.listener.handle((Exception) e);
        stop();
    }

    public void stop() {
        this.run = false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0091, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        throw r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private long readLines(java.io.RandomAccessFile r17) throws java.io.IOException {
        /*
            r16 = this;
            r1 = r16
            r2 = r17
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream
            r3 = 64
            r0.<init>(r3)
            r3 = r0
            long r4 = r17.getFilePointer()     // Catch:{ all -> 0x0091 }
            r6 = r4
            r0 = 0
        L_0x0012:
            boolean r8 = r16.getRun()     // Catch:{ all -> 0x0091 }
            if (r8 == 0) goto L_0x007c
            byte[] r8 = r1.inbuf     // Catch:{ all -> 0x0091 }
            int r8 = r2.read(r8)     // Catch:{ all -> 0x0091 }
            r9 = r8
            r10 = -1
            if (r8 == r10) goto L_0x007c
            r8 = 0
        L_0x0023:
            if (r8 >= r9) goto L_0x0076
            byte[] r10 = r1.inbuf     // Catch:{ all -> 0x0091 }
            byte r10 = r10[r8]     // Catch:{ all -> 0x0091 }
            r11 = 10
            if (r10 == r11) goto L_0x0058
            r11 = 13
            if (r10 == r11) goto L_0x0051
            if (r0 == 0) goto L_0x004d
            r0 = 0
            org.apache.commons.io.input.TailerListener r11 = r1.listener     // Catch:{ all -> 0x0091 }
            java.lang.String r14 = new java.lang.String     // Catch:{ all -> 0x0091 }
            byte[] r15 = r3.toByteArray()     // Catch:{ all -> 0x0091 }
            java.nio.charset.Charset r12 = r1.cset     // Catch:{ all -> 0x0091 }
            r14.<init>(r15, r12)     // Catch:{ all -> 0x0091 }
            r11.handle(r14)     // Catch:{ all -> 0x0091 }
            r3.reset()     // Catch:{ all -> 0x0091 }
            long r11 = (long) r8     // Catch:{ all -> 0x0091 }
            long r11 = r11 + r4
            r13 = 1
            long r11 = r11 + r13
            r6 = r11
        L_0x004d:
            r3.write(r10)     // Catch:{ all -> 0x0091 }
            goto L_0x0073
        L_0x0051:
            if (r0 == 0) goto L_0x0056
            r3.write(r11)     // Catch:{ all -> 0x0091 }
        L_0x0056:
            r0 = 1
            goto L_0x0073
        L_0x0058:
            r0 = 0
            org.apache.commons.io.input.TailerListener r11 = r1.listener     // Catch:{ all -> 0x0091 }
            java.lang.String r12 = new java.lang.String     // Catch:{ all -> 0x0091 }
            byte[] r13 = r3.toByteArray()     // Catch:{ all -> 0x0091 }
            java.nio.charset.Charset r14 = r1.cset     // Catch:{ all -> 0x0091 }
            r12.<init>(r13, r14)     // Catch:{ all -> 0x0091 }
            r11.handle(r12)     // Catch:{ all -> 0x0091 }
            r3.reset()     // Catch:{ all -> 0x0091 }
            long r11 = (long) r8     // Catch:{ all -> 0x0091 }
            long r11 = r11 + r4
            r13 = 1
            long r6 = r11 + r13
        L_0x0073:
            int r8 = r8 + 1
            goto L_0x0023
        L_0x0076:
            long r10 = r17.getFilePointer()     // Catch:{ all -> 0x0091 }
            r4 = r10
            goto L_0x0012
        L_0x007c:
            r2.seek(r6)     // Catch:{ all -> 0x0091 }
            org.apache.commons.io.input.TailerListener r8 = r1.listener     // Catch:{ all -> 0x0091 }
            boolean r8 = r8 instanceof org.apache.commons.p004io.input.TailerListenerAdapter     // Catch:{ all -> 0x0091 }
            if (r8 == 0) goto L_0x008c
            org.apache.commons.io.input.TailerListener r8 = r1.listener     // Catch:{ all -> 0x0091 }
            org.apache.commons.io.input.TailerListenerAdapter r8 = (org.apache.commons.p004io.input.TailerListenerAdapter) r8     // Catch:{ all -> 0x0091 }
            r8.endOfFileReached()     // Catch:{ all -> 0x0091 }
        L_0x008c:
            r3.close()
            return r6
        L_0x0091:
            r0 = move-exception
            r4 = r0
            throw r4     // Catch:{ all -> 0x0094 }
        L_0x0094:
            r0 = move-exception
            r5 = r0
            r3.close()     // Catch:{ all -> 0x009a }
            goto L_0x009f
        L_0x009a:
            r0 = move-exception
            r6 = r0
            r4.addSuppressed(r6)
        L_0x009f:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p004io.input.Tailer.readLines(java.io.RandomAccessFile):long");
    }
}
