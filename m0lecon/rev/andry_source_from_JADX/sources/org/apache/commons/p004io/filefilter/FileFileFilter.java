package org.apache.commons.p004io.filefilter;

import java.io.File;
import java.io.Serializable;

/* renamed from: org.apache.commons.io.filefilter.FileFileFilter */
public class FileFileFilter extends AbstractFileFilter implements Serializable {
    public static final IOFileFilter FILE = new FileFileFilter();
    private static final long serialVersionUID = 5345244090827540862L;

    protected FileFileFilter() {
    }

    public boolean accept(File file) {
        return file.isFile();
    }
}
