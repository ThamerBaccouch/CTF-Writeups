package org.apache.commons.p004io.monitor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadFactory;

/* renamed from: org.apache.commons.io.monitor.FileAlterationMonitor */
public final class FileAlterationMonitor implements Runnable {
    private final long interval;
    private final List<FileAlterationObserver> observers;
    private volatile boolean running;
    private Thread thread;
    private ThreadFactory threadFactory;

    public FileAlterationMonitor() {
        this(10000);
    }

    public FileAlterationMonitor(long interval2) {
        this.observers = new CopyOnWriteArrayList();
        this.thread = null;
        this.running = false;
        this.interval = interval2;
    }

    public FileAlterationMonitor(long interval2, FileAlterationObserver... observers2) {
        this(interval2);
        if (observers2 != null) {
            for (FileAlterationObserver observer : observers2) {
                addObserver(observer);
            }
        }
    }

    public long getInterval() {
        return this.interval;
    }

    public synchronized void setThreadFactory(ThreadFactory threadFactory2) {
        this.threadFactory = threadFactory2;
    }

    public void addObserver(FileAlterationObserver observer) {
        if (observer != null) {
            this.observers.add(observer);
        }
    }

    public void removeObserver(FileAlterationObserver observer) {
        if (observer != null) {
            do {
            } while (this.observers.remove(observer));
        }
    }

    public Iterable<FileAlterationObserver> getObservers() {
        return this.observers;
    }

    public synchronized void start() throws Exception {
        if (!this.running) {
            for (FileAlterationObserver observer : this.observers) {
                observer.initialize();
            }
            this.running = true;
            if (this.threadFactory != null) {
                this.thread = this.threadFactory.newThread(this);
            } else {
                this.thread = new Thread(this);
            }
            this.thread.start();
        } else {
            throw new IllegalStateException("Monitor is already running");
        }
    }

    public synchronized void stop() throws Exception {
        stop(this.interval);
    }

    public synchronized void stop(long stopInterval) throws Exception {
        if (this.running) {
            this.running = false;
            try {
                this.thread.join(stopInterval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            for (FileAlterationObserver observer : this.observers) {
                observer.destroy();
            }
        } else {
            throw new IllegalStateException("Monitor is not running");
        }
    }

    public void run() {
        while (this.running) {
            for (FileAlterationObserver observer : this.observers) {
                observer.checkAndNotify();
            }
            if (this.running) {
                try {
                    Thread.sleep(this.interval);
                } catch (InterruptedException e) {
                }
            } else {
                return;
            }
        }
    }
}
