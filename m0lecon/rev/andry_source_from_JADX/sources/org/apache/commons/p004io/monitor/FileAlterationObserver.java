package org.apache.commons.p004io.monitor;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.p004io.FileUtils;
import org.apache.commons.p004io.IOCase;
import org.apache.commons.p004io.comparator.NameFileComparator;

/* renamed from: org.apache.commons.io.monitor.FileAlterationObserver */
public class FileAlterationObserver implements Serializable {
    private static final long serialVersionUID = 1185122225658782848L;
    private final Comparator<File> comparator;
    private final FileFilter fileFilter;
    private final List<FileAlterationListener> listeners;
    private final FileEntry rootEntry;

    public FileAlterationObserver(String directoryName) {
        this(new File(directoryName));
    }

    public FileAlterationObserver(String directoryName, FileFilter fileFilter2) {
        this(new File(directoryName), fileFilter2);
    }

    public FileAlterationObserver(String directoryName, FileFilter fileFilter2, IOCase caseSensitivity) {
        this(new File(directoryName), fileFilter2, caseSensitivity);
    }

    public FileAlterationObserver(File directory) {
        this(directory, (FileFilter) null);
    }

    public FileAlterationObserver(File directory, FileFilter fileFilter2) {
        this(directory, fileFilter2, (IOCase) null);
    }

    public FileAlterationObserver(File directory, FileFilter fileFilter2, IOCase caseSensitivity) {
        this(new FileEntry(directory), fileFilter2, caseSensitivity);
    }

    protected FileAlterationObserver(FileEntry rootEntry2, FileFilter fileFilter2, IOCase caseSensitivity) {
        this.listeners = new CopyOnWriteArrayList();
        if (rootEntry2 == null) {
            throw new IllegalArgumentException("Root entry is missing");
        } else if (rootEntry2.getFile() != null) {
            this.rootEntry = rootEntry2;
            this.fileFilter = fileFilter2;
            if (caseSensitivity == null || caseSensitivity.equals(IOCase.SYSTEM)) {
                this.comparator = NameFileComparator.NAME_SYSTEM_COMPARATOR;
            } else if (caseSensitivity.equals(IOCase.INSENSITIVE)) {
                this.comparator = NameFileComparator.NAME_INSENSITIVE_COMPARATOR;
            } else {
                this.comparator = NameFileComparator.NAME_COMPARATOR;
            }
        } else {
            throw new IllegalArgumentException("Root directory is missing");
        }
    }

    public File getDirectory() {
        return this.rootEntry.getFile();
    }

    public FileFilter getFileFilter() {
        return this.fileFilter;
    }

    public void addListener(FileAlterationListener listener) {
        if (listener != null) {
            this.listeners.add(listener);
        }
    }

    public void removeListener(FileAlterationListener listener) {
        if (listener != null) {
            do {
            } while (this.listeners.remove(listener));
        }
    }

    public Iterable<FileAlterationListener> getListeners() {
        return this.listeners;
    }

    public void initialize() throws Exception {
        FileEntry fileEntry = this.rootEntry;
        fileEntry.refresh(fileEntry.getFile());
        this.rootEntry.setChildren(doListFiles(this.rootEntry.getFile(), this.rootEntry));
    }

    public void destroy() throws Exception {
    }

    public void checkAndNotify() {
        for (FileAlterationListener listener : this.listeners) {
            listener.onStart(this);
        }
        File rootFile = this.rootEntry.getFile();
        if (rootFile.exists()) {
            FileEntry fileEntry = this.rootEntry;
            checkAndNotify(fileEntry, fileEntry.getChildren(), listFiles(rootFile));
        } else if (this.rootEntry.isExists()) {
            FileEntry fileEntry2 = this.rootEntry;
            checkAndNotify(fileEntry2, fileEntry2.getChildren(), FileUtils.EMPTY_FILE_ARRAY);
        }
        for (FileAlterationListener listener2 : this.listeners) {
            listener2.onStop(this);
        }
    }

    private void checkAndNotify(FileEntry parent, FileEntry[] previous, File[] files) {
        int c = 0;
        FileEntry[] current = files.length > 0 ? new FileEntry[files.length] : FileEntry.EMPTY_ENTRIES;
        int length = previous.length;
        for (int i = 0; i < length; i++) {
            FileEntry entry = previous[i];
            while (c < files.length && this.comparator.compare(entry.getFile(), files[c]) > 0) {
                current[c] = createFileEntry(parent, files[c]);
                doCreate(current[c]);
                c++;
            }
            if (c >= files.length || this.comparator.compare(entry.getFile(), files[c]) != 0) {
                checkAndNotify(entry, entry.getChildren(), FileUtils.EMPTY_FILE_ARRAY);
                doDelete(entry);
            } else {
                doMatch(entry, files[c]);
                checkAndNotify(entry, entry.getChildren(), listFiles(files[c]));
                current[c] = entry;
                c++;
            }
        }
        while (c < files.length) {
            current[c] = createFileEntry(parent, files[c]);
            doCreate(current[c]);
            c++;
        }
        parent.setChildren(current);
    }

    private FileEntry createFileEntry(FileEntry parent, File file) {
        FileEntry entry = parent.newChildInstance(file);
        entry.refresh(file);
        entry.setChildren(doListFiles(file, entry));
        return entry;
    }

    private FileEntry[] doListFiles(File file, FileEntry entry) {
        File[] files = listFiles(file);
        FileEntry[] children = files.length > 0 ? new FileEntry[files.length] : FileEntry.EMPTY_ENTRIES;
        for (int i = 0; i < files.length; i++) {
            children[i] = createFileEntry(entry, files[i]);
        }
        return children;
    }

    private void doCreate(FileEntry entry) {
        for (FileAlterationListener listener : this.listeners) {
            if (entry.isDirectory()) {
                listener.onDirectoryCreate(entry.getFile());
            } else {
                listener.onFileCreate(entry.getFile());
            }
        }
        for (FileEntry aChildren : entry.getChildren()) {
            doCreate(aChildren);
        }
    }

    private void doMatch(FileEntry entry, File file) {
        if (entry.refresh(file)) {
            for (FileAlterationListener listener : this.listeners) {
                if (entry.isDirectory()) {
                    listener.onDirectoryChange(file);
                } else {
                    listener.onFileChange(file);
                }
            }
        }
    }

    private void doDelete(FileEntry entry) {
        for (FileAlterationListener listener : this.listeners) {
            if (entry.isDirectory()) {
                listener.onDirectoryDelete(entry.getFile());
            } else {
                listener.onFileDelete(entry.getFile());
            }
        }
    }

    private File[] listFiles(File file) {
        File[] children = null;
        if (file.isDirectory()) {
            FileFilter fileFilter2 = this.fileFilter;
            children = fileFilter2 == null ? file.listFiles() : file.listFiles(fileFilter2);
        }
        if (children == null) {
            children = FileUtils.EMPTY_FILE_ARRAY;
        }
        Comparator<File> comparator2 = this.comparator;
        if (comparator2 != null && children.length > 1) {
            Arrays.sort(children, comparator2);
        }
        return children;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getSimpleName());
        builder.append("[file='");
        builder.append(getDirectory().getPath());
        builder.append('\'');
        if (this.fileFilter != null) {
            builder.append(", ");
            builder.append(this.fileFilter.toString());
        }
        builder.append(", listeners=");
        builder.append(this.listeners.size());
        builder.append("]");
        return builder.toString();
    }
}
