package org.apache.commons.p004io;

/* renamed from: org.apache.commons.io.ThreadMonitor */
class ThreadMonitor implements Runnable {
    private final Thread thread;
    private final long timeout;

    public static Thread start(long timeout2) {
        return start(Thread.currentThread(), timeout2);
    }

    public static Thread start(Thread thread2, long timeout2) {
        if (timeout2 <= 0) {
            return null;
        }
        Thread monitor = new Thread(new ThreadMonitor(thread2, timeout2), ThreadMonitor.class.getSimpleName());
        monitor.setDaemon(true);
        monitor.start();
        return monitor;
    }

    public static void stop(Thread thread2) {
        if (thread2 != null) {
            thread2.interrupt();
        }
    }

    private ThreadMonitor(Thread thread2, long timeout2) {
        this.thread = thread2;
        this.timeout = timeout2;
    }

    public void run() {
        try {
            sleep(this.timeout);
            this.thread.interrupt();
        } catch (InterruptedException e) {
        }
    }

    private static void sleep(long ms) throws InterruptedException {
        long finishAt = System.currentTimeMillis() + ms;
        long remaining = ms;
        do {
            Thread.sleep(remaining);
            remaining = finishAt - System.currentTimeMillis();
        } while (remaining > 0);
    }
}
