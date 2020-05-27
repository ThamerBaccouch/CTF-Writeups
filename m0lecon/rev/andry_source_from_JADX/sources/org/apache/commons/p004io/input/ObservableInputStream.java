package org.apache.commons.p004io.input;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/* renamed from: org.apache.commons.io.input.ObservableInputStream */
public class ObservableInputStream extends ProxyInputStream {
    private final List<Observer> observers = new ArrayList();

    /* renamed from: org.apache.commons.io.input.ObservableInputStream$Observer */
    public static abstract class Observer {
        /* access modifiers changed from: 0000 */
        public void data(int pByte) throws IOException {
        }

        /* access modifiers changed from: 0000 */
        public void data(byte[] pBuffer, int pOffset, int pLength) throws IOException {
        }

        /* access modifiers changed from: 0000 */
        public void finished() throws IOException {
        }

        /* access modifiers changed from: 0000 */
        public void closed() throws IOException {
        }

        /* access modifiers changed from: 0000 */
        public void error(IOException pException) throws IOException {
            throw pException;
        }
    }

    public ObservableInputStream(InputStream pProxy) {
        super(pProxy);
    }

    public void add(Observer pObserver) {
        this.observers.add(pObserver);
    }

    public void remove(Observer pObserver) {
        this.observers.remove(pObserver);
    }

    public void removeAllObservers() {
        this.observers.clear();
    }

    public int read() throws IOException {
        int result = 0;
        IOException ioe = null;
        try {
            result = super.read();
        } catch (IOException pException) {
            ioe = pException;
        }
        if (ioe != null) {
            noteError(ioe);
        } else if (result == -1) {
            noteFinished();
        } else {
            noteDataByte(result);
        }
        return result;
    }

    public int read(byte[] pBuffer) throws IOException {
        int result = 0;
        IOException ioe = null;
        try {
            result = super.read(pBuffer);
        } catch (IOException pException) {
            ioe = pException;
        }
        if (ioe != null) {
            noteError(ioe);
        } else if (result == -1) {
            noteFinished();
        } else if (result > 0) {
            noteDataBytes(pBuffer, 0, result);
        }
        return result;
    }

    public int read(byte[] pBuffer, int pOffset, int pLength) throws IOException {
        int result = 0;
        IOException ioe = null;
        try {
            result = super.read(pBuffer, pOffset, pLength);
        } catch (IOException pException) {
            ioe = pException;
        }
        if (ioe != null) {
            noteError(ioe);
        } else if (result == -1) {
            noteFinished();
        } else if (result > 0) {
            noteDataBytes(pBuffer, pOffset, result);
        }
        return result;
    }

    /* access modifiers changed from: protected */
    public void noteDataBytes(byte[] pBuffer, int pOffset, int pLength) throws IOException {
        for (Observer observer : getObservers()) {
            observer.data(pBuffer, pOffset, pLength);
        }
    }

    /* access modifiers changed from: protected */
    public void noteFinished() throws IOException {
        for (Observer observer : getObservers()) {
            observer.finished();
        }
    }

    /* access modifiers changed from: protected */
    public void noteDataByte(int pDataByte) throws IOException {
        for (Observer observer : getObservers()) {
            observer.data(pDataByte);
        }
    }

    /* access modifiers changed from: protected */
    public void noteError(IOException pException) throws IOException {
        for (Observer observer : getObservers()) {
            observer.error(pException);
        }
    }

    /* access modifiers changed from: protected */
    public void noteClosed() throws IOException {
        for (Observer observer : getObservers()) {
            observer.closed();
        }
    }

    /* access modifiers changed from: protected */
    public List<Observer> getObservers() {
        return this.observers;
    }

    public void close() throws IOException {
        IOException ioe = null;
        try {
            super.close();
        } catch (IOException e) {
            ioe = e;
        }
        if (ioe == null) {
            noteClosed();
        } else {
            noteError(ioe);
        }
    }

    public void consume() throws IOException {
        do {
        } while (read(new byte[8192]) != -1);
    }
}
