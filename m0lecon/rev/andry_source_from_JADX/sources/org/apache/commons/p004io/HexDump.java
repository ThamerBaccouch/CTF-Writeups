package org.apache.commons.p004io;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/* renamed from: org.apache.commons.io.HexDump */
public class HexDump {
    public static final String EOL = System.getProperty("line.separator");
    private static final char[] _hexcodes = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final int[] _shifts = {28, 24, 20, 16, 12, 8, 4, 0};

    public static void dump(byte[] data, long offset, OutputStream stream, int index) throws IOException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (index < 0 || index >= data.length) {
            StringBuilder sb = new StringBuilder();
            sb.append("illegal index: ");
            sb.append(index);
            sb.append(" into array of length ");
            sb.append(data.length);
            throw new ArrayIndexOutOfBoundsException(sb.toString());
        } else if (stream != null) {
            long display_offset = ((long) index) + offset;
            StringBuilder buffer = new StringBuilder(74);
            for (int j = index; j < data.length; j += 16) {
                int chars_read = data.length - j;
                if (chars_read > 16) {
                    chars_read = 16;
                }
                dump(buffer, display_offset).append(' ');
                for (int k = 0; k < 16; k++) {
                    if (k < chars_read) {
                        dump(buffer, data[k + j]);
                    } else {
                        buffer.append("  ");
                    }
                    buffer.append(' ');
                }
                for (int k2 = 0; k2 < chars_read; k2++) {
                    if (data[k2 + j] < 32 || data[k2 + j] >= Byte.MAX_VALUE) {
                        buffer.append(FilenameUtils.EXTENSION_SEPARATOR);
                    } else {
                        buffer.append((char) data[k2 + j]);
                    }
                }
                buffer.append(EOL);
                stream.write(buffer.toString().getBytes(Charset.defaultCharset()));
                stream.flush();
                buffer.setLength(0);
                display_offset += (long) chars_read;
            }
        } else {
            throw new IllegalArgumentException("cannot write to nullstream");
        }
    }

    private static StringBuilder dump(StringBuilder _lbuffer, long value) {
        for (int j = 0; j < 8; j++) {
            _lbuffer.append(_hexcodes[((int) (value >> _shifts[j])) & 15]);
        }
        return _lbuffer;
    }

    private static StringBuilder dump(StringBuilder _cbuffer, byte value) {
        for (int j = 0; j < 2; j++) {
            _cbuffer.append(_hexcodes[(value >> _shifts[j + 6]) & 15]);
        }
        return _cbuffer;
    }
}
