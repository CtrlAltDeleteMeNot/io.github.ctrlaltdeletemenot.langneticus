package io.github.langneticus.plugin;

import io.github.langneticus.core.TaskGenerateClassFromJsonFileCore;
import io.github.langneticus.core.NotNull;
import io.github.langneticus.core.Nullable;
import io.github.langneticus.core.exceptions.FailedToCreateDirectoryException;
import io.github.langneticus.core.validation.exceptions.ValidationException;
import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.Task;
import org.gradle.api.logging.LogLevel;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.*;
import org.gradle.api.tasks.options.Option;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class TaskGenerateClassFromJsonFile extends DefaultTask {
    private File inputJsonFile;
    private File outDir;
    private String packageName;
    private List<String> supportedLanguages;
    private String className;

    public TaskGenerateClassFromJsonFile() {
        super();
    }

    @Input
    public List<String> getSupportedLanguages() {
        return supportedLanguages;
    }

    @Option(option = "supportedLanguages", description = "Supported language tags")
    public void setSupportedLanguages(List<String> aSupportedLanguages) {
        this.supportedLanguages = aSupportedLanguages;
    }

    @Input
    public String getPackageName() {
        return packageName;
    }

    @Option(option = "packageName", description = "Package name for generated classes.")
    public void setPackageName(String aPackageName) {
        this.packageName = aPackageName;
    }

    @InputFiles
    public File getInputJsonFile() {
        return inputJsonFile;
    }

    @Option(option = "inputJsonFile", description = "Input json file containing translation rules.")
    public void setInputJsonFile(File anInputJsonFile) {
        this.inputJsonFile = anInputJsonFile;
    }

    @OutputDirectory
    public File getOutDir() {
        return outDir;
    }

    @Option(option = "outDir", description = "Output directory for generated classes.")
    public void setOutDir(File anOutputDir) {
        this.outDir = anOutputDir;
    }

    @Input
    public String getClassName() {
        return className;
    }

    @Option(option = "className", description = "Name for the generated class.")
    public void setClassName(String className) {
        this.className = className;
    }

    @TaskAction
    public void run() throws IOException, ValidationException, FailedToCreateDirectoryException {
        final TaskGenerateClassFromJsonFileCore impl = new TaskGenerateClassFromJsonFileCore(inputJsonFile.getAbsolutePath(), outDir, className, packageName, supportedLanguages, this::info, this::warn);
        impl.run();
    }

    private void info(@NotNull final String aMessage) {
        final Logger logger = getProject().getLogger();
        logger.log(LogLevel.LIFECYCLE, aMessage);
    }

    private void warn(@NotNull final String aMessage, @Nullable final Throwable aThrowable) {
        final Logger logger = getProject().getLogger();
        logger.log(LogLevel.LIFECYCLE, aMessage, aThrowable);
    }


}
