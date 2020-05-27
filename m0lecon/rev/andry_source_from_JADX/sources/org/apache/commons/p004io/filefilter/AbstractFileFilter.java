package org.apache.commons.p004io.filefilter;

import java.io.File;

/* renamed from: org.apache.commons.io.filefilter.AbstractFileFilter */
public abstract class AbstractFileFilter implements IOFileFilter {
    public boolean accept(File file) {
        return accept(file.getParentFile(), file.getName());
    }

    public boolean accept(File dir, String name) {
        return accept(new File(dir, name));
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
