package org.apache.commons.p004io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.regex.Pattern;
import org.apache.commons.p004io.IOCase;

/* renamed from: org.apache.commons.io.filefilter.RegexFileFilter */
public class RegexFileFilter extends AbstractFileFilter implements Serializable {
    private static final long serialVersionUID = 4269646126155225062L;
    private final Pattern pattern;

    public RegexFileFilter(String pattern2) {
        if (pattern2 != null) {
            this.pattern = Pattern.compile(pattern2);
            return;
        }
        throw new IllegalArgumentException("Pattern is missing");
    }

    public RegexFileFilter(String pattern2, IOCase caseSensitivity) {
        if (pattern2 != null) {
            int flags = 0;
            if (caseSensitivity != null && !caseSensitivity.isCaseSensitive()) {
                flags = 2;
            }
            this.pattern = Pattern.compile(pattern2, flags);
            return;
        }
        throw new IllegalArgumentException("Pattern is missing");
    }

    public RegexFileFilter(String pattern2, int flags) {
        if (pattern2 != null) {
            this.pattern = Pattern.compile(pattern2, flags);
            return;
        }
        throw new IllegalArgumentException("Pattern is missing");
    }

    public RegexFileFilter(Pattern pattern2) {
        if (pattern2 != null) {
            this.pattern = pattern2;
            return;
        }
        throw new IllegalArgumentException("Pattern is missing");
    }

    public boolean accept(File dir, String name) {
        return this.pattern.matcher(name).matches();
    }
}
