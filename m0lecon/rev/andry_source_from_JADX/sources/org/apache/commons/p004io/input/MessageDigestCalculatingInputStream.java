package org.apache.commons.p004io.input;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.p004io.input.ObservableInputStream.Observer;

/* renamed from: org.apache.commons.io.input.MessageDigestCalculatingInputStream */
public class MessageDigestCalculatingInputStream extends ObservableInputStream {
    private final MessageDigest messageDigest;

    /* renamed from: org.apache.commons.io.input.MessageDigestCalculatingInputStream$MessageDigestMaintainingObserver */
    public static class MessageDigestMaintainingObserver extends Observer {

        /* renamed from: md */
        private final MessageDigest f39md;

        public MessageDigestMaintainingObserver(MessageDigest pMd) {
            this.f39md = pMd;
        }

        /* access modifiers changed from: 0000 */
        public void data(int pByte) throws IOException {
            this.f39md.update((byte) pByte);
        }

        /* access modifiers changed from: 0000 */
        public void data(byte[] pBuffer, int pOffset, int pLength) throws IOException {
            this.f39md.update(pBuffer, pOffset, pLength);
        }
    }

    public MessageDigestCalculatingInputStream(InputStream pStream, MessageDigest pDigest) {
        super(pStream);
        this.messageDigest = pDigest;
        add(new MessageDigestMaintainingObserver(pDigest));
    }

    public MessageDigestCalculatingInputStream(InputStream pStream, String pAlgorithm) throws NoSuchAlgorithmException {
        this(pStream, MessageDigest.getInstance(pAlgorithm));
    }

    public MessageDigestCalculatingInputStream(InputStream pStream) throws NoSuchAlgorithmException {
        this(pStream, MessageDigest.getInstance("MD5"));
    }

    public MessageDigest getMessageDigest() {
        return this.messageDigest;
    }
}
