package org.apache.commons.p004io;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.CRC32;
import org.apache.commons.p004io.filefilter.DirectoryFileFilter;
import org.apache.commons.p004io.filefilter.FalseFileFilter;
import org.apache.commons.p004io.filefilter.FileFilterUtils;
import org.apache.commons.p004io.filefilter.IOFileFilter;
import org.apache.commons.p004io.filefilter.SuffixFileFilter;
import org.apache.commons.p004io.filefilter.TrueFileFilter;

/* renamed from: org.apache.commons.io.FileUtils */
public class FileUtils {
    public static final File[] EMPTY_FILE_ARRAY = new File[0];
    private static final long FILE_COPY_BUFFER_SIZE = 31457280;
    public static final long ONE_EB = 1152921504606846976L;
    public static final BigInteger ONE_EB_BI;
    public static final long ONE_GB = 1073741824;
    public static final BigInteger ONE_GB_BI;
    public static final long ONE_KB = 1024;
    public static final BigInteger ONE_KB_BI;
    public static final long ONE_MB = 1048576;
    public static final BigInteger ONE_MB_BI;
    public static final long ONE_PB = 1125899906842624L;
    public static final BigInteger ONE_PB_BI;
    public static final long ONE_TB = 1099511627776L;
    public static final BigInteger ONE_TB_BI;
    public static final BigInteger ONE_YB;
    public static final BigInteger ONE_ZB;

    static {
        BigInteger valueOf = BigInteger.valueOf(ONE_KB);
        ONE_KB_BI = valueOf;
        BigInteger multiply = valueOf.multiply(valueOf);
        ONE_MB_BI = multiply;
        BigInteger multiply2 = ONE_KB_BI.multiply(multiply);
        ONE_GB_BI = multiply2;
        BigInteger multiply3 = ONE_KB_BI.multiply(multiply2);
        ONE_TB_BI = multiply3;
        BigInteger multiply4 = ONE_KB_BI.multiply(multiply3);
        ONE_PB_BI = multiply4;
        ONE_EB_BI = ONE_KB_BI.multiply(multiply4);
        BigInteger multiply5 = BigInteger.valueOf(ONE_KB).multiply(BigInteger.valueOf(ONE_EB));
        ONE_ZB = multiply5;
        ONE_YB = ONE_KB_BI.multiply(multiply5);
    }

    public static File getFile(File directory, String... names) {
        if (directory == null) {
            throw new NullPointerException("directory must not be null");
        } else if (names != null) {
            File file = directory;
            for (String name : names) {
                file = new File(file, name);
            }
            return file;
        } else {
            throw new NullPointerException("names must not be null");
        }
    }

    public static File getFile(String... names) {
        File file;
        if (names != null) {
            File file2 = null;
            for (String name : names) {
                if (file2 == null) {
                    file = new File(name);
                } else {
                    file = new File(file2, name);
                }
                file2 = file;
            }
            return file2;
        }
        throw new NullPointerException("names must not be null");
    }

    public static String getTempDirectoryPath() {
        return System.getProperty("java.io.tmpdir");
    }

    public static File getTempDirectory() {
        return new File(getTempDirectoryPath());
    }

    public static String getUserDirectoryPath() {
        return System.getProperty("user.home");
    }

    public static File getUserDirectory() {
        return new File(getUserDirectoryPath());
    }

