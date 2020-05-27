package org.apache.commons.p004io.filefilter;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

/* renamed from: org.apache.commons.io.filefilter.IOFileFilter */
public interface IOFileFilter extends FileFilter, FilenameFilter {
    boolean accept(File file);

    boolean accept(File file, String str);
}
