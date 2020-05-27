package org.apache.commons.p004io.monitor;

import java.io.File;
import java.io.Serializable;

/* renamed from: org.apache.commons.io.monitor.FileEntry */
public class FileEntry implements Serializable {
    static final FileEntry[] EMPTY_ENTRIES = new FileEntry[0];
    private static final long serialVersionUID = -2505664948818681153L;
    private FileEntry[] children;
    private boolean directory;
    private boolean exists;
    private final File file;
    private long lastModified;
    private long length;
    private String name;
    private final FileEntry parent;

    public FileEntry(File file2) {
        this(null, file2);
    }

    public FileEntry(FileEntry parent2, File file2) {
        if (file2 != null) {
            this.file = file2;
            this.parent = parent2;
            this.name = file2.getName();
            return;
        }
        throw new IllegalArgumentException("File is missing");
    }

    public boolean refresh(File file2) {
        boolean origExists = this.exists;
        long origLastModified = this.lastModified;
        boolean origDirectory = this.directory;
        long origLength = this.length;
        this.name = file2.getName();
        boolean exists2 = file2.exists();
        this.exists = exists2;
        this.directory = exists2 && file2.isDirectory();
        long j = 0;
        this.lastModified = this.exists ? file2.lastModified() : 0;
        if (this.exists && !this.directory) {
            j = file2.length();
        }
        this.length = j;
        if (this.exists == origExists && this.lastModified == origLastModified && this.directory == origDirectory && j == origLength) {
            return false;
        }
        return true;
    }

    public FileEntry newChildInstance(File file2) {
        return new FileEntry(this, file2);
    }

    public FileEntry getParent() {
        return this.parent;
    }

    public int getLevel() {
        FileEntry fileEntry = this.parent;
        if (fileEntry == null) {
            return 0;
        }
        return fileEntry.getLevel() + 1;
    }

    public FileEntry[] getChildren() {
        FileEntry[] fileEntryArr = this.children;
        return fileEntryArr != null ? fileEntryArr : EMPTY_ENTRIES;
    }

    public void setChildren(FileEntry[] children2) {
        this.children = children2;
    }

    public File getFile() {
        return this.file;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public long getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(long lastModified2) {
        this.lastModified = lastModified2;
    }

    public long getLength() {
        return this.length;
    }

    public void setLength(long length2) {
        this.length = length2;
    }

    public boolean isExists() {
        return this.exists;
    }

    public void setExists(boolean exists2) {
        this.exists = exists2;
    }

    public boolean isDirectory() {
        return this.directory;
    }

    public void setDirectory(boolean directory2) {
        this.directory = directory2;
    }
}
