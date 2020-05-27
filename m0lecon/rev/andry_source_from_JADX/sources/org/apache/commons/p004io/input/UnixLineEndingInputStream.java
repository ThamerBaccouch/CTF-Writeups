package org.apache.commons.p004io.input;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: org.apache.commons.io.input.UnixLineEndingInputStream */
public class UnixLineEndingInputStream extends InputStream {
    private final boolean ensureLineFeedAtEndOfFile;
    private boolean eofSeen = false;
    private boolean slashNSeen = false;
    private boolean slashRSeen = false;
    private final InputStream target;

    public UnixLineEndingInputStream(InputStream in, boolean ensureLineFeedAtEndOfFile2) {
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
        this.slashNSeen = target2 == 10;
        if (target2 != 13) {
            z = false;
        }
        this.slashRSeen = z;
        return target2;
    }

    public int read() throws IOException {
        boolean previousWasSlashR = this.slashRSeen;
        if (this.eofSeen) {
            return eofGame(previousWasSlashR);
        }
        int target2 = readWithUpdate();
        if (this.eofSeen) {
            return eofGame(previousWasSlashR);
        }
        if (this.slashRSeen) {
            return 10;
        }
        if (!previousWasSlashR || !this.slashNSeen) {
            return target2;
        }
        return read();
    }

    private int eofGame(boolean previousWasSlashR) {
        if (previousWasSlashR || !this.ensureLineFeedAtEndOfFile || this.slashNSeen) {
            return -1;
        }
        this.slashNSeen = true;
        return 10;
    }

    public void close() throws IOException {
        super.close();
        this.target.close();
    }

    public synchronized void mark(int readlimit) {
        throw new UnsupportedOperationException("Mark notsupported");
    }
}
