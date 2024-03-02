package io.github.langneticus.core.util;

import io.github.langneticus.core.exceptions.FailedToCreateDirectoryException;
import io.github.langneticus.core.exceptions.FailedToDeleteDirectoryException;
import io.github.langneticus.core.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public final class DirectoryUtil {
    private DirectoryUtil() {
    }

    public static void create(@NotNull final String anAbsolutePath) throws FailedToCreateDirectoryException {
        final File file = new File(anAbsolutePath);
        create(file);
    }

    public static void create(@NotNull final File aFile) throws FailedToCreateDirectoryException {
        if (aFile.exists()) {
            return;
        }
        final boolean created = aFile.mkdirs();
        if (!created) {
            throw new FailedToCreateDirectoryException(aFile);
        }
    }

    public static void delete(@NotNull final String anAbsolutePath) throws IOException, FailedToDeleteDirectoryException {
        final Path dir = Paths.get(anAbsolutePath);
        delete(dir);
    }

    public static void delete(@NotNull final File aFile) throws IOException, FailedToDeleteDirectoryException {
        final Path dir = aFile.toPath();
        delete(dir);
    }

    private static void delete(@NotNull final Path aPath) throws IOException, FailedToDeleteDirectoryException {
        final AtomicBoolean hasFailure = new AtomicBoolean(false);
        try(Stream<Path> stream = Files.walk(aPath).sorted(Comparator.reverseOrder())){
            stream.forEach((anItem)->{
                try {
                    Files.delete(anItem);
                } catch (IOException e) {
                   hasFailure.set(true);
                }
            });
        }
        if(hasFailure.get()){
            throw new FailedToDeleteDirectoryException(aPath.toFile());
        }
    }

}
