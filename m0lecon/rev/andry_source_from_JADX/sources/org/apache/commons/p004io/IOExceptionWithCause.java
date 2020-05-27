package org.apache.commons.p004io;

import java.io.IOException;

@Deprecated
/* renamed from: org.apache.commons.io.IOExceptionWithCause */
public class IOExceptionWithCause extends IOException {
    private static final long serialVersionUID = 1;

    public IOExceptionWithCause(String message, Throwable cause) {
        super(message, cause);
    }

    public IOExceptionWithCause(Throwable cause) {
        super(cause);
    }
}
