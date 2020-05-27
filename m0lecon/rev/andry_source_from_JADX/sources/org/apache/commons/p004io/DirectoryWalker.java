package org.apache.commons.p004io;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Collection;
import org.apache.commons.p004io.filefilter.FileFilterUtils;
import org.apache.commons.p004io.filefilter.IOFileFilter;
import org.apache.commons.p004io.filefilter.TrueFileFilter;

/* renamed from: org.apache.commons.io.DirectoryWalker */
public abstract class DirectoryWalker<T> {
    private final int depthLimit;
    private final FileFilter filter;

    /* renamed from: org.apache.commons.io.DirectoryWalker$CancelException */
    public static class CancelException extends IOException {
        private static final long serialVersionUID = 1347339620135041008L;
        private final int depth;
        private final File file;

        public CancelException(File file2, int depth2) {
            this("Operation Cancelled", file2, depth2);
        }

        public CancelException(String message, File file2, int depth2) {
            super(message);
            this.file = file2;
            this.depth = depth2;
        }

        public File getFile() {
            return this.file;
        }

        public int getDepth() {
            return this.depth;
        }
    }

    protected DirectoryWalker() {
        this(null, -1);
    }

    protected DirectoryWalker(FileFilter filter2, int depthLimit2) {
        this.filter = filter2;
        this.depthLimit = depthLimit2;
    }

    protected DirectoryWalker(IOFileFilter directoryFilter, IOFileFilter fileFilter, int depthLimit2) {
        if (directoryFilter == null && fileFilter == null) {
            this.filter = null;
        } else {
            this.filter = FileFilterUtils.m17or(FileFilterUtils.makeDirectoryOnly(directoryFilter != null ? directoryFilter : TrueFileFilter.TRUE), FileFilterUtils.makeFileOnly(fileFilter != null ? fileFilter : TrueFileFilter.TRUE));
        }
        this.depthLimit = depthLimit2;
    }

    /* access modifiers changed from: protected */
    public final void walk(File startDirectory, Collection<T> results) throws IOException {
        if (startDirectory != null) {
            try {
                handleStart(startDirectory, results);
                walk(startDirectory, 0, results);
                handleEnd(results);
            } catch (CancelException cancel) {
                handleCancelled(startDirectory, results, cancel);
            }
        } else {
            throw new NullPointerException("Start Directory is null");
        }
    }

    private void walk(File directory, int depth, Collection<T> results) throws IOException {
        checkIfCancelled(directory, depth, results);
        if (handleDirectory(directory, depth, results)) {
            handleDirectoryStart(directory, depth, results);
            int childDepth = depth + 1;
            int i = this.depthLimit;
            if (i < 0 || childDepth <= i) {
                checkIfCancelled(directory, depth, results);
                FileFilter fileFilter = this.filter;
                File[] childFiles = filterDirectoryContents(directory, depth, fileFilter == null ? directory.listFiles() : directory.listFiles(fileFilter));
                if (childFiles == null) {
                    handleRestricted(directory, childDepth, results);
                } else {
                    for (File childFile : childFiles) {
                        if (childFile.isDirectory()) {
                            walk(childFile, childDepth, results);
                        } else {
                            checkIfCancelled(childFile, childDepth, results);
                            handleFile(childFile, childDepth, results);
                            checkIfCancelled(childFile, childDepth, results);
                        }
                    }
                }
            }
            handleDirectoryEnd(directory, depth, results);
        }
        checkIfCancelled(directory, depth, results);
    }

    /* access modifiers changed from: protected */
    public final void checkIfCancelled(File file, int depth, Collection<T> results) throws IOException {
        if (handleIsCancelled(file, depth, results)) {
            throw new CancelException(file, depth);
        }
    }

    /* access modifiers changed from: protected */
    public boolean handleIsCancelled(File file, int depth, Collection<T> collection) throws IOException {
        return false;
    }

    /* access modifiers changed from: protected */
    public void handleCancelled(File startDirectory, Collection<T> collection, CancelException cancel) throws IOException {
        throw cancel;
    }

    /* access modifiers changed from: protected */
    public void handleStart(File startDirectory, Collection<T> collection) throws IOException {
    }

    /* access modifiers changed from: protected */
    public boolean handleDirectory(File directory, int depth, Collection<T> collection) throws IOException {
        return true;
    }

    /* access modifiers changed from: protected */
    public void handleDirectoryStart(File directory, int depth, Collection<T> collection) throws IOException {
    }

    /* access modifiers changed from: protected */
    public File[] filterDirectoryContents(File directory, int depth, File[] files) throws IOException {
        return files;
    }

    /* access modifiers changed from: protected */
    public void handleFile(File file, int depth, Collection<T> collection) throws IOException {
    }

    /* access modifiers changed from: protected */
    public void handleRestricted(File directory, int depth, Collection<T> collection) throws IOException {
    }

    /* access modifiers changed from: protected */
    public void handleDirectoryEnd(File directory, int depth, Collection<T> collection) throws IOException {
    }

    /* access modifiers changed from: protected */
    public void handleEnd(Collection<T> collection) throws IOException {
    }
}
