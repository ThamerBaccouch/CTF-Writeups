package org.apache.commons.p004io.input;

import java.io.InputStream;

/* renamed from: org.apache.commons.io.input.InfiniteCircularInputStream */
public class InfiniteCircularInputStream extends InputStream {
    private int position = -1;
    private final byte[] repeatedContent;

    public InfiniteCircularInputStream(byte[] repeatedContent2) {
        this.repeatedContent = repeatedContent2;
    }

    public int read() {
        int i = this.position + 1;
        byte[] bArr = this.repeatedContent;
        int length = i % bArr.length;
        this.position = length;
        return bArr[length] & 255;
    }
}
