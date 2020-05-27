package org.apache.commons.p004io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.p004io.IOCase;

/* renamed from: org.apache.commons.io.filefilter.SuffixFileFilter */
public class SuffixFileFilter extends AbstractFileFilter implements Serializable {
    private static final long serialVersionUID = -3389157631240246157L;
    private final IOCase caseSensitivity;
    private final String[] suffixes;

    public SuffixFileFilter(String suffix) {
        this(suffix, IOCase.SENSITIVE);
    }

    public SuffixFileFilter(String suffix, IOCase caseSensitivity2) {
        if (suffix != null) {
            this.suffixes = new String[]{suffix};
            this.caseSensitivity = caseSensitivity2 == null ? IOCase.SENSITIVE : caseSensitivity2;
            return;
        }
        throw new IllegalArgumentException("The suffix must not be null");
    }

    public SuffixFileFilter(String[] suffixes2) {
        this(suffixes2, IOCase.SENSITIVE);
    }

    public SuffixFileFilter(String[] suffixes2, IOCase caseSensitivity2) {
        if (suffixes2 != null) {
            String[] strArr = new String[suffixes2.length];
            this.suffixes = strArr;
            System.arraycopy(suffixes2, 0, strArr, 0, suffixes2.length);
            this.caseSensitivity = caseSensitivity2 == null ? IOCase.SENSITIVE : caseSensitivity2;
            return;
        }
        throw new IllegalArgumentException("The array of suffixes must not be null");
    }

    public SuffixFileFilter(List<String> suffixes2) {
        this(suffixes2, IOCase.SENSITIVE);
    }

    public SuffixFileFilter(List<String> suffixes2, IOCase caseSensitivity2) {
        if (suffixes2 != null) {
            this.suffixes = (String[]) suffixes2.toArray(new String[suffixes2.size()]);
            this.caseSensitivity = caseSensitivity2 == null ? IOCase.SENSITIVE : caseSensitivity2;
            return;
        }
        throw new IllegalArgumentException("The list of suffixes must not be null");
    }

    public boolean accept(File file) {
        String name = file.getName();
        for (String suffix : this.suffixes) {
            if (this.caseSensitivity.checkEndsWith(name, suffix)) {
                return true;
            }
        }
        return false;
    }

    public boolean accept(File file, String name) {
        for (String suffix : this.suffixes) {
            if (this.caseSensitivity.checkEndsWith(name, suffix)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(super.toString());
        buffer.append("(");
        if (this.suffixes != null) {
            for (int i = 0; i < this.suffixes.length; i++) {
                if (i > 0) {
                    buffer.append(",");
                }
                buffer.append(this.suffixes[i]);
            }
        }
        buffer.append(")");
        return buffer.toString();
    }
}
