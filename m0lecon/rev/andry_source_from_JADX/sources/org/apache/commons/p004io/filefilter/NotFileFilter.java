package org.apache.commons.p004io.filefilter;

import java.io.File;
import java.io.Serializable;

/* renamed from: org.apache.commons.io.filefilter.NotFileFilter */
public class NotFileFilter extends AbstractFileFilter implements Serializable {
    private static final long serialVersionUID = 6131563330944994230L;
    private final IOFileFilter filter;

    public NotFileFilter(IOFileFilter filter2) {
        if (filter2 != null) {
            this.filter = filter2;
            return;
        }
        throw new IllegalArgumentException("The filter must not be null");
    }

    public boolean accept(File file) {
        return !this.filter.accept(file);
    }

    public boolean accept(File file, String name) {
        return !this.filter.accept(file, name);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("(");
        sb.append(this.filter.toString());
        sb.append(")");
        return sb.toString();
    }
}
