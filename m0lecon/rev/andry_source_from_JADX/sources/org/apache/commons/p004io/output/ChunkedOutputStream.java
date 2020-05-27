package org.apache.commons.p004io.output;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* renamed from: org.apache.commons.io.output.ChunkedOutputStream */
public class ChunkedOutputStream extends FilterOutputStream {
    private static final int DEFAULT_CHUNK_SIZE = 4096;
    private final int chunkSize;

    public ChunkedOutputStream(OutputStream stream, int chunkSize2) {
        super(stream);
        if (chunkSize2 > 0) {
            this.chunkSize = chunkSize2;
            return;
        }
        throw new IllegalArgumentException();
    }

    public ChunkedOutputStream(OutputStream stream) {
        this(stream, 4096);
    }

    public void write(byte[] data, int srcOffset, int length) throws IOException {
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
