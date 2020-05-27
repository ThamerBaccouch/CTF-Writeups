package org.apache.commons.p004io.output;

import java.io.Serializable;
import java.io.Writer;

/* renamed from: org.apache.commons.io.output.StringBuilderWriter */
public class StringBuilderWriter extends Writer implements Serializable {
    private static final long serialVersionUID = -146927496096066153L;
    private final StringBuilder builder;

    public StringBuilderWriter() {
        this.builder = new StringBuilder();
    }

    public StringBuilderWriter(int capacity) {
        this.builder = new StringBuilder(capacity);
    }

    public StringBuilderWriter(StringBuilder builder2) {
        this.builder = builder2 != null ? builder2 : new StringBuilder();
    }

    public Writer append(char value) {
        this.builder.append(value);
        return this;
    }

    public Writer append(CharSequence value) {
        this.builder.append(value);
        return this;
    }

    public Writer append(CharSequence value, int start, int end) {
        this.builder.append(value, start, end);
        return this;
    }

    public void close() {
    }

    public void flush() {
    }

    public void write(String value) {
        if (value != null) {
            this.builder.append(value);
        }
    }

    public void write(char[] value, int offset, int length) {
        if (value != null) {
            this.builder.append(value, offset, length);
        }
    }

    public StringBuilder getBuilder() {
        return this.builder;
    }

    public String toString() {
        return this.builder.toString();
    }
}
