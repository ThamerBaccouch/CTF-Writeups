package org.apache.commons.p004io.filefilter;

import java.io.File;
import java.io.Serializable;

/* renamed from: org.apache.commons.io.filefilter.SizeFileFilter */
public class SizeFileFilter extends AbstractFileFilter implements Serializable {
    private static final long serialVersionUID = 7388077430788600069L;
    private final boolean acceptLarger;
    private final long size;

    public SizeFileFilter(long size2) {
        this(size2, true);
    }

    public SizeFileFilter(long size2, boolean acceptLarger2) {
        if (size2 >= 0) {
            this.size = size2;
            this.acceptLarger = acceptLarger2;
            return;
        }
        throw new IllegalArgumentException("The size must be non-negative");
    }

    public boolean accept(File file) {
        boolean smaller = file.length() < this.size;
        if (!this.acceptLarger) {
            return smaller;
        }
        if (!smaller) {
            return true;
        }
        return false;
    }

    public String toString() {
        String condition = this.acceptLarger ? ">=" : "<";
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("(");
        sb.append(condition);
        sb.append(this.size);
        sb.append(")");
        return sb.toString();
    }
}
