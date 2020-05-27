package org.apache.commons.p004io.filefilter;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.Serializable;

/* renamed from: org.apache.commons.io.filefilter.DelegateFileFilter */
public class DelegateFileFilter extends AbstractFileFilter implements Serializable {
    private static final long serialVersionUID = -8723373124984771318L;
    private final FileFilter fileFilter;
    private final FilenameFilter filenameFilter;

    public DelegateFileFilter(FilenameFilter filter) {
        if (filter != null) {
            this.filenameFilter = filter;
            this.fileFilter = null;
            return;
        }
        throw new IllegalArgumentException("The FilenameFilter must not be null");
    }

    public DelegateFileFilter(FileFilter filter) {
        if (filter != null) {
            this.fileFilter = filter;
            this.filenameFilter = null;
            return;
        }
        throw new IllegalArgumentException("The FileFilter must not be null");
    }

    public boolean accept(File file) {
        FileFilter fileFilter2 = this.fileFilter;
        if (fileFilter2 != null) {
            return fileFilter2.accept(file);
        }
        return super.accept(file);
    }

    public boolean accept(File dir, String name) {
        FilenameFilter filenameFilter2 = this.filenameFilter;
        if (filenameFilter2 != null) {
            return filenameFilter2.accept(dir, name);
        }
        return super.accept(dir, name);
    }

    public String toString() {
        Object obj = this.fileFilter;
        if (obj == null) {
            obj = this.filenameFilter;
        }
        String delegate = obj.toString();
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("(");
        sb.append(delegate);
        sb.append(")");
        return sb.toString();
    }
}
