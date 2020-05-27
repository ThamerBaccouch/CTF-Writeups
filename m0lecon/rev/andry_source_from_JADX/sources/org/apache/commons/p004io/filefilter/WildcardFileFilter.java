package org.apache.commons.p004io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.p004io.FilenameUtils;
import org.apache.commons.p004io.IOCase;

/* renamed from: org.apache.commons.io.filefilter.WildcardFileFilter */
public class WildcardFileFilter extends AbstractFileFilter implements Serializable {
    private static final long serialVersionUID = -7426486598995782105L;
    private final IOCase caseSensitivity;
    private final String[] wildcards;

    public WildcardFileFilter(String wildcard) {
        this(wildcard, IOCase.SENSITIVE);
    }

    public WildcardFileFilter(String wildcard, IOCase caseSensitivity2) {
        if (wildcard != null) {
            this.wildcards = new String[]{wildcard};
            this.caseSensitivity = caseSensitivity2 == null ? IOCase.SENSITIVE : caseSensitivity2;
            return;
        }
        throw new IllegalArgumentException("The wildcard must not be null");
    }

    public WildcardFileFilter(String[] wildcards2) {
        this(wildcards2, IOCase.SENSITIVE);
    }

    public WildcardFileFilter(String[] wildcards2, IOCase caseSensitivity2) {
        if (wildcards2 != null) {
            String[] strArr = new String[wildcards2.length];
            this.wildcards = strArr;
            System.arraycopy(wildcards2, 0, strArr, 0, wildcards2.length);
            this.caseSensitivity = caseSensitivity2 == null ? IOCase.SENSITIVE : caseSensitivity2;
            return;
        }
        throw new IllegalArgumentException("The wildcard array must not be null");
    }

    public WildcardFileFilter(List<String> wildcards2) {
        this(wildcards2, IOCase.SENSITIVE);
    }

    public WildcardFileFilter(List<String> wildcards2, IOCase caseSensitivity2) {
        if (wildcards2 != null) {
            this.wildcards = (String[]) wildcards2.toArray(new String[wildcards2.size()]);
            this.caseSensitivity = caseSensitivity2 == null ? IOCase.SENSITIVE : caseSensitivity2;
            return;
        }
        throw new IllegalArgumentException("The wildcard list must not be null");
    }

    public boolean accept(File dir, String name) {
        for (String wildcard : this.wildcards) {
            if (FilenameUtils.wildcardMatch(name, wildcard, this.caseSensitivity)) {
                return true;
            }
        }
        return false;
    }

    public boolean accept(File file) {
        String name = file.getName();
        for (String wildcard : this.wildcards) {
            if (FilenameUtils.wildcardMatch(name, wildcard, this.caseSensitivity)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(super.toString());
        buffer.append("(");
        if (this.wildcards != null) {
            for (int i = 0; i < this.wildcards.length; i++) {
                if (i > 0) {
                    buffer.append(",");
                }
                buffer.append(this.wildcards[i]);
            }
        }
        buffer.append(")");
        return buffer.toString();
    }
}
