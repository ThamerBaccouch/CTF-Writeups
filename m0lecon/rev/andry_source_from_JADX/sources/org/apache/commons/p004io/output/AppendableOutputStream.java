package org.apache.commons.p004io.output;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.Appendable;

/* renamed from: org.apache.commons.io.output.AppendableOutputStream */
public class AppendableOutputStream<T extends Appendable> extends OutputStream {
    private final T appendable;

    public AppendableOutputStream(T appendable2) {
        this.appendable = appendable2;
    }

    public void write(int b) throws IOException {
        this.appendable.append((char) b);
    }

    public T getAppendable() {
        return this.appendable;
    }
}
