package org.apache.commons.p004io;

import java.io.IOException;
import java.io.Serializable;

/* renamed from: org.apache.commons.io.TaggedIOException */
public class TaggedIOException extends IOExceptionWithCause {
    private static final long serialVersionUID = -6994123481142850163L;
    private final Serializable tag;

    public static boolean isTaggedWith(Throwable throwable, Object tag2) {
        return tag2 != null && (throwable instanceof TaggedIOException) && tag2.equals(((TaggedIOException) throwable).tag);
    }

    public static void throwCauseIfTaggedWith(Throwable throwable, Object tag2) throws IOException {
        if (isTaggedWith(throwable, tag2)) {
            throw ((TaggedIOException) throwable).getCause();
        }
    }

    public TaggedIOException(IOException original, Serializable tag2) {
        super(original.getMessage(), original);
        this.tag = tag2;
    }

    public Serializable getTag() {
        return this.tag;
    }

    public IOException getCause() {
        return (IOException) super.getCause();
    }
}
