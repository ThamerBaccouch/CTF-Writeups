package org.apache.commons.p004io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* renamed from: org.apache.commons.io.comparator.CompositeFileComparator */
public class CompositeFileComparator extends AbstractFileComparator implements Serializable {
    private static final Comparator<?>[] NO_COMPARATORS = new Comparator[0];
    private static final long serialVersionUID = -2224170307287243428L;
    private final Comparator<File>[] delegates;

    public /* bridge */ /* synthetic */ List sort(List list) {
        return super.sort(list);
    }

    public /* bridge */ /* synthetic */ File[] sort(File[] fileArr) {
        return super.sort(fileArr);
    }

    public CompositeFileComparator(Comparator<File>... delegates2) {
        if (delegates2 == null) {
            this.delegates = (Comparator[]) NO_COMPARATORS;
            return;
        }
        Comparator<File>[] comparatorArr = (Comparator[]) new Comparator[delegates2.length];
        this.delegates = comparatorArr;
        System.arraycopy(delegates2, 0, comparatorArr, 0, delegates2.length);
    }

    public CompositeFileComparator(Iterable<Comparator<File>> delegates2) {
        if (delegates2 == null) {
            this.delegates = (Comparator[]) NO_COMPARATORS;
            return;
        }
        List<Comparator<File>> list = new ArrayList<>();
        for (Comparator<File> comparator : delegates2) {
            list.add(comparator);
        }
        this.delegates = (Comparator[]) list.toArray(new Comparator[list.size()]);
    }

    public int compare(File file1, File file2) {
        int result = 0;
        for (Comparator<File> delegate : this.delegates) {
            result = delegate.compare(file1, file2);
            if (result != 0) {
                break;
            }
        }
        return result;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append('{');
        for (int i = 0; i < this.delegates.length; i++) {
            if (i > 0) {
                builder.append(',');
            }
            builder.append(this.delegates[i]);
        }
        builder.append('}');
        return builder.toString();
    }
}
