package org.apache.commons.p004io.input;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: org.apache.commons.io.input.WindowsLineEndingInputStream */
public class WindowsLineEndingInputStream extends InputStream {
    private final boolean ensureLineFeedAtEndOfFile;
    private boolean eofSeen = false;
    private boolean injectSlashN = false;
    private boolean slashNSeen = false;
    private boolean slashRSeen = false;
    private final InputStream target;

    public WindowsLineEndingInputStream(InputStream in, boolean ensureLineFeedAtEndOfFile2) {
        this.target = in;
        this.ensureLineFeedAtEndOfFile = ensureLineFeedAtEndOfFile2;
    }

    private int readWithUpdate() throws IOException {
        int target2 = this.target.read();
        boolean z = true;
        boolean z2 = target2 == -1;
        this.eofSeen = z2;
        if (z2) {
            return target2;
        }
        this.slashRSeen = target2 == 13;
        if (target2 != 10) {
            z = false;
        }
        this.slashNSeen = z;
        return target2;
    }

    public int read() throws IOException {
        if (this.eofSeen) {
            return eofGame();
        }
        if (this.injectSlashN) {
            this.injectSlashN = false;
            return 10;
        }
        boolean prevWasSlashR = this.slashRSeen;
        int target2 = readWithUpdate();
        if (this.eofSeen) {
            return eofGame();
        }
        if (target2 != 10 || prevWasSlashR) {
            return target2;
        }
        this.injectSlashN = true;
        return 13;
    }

    private int eofGame() {
        if (!this.ensureLineFeedAtEndOfFile) {
            return -1;
        }
        if (!this.slashNSeen && !this.slashRSeen) {
            this.slashRSeen = true;
            return 13;
        } else if (this.slashNSeen) {
            return -1;
        } else {
            this.slashRSeen = false;
            this.slashNSeen = true;
            return 10;
        }
    }

    public void close() throws IOException {
        super.close();
        this.target.close();
    }

    public synchronized void mark(int readlimit) {
        throw new UnsupportedOperationException("Mark not supported");
    }
}
