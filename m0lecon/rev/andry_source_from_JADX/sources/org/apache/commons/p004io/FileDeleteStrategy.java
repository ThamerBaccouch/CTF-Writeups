package org.apache.commons.p004io;

import java.io.File;
import java.io.IOException;

/* renamed from: org.apache.commons.io.FileDeleteStrategy */
public class FileDeleteStrategy {
    public static final FileDeleteStrategy FORCE = new ForceFileDeleteStrategy();
    public static final FileDeleteStrategy NORMAL = new FileDeleteStrategy("Normal");
    private final String name;

    /* renamed from: org.apache.commons.io.FileDeleteStrategy$ForceFileDeleteStrategy */
    static class ForceFileDeleteStrategy extends FileDeleteStrategy {
        ForceFileDeleteStrategy() {
            super("Force");
        }

        /* access modifiers changed from: protected */
        public boolean doDelete(File fileToDelete) throws IOException {
            FileUtils.forceDelete(fileToDelete);
            return true;
        }
    }

    protected FileDeleteStrategy(String name2) {
        this.name = name2;
    }

    public boolean deleteQuietly(File fileToDelete) {
        if (fileToDelete == null || !fileToDelete.exists()) {
            return true;
        }
        try {
            return doDelete(fileToDelete);
        } catch (IOException e) {
            return false;
        }
    }

    public void delete(File fileToDelete) throws IOException {
        if (fileToDelete.exists() && !doDelete(fileToDelete)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Deletion failed: ");
            sb.append(fileToDelete);
            throw new IOException(sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public boolean doDelete(File fileToDelete) throws IOException {
        return fileToDelete.delete();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FileDeleteStrategy[");
        sb.append(this.name);
        sb.append("]");
        return sb.toString();
    }
}
