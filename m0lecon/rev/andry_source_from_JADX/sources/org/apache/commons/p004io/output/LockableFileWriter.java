package org.apache.commons.p004io.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import org.apache.commons.p004io.Charsets;
import org.apache.commons.p004io.FileUtils;

/* renamed from: org.apache.commons.io.output.LockableFileWriter */
public class LockableFileWriter extends Writer {
    private static final String LCK = ".lck";
    private final File lockFile;
    private final Writer out;

    public LockableFileWriter(String fileName) throws IOException {
        this(fileName, false, (String) null);
    }

    public LockableFileWriter(String fileName, boolean append) throws IOException {
        this(fileName, append, (String) null);
    }

    public LockableFileWriter(String fileName, boolean append, String lockDir) throws IOException {
        this(new File(fileName), append, lockDir);
    }

    public LockableFileWriter(File file) throws IOException {
        this(file, false, (String) null);
    }

    public LockableFileWriter(File file, boolean append) throws IOException {
        this(file, append, (String) null);
    }

    @Deprecated
    public LockableFileWriter(File file, boolean append, String lockDir) throws IOException {
        this(file, Charset.defaultCharset(), append, lockDir);
    }

    public LockableFileWriter(File file, Charset encoding) throws IOException {
        this(file, encoding, false, (String) null);
    }

    public LockableFileWriter(File file, String encoding) throws IOException {
        this(file, encoding, false, (String) null);
    }

    public LockableFileWriter(File file, Charset encoding, boolean append, String lockDir) throws IOException {
        File file2 = file.getAbsoluteFile();
        if (file2.getParentFile() != null) {
            FileUtils.forceMkdir(file2.getParentFile());
        }
        if (!file2.isDirectory()) {
            if (lockDir == null) {
                lockDir = System.getProperty("java.io.tmpdir");
            }
            File lockDirFile = new File(lockDir);
            FileUtils.forceMkdir(lockDirFile);
            testLockDir(lockDirFile);
            StringBuilder sb = new StringBuilder();
            sb.append(file2.getName());
            sb.append(LCK);
            this.lockFile = new File(lockDirFile, sb.toString());
            createLock();
            this.out = initWriter(file2, encoding, append);
            return;
        }
        throw new IOException("File specified is a directory");
    }

    public LockableFileWriter(File file, String encoding, boolean append, String lockDir) throws IOException {
        this(file, Charsets.toCharset(encoding), append, lockDir);
    }

    private void testLockDir(File lockDir) throws IOException {
        if (!lockDir.exists()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Could not find lockDir: ");
            sb.append(lockDir.getAbsolutePath());
            throw new IOException(sb.toString());
        } else if (!lockDir.canWrite()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Could not write to lockDir: ");
            sb2.append(lockDir.getAbsolutePath());
            throw new IOException(sb2.toString());
        }
    }

    private void createLock() throws IOException {
        synchronized (LockableFileWriter.class) {
            if (this.lockFile.createNewFile()) {
                this.lockFile.deleteOnExit();
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Can't write file, lock ");
                sb.append(this.lockFile.getAbsolutePath());
                sb.append(" exists");
                throw new IOException(sb.toString());
            }
        }
    }

    private Writer initWriter(File file, Charset encoding, boolean append) throws IOException {
        boolean fileExistedAlready = file.exists();
        try {
            return new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath(), append), Charsets.toCharset(encoding));
        } catch (IOException | RuntimeException ex) {
            FileUtils.deleteQuietly(this.lockFile);
            if (!fileExistedAlready) {
                FileUtils.deleteQuietly(file);
            }
            throw ex;
        }
    }

    public void close() throws IOException {
        try {
            this.out.close();
        } finally {
            this.lockFile.delete();
        }
    }

    public void write(int idx) throws IOException {
        this.out.write(idx);
    }

    public void write(char[] chr) throws IOException {
        this.out.write(chr);
    }

    public void write(char[] chr, int st, int end) throws IOException {
        this.out.write(chr, st, end);
    }

    public void write(String str) throws IOException {
        this.out.write(str);
    }

    public void write(String str, int st, int end) throws IOException {
        this.out.write(str, st, end);
    }

    public void flush() throws IOException {
        this.out.flush();
    }
}
