package org.apache.commons.p004io;

import java.io.File;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/* renamed from: org.apache.commons.io.FileCleaningTracker */
public class FileCleaningTracker {
    final List<String> deleteFailures = Collections.synchronizedList(new ArrayList());
    volatile boolean exitWhenFinished = false;

    /* renamed from: q */
    ReferenceQueue<Object> f35q = new ReferenceQueue<>();
    Thread reaper;
    final Collection<Tracker> trackers = Collections.synchronizedSet(new HashSet());

    /* renamed from: org.apache.commons.io.FileCleaningTracker$Reaper */
    private final class Reaper extends Thread {
        Reaper() {
            super("File Reaper");
            setPriority(10);
            setDaemon(true);
        }

        public void run() {
            while (true) {
                if (!FileCleaningTracker.this.exitWhenFinished || FileCleaningTracker.this.trackers.size() > 0) {
                    try {
                        Tracker tracker = (Tracker) FileCleaningTracker.this.f35q.remove();
                        FileCleaningTracker.this.trackers.remove(tracker);
                        if (!tracker.delete()) {
                            FileCleaningTracker.this.deleteFailures.add(tracker.getPath());
                        }
                        tracker.clear();
                    } catch (InterruptedException e) {
                    }
                } else {
                    return;
                }
            }
        }
    }

    /* renamed from: org.apache.commons.io.FileCleaningTracker$Tracker */
    private static final class Tracker extends PhantomReference<Object> {
        private final FileDeleteStrategy deleteStrategy;
        private final String path;

        Tracker(String path2, FileDeleteStrategy deleteStrategy2, Object marker, ReferenceQueue<? super Object> queue) {
            super(marker, queue);
            this.path = path2;
            this.deleteStrategy = deleteStrategy2 == null ? FileDeleteStrategy.NORMAL : deleteStrategy2;
        }

        public String getPath() {
            return this.path;
        }

        public boolean delete() {
            return this.deleteStrategy.deleteQuietly(new File(this.path));
        }
    }

    public void track(File file, Object marker) {
        track(file, marker, (FileDeleteStrategy) null);
    }

    public void track(File file, Object marker, FileDeleteStrategy deleteStrategy) {
        if (file != null) {
            addTracker(file.getPath(), marker, deleteStrategy);
            return;
        }
        throw new NullPointerException("The file must not be null");
    }

    public void track(String path, Object marker) {
        track(path, marker, (FileDeleteStrategy) null);
    }

    public void track(String path, Object marker, FileDeleteStrategy deleteStrategy) {
        if (path != null) {
            addTracker(path, marker, deleteStrategy);
            return;
        }
        throw new NullPointerException("The path must not be null");
    }

    private synchronized void addTracker(String path, Object marker, FileDeleteStrategy deleteStrategy) {
        if (!this.exitWhenFinished) {
            if (this.reaper == null) {
                Reaper reaper2 = new Reaper();
                this.reaper = reaper2;
                reaper2.start();
            }
            this.trackers.add(new Tracker(path, deleteStrategy, marker, this.f35q));
        } else {
            throw new IllegalStateException("No new trackers can be added once exitWhenFinished() is called");
        }
    }

    public int getTrackCount() {
        return this.trackers.size();
    }

    public List<String> getDeleteFailures() {
        return this.deleteFailures;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0015, code lost:
        r1 = th;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void exitWhenFinished() {
        /*
            r2 = this;
            monitor-enter(r2)
            r0 = 1
            r2.exitWhenFinished = r0     // Catch:{ all -> 0x0019 }
            java.lang.Thread r0 = r2.reaper     // Catch:{ all -> 0x0019 }
            if (r0 == 0) goto L_0x0017
            java.lang.Thread r0 = r2.reaper     // Catch:{ all -> 0x0019 }
            monitor-enter(r0)     // Catch:{ all -> 0x0019 }
            java.lang.Thread r1 = r2.reaper     // Catch:{ all -> 0x0012 }
            r1.interrupt()     // Catch:{ all -> 0x0012 }
            monitor-exit(r0)     // Catch:{ all -> 0x0012 }
            goto L_0x0017
        L_0x0012:
            r1 = move-exception
        L_0x0013:
            monitor-exit(r0)     // Catch:{ all -> 0x0015 }
            throw r1     // Catch:{ all -> 0x0019 }
        L_0x0015:
            r1 = move-exception
            goto L_0x0013
        L_0x0017:
            monitor-exit(r2)
            return
        L_0x0019:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p004io.FileCleaningTracker.exitWhenFinished():void");
    }
}
