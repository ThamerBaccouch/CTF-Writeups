package org.apache.commons.p004io.output;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/* renamed from: org.apache.commons.io.output.ChunkedWriter */
public class ChunkedWriter extends FilterWriter {
    private static final int DEFAULT_CHUNK_SIZE = 4096;
    private final int chunkSize;

    public ChunkedWriter(Writer writer, int chunkSize2) {
        super(writer);
        if (chunkSize2 > 0) {
            this.chunkSize = chunkSize2;
            return;
        }
        throw new IllegalArgumentException();
    }

    public ChunkedWriter(Writer writer) {
        this(writer, 4096);
    }

    public void write(char[] data, int srcOffset, int length) throws IOException {
        int bytes = length;
        int dstOffset = srcOffset;
        while (bytes > 0) {
            int chunk = Math.min(bytes, this.chunkSize);
            this.out.write(data, dstOffset, chunk);
            bytes -= chunk;
            dstOffset += chunk;
        }
    }
}
