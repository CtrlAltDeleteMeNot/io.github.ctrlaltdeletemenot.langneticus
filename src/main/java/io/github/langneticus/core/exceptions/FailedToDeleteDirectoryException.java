package io.github.langneticus.core.exceptions;

import io.github.langneticus.core.NotNull;

import java.io.File;

public class FailedToDeleteDirectoryException extends Exception {
    public FailedToDeleteDirectoryException(@NotNull File aFile) {
        super("Directory " + aFile.getAbsolutePath() + " could not be deleted.");
    }
}