    public static FileInputStream openInputStream(File file) throws IOException {
        String str = "File '";
        if (!file.exists()) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(file);
            sb.append("' does not exist");
            throw new FileNotFoundException(sb.toString());
        } else if (file.isDirectory()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(file);
            sb2.append("' exists but is a directory");
            throw new IOException(sb2.toString());
        } else if (file.canRead()) {
            return new FileInputStream(file);
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(file);
            sb3.append("' cannot be read");
            throw new IOException(sb3.toString());
        }
    }

    public static FileOutputStream openOutputStream(File file) throws IOException {
        return openOutputStream(file, false);
    }

    public static FileOutputStream openOutputStream(File file, boolean append) throws IOException {
        if (file.exists()) {
            String str = "File '";
            if (file.isDirectory()) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(file);
                sb.append("' exists but is a directory");
                throw new IOException(sb.toString());
            } else if (!file.canWrite()) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append(file);
                sb2.append("' cannot be written to");
                throw new IOException(sb2.toString());
            }
        } else {
            File parent = file.getParentFile();
            if (parent != null && !parent.mkdirs() && !parent.isDirectory()) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Directory '");
                sb3.append(parent);
                sb3.append("' could not be created");
                throw new IOException(sb3.toString());
            }
        }
        return new FileOutputStream(file, append);
    }

    public static String byteCountToDisplaySize(BigInteger size) {
        if (size.divide(ONE_EB_BI).compareTo(BigInteger.ZERO) > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.valueOf(size.divide(ONE_EB_BI)));
            sb.append(" EB");
            return sb.toString();
        } else if (size.divide(ONE_PB_BI).compareTo(BigInteger.ZERO) > 0) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(String.valueOf(size.divide(ONE_PB_BI)));
            sb2.append(" PB");
            return sb2.toString();
        } else if (size.divide(ONE_TB_BI).compareTo(BigInteger.ZERO) > 0) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(String.valueOf(size.divide(ONE_TB_BI)));
            sb3.append(" TB");
            return sb3.toString();
        } else if (size.divide(ONE_GB_BI).compareTo(BigInteger.ZERO) > 0) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(String.valueOf(size.divide(ONE_GB_BI)));
            sb4.append(" GB");
            return sb4.toString();
        } else if (size.divide(ONE_MB_BI).compareTo(BigInteger.ZERO) > 0) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(String.valueOf(size.divide(ONE_MB_BI)));
            sb5.append(" MB");
            return sb5.toString();
        } else if (size.divide(ONE_KB_BI).compareTo(BigInteger.ZERO) > 0) {
            StringBuilder sb6 = new StringBuilder();
            sb6.append(String.valueOf(size.divide(ONE_KB_BI)));
            sb6.append(" KB");
            return sb6.toString();
        } else {
            StringBuilder sb7 = new StringBuilder();
            sb7.append(String.valueOf(size));
            sb7.append(" bytes");
            return sb7.toString();
        }
    }

    public static String byteCountToDisplaySize(long size) {
        return byteCountToDisplaySize(BigInteger.valueOf(size));
    }

    public static void touch(File file) throws IOException {
        if (!file.exists()) {
            openOutputStream(file).close();
        }
        if (!file.setLastModified(System.currentTimeMillis())) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unable to set the last modification time for ");
            sb.append(file);
            throw new IOException(sb.toString());
        }
    }

    public static File[] convertFileCollectionToFileArray(Collection<File> files) {
        return (File[]) files.toArray(new File[files.size()]);
    }

    private static void innerListFiles(Collection<File> files, File directory, IOFileFilter filter, boolean includeSubDirectories) {
        File[] found = directory.listFiles(filter);
        if (found != null) {
            for (File file : found) {
                if (file.isDirectory()) {
                    if (includeSubDirectories) {
                        files.add(file);
                    }
                    innerListFiles(files, file, filter, includeSubDirectories);
                } else {
                    files.add(file);
                }
            }
        }
    }

    public static Collection<File> listFiles(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter) {
        validateListFilesParameters(directory, fileFilter);
        IOFileFilter effFileFilter = setUpEffectiveFileFilter(fileFilter);
        IOFileFilter effDirFilter = setUpEffectiveDirFilter(dirFilter);
        Collection<File> files = new LinkedList<>();
        innerListFiles(files, directory, FileFilterUtils.m17or(effFileFilter, effDirFilter), false);
        return files;
    }

    private static void validateListFilesParameters(File directory, IOFileFilter fileFilter) {
        if (!directory.isDirectory()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Parameter 'directory' is not a directory: ");
            sb.append(directory);
            throw new IllegalArgumentException(sb.toString());
        } else if (fileFilter == null) {
            throw new NullPointerException("Parameter 'fileFilter' is null");
        }
    }

    private static IOFileFilter setUpEffectiveFileFilter(IOFileFilter fileFilter) {
        return FileFilterUtils.and(fileFilter, FileFilterUtils.notFileFilter(DirectoryFileFilter.INSTANCE));
    }

    private static IOFileFilter setUpEffectiveDirFilter(IOFileFilter dirFilter) {
        if (dirFilter == null) {
            return FalseFileFilter.INSTANCE;
        }
        return FileFilterUtils.and(dirFilter, DirectoryFileFilter.INSTANCE);
    }

    public static Collection<File> listFilesAndDirs(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter) {
        validateListFilesParameters(directory, fileFilter);
        IOFileFilter effFileFilter = setUpEffectiveFileFilter(fileFilter);
        IOFileFilter effDirFilter = setUpEffectiveDirFilter(dirFilter);
        Collection<File> files = new LinkedList<>();
        if (directory.isDirectory()) {
            files.add(directory);
        }
        innerListFiles(files, directory, FileFilterUtils.m17or(effFileFilter, effDirFilter), true);
        return files;
    }

    public static Iterator<File> iterateFiles(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter) {
        return listFiles(directory, fileFilter, dirFilter).iterator();
    }

    public static Iterator<File> iterateFilesAndDirs(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter) {
        return listFilesAndDirs(directory, fileFilter, dirFilter).iterator();
    }

    private static String[] toSuffixes(String[] extensions) {
        String[] suffixes = new String[extensions.length];
        for (int i = 0; i < extensions.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(".");
            sb.append(extensions[i]);
            suffixes[i] = sb.toString();
        }
        return suffixes;
    }

    public static Collection<File> listFiles(File directory, String[] extensions, boolean recursive) {
        IOFileFilter filter;
        if (extensions == null) {
            filter = TrueFileFilter.INSTANCE;
        } else {
            filter = new SuffixFileFilter(toSuffixes(extensions));
        }
        return listFiles(directory, filter, recursive ? TrueFileFilter.INSTANCE : FalseFileFilter.INSTANCE);
    }

    public static Iterator<File> iterateFiles(File directory, String[] extensions, boolean recursive) {
        return listFiles(directory, extensions, recursive).iterator();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0050, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0055, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
        r3.addSuppressed(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0059, code lost:
        throw r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x005c, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0061, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0062, code lost:
        r2.addSuppressed(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0065, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean contentEquals(java.io.File r8, java.io.File r9) throws java.io.IOException {
        /*
            boolean r0 = r8.exists()
            boolean r1 = r9.exists()
            r2 = 0
            if (r0 == r1) goto L_0x000c
            return r2
        L_0x000c:
            r1 = 1
            if (r0 != 0) goto L_0x0010
            return r1
        L_0x0010:
            boolean r3 = r8.isDirectory()
            if (r3 != 0) goto L_0x0066
            boolean r3 = r9.isDirectory()
            if (r3 != 0) goto L_0x0066
            long r3 = r8.length()
            long r5 = r9.length()
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 == 0) goto L_0x0029
            return r2
        L_0x0029:
            java.io.File r2 = r8.getCanonicalFile()
            java.io.File r3 = r9.getCanonicalFile()
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0038
            return r1
        L_0x0038:
            java.io.FileInputStream r1 = new java.io.FileInputStream
            r1.<init>(r8)
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ all -> 0x005a }
            r2.<init>(r9)     // Catch:{ all -> 0x005a }
            boolean r3 = org.apache.commons.p004io.IOUtils.contentEquals(r1, r2)     // Catch:{ all -> 0x004e }
            r2.close()     // Catch:{ all -> 0x005a }
            r1.close()
            return r3
        L_0x004e:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x0050 }
        L_0x0050:
            r4 = move-exception
            r2.close()     // Catch:{ all -> 0x0055 }
            goto L_0x0059
        L_0x0055:
            r5 = move-exception
            r3.addSuppressed(r5)     // Catch:{ all -> 0x005a }
        L_0x0059:
            throw r4     // Catch:{ all -> 0x005a }
        L_0x005a:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x005c }
        L_0x005c:
            r3 = move-exception
            r1.close()     // Catch:{ all -> 0x0061 }
            goto L_0x0065
        L_0x0061:
            r4 = move-exception
            r2.addSuppressed(r4)
        L_0x0065:
            throw r3
        L_0x0066:
            java.io.IOException r1 = new java.io.IOException
            java.lang.String r2 = "Can't compare directories, only files"
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p004io.FileUtils.contentEquals(java.io.File, java.io.File):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0070, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0075, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        r3.addSuppressed(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0079, code lost:
        throw r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x007c, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0081, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0082, code lost:
        r2.addSuppressed(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0085, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean contentEqualsIgnoreEOL(java.io.File r6, java.io.File r7, java.lang.String r8) throws java.io.IOException {
        /*
            boolean r0 = r6.exists()
            boolean r1 = r7.exists()
            if (r0 == r1) goto L_0x000c
            r1 = 0
            return r1
        L_0x000c:
            r1 = 1
            if (r0 != 0) goto L_0x0010
            return r1
        L_0x0010:
            boolean r2 = r6.isDirectory()
            if (r2 != 0) goto L_0x0086
            boolean r2 = r7.isDirectory()
            if (r2 != 0) goto L_0x0086
            java.io.File r2 = r6.getCanonicalFile()
            java.io.File r3 = r7.getCanonicalFile()
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x002b
            return r1
        L_0x002b:
            if (r8 != 0) goto L_0x003c
            java.io.InputStreamReader r1 = new java.io.InputStreamReader
            java.io.FileInputStream r2 = new java.io.FileInputStream
            r2.<init>(r6)
            java.nio.charset.Charset r3 = java.nio.charset.Charset.defaultCharset()
            r1.<init>(r2, r3)
            goto L_0x0046
        L_0x003c:
            java.io.InputStreamReader r1 = new java.io.InputStreamReader
            java.io.FileInputStream r2 = new java.io.FileInputStream
            r2.<init>(r6)
            r1.<init>(r2, r8)
        L_0x0046:
            if (r8 != 0) goto L_0x0058
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch:{ all -> 0x007a }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ all -> 0x007a }
            r3.<init>(r7)     // Catch:{ all -> 0x007a }
            java.nio.charset.Charset r4 = java.nio.charset.Charset.defaultCharset()     // Catch:{ all -> 0x007a }
            r2.<init>(r3, r4)     // Catch:{ all -> 0x007a }
            goto L_0x0062
        L_0x0058:
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch:{ all -> 0x007a }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ all -> 0x007a }
            r3.<init>(r7)     // Catch:{ all -> 0x007a }
            r2.<init>(r3, r8)     // Catch:{ all -> 0x007a }
        L_0x0062:
            boolean r3 = org.apache.commons.p004io.IOUtils.contentEqualsIgnoreEOL(r1, r2)     // Catch:{ all -> 0x006e }
            r2.close()     // Catch:{ all -> 0x007a }
            r1.close()
            return r3
        L_0x006e:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x0070 }
        L_0x0070:
            r4 = move-exception
            r2.close()     // Catch:{ all -> 0x0075 }
            goto L_0x0079
        L_0x0075:
            r5 = move-exception
            r3.addSuppressed(r5)     // Catch:{ all -> 0x007a }
        L_0x0079:
            throw r4     // Catch:{ all -> 0x007a }
        L_0x007a:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x007c }
        L_0x007c:
            r3 = move-exception
            r1.close()     // Catch:{ all -> 0x0081 }
            goto L_0x0085
        L_0x0081:
            r4 = move-exception
            r2.addSuppressed(r4)
        L_0x0085:
            throw r3
        L_0x0086:
            java.io.IOException r1 = new java.io.IOException
            java.lang.String r2 = "Can't compare directories, only files"
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p004io.FileUtils.contentEqualsIgnoreEOL(java.io.File, java.io.File, java.lang.String):boolean");
    }

    public static File toFile(URL url) {
        if (url != null) {
            if ("file".equalsIgnoreCase(url.getProtocol())) {
                return new File(decodeUrl(url.getFile().replace(IOUtils.DIR_SEPARATOR_UNIX, File.separatorChar)));
            }
        }
        return null;
    }

    static String decodeUrl(String url) {
        String decoded = url;
        if (url == null || url.indexOf(37) < 0) {
            return decoded;
        }
        int n = url.length();
        StringBuilder buffer = new StringBuilder();
        ByteBuffer bytes = ByteBuffer.allocate(n);
        int i = 0;
        while (i < n) {
            if (url.charAt(i) == '%') {
                do {
                    try {
                        bytes.put((byte) Integer.parseInt(url.substring(i + 1, i + 3), 16));
                        i += 3;
                        if (i >= n) {
                            break;
                        }
                    } catch (RuntimeException e) {
                        if (bytes.position() > 0) {
                            bytes.flip();
                            buffer.append(StandardCharsets.UTF_8.decode(bytes).toString());
                            bytes.clear();
                        }
                    } catch (Throwable th) {
                        if (bytes.position() > 0) {
                            bytes.flip();
                            buffer.append(StandardCharsets.UTF_8.decode(bytes).toString());
                            bytes.clear();
                        }
                        throw th;
                    }
                } while (url.charAt(i) == '%');
                if (bytes.position() > 0) {
                    bytes.flip();
                    buffer.append(StandardCharsets.UTF_8.decode(bytes).toString());
                    bytes.clear();
                }
            }
            int i2 = i + 1;
            buffer.append(url.charAt(i));
            i = i2;
        }
        return buffer.toString();
    }

    public static File[] toFiles(URL[] urls) {
        if (urls == null || urls.length == 0) {
            return EMPTY_FILE_ARRAY;
        }
        File[] files = new File[urls.length];
        for (int i = 0; i < urls.length; i++) {
            URL url = urls[i];
            if (url != null) {
                if (url.getProtocol().equals("file")) {
                    files[i] = toFile(url);
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("URL could not be converted to a File: ");
                    sb.append(url);
                    throw new IllegalArgumentException(sb.toString());
                }
            }
        }
        return files;
    }

    public static URL[] toURLs(File[] files) throws IOException {
        URL[] urls = new URL[files.length];
        for (int i = 0; i < urls.length; i++) {
            urls[i] = files[i].toURI().toURL();
        }
        return urls;
    }

    public static void copyFileToDirectory(File srcFile, File destDir) throws IOException {
        copyFileToDirectory(srcFile, destDir, true);
    }

    public static void copyFileToDirectory(File srcFile, File destDir, boolean preserveFileDate) throws IOException {
        if (destDir == null) {
            throw new NullPointerException("Destination must not be null");
        } else if (!destDir.exists() || destDir.isDirectory()) {
            copyFile(srcFile, new File(destDir, srcFile.getName()), preserveFileDate);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Destination '");
            sb.append(destDir);
            sb.append("' is not a directory");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public static void copyFile(File srcFile, File destFile) throws IOException {
        copyFile(srcFile, destFile, true);
    }

    public static void copyFile(File srcFile, File destFile, boolean preserveFileDate) throws IOException {
        checkFileRequirements(srcFile, destFile);
        String str = "Source '";
        if (srcFile.isDirectory()) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(srcFile);
            sb.append("' exists but is a directory");
            throw new IOException(sb.toString());
        } else if (!srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {
            File parentFile = destFile.getParentFile();
            String str2 = "Destination '";
            if (parentFile != null && !parentFile.mkdirs() && !parentFile.isDirectory()) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str2);
                sb2.append(parentFile);
                sb2.append("' directory cannot be created");
                throw new IOException(sb2.toString());
            } else if (!destFile.exists() || destFile.canWrite()) {
                doCopyFile(srcFile, destFile, preserveFileDate);
            } else {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str2);
                sb3.append(destFile);
                sb3.append("' exists but is read-only");
                throw new IOException(sb3.toString());
            }
        } else {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str);
            sb4.append(srcFile);
            sb4.append("' and destination '");
            sb4.append(destFile);
            sb4.append("' are the same");
            throw new IOException(sb4.toString());
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0014, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0015, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0018, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000f, code lost:
        r2 = move-exception;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long copyFile(java.io.File r4, java.io.OutputStream r5) throws java.io.IOException {
        /*
            java.io.FileInputStream r0 = new java.io.FileInputStream
            r0.<init>(r4)
            long r1 = org.apache.commons.p004io.IOUtils.copyLarge(r0, r5)     // Catch:{ all -> 0x000d }
            r0.close()
            return r1
        L_0x000d:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x000f }
        L_0x000f:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x0014 }
            goto L_0x0018
        L_0x0014:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x0018:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p004io.FileUtils.copyFile(java.io.File, java.io.OutputStream):long");
    }

    private static void doCopyFile(File srcFile, File destFile, boolean preserveFileDate) throws IOException {
        Throwable input;
        Throwable fos;
        Throwable output;
        long j;
        File file = srcFile;
        File file2 = destFile;
        if (!destFile.exists() || !destFile.isDirectory()) {
            FileInputStream fis = new FileInputStream(file);
            try {
                th = fis.getChannel();
                try {
                    th = new FileOutputStream(file2);
                    try {
                        th = fos.getChannel();
                        try {
                            long size = input.size();
                            long pos = 0;
                            while (pos < size) {
                                long remain = size - pos;
                                count = remain > FILE_COPY_BUFFER_SIZE ? 31457280 : remain;
                                long bytesCopied = output.transferFrom(input, pos, count);
                                if (bytesCopied == 0) {
                                    break;
                                }
                                pos += bytesCopied;
                            }
                            if (output != null) {
                                output.close();
                            }
                            fos.close();
                            if (input != null) {
                                input.close();
                            }
                            fis.close();
                            long srcLen = srcFile.length();
                            long dstLen = destFile.length();
                            if (srcLen != dstLen) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("Failed to copy full contents from '");
                                sb.append(file);
                                sb.append("' to '");
                                sb.append(file2);
                                sb.append("' Expected length: ");
                                sb.append(srcLen);
                                sb.append(" Actual: ");
                                sb.append(dstLen);
                                throw new IOException(sb.toString());
                            } else if (preserveFileDate) {
                                file2.setLastModified(srcFile.lastModified());
                            }
                        } catch (Throwable count) {
                            if (output != null) {
                                output.close();
                            }
                            throw j;
                        } finally {
                            j = count;
                        }
                    } catch (Throwable th) {
                        j.addSuppressed(th);
                    } finally {
                        output = th;
                        try {
                        } catch (Throwable th2) {
                            Throwable th3 = th2;
                            fos.close();
                            throw th3;
                        }
                    }
                } catch (Throwable th4) {
                    output.addSuppressed(th4);
                } finally {
                    fos = th4;
                    try {
                    } catch (Throwable th5) {
                        Throwable th6 = th5;
                        if (input != null) {
                            input.close();
                        }
                        throw th6;
                    }
                }
            } catch (Throwable th7) {
                fos.addSuppressed(th7);
            } finally {
                input = th7;
                try {
                } catch (Throwable th8) {
                    Throwable th9 = th8;
                    try {
                        fis.close();
                    } catch (Throwable th10) {
                        input.addSuppressed(th10);
                    }
                    throw th9;
                }
            }
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Destination '");
            sb2.append(file2);
            sb2.append("' exists but is a directory");
            throw new IOException(sb2.toString());
        }
    }

    public static void copyDirectoryToDirectory(File srcDir, File destDir) throws IOException {
        if (srcDir != null) {
            String str = "' is not a directory";
            if (srcDir.exists() && !srcDir.isDirectory()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Source '");
                sb.append(destDir);
                sb.append(str);
                throw new IllegalArgumentException(sb.toString());
            } else if (destDir == null) {
                throw new NullPointerException("Destination must not be null");
            } else if (!destDir.exists() || destDir.isDirectory()) {
                copyDirectory(srcDir, new File(destDir, srcDir.getName()), true);
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Destination '");
                sb2.append(destDir);
                sb2.append(str);
                throw new IllegalArgumentException(sb2.toString());
            }
        } else {
            throw new NullPointerException("Source must not be null");
        }
    }

    public static void copyDirectory(File srcDir, File destDir) throws IOException {
        copyDirectory(srcDir, destDir, true);
    }

    public static void copyDirectory(File srcDir, File destDir, boolean preserveFileDate) throws IOException {
        copyDirectory(srcDir, destDir, null, preserveFileDate);
    }

    public static void copyDirectory(File srcDir, File destDir, FileFilter filter) throws IOException {
        copyDirectory(srcDir, destDir, filter, true);
    }

    public static void copyDirectory(File srcDir, File destDir, FileFilter filter, boolean preserveFileDate) throws IOException {
        checkFileRequirements(srcDir, destDir);
        String str = "Source '";
        if (!srcDir.isDirectory()) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(srcDir);
            sb.append("' exists but is not a directory");
            throw new IOException(sb.toString());
        } else if (!srcDir.getCanonicalPath().equals(destDir.getCanonicalPath())) {
            List<String> exclusionList = null;
            if (destDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath())) {
                File[] srcFiles = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
                if (srcFiles != null && srcFiles.length > 0) {
                    exclusionList = new ArrayList<>(srcFiles.length);
                    for (File srcFile : srcFiles) {
                        exclusionList.add(new File(destDir, srcFile.getName()).getCanonicalPath());
                    }
                }
            }
            doCopyDirectory(srcDir, destDir, filter, preserveFileDate, exclusionList);
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(srcDir);
            sb2.append("' and destination '");
            sb2.append(destDir);
            sb2.append("' are the same");
            throw new IOException(sb2.toString());
        }
    }

    private static void checkFileRequirements(File src, File dest) throws FileNotFoundException {
        if (src == null) {
            throw new NullPointerException("Source must not be null");
        } else if (dest == null) {
            throw new NullPointerException("Destination must not be null");
        } else if (!src.exists()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Source '");
            sb.append(src);
            sb.append("' does not exist");
            throw new FileNotFoundException(sb.toString());
        }
    }

    private static void doCopyDirectory(File srcDir, File destDir, FileFilter filter, boolean preserveFileDate, List<String> exclusionList) throws IOException {
        File[] srcFiles = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
        if (srcFiles != null) {
            String str = "Destination '";
            if (destDir.exists()) {
                if (!destDir.isDirectory()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(destDir);
                    sb.append("' exists but is not a directory");
                    throw new IOException(sb.toString());
                }
            } else if (!destDir.mkdirs() && !destDir.isDirectory()) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append(destDir);
                sb2.append("' directory cannot be created");
                throw new IOException(sb2.toString());
            }
            if (destDir.canWrite()) {
                for (File srcFile : srcFiles) {
                    File dstFile = new File(destDir, srcFile.getName());
                    if (exclusionList == null || !exclusionList.contains(srcFile.getCanonicalPath())) {
                        if (srcFile.isDirectory()) {
                            doCopyDirectory(srcFile, dstFile, filter, preserveFileDate, exclusionList);
                        } else {
                            doCopyFile(srcFile, dstFile, preserveFileDate);
                        }
                    }
                }
                if (preserveFileDate) {
                    destDir.setLastModified(srcDir.lastModified());
                    return;
                }
                return;
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(destDir);
            sb3.append("' cannot be written to");
            throw new IOException(sb3.toString());
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append("Failed to list contents of ");
        sb4.append(srcDir);
        throw new IOException(sb4.toString());
    }

    public static void copyURLToFile(URL source, File destination) throws IOException {
        copyInputStreamToFile(source.openStream(), destination);
    }

    public static void copyURLToFile(URL source, File destination, int connectionTimeout, int readTimeout) throws IOException {
        URLConnection connection = source.openConnection();
        connection.setConnectTimeout(connectionTimeout);
        connection.setReadTimeout(readTimeout);
        copyInputStreamToFile(connection.getInputStream(), destination);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0013, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0014, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0017, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000c, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000d, code lost:
        if (r0 != null) goto L_0x000f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void copyInputStreamToFile(java.io.InputStream r4, java.io.File r5) throws java.io.IOException {
        /*
            r0 = r4
            copyToFile(r0, r5)     // Catch:{ all -> 0x000a }
            if (r0 == 0) goto L_0x0009
            r0.close()
        L_0x0009:
            return
        L_0x000a:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x000c }
        L_0x000c:
            r2 = move-exception
            if (r0 == 0) goto L_0x0017
            r0.close()     // Catch:{ all -> 0x0013 }
            goto L_0x0017
        L_0x0013:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x0017:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p004io.FileUtils.copyInputStreamToFile(java.io.InputStream, java.io.File):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0016, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0017, code lost:
        if (r1 != null) goto L_0x0019;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x001d, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        r2.addSuppressed(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0021, code lost:
        throw r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0024, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0025, code lost:
        if (r0 != null) goto L_0x0027;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x002b, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x002c, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x002f, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void copyToFile(java.io.InputStream r5, java.io.File r6) throws java.io.IOException {
        /*
            r0 = r5
            java.io.FileOutputStream r1 = openOutputStream(r6)     // Catch:{ all -> 0x0022 }
            org.apache.commons.p004io.IOUtils.copy(r0, r1)     // Catch:{ all -> 0x0014 }
            if (r1 == 0) goto L_0x000e
            r1.close()     // Catch:{ all -> 0x0022 }
        L_0x000e:
            if (r0 == 0) goto L_0x0013
            r0.close()
        L_0x0013:
            return
        L_0x0014:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0016 }
        L_0x0016:
            r3 = move-exception
            if (r1 == 0) goto L_0x0021
            r1.close()     // Catch:{ all -> 0x001d }
            goto L_0x0021
        L_0x001d:
            r4 = move-exception
            r2.addSuppressed(r4)     // Catch:{ all -> 0x0022 }
        L_0x0021:
            throw r3     // Catch:{ all -> 0x0022 }
        L_0x0022:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0024 }
        L_0x0024:
            r2 = move-exception
            if (r0 == 0) goto L_0x002f
            r0.close()     // Catch:{ all -> 0x002b }
            goto L_0x002f
        L_0x002b:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x002f:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p004io.FileUtils.copyToFile(java.io.InputStream, java.io.File):void");
    }

    public static void copyToDirectory(File src, File destDir) throws IOException {
        if (src == null) {
            throw new NullPointerException("Source must not be null");
        } else if (src.isFile()) {
            copyFileToDirectory(src, destDir);
        } else if (src.isDirectory()) {
            copyDirectoryToDirectory(src, destDir);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("The source ");
            sb.append(src);
            sb.append(" does not exist");
            throw new IOException(sb.toString());
        }
    }

    public static void copyToDirectory(Iterable<File> srcs, File destDir) throws IOException {
        if (srcs != null) {
            for (File src : srcs) {
                copyFileToDirectory(src, destDir);
            }
            return;
        }
        throw new NullPointerException("Sources must not be null");
    }

    public static void deleteDirectory(File directory) throws IOException {
        if (directory.exists()) {
            if (!isSymlink(directory)) {
                cleanDirectory(directory);
            }
            if (!directory.delete()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Unable to delete directory ");
                sb.append(directory);
                sb.append(".");
                throw new IOException(sb.toString());
            }
        }
    }

    public static boolean deleteQuietly(File file) {
        if (file == null) {
            return false;
        }
        try {
            if (file.isDirectory()) {
                cleanDirectory(file);
            }
        } catch (Exception e) {
        }
        try {
            return file.delete();
        } catch (Exception e2) {
            return false;
        }
    }

    public static boolean directoryContains(File directory, File child) throws IOException {
        if (directory == null) {
            throw new IllegalArgumentException("Directory must not be null");
        } else if (!directory.isDirectory()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Not a directory: ");
            sb.append(directory);
            throw new IllegalArgumentException(sb.toString());
        } else if (child != null && directory.exists() && child.exists()) {
            return FilenameUtils.directoryContains(directory.getCanonicalPath(), child.getCanonicalPath());
        } else {
            return false;
        }
    }

    public static void cleanDirectory(File directory) throws IOException {
        IOException exception = null;
        for (File file : verifiedListFiles(directory)) {
            try {
                forceDelete(file);
            } catch (IOException ioe) {
                exception = ioe;
            }
        }
        if (exception != null) {
            throw exception;
        }
    }

    private static File[] verifiedListFiles(File directory) throws IOException {
        if (!directory.exists()) {
            StringBuilder sb = new StringBuilder();
            sb.append(directory);
            sb.append(" does not exist");
            throw new IllegalArgumentException(sb.toString());
        } else if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                return files;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Failed to list contents of ");
            sb2.append(directory);
            throw new IOException(sb2.toString());
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(directory);
            sb3.append(" is not a directory");
            throw new IllegalArgumentException(sb3.toString());
        }
    }

    public static boolean waitFor(File file, int seconds) {
        long finishAt = System.currentTimeMillis() + (((long) seconds) * 1000);
        boolean wasInterrupted = false;
        while (!file.exists()) {
            try {
                long remaining = finishAt - System.currentTimeMillis();
                if (remaining < 0) {
                    return false;
                }
                try {
                    Thread.sleep(Math.min(100, remaining));
                } catch (InterruptedException e) {
                    wasInterrupted = true;
                } catch (Exception e2) {
                }
            } finally {
                if (wasInterrupted) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        if (wasInterrupted) {
            Thread.currentThread().interrupt();
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0015, code lost:
        if (r0 != null) goto L_0x0017;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001b, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001c, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x001f, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0014, code lost:
        r2 = move-exception;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String readFileToString(java.io.File r4, java.nio.charset.Charset r5) throws java.io.IOException {
        /*
            java.io.FileInputStream r0 = openInputStream(r4)
            java.nio.charset.Charset r1 = org.apache.commons.p004io.Charsets.toCharset(r5)     // Catch:{ all -> 0x0012 }
            java.lang.String r1 = org.apache.commons.p004io.IOUtils.toString(r0, r1)     // Catch:{ all -> 0x0012 }
            if (r0 == 0) goto L_0x0011
            r0.close()
        L_0x0011:
            return r1
        L_0x0012:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0014 }
        L_0x0014:
            r2 = move-exception
            if (r0 == 0) goto L_0x001f
            r0.close()     // Catch:{ all -> 0x001b }
            goto L_0x001f
        L_0x001b:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x001f:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p004io.FileUtils.readFileToString(java.io.File, java.nio.charset.Charset):java.lang.String");
    }

    public static String readFileToString(File file, String encoding) throws IOException {
        return readFileToString(file, Charsets.toCharset(encoding));
    }

    @Deprecated
    public static String readFileToString(File file) throws IOException {
        return readFileToString(file, Charset.defaultCharset());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001f, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0020, code lost:
        if (r0 != null) goto L_0x0022;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0026, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0027, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002a, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] readFileToByteArray(java.io.File r6) throws java.io.IOException {
        /*
            java.io.FileInputStream r0 = openInputStream(r6)
            long r1 = r6.length()     // Catch:{ all -> 0x001d }
            r3 = 0
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 <= 0) goto L_0x0013
            byte[] r3 = org.apache.commons.p004io.IOUtils.toByteArray(r0, r1)     // Catch:{ all -> 0x001d }
            goto L_0x0017
        L_0x0013:
            byte[] r3 = org.apache.commons.p004io.IOUtils.toByteArray(r0)     // Catch:{ all -> 0x001d }
        L_0x0017:
            if (r0 == 0) goto L_0x001c
            r0.close()
        L_0x001c:
            return r3
        L_0x001d:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x001f }
        L_0x001f:
            r2 = move-exception
            if (r0 == 0) goto L_0x002a
            r0.close()     // Catch:{ all -> 0x0026 }
            goto L_0x002a
        L_0x0026:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x002a:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p004io.FileUtils.readFileToByteArray(java.io.File):byte[]");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0015, code lost:
        if (r0 != null) goto L_0x0017;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001b, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001c, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x001f, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0014, code lost:
        r2 = move-exception;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.List<java.lang.String> readLines(java.io.File r4, java.nio.charset.Charset r5) throws java.io.IOException {
        /*
            java.io.FileInputStream r0 = openInputStream(r4)
            java.nio.charset.Charset r1 = org.apache.commons.p004io.Charsets.toCharset(r5)     // Catch:{ all -> 0x0012 }
            java.util.List r1 = org.apache.commons.p004io.IOUtils.readLines(r0, r1)     // Catch:{ all -> 0x0012 }
            if (r0 == 0) goto L_0x0011
            r0.close()
        L_0x0011:
            return r1
        L_0x0012:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0014 }
        L_0x0014:
            r2 = move-exception
            if (r0 == 0) goto L_0x001f
            r0.close()     // Catch:{ all -> 0x001b }
            goto L_0x001f
        L_0x001b:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x001f:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p004io.FileUtils.readLines(java.io.File, java.nio.charset.Charset):java.util.List");
    }

    public static List<String> readLines(File file, String encoding) throws IOException {
        return readLines(file, Charsets.toCharset(encoding));
    }

    @Deprecated
    public static List<String> readLines(File file) throws IOException {
        return readLines(file, Charset.defaultCharset());
    }

    public static LineIterator lineIterator(File file, String encoding) throws IOException {
        InputStream in = null;
        try {
            return IOUtils.lineIterator(openInputStream(file), encoding);
        } catch (IOException | RuntimeException ex) {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    ex.addSuppressed(e);
                }
            }
            throw ex;
        }
    }

    public static LineIterator lineIterator(File file) throws IOException {
        return lineIterator(file, null);
    }

    public static void writeStringToFile(File file, String data, Charset encoding) throws IOException {
        writeStringToFile(file, data, encoding, false);
    }

    public static void writeStringToFile(File file, String data, String encoding) throws IOException {
        writeStringToFile(file, data, encoding, false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0016, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0017, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001a, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000f, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0010, code lost:
        if (r0 != null) goto L_0x0012;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void writeStringToFile(java.io.File r4, java.lang.String r5, java.nio.charset.Charset r6, boolean r7) throws java.io.IOException {
        /*
            java.io.FileOutputStream r0 = openOutputStream(r4, r7)
            org.apache.commons.p004io.IOUtils.write(r5, r0, r6)     // Catch:{ all -> 0x000d }
            if (r0 == 0) goto L_0x000c
            r0.close()
        L_0x000c:
            return
        L_0x000d:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x000f }
        L_0x000f:
            r2 = move-exception
            if (r0 == 0) goto L_0x001a
            r0.close()     // Catch:{ all -> 0x0016 }
            goto L_0x001a
        L_0x0016:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x001a:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p004io.FileUtils.writeStringToFile(java.io.File, java.lang.String, java.nio.charset.Charset, boolean):void");
    }

    public static void writeStringToFile(File file, String data, String encoding, boolean append) throws IOException {
        writeStringToFile(file, data, Charsets.toCharset(encoding), append);
    }

    @Deprecated
    public static void writeStringToFile(File file, String data) throws IOException {
        writeStringToFile(file, data, Charset.defaultCharset(), false);
    }

    @Deprecated
    public static void writeStringToFile(File file, String data, boolean append) throws IOException {
        writeStringToFile(file, data, Charset.defaultCharset(), append);
    }

    @Deprecated
    public static void write(File file, CharSequence data) throws IOException {
        write(file, data, Charset.defaultCharset(), false);
    }

    @Deprecated
    public static void write(File file, CharSequence data, boolean append) throws IOException {
        write(file, data, Charset.defaultCharset(), append);
    }

    public static void write(File file, CharSequence data, Charset encoding) throws IOException {
        write(file, data, encoding, false);
    }

    public static void write(File file, CharSequence data, String encoding) throws IOException {
        write(file, data, encoding, false);
    }

    public static void write(File file, CharSequence data, Charset encoding, boolean append) throws IOException {
        writeStringToFile(file, data == null ? null : data.toString(), encoding, append);
    }

    public static void write(File file, CharSequence data, String encoding, boolean append) throws IOException {
        write(file, data, Charsets.toCharset(encoding), append);
    }

    public static void writeByteArrayToFile(File file, byte[] data) throws IOException {
        writeByteArrayToFile(file, data, false);
    }

    public static void writeByteArrayToFile(File file, byte[] data, boolean append) throws IOException {
        writeByteArrayToFile(file, data, 0, data.length, append);
    }

    public static void writeByteArrayToFile(File file, byte[] data, int off, int len) throws IOException {
        writeByteArrayToFile(file, data, off, len, false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0016, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0017, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001a, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000f, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0010, code lost:
        if (r0 != null) goto L_0x0012;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void writeByteArrayToFile(java.io.File r4, byte[] r5, int r6, int r7, boolean r8) throws java.io.IOException {
        /*
            java.io.FileOutputStream r0 = openOutputStream(r4, r8)
            r0.write(r5, r6, r7)     // Catch:{ all -> 0x000d }
            if (r0 == 0) goto L_0x000c
            r0.close()
        L_0x000c:
            return
        L_0x000d:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x000f }
        L_0x000f:
            r2 = move-exception
            if (r0 == 0) goto L_0x001a
            r0.close()     // Catch:{ all -> 0x0016 }
            goto L_0x001a
        L_0x0016:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x001a:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p004io.FileUtils.writeByteArrayToFile(java.io.File, byte[], int, int, boolean):void");
    }

    public static void writeLines(File file, String encoding, Collection<?> lines) throws IOException {
        writeLines(file, encoding, lines, null, false);
    }

    public static void writeLines(File file, String encoding, Collection<?> lines, boolean append) throws IOException {
        writeLines(file, encoding, lines, null, append);
    }

    public static void writeLines(File file, Collection<?> lines) throws IOException {
        writeLines(file, null, lines, null, false);
    }

    public static void writeLines(File file, Collection<?> lines, boolean append) throws IOException {
        writeLines(file, null, lines, null, append);
    }

    public static void writeLines(File file, String encoding, Collection<?> lines, String lineEnding) throws IOException {
        writeLines(file, encoding, lines, lineEnding, false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0017, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0018, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001b, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0012, code lost:
        r2 = move-exception;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void writeLines(java.io.File r4, java.lang.String r5, java.util.Collection<?> r6, java.lang.String r7, boolean r8) throws java.io.IOException {
        /*
            java.io.BufferedOutputStream r0 = new java.io.BufferedOutputStream
            java.io.FileOutputStream r1 = openOutputStream(r4, r8)
            r0.<init>(r1)
            org.apache.commons.p004io.IOUtils.writeLines(r6, r7, r0, r5)     // Catch:{ all -> 0x0010 }
            r0.close()
            return
        L_0x0010:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0012 }
        L_0x0012:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x0017 }
            goto L_0x001b
        L_0x0017:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x001b:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p004io.FileUtils.writeLines(java.io.File, java.lang.String, java.util.Collection, java.lang.String, boolean):void");
    }

    public static void writeLines(File file, Collection<?> lines, String lineEnding) throws IOException {
        writeLines(file, null, lines, lineEnding, false);
    }

    public static void writeLines(File file, Collection<?> lines, String lineEnding, boolean append) throws IOException {
        writeLines(file, null, lines, lineEnding, append);
    }

    public static void forceDelete(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
            return;
        }
        boolean filePresent = file.exists();
        if (file.delete()) {
            return;
        }
        if (!filePresent) {
            StringBuilder sb = new StringBuilder();
            sb.append("File does not exist: ");
            sb.append(file);
            throw new FileNotFoundException(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Unable to delete file: ");
        sb2.append(file);
        throw new IOException(sb2.toString());
    }

    public static void forceDeleteOnExit(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectoryOnExit(file);
        } else {
            file.deleteOnExit();
        }
    }

    private static void deleteDirectoryOnExit(File directory) throws IOException {
        if (directory.exists()) {
            directory.deleteOnExit();
            if (!isSymlink(directory)) {
                cleanDirectoryOnExit(directory);
            }
        }
    }

    private static void cleanDirectoryOnExit(File directory) throws IOException {
        IOException exception = null;
        for (File file : verifiedListFiles(directory)) {
            try {
                forceDeleteOnExit(file);
            } catch (IOException ioe) {
                exception = ioe;
            }
        }
        if (exception != null) {
            throw exception;
        }
    }

    public static void forceMkdir(File directory) throws IOException {
        if (directory.exists()) {
            if (!directory.isDirectory()) {
                StringBuilder sb = new StringBuilder();
                sb.append("File ");
                sb.append(directory);
                sb.append(" exists and is not a directory. Unable to create directory.");
                throw new IOException(sb.toString());
            }
        } else if (!directory.mkdirs() && !directory.isDirectory()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Unable to create directory ");
            sb2.append(directory);
            throw new IOException(sb2.toString());
        }
    }

    public static void forceMkdirParent(File file) throws IOException {
        File parent = file.getParentFile();
        if (parent != null) {
            forceMkdir(parent);
        }
    }

    public static long sizeOf(File file) {
        if (!file.exists()) {
            StringBuilder sb = new StringBuilder();
            sb.append(file);
            sb.append(" does not exist");
            throw new IllegalArgumentException(sb.toString());
        } else if (file.isDirectory()) {
            return sizeOfDirectory0(file);
        } else {
            return file.length();
        }
    }

    public static BigInteger sizeOfAsBigInteger(File file) {
        if (!file.exists()) {
            StringBuilder sb = new StringBuilder();
            sb.append(file);
            sb.append(" does not exist");
            throw new IllegalArgumentException(sb.toString());
        } else if (file.isDirectory()) {
            return sizeOfDirectoryBig0(file);
        } else {
            return BigInteger.valueOf(file.length());
        }
    }

    public static long sizeOfDirectory(File directory) {
        checkDirectory(directory);
        return sizeOfDirectory0(directory);
    }

    private static long sizeOfDirectory0(File directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            return 0;
        }
        long size = 0;
        for (File file : files) {
            try {
                if (!isSymlink(file)) {
                    size += sizeOf0(file);
                    if (size < 0) {
                        break;
                    }
                } else {
                    continue;
                }
            } catch (IOException e) {
            }
        }
        return size;
    }

    private static long sizeOf0(File file) {
        if (file.isDirectory()) {
            return sizeOfDirectory0(file);
        }
        return file.length();
    }

    public static BigInteger sizeOfDirectoryAsBigInteger(File directory) {
        checkDirectory(directory);
        return sizeOfDirectoryBig0(directory);
    }

    private static BigInteger sizeOfDirectoryBig0(File directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            return BigInteger.ZERO;
        }
        BigInteger size = BigInteger.ZERO;
        for (File file : files) {
            try {
                if (!isSymlink(file)) {
                    size = size.add(sizeOfBig0(file));
                }
            } catch (IOException e) {
            }
        }
        return size;
    }

    private static BigInteger sizeOfBig0(File fileOrDir) {
        if (fileOrDir.isDirectory()) {
            return sizeOfDirectoryBig0(fileOrDir);
        }
        return BigInteger.valueOf(fileOrDir.length());
    }

    private static void checkDirectory(File directory) {
        if (!directory.exists()) {
            StringBuilder sb = new StringBuilder();
            sb.append(directory);
            sb.append(" does not exist");
            throw new IllegalArgumentException(sb.toString());
        } else if (!directory.isDirectory()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(directory);
            sb2.append(" is not a directory");
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    public static boolean isFileNewer(File file, File reference) {
        if (reference == null) {
            throw new IllegalArgumentException("No specified reference file");
        } else if (reference.exists()) {
            return isFileNewer(file, reference.lastModified());
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("The reference file '");
            sb.append(reference);
            sb.append("' doesn't exist");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public static boolean isFileNewer(File file, Date date) {
        if (date != null) {
            return isFileNewer(file, date.getTime());
        }
        throw new IllegalArgumentException("No specified date");
    }

    public static boolean isFileNewer(File file, long timeMillis) {
        if (file != null) {
            boolean z = false;
            if (!file.exists()) {
                return false;
            }
            if (file.lastModified() > timeMillis) {
                z = true;
            }
            return z;
        }
        throw new IllegalArgumentException("No specified file");
    }

    public static boolean isFileOlder(File file, File reference) {
        if (reference == null) {
            throw new IllegalArgumentException("No specified reference file");
        } else if (reference.exists()) {
            return isFileOlder(file, reference.lastModified());
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("The reference file '");
            sb.append(reference);
            sb.append("' doesn't exist");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public static boolean isFileOlder(File file, Date date) {
        if (date != null) {
            return isFileOlder(file, date.getTime());
        }
        throw new IllegalArgumentException("No specified date");
    }

    public static boolean isFileOlder(File file, long timeMillis) {
        if (file != null) {
            boolean z = false;
            if (!file.exists()) {
                return false;
            }
            if (file.lastModified() < timeMillis) {
                z = true;
            }
            return z;
        }
        throw new IllegalArgumentException("No specified file");
    }

    public static long checksumCRC32(File file) throws IOException {
        CRC32 crc = new CRC32();
        checksum(file, crc);
        return crc.getValue();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001e, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0023, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0024, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0027, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.zip.Checksum checksum(java.io.File r4, java.util.zip.Checksum r5) throws java.io.IOException {
        /*
            boolean r0 = r4.isDirectory()
            if (r0 != 0) goto L_0x0028
            java.util.zip.CheckedInputStream r0 = new java.util.zip.CheckedInputStream
            java.io.FileInputStream r1 = new java.io.FileInputStream
            r1.<init>(r4)
            r0.<init>(r1, r5)
            org.apache.commons.io.output.NullOutputStream r1 = new org.apache.commons.io.output.NullOutputStream     // Catch:{ all -> 0x001c }
            r1.<init>()     // Catch:{ all -> 0x001c }
            org.apache.commons.p004io.IOUtils.copy(r0, r1)     // Catch:{ all -> 0x001c }
            r0.close()
            return r5
        L_0x001c:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x001e }
        L_0x001e:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x0023 }
            goto L_0x0027
        L_0x0023:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x0027:
            throw r2
        L_0x0028:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Checksums can't be computed on directories"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p004io.FileUtils.checksum(java.io.File, java.util.zip.Checksum):java.util.zip.Checksum");
    }

    public static void moveDirectory(File srcDir, File destDir) throws IOException {
        if (srcDir == null) {
            throw new NullPointerException("Source must not be null");
        } else if (destDir != null) {
            String str = "Source '";
            if (!srcDir.exists()) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(srcDir);
                sb.append("' does not exist");
                throw new FileNotFoundException(sb.toString());
            } else if (!srcDir.isDirectory()) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append(srcDir);
                sb2.append("' is not a directory");
                throw new IOException(sb2.toString());
            } else if (destDir.exists()) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Destination '");
                sb3.append(destDir);
                sb3.append("' already exists");
                throw new FileExistsException(sb3.toString());
            } else if (!srcDir.renameTo(destDir)) {
                String canonicalPath = destDir.getCanonicalPath();
                StringBuilder sb4 = new StringBuilder();
                sb4.append(srcDir.getCanonicalPath());
                sb4.append(File.separator);
                if (!canonicalPath.startsWith(sb4.toString())) {
                    copyDirectory(srcDir, destDir);
                    deleteDirectory(srcDir);
                    if (srcDir.exists()) {
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append("Failed to delete original directory '");
                        sb5.append(srcDir);
                        sb5.append("' after copy to '");
                        sb5.append(destDir);
                        sb5.append("'");
                        throw new IOException(sb5.toString());
                    }
                    return;
                }
                StringBuilder sb6 = new StringBuilder();
                sb6.append("Cannot move directory: ");
                sb6.append(srcDir);
                sb6.append(" to a subdirectory of itself: ");
                sb6.append(destDir);
                throw new IOException(sb6.toString());
            }
        } else {
            throw new NullPointerException("Destination must not be null");
        }
    }

    public static void moveDirectoryToDirectory(File src, File destDir, boolean createDestDir) throws IOException {
        if (src == null) {
            throw new NullPointerException("Source must not be null");
        } else if (destDir != null) {
            if (!destDir.exists() && createDestDir) {
                destDir.mkdirs();
            }
            if (!destDir.exists()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Destination directory '");
                sb.append(destDir);
                sb.append("' does not exist [createDestDir=");
                sb.append(createDestDir);
                sb.append("]");
                throw new FileNotFoundException(sb.toString());
            } else if (destDir.isDirectory()) {
                moveDirectory(src, new File(destDir, src.getName()));
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Destination '");
                sb2.append(destDir);
                sb2.append("' is not a directory");
                throw new IOException(sb2.toString());
            }
        } else {
            throw new NullPointerException("Destination directory must not be null");
        }
    }

    public static void moveFile(File srcFile, File destFile) throws IOException {
        if (srcFile == null) {
            throw new NullPointerException("Source must not be null");
        } else if (destFile != null) {
            String str = "Source '";
            if (srcFile.exists()) {
                String str2 = "' is a directory";
                if (!srcFile.isDirectory()) {
                    String str3 = "Destination '";
                    if (destFile.exists()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str3);
                        sb.append(destFile);
                        sb.append("' already exists");
                        throw new FileExistsException(sb.toString());
                    } else if (destFile.isDirectory()) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str3);
                        sb2.append(destFile);
                        sb2.append(str2);
                        throw new IOException(sb2.toString());
                    } else if (!srcFile.renameTo(destFile)) {
                        copyFile(srcFile, destFile);
                        if (!srcFile.delete()) {
                            deleteQuietly(destFile);
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append("Failed to delete original file '");
                            sb3.append(srcFile);
                            sb3.append("' after copy to '");
                            sb3.append(destFile);
                            sb3.append("'");
                            throw new IOException(sb3.toString());
                        }
                    }
                } else {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(str);
                    sb4.append(srcFile);
                    sb4.append(str2);
                    throw new IOException(sb4.toString());
                }
            } else {
                StringBuilder sb5 = new StringBuilder();
                sb5.append(str);
                sb5.append(srcFile);
                sb5.append("' does not exist");
                throw new FileNotFoundException(sb5.toString());
            }
        } else {
            throw new NullPointerException("Destination must not be null");
        }
    }

    public static void moveFileToDirectory(File srcFile, File destDir, boolean createDestDir) throws IOException {
        if (srcFile == null) {
            throw new NullPointerException("Source must not be null");
        } else if (destDir != null) {
            if (!destDir.exists() && createDestDir) {
                destDir.mkdirs();
            }
            if (!destDir.exists()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Destination directory '");
                sb.append(destDir);
                sb.append("' does not exist [createDestDir=");
                sb.append(createDestDir);
                sb.append("]");
                throw new FileNotFoundException(sb.toString());
            } else if (destDir.isDirectory()) {
                moveFile(srcFile, new File(destDir, srcFile.getName()));
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Destination '");
                sb2.append(destDir);
                sb2.append("' is not a directory");
                throw new IOException(sb2.toString());
            }
        } else {
            throw new NullPointerException("Destination directory must not be null");
        }
    }

    public static void moveToDirectory(File src, File destDir, boolean createDestDir) throws IOException {
        if (src == null) {
            throw new NullPointerException("Source must not be null");
        } else if (destDir == null) {
            throw new NullPointerException("Destination must not be null");
        } else if (!src.exists()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Source '");
            sb.append(src);
            sb.append("' does not exist");
            throw new FileNotFoundException(sb.toString());
        } else if (src.isDirectory()) {
            moveDirectoryToDirectory(src, destDir, createDestDir);
        } else {
            moveFileToDirectory(src, destDir, createDestDir);
        }
    }

    public static boolean isSymlink(File file) throws IOException {
        if (file != null) {
            return Files.isSymbolicLink(file.toPath());
        }
        throw new NullPointerException("File must not be null");
    }
}
