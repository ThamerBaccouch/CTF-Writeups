package org.apache.commons.p004io;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

@Deprecated
/* renamed from: org.apache.commons.io.FileSystemUtils */
public class FileSystemUtils {

    /* renamed from: DF */
    private static final String f36DF;
    private static final int INIT_PROBLEM = -1;
    private static final FileSystemUtils INSTANCE = new FileSystemUtils();

    /* renamed from: OS */
    private static final int f37OS;
    private static final int OTHER = 0;
    private static final int POSIX_UNIX = 3;
    private static final int UNIX = 2;
    private static final int WINDOWS = 1;

    static {
        int os;
        String dfPath = "df";
        try {
            String osName = System.getProperty("os.name");
            if (osName != null) {
                String osName2 = osName.toLowerCase(Locale.ENGLISH);
                if (osName2.contains("windows")) {
                    os = 1;
                } else {
                    if (!osName2.contains("linux") && !osName2.contains("mpe/ix") && !osName2.contains("freebsd") && !osName2.contains("openbsd") && !osName2.contains("irix") && !osName2.contains("digital unix") && !osName2.contains("unix")) {
                        if (!osName2.contains("mac os x")) {
                            if (!osName2.contains("sun os") && !osName2.contains("sunos")) {
                                if (!osName2.contains("solaris")) {
                                    if (!osName2.contains("hp-ux")) {
                                        if (!osName2.contains("aix")) {
                                            os = 0;
                                        }
                                    }
                                    os = 3;
                                }
                            }
                            os = 3;
                            dfPath = "/usr/xpg4/bin/df";
                        }
                    }
                    os = 2;
                }
                f37OS = os;
                f36DF = dfPath;
                return;
            }
            throw new IOException("os.name not found");
        } catch (Exception e) {
            os = -1;
        }
    }

    @Deprecated
    public static long freeSpace(String path) throws IOException {
        return INSTANCE.freeSpaceOS(path, f37OS, false, -1);
    }

    @Deprecated
    public static long freeSpaceKb(String path) throws IOException {
        return freeSpaceKb(path, -1);
    }

    @Deprecated
    public static long freeSpaceKb(String path, long timeout) throws IOException {
        return INSTANCE.freeSpaceOS(path, f37OS, true, timeout);
    }

    @Deprecated
    public static long freeSpaceKb() throws IOException {
        return freeSpaceKb(-1);
    }

    @Deprecated
    public static long freeSpaceKb(long timeout) throws IOException {
        return freeSpaceKb(new File(".").getAbsolutePath(), timeout);
    }

