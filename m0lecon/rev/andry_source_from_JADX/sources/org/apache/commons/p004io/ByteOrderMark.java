package org.apache.commons.p004io;

import java.io.Serializable;

/* renamed from: org.apache.commons.io.ByteOrderMark */
public class ByteOrderMark implements Serializable {
    public static final ByteOrderMark UTF_16BE = new ByteOrderMark("UTF-16BE", 254, 255);
    public static final ByteOrderMark UTF_16LE = new ByteOrderMark("UTF-16LE", 255, 254);
    public static final ByteOrderMark UTF_32BE = new ByteOrderMark("UTF-32BE", 0, 0, 254, 255);
    public static final ByteOrderMark UTF_32LE = new ByteOrderMark("UTF-32LE", 255, 254, 0, 0);
    public static final ByteOrderMark UTF_8 = new ByteOrderMark("UTF-8", 239, 187, 191);
    public static final char UTF_BOM = '﻿';
    private static final long serialVersionUID = 1;
    private final int[] bytes;
    private final String charsetName;

    public ByteOrderMark(String charsetName2, int... bytes2) {
        if (charsetName2 == null || charsetName2.isEmpty()) {
            throw new IllegalArgumentException("No charsetName specified");
        } else if (bytes2 == null || bytes2.length == 0) {
            throw new IllegalArgumentException("No bytes specified");
        } else {
            this.charsetName = charsetName2;
            int[] iArr = new int[bytes2.length];
            this.bytes = iArr;
            System.arraycopy(bytes2, 0, iArr, 0, bytes2.length);
        }
    }

    public String getCharsetName() {
        return this.charsetName;
    }

    public int length() {
        return this.bytes.length;
    }

    public int get(int pos) {
        return this.bytes[pos];
    }

    public byte[] getBytes() {
        byte[] copy = new byte[this.bytes.length];
        int i = 0;
        while (true) {
            int[] iArr = this.bytes;
            if (i >= iArr.length) {
                return copy;
            }
            copy[i] = (byte) iArr[i];
            i++;
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ByteOrderMark)) {
            return false;
        }
        ByteOrderMark bom = (ByteOrderMark) obj;
        if (this.bytes.length != bom.length()) {
            return false;
        }
        int i = 0;
        while (true) {
            int[] iArr = this.bytes;
            if (i >= iArr.length) {
                return true;
            }
            if (iArr[i] != bom.get(i)) {
                return false;
            }
            i++;
        }
    }

    public int hashCode() {
        int hashCode = getClass().hashCode();
        for (int b : this.bytes) {
            hashCode += b;
        }
        return hashCode;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getSimpleName());
        builder.append('[');
        builder.append(this.charsetName);
        builder.append(": ");
        for (int i = 0; i < this.bytes.length; i++) {
            if (i > 0) {
                builder.append(",");
            }
            builder.append("0x");
            builder.append(Integer.toHexString(this.bytes[i] & 255).toUpperCase());
        }
        builder.append(']');
        return builder.toString();
    }
}
