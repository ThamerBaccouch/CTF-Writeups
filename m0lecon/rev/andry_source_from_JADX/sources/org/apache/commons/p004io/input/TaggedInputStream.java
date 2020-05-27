package org.apache.commons.p004io.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.UUID;
import org.apache.commons.p004io.TaggedIOException;

/* renamed from: org.apache.commons.io.input.TaggedInputStream */
public class TaggedInputStream extends ProxyInputStream {
    private final Serializable tag = UUID.randomUUID();

    public TaggedInputStream(InputStream proxy) {
        super(proxy);
    }

    public boolean isCauseOf(Throwable exception) {
        return TaggedIOException.isTaggedWith(exception, this.tag);
    }

    public void throwIfCauseOf(Throwable throwable) throws IOException {
        TaggedIOException.throwCauseIfTaggedWith(throwable, this.tag);
    }

    /* access modifiers changed from: protected */
    public void handleIOException(IOException e) throws IOException {
        throw new TaggedIOException(e, this.tag);
    }
}