    /* access modifiers changed from: 0000 */
    public long freeSpaceOS(String path, int os, boolean kb, long timeout) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("Path must not be null");
        } else if (os == 0) {
            throw new IllegalStateException("Unsupported operating system");
        } else if (os == 1) {
            long freeSpaceWindows = freeSpaceWindows(path, timeout);
            if (kb) {
                freeSpaceWindows /= FileUtils.ONE_KB;
            }
            return freeSpaceWindows;
        } else if (os == 2) {
            return freeSpaceUnix(path, kb, false, timeout);
        } else {
            if (os == 3) {
                return freeSpaceUnix(path, kb, true, timeout);
            }
            throw new IllegalStateException("Exception caught when determining operating system");
        }
    }

    /* access modifiers changed from: 0000 */
    public long freeSpaceWindows(String path, long timeout) throws IOException {
        String normPath = FilenameUtils.normalize(path, false);
        if (normPath != null) {
            if (normPath.length() > 0 && normPath.charAt(0) != '\"') {
                StringBuilder sb = new StringBuilder();
                String str = "\"";
                sb.append(str);
                sb.append(normPath);
                sb.append(str);
                normPath = sb.toString();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("dir /a /-c ");
            sb2.append(normPath);
            List<String> lines = performCommand(new String[]{"cmd.exe", "/C", sb2.toString()}, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, timeout);
            for (int i = lines.size() - 1; i >= 0; i--) {
                String line = (String) lines.get(i);
                if (line.length() > 0) {
                    return parseDir(line, normPath);
                }
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Command line 'dir /-c' did not return any info for path '");
            sb3.append(normPath);
            sb3.append("'");
            throw new IOException(sb3.toString());
        }
        throw new IllegalArgumentException(path);
    }

    /* access modifiers changed from: 0000 */
    public long parseDir(String line, String path) throws IOException {
        int bytesStart = 0;
        int bytesEnd = 0;
        int j = line.length() - 1;
        while (true) {
            if (j < 0) {
                break;
            } else if (Character.isDigit(line.charAt(j))) {
                bytesEnd = j + 1;
                break;
            } else {
                j--;
            }
        }
        while (true) {
            if (j < 0) {
                break;
            }
            char c = line.charAt(j);
            if (!Character.isDigit(c) && c != ',' && c != '.') {
                bytesStart = j + 1;
                break;
            }
            j--;
        }
        if (j >= 0) {
            StringBuilder buf = new StringBuilder(line.substring(bytesStart, bytesEnd));
            int k = 0;
            while (k < buf.length()) {
                if (buf.charAt(k) == ',' || buf.charAt(k) == '.') {
                    int k2 = k - 1;
                    buf.deleteCharAt(k);
                    k = k2;
                }
                k++;
            }
            return parseBytes(buf.toString(), path);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Command line 'dir /-c' did not return valid info for path '");
        sb.append(path);
        sb.append("'");
        throw new IOException(sb.toString());
    }

    /* access modifiers changed from: 0000 */
    public long freeSpaceUnix(String path, boolean kb, boolean posix, long timeout) throws IOException {
        String str = path;
        if (!path.isEmpty()) {
            String flags = "-";
            if (kb) {
                StringBuilder sb = new StringBuilder();
                sb.append(flags);
                sb.append("k");
                flags = sb.toString();
            }
            if (posix) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(flags);
                sb2.append("P");
                flags = sb2.toString();
            }
            List<String> lines = performCommand(flags.length() > 1 ? new String[]{f36DF, flags, str} : new String[]{f36DF, str}, 3, timeout);
            String str2 = "Command line '";
            if (lines.size() >= 2) {
                String str3 = " ";
                StringTokenizer tok = new StringTokenizer((String) lines.get(1), str3);
                if (tok.countTokens() >= 4) {
                    tok.nextToken();
                } else if (tok.countTokens() != 1 || lines.size() < 3) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str2);
                    sb3.append(f36DF);
                    sb3.append("' did not return data as expected for path '");
                    sb3.append(str);
                    sb3.append("'- check path is valid");
                    throw new IOException(sb3.toString());
                } else {
                    tok = new StringTokenizer((String) lines.get(2), str3);
                }
                tok.nextToken();
                tok.nextToken();
                return parseBytes(tok.nextToken(), str);
            }
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str2);
            sb4.append(f36DF);
            sb4.append("' did not return info as expected for path '");
            sb4.append(str);
            sb4.append("'- response was ");
            sb4.append(lines);
            throw new IOException(sb4.toString());
        }
        long j = timeout;
        throw new IllegalArgumentException("Path must not be empty");
    }

    /* access modifiers changed from: 0000 */
    public long parseBytes(String freeSpace, String path) throws IOException {
        String str = "'- check path is valid";
        String str2 = "Command line '";
        try {
            long bytes = Long.parseLong(freeSpace);
            if (bytes >= 0) {
                return bytes;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(f36DF);
            sb.append("' did not find free space in response for path '");
            sb.append(path);
            sb.append(str);
            throw new IOException(sb.toString());
        } catch (NumberFormatException ex) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str2);
            sb2.append(f36DF);
            sb2.append("' did not return numeric data as expected for path '");
            sb2.append(path);
            sb2.append(str);
            throw new IOException(sb2.toString(), ex);
        }
    }

    /* access modifiers changed from: 0000 */
    public List<String> performCommand(String[] cmdAttribs, int max, long timeout) throws IOException {
        List<String> lines = new ArrayList<>(20);
        Process proc = null;
        InputStream in = null;
        OutputStream out = null;
        InputStream err = null;
        BufferedReader inr = null;
        try {
            Thread monitor = ThreadMonitor.start(timeout);
            proc = openProcess(cmdAttribs);
            in = proc.getInputStream();
            out = proc.getOutputStream();
            err = proc.getErrorStream();
            inr = new BufferedReader(new InputStreamReader(in, Charset.defaultCharset()));
            for (String line = inr.readLine(); line != null && lines.size() < max; line = inr.readLine()) {
                lines.add(line.toLowerCase(Locale.ENGLISH).trim());
            }
            proc.waitFor();
            ThreadMonitor.stop(monitor);
            if (proc.exitValue() != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("Command line returned OS error code '");
                sb.append(proc.exitValue());
                sb.append("' for command ");
                sb.append(Arrays.asList(cmdAttribs));
                throw new IOException(sb.toString());
            } else if (!lines.isEmpty()) {
                inr.close();
                in.close();
                if (out != null) {
                    out.close();
                    out = null;
                }
                if (err != null) {
                    err.close();
                    err = null;
                }
                IOUtils.closeQuietly((InputStream) null);
                IOUtils.closeQuietly(out);
                IOUtils.closeQuietly(err);
                IOUtils.closeQuietly((Reader) null);
                if (proc != null) {
                    proc.destroy();
                }
                return lines;
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Command line did not return any info for command ");
                sb2.append(Arrays.asList(cmdAttribs));
                throw new IOException(sb2.toString());
            }
        } catch (InterruptedException ex) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Command line threw an InterruptedException for command ");
            sb3.append(Arrays.asList(cmdAttribs));
            sb3.append(" timeout=");
            sb3.append(timeout);
            throw new IOException(sb3.toString(), ex);
        } catch (Throwable th) {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(err);
            IOUtils.closeQuietly((Reader) inr);
            if (proc != null) {
                proc.destroy();
            }
            throw th;
        }
    }

    /* access modifiers changed from: 0000 */
    public Process openProcess(String[] cmdAttribs) throws IOException {
        return Runtime.getRuntime().exec(cmdAttribs);
    }
}
