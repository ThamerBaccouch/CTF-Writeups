package org.apache.commons.p004io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.p004io.IOCase;

/* renamed from: org.apache.commons.io.comparator.PathFileComparator */
public class PathFileComparator extends AbstractFileComparator implements Serializable {
    public static final Comparator<File> PATH_COMPARATOR = new PathFileComparator();
    public static final Comparator<File> PATH_INSENSITIVE_COMPARATOR = new PathFileComparator(IOCase.INSENSITIVE);
    public static final Comparator<File> PATH_INSENSITIVE_REVERSE = new ReverseComparator(PATH_INSENSITIVE_COMPARATOR);
    public static final Comparator<File> PATH_REVERSE = new ReverseComparator(PATH_COMPARATOR);
    public static final Comparator<File> PATH_SYSTEM_COMPARATOR = new PathFileComparator(IOCase.SYSTEM);
    public static final Comparator<File> PATH_SYSTEM_REVERSE = new ReverseComparator(PATH_SYSTEM_COMPARATOR);
    private static final long serialVersionUID = 6527501707585768673L;
    private final IOCase caseSensitivity;

    public /* bridge */ /* synthetic */ List sort(List list) {
        return super.sort(list);
    }

    public /* bridge */ /* synthetic */ File[] sort(File[] fileArr) {
        return super.sort(fileArr);
    }

    public PathFileComparator() {
        this.caseSensitivity = IOCase.SENSITIVE;
    }

    public PathFileComparator(IOCase caseSensitivity2) {
        this.caseSensitivity = caseSensitivity2 == null ? IOCase.SENSITIVE : caseSensitivity2;
    }

    public int compare(File file1, File file2) {
        return this.caseSensitivity.checkCompareTo(file1.getPath(), file2.getPath());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("[caseSensitivity=");
        sb.append(this.caseSensitivity);
        sb.append("]");
        return sb.toString();
    }
}
