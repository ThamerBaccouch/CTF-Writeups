package org.apache.commons.p004io.monitor;

import java.io.File;

/* renamed from: org.apache.commons.io.monitor.FileAlterationListener */
public interface FileAlterationListener {
    void onDirectoryChange(File file);

    void onDirectoryCreate(File file);

    void onDirectoryDelete(File file);

    void onFileChange(File file);

    void onFileCreate(File file);

    void onFileDelete(File file);

    void onStart(FileAlterationObserver fileAlterationObserver);

    void onStop(FileAlterationObserver fileAlterationObserver);
}
