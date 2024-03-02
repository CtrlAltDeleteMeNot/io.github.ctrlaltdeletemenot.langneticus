package io.github.langneticus.core.util;



import io.github.langneticus.core.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public final class FileUtil {
    private FileUtil() {
    }

    public static String readMultibyteString(@NotNull String anAbsoluteFilePath) throws IOException {
        final File file = new File(anAbsoluteFilePath);
        return readMultibyteString(file);
    }

    public static String readMultibyteString(@NotNull File aPath) throws IOException {
        return readMultibyteString(aPath.toPath());
    }

    public static String readMultibyteString(@NotNull Path aPath) throws IOException {
        final byte[] content = java.nio.file.Files.readAllBytes(aPath);
        return new String(content, StandardCharsets.UTF_8);
    }

    public static void writeMultibyteString(@NotNull String anAbsolutePath, @NotNull String aString) throws IOException {
        final File file = new File(anAbsolutePath);
        writeMultibyteString(file, aString);
    }
    public static void writeMultibyteString(@NotNull File aFile, @NotNull String aString) throws IOException {
        writeMultibyteString(aFile.toPath(), aString);
    }

    public static void writeMultibyteString(@NotNull Path aPath, @NotNull String aString) throws IOException {
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                java.nio.file.Files.newOutputStream(aPath, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE, StandardOpenOption.WRITE),
                StandardCharsets.UTF_8)) {
            outputStreamWriter.write(aString);
        }
    }


}
