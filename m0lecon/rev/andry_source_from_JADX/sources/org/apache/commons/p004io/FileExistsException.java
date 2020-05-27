package org.apache.commons.p004io;

import java.io.File;
import java.io.IOException;

/* renamed from: org.apache.commons.io.FileExistsException */
public class FileExistsException extends IOException {
    private static final long serialVersionUID = 1;

    public FileExistsException() {
    }

    public FileExistsException(String message) {
        super(message);
    }

    public FileExistsException(File file) {
        StringBuilder sb = new StringBuilder();
        sb.append("File ");
        sb.append(file);
        sb.append(" exists");
        super(sb.toString());
    }
}
