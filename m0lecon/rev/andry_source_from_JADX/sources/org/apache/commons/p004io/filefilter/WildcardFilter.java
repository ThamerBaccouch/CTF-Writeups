package org.apache.commons.p004io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.p004io.FilenameUtils;

@Deprecated
/* renamed from: org.apache.commons.io.filefilter.WildcardFilter */
public class WildcardFilter extends AbstractFileFilter implements Serializable {
    private static final long serialVersionUID = -5037645902506953517L;
    private final String[] wildcards;

    public WildcardFilter(String wildcard) {
        if (wildcard != null) {
            this.wildcards = new String[]{wildcard};
            return;
        }
        throw new IllegalArgumentException("The wildcard must not be null");
    }

    public WildcardFilter(String[] wildcards2) {
        if (wildcards2 != null) {
            String[] strArr = new String[wildcards2.length];
            this.wildcards = strArr;
            System.arraycopy(wildcards2, 0, strArr, 0, wildcards2.length);
            return;
        }
        throw new IllegalArgumentException("The wildcard array must not be null");
    }

    public WildcardFilter(List<String> wildcards2) {
        if (wildcards2 != null) {
            this.wildcards = (String[]) wildcards2.toArray(new String[wildcards2.size()]);
            return;
        }
        throw new IllegalArgumentException("The wildcard list must not be null");
    }

    public boolean accept(File dir, String name) {
        if (dir != null && new File(dir, name).isDirectory()) {
            return false;
        }
        for (String wildcard : this.wildcards) {
            if (FilenameUtils.wildcardMatch(name, wildcard)) {
                return true;
            }
        }
        return false;
    }

    public boolean accept(File file) {
        if (file.isDirectory()) {
            return false;
        }
        for (String wildcard : this.wildcards) {
            if (FilenameUtils.wildcardMatch(file.getName(), wildcard)) {
                return true;
            }
        }
        return false;
    }
}
