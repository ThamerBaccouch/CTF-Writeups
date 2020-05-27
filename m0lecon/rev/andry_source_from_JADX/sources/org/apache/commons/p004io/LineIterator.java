package org.apache.commons.p004io;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* renamed from: org.apache.commons.io.LineIterator */
public class LineIterator implements Iterator<String>, Closeable {
    private final BufferedReader bufferedReader;
    private String cachedLine;
    private boolean finished = false;

    public LineIterator(Reader reader) throws IllegalArgumentException {
        if (reader == null) {
            throw new IllegalArgumentException("Reader must not be null");
        } else if (reader instanceof BufferedReader) {
            this.bufferedReader = (BufferedReader) reader;
        } else {
            this.bufferedReader = new BufferedReader(reader);
        }
    }

    public boolean hasNext() {
        String line;
        if (this.cachedLine != null) {
            return true;
        }
        if (this.finished) {
            return false;
        }
        do {
            try {
                line = this.bufferedReader.readLine();
                if (line == null) {
                    this.finished = true;
                    return false;
                }
            } catch (IOException ioe) {
                try {
                    close();
                } catch (IOException e) {
                    ioe.addSuppressed(e);
                }
                throw new IllegalStateException(ioe);
            }
        } while (!isValidLine(line));
        this.cachedLine = line;
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isValidLine(String line) {
        return true;
    }

    public String next() {
        return nextLine();
    }

    public String nextLine() {
        if (hasNext()) {
            String currentLine = this.cachedLine;
            this.cachedLine = null;
            return currentLine;
        }
        throw new NoSuchElementException("No more lines");
    }

    public void close() throws IOException {
        this.finished = true;
        this.cachedLine = null;
        BufferedReader bufferedReader2 = this.bufferedReader;
        if (bufferedReader2 != null) {
            bufferedReader2.close();
        }
    }

    public void remove() {
        throw new UnsupportedOperationException("Remove unsupported on LineIterator");
    }

    @Deprecated
    public static void closeQuietly(LineIterator iterator) {
        if (iterator != null) {
            try {
                iterator.close();
            } catch (IOException e) {
            }
        }
    }
}
