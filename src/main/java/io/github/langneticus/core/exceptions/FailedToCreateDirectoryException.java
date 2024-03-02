package io.github.langneticus.core.exceptions;

import io.github.langneticus.core.NotNull;

import java.io.File;

public class FailedToCreateDirectoryException extends Exception {
    public FailedToCreateDirectoryException(@NotNull File aFile) {
        super("Directory " + aFile.getAbsolutePath() + " could not be created.");
    }
}
