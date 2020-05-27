package org.apache.commons.p004io;

import java.nio.ByteOrder;

/* renamed from: org.apache.commons.io.ByteOrderParser */
public final class ByteOrderParser {
    private ByteOrderParser() {
    }

    public static ByteOrder parseByteOrder(String value) {
        if (ByteOrder.BIG_ENDIAN.toString().equals(value)) {
            return ByteOrder.BIG_ENDIAN;
        }
        if (ByteOrder.LITTLE_ENDIAN.toString().equals(value)) {
            return ByteOrder.LITTLE_ENDIAN;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unsupported byte order setting: ");
        sb.append(value);
        sb.append(", expeced one of ");
        sb.append(ByteOrder.LITTLE_ENDIAN);
        sb.append(", ");
        sb.append(ByteOrder.BIG_ENDIAN);
        throw new IllegalArgumentException(sb.toString());
    }
}
