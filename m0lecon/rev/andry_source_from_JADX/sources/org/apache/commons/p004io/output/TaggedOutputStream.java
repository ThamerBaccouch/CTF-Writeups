package org.apache.commons.p004io.output;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.UUID;
import org.apache.commons.p004io.TaggedIOException;

/* renamed from: org.apache.commons.io.output.TaggedOutputStream */
public class TaggedOutputStream extends ProxyOutputStream {
    private final Serializable tag = UUID.randomUUID();

    public TaggedOutputStream(OutputStream proxy) {
        super(proxy);
    }

    public boolean isCauseOf(Exception exception) {
        return TaggedIOException.isTaggedWith(exception, this.tag);
    }

    public void throwIfCauseOf(Exception exception) throws IOException {
        TaggedIOException.throwCauseIfTaggedWith(exception, this.tag);
    }

    /* access modifiers changed from: protected */
    public void handleIOException(IOException e) throws IOException {
        throw new TaggedIOException(e, this.tag);
    }
}
