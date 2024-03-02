package io.github.langneticus.core;

import io.github.langneticus.core.exceptions.FailedToCreateDirectoryException;
import io.github.langneticus.core.util.DirectoryUtil;
import io.github.langneticus.core.util.FileUtil;
import io.github.langneticus.core.validation.ClassNameValidator;
import io.github.langneticus.core.validation.LanguageLabelValidator;
import io.github.langneticus.core.validation.LanguageTagValidator;
import io.github.langneticus.core.validation.PackageNameValidator;
import io.github.langneticus.core.validation.exceptions.ValidationException;
import org.gradle.internal.impldep.com.beust.jcommander.StringKey;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TaskGenerateClassFromJsonFileCore {
    public static final String SETTINGS = "SETTINGS";
    public static final String ACCOUNT = "ACCOUNT";
    private final String inputJsonFile;
    private final File outDir;
    private final String packageName;
    private final List<String> supportedLanguages;
    private final Consumer<String> info;
    private final BiConsumer<String, Throwable> warn;
    private final String className;
    private final ClassNameValidator classNameValidator;
    private final PackageNameValidator packageNameValidator;

    public TaskGenerateClassFromJsonFileCore(@NotNull String inputJsonFile, @NotNull File outDir, @NotNull String className, @NotNull String packageName, @NotNull List<String> supportedLanguages, @NotNull Consumer<String> info, @NotNull BiConsumer<String, Throwable> warn) {
        this.inputJsonFile = inputJsonFile;
        this.outDir = outDir;
        this.packageName = packageName;
        this.supportedLanguages = supportedLanguages;
        this.info = info;
        this.warn = warn;
        this.className = className;
        this.classNameValidator = new ClassNameValidator();
        this.packageNameValidator = new PackageNameValidator();
    }

    public void run() throws IOException, ValidationException, FailedToCreateDirectoryException {
        showBanner();
        validateSupportedLanguages();
        validateClassName();
        validatePackageName();
        validateInputJsonFile();
        validateOutputDirectory();
        createOutputDirectory();
        generateClass();
    }

    private void validateInputJsonFile() {
        Objects.requireNonNull(inputJsonFile, "Property inputJsonFile is not set.");
    }

    private void validateOutputDirectory() {
        Objects.requireNonNull(outDir, "Property outDir is not set.");
    }

    private void createOutputDirectory() throws FailedToCreateDirectoryException {
        DirectoryUtil.create(outDir);
    }

    private void validatePackageName() throws ValidationException {
        packageNameValidator.validate(packageName);
    }

    private void validateClassName() throws ValidationException {
        classNameValidator.validate(className);
    }

    private void validateSupportedLanguages() throws ValidationException {
        Objects.requireNonNull(supportedLanguages, "Property supportedLanguages not set.");
        if (supportedLanguages.isEmpty()) {
            throw new RuntimeException("Property supportedLanguages should contain at least an entry");
        }
        final LanguageTagValidator validator = new LanguageTagValidator();
        for (final String supportedLanguage : supportedLanguages) {
            validator.validate(supportedLanguage);
        }
    }


    private void showBanner() {
        info("=====================================");
        info("Welcome to Langneticus Gradle Plugin!");
        info("=====================================");
    }

    @SuppressWarnings("unchecked")
    private void generateClass() throws IOException, FailedToCreateDirectoryException, ValidationException {
        final File input = new File(inputJsonFile);
        if (!input.exists()) {
            generateSampleJson();
        }

        info("[+] Generating class " + packageName + "." + className + " from " + inputJsonFile + " to dir " + outDir.getAbsolutePath() + " ...");
        final String content = FileUtil.readMultibyteString(input);
        final JSONObject jsonObject = new JSONObject(content);
        final Map<String, Object> data = jsonObject.toMap();

        final Set<String> stringKeys = data.keySet();
        if (stringKeys.isEmpty()) {
            throw new RuntimeException("No valid keys defined in json file " + inputJsonFile + ".");
        }

        final LanguageLabelValidator validator = new LanguageLabelValidator(this::warn);
        for (final String stringKey: stringKeys){
            validator.validate(stringKey);
        }

        for (final String stringKey : stringKeys) {
            final Map<String, String> stringValues = (Map<String, String>) data.get(stringKey);
            for (final String language : supportedLanguages) {
                if (!stringValues.containsKey(language)) {
                    warn(String.format("[-] Warning, a translation is missing for key %s, language %s.", stringKey, language), null);
                }
            }
        }


        final File built = new TemplatedCodeBuilder()
                .setClassName(className)
                .setPackageName(packageName)
                .setStringKeys(stringKeys)
                .setStringValues(data)
                .setLanguages(supportedLanguages)
                .saveTo(outDir);

        info("[+] Generated class " + packageName + "." + className + " to file " + built.getAbsolutePath() + ".");

    }


    private void generateSampleJson() throws IOException, FailedToCreateDirectoryException {
        final File in = new File(inputJsonFile);
        info("[+] Generating sample json in file " + in.getAbsolutePath() + " ...");
        final File parent = in.getParentFile();
        DirectoryUtil.create(parent);

        final JSONObject object = new JSONObject();
        object.put(SETTINGS, getMapWithPlaceholder(SETTINGS));
        object.put(ACCOUNT, getMapWithPlaceholder(ACCOUNT));
        final String toWrite = object.toString(4);
        FileUtil.writeMultibyteString(in, toWrite);
    }

    private Map<String, String> getMapWithPlaceholder(final String aKey) {
        final Map<String, String> toReturn = new LinkedHashMap<>();
        for (String supportedLanguage : supportedLanguages) {
            toReturn.put(supportedLanguage, "$_" + aKey + "_$");
        }
        return toReturn;
    }

    private void info(@NotNull final String aMessage) {
        info.accept(aMessage);
    }

    private void warn(@NotNull String aMessage, @NotNull final Throwable anError) {
        warn.accept(aMessage, anError);
    }
}
