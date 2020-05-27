package org.apache.commons.p004io.input;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.p004io.ByteOrderMark;

/* renamed from: org.apache.commons.io.input.BOMInputStream */
public class BOMInputStream extends ProxyInputStream {
    private static final Comparator<ByteOrderMark> ByteOrderMarkLengthComparator = new Comparator<ByteOrderMark>() {
        public int compare(ByteOrderMark bom1, ByteOrderMark bom2) {
            int len1 = bom1.length();
            int len2 = bom2.length();
            if (len1 > len2) {
                return -1;
            }
            if (len2 > len1) {
                return 1;
            }
            return 0;
        }
    };
    private final List<ByteOrderMark> boms;
    private ByteOrderMark byteOrderMark;
    private int fbIndex;
    private int fbLength;
    private int[] firstBytes;
    private final boolean include;
    private int markFbIndex;
    private boolean markedAtStart;

    public BOMInputStream(InputStream delegate) {
        this(delegate, false, ByteOrderMark.UTF_8);
    }

    public BOMInputStream(InputStream delegate, boolean include2) {
        this(delegate, include2, ByteOrderMark.UTF_8);
    }

    public BOMInputStream(InputStream delegate, ByteOrderMark... boms2) {
        this(delegate, false, boms2);
    }

    public BOMInputStream(InputStream delegate, boolean include2, ByteOrderMark... boms2) {
        super(delegate);
        if (boms2 == null || boms2.length == 0) {
            throw new IllegalArgumentException("No BOMs specified");
        }
        this.include = include2;
        List<ByteOrderMark> list = Arrays.asList(boms2);
        Collections.sort(list, ByteOrderMarkLengthComparator);
        this.boms = list;
    }

    public boolean hasBOM() throws IOException {
        return getBOM() != null;
    }

    public boolean hasBOM(ByteOrderMark bom) throws IOException {
        if (this.boms.contains(bom)) {
            getBOM();
            ByteOrderMark byteOrderMark2 = this.byteOrderMark;
            return byteOrderMark2 != null && byteOrderMark2.equals(bom);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Stream not configure to detect ");
        sb.append(bom);
        throw new IllegalArgumentException(sb.toString());
    }

    public ByteOrderMark getBOM() throws IOException {
        if (this.firstBytes == null) {
            this.fbLength = 0;
            this.firstBytes = new int[((ByteOrderMark) this.boms.get(0)).length()];
            int i = 0;
            while (true) {
                int[] iArr = this.firstBytes;
                if (i >= iArr.length) {
                    break;
                }
                iArr[i] = this.in.read();
                this.fbLength++;
                if (this.firstBytes[i] < 0) {
                    break;
                }
                i++;
            }
            ByteOrderMark find = find();
            this.byteOrderMark = find;
            if (find != null && !this.include) {
                if (find.length() < this.firstBytes.length) {
                    this.fbIndex = this.byteOrderMark.length();
                } else {
                    this.fbLength = 0;
                }
            }
        }
        return this.byteOrderMark;
    }

    public String getBOMCharsetName() throws IOException {
        getBOM();
        ByteOrderMark byteOrderMark2 = this.byteOrderMark;
        if (byteOrderMark2 == null) {
            return null;
        }
        return byteOrderMark2.getCharsetName();
    }

    private int readFirstBytes() throws IOException {
        getBOM();
        int i = this.fbIndex;
        if (i >= this.fbLength) {
            return -1;
        }
        int[] iArr = this.firstBytes;
        this.fbIndex = i + 1;
        return iArr[i];
    }

    private ByteOrderMark find() {
        for (ByteOrderMark bom : this.boms) {
            if (matches(bom)) {
                return bom;
            }
        }
        return null;
    }

    private boolean matches(ByteOrderMark bom) {
        for (int i = 0; i < bom.length(); i++) {
            if (bom.get(i) != this.firstBytes[i]) {
                return false;
            }
        }
        return true;
    }

    public int read() throws IOException {
        int b = readFirstBytes();
        return b >= 0 ? b : this.in.read();
    }

    public int read(byte[] buf, int off, int len) throws IOException {
        int firstCount = 0;
        int b = 0;
        while (len > 0 && b >= 0) {
            b = readFirstBytes();
            if (b >= 0) {
                int off2 = off + 1;
                buf[off] = (byte) (b & 255);
                len--;
                firstCount++;
                off = off2;
            }
        }
        int secondCount = this.in.read(buf, off, len);
        if (secondCount >= 0) {
            return firstCount + secondCount;
        }
        if (firstCount > 0) {
            return firstCount;
        }
        return -1;
    }

    public int read(byte[] buf) throws IOException {
        return read(buf, 0, buf.length);
    }

    public synchronized void mark(int readlimit) {
        this.markFbIndex = this.fbIndex;
        this.markedAtStart = this.firstBytes == null;
        this.in.mark(readlimit);
    }

    public synchronized void reset() throws IOException {
        this.fbIndex = this.markFbIndex;
        if (this.markedAtStart) {
            this.firstBytes = null;
        }
        this.in.reset();
    }

    public long skip(long n) throws IOException {
        int skipped = 0;
        while (n > ((long) skipped) && readFirstBytes() >= 0) {
            skipped++;
        }
        return this.in.skip(n - ((long) skipped)) + ((long) skipped);
    }
}
