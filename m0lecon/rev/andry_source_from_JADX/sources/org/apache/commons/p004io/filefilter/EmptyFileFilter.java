package org.apache.commons.p004io.filefilter;

import java.io.File;
import java.io.Serializable;

/* renamed from: org.apache.commons.io.filefilter.EmptyFileFilter */
public class EmptyFileFilter extends AbstractFileFilter implements Serializable {
    public static final IOFileFilter EMPTY = new EmptyFileFilter();
    public static final IOFileFilter NOT_EMPTY = new NotFileFilter(EMPTY);
    private static final long serialVersionUID = 3631422087512832211L;

    protected EmptyFileFilter() {
    }

    public boolean accept(File file) {
        boolean z = true;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (!(files == null || files.length == 0)) {
                z = false;
            }
            return z;
        }
        if (file.length() != 0) {
            z = false;
        }
        return z;
    }
}
