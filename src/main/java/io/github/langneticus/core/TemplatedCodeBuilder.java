package io.github.langneticus.core;

import io.github.langneticus.core.util.ByteUtil;
import io.github.langneticus.core.util.FileUtil;
import io.github.langneticus.core.validation.exceptions.ValidationException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

class TemplatedCodeBuilder {
    private final static String TEMPLATE = "package org.langneticus.reference;\n" +
            "\n" +
            "import java.nio.charset.StandardCharsets;\n" +
            "import java.util.*;\n" +
            "\n" +
            "public class I18n {\n" +
            "\n" +
            "    private I18n(final int... data) {\n" +
            "        values = new String[LANGUAGE_COUNT];\n" +
            "        int startOffset = LANGUAGE_COUNT;\n" +
            "        for (int i = 0; i < LANGUAGE_COUNT; i++) {\n" +
            "            int outputSize;\n" +
            "            if (i == LANGUAGE_COUNT - 1) {\n" +
            "                outputSize = data.length - data[i];\n" +
            "            } else {\n" +
            "                outputSize = data[i + 1] - data[i];\n" +
            "            }\n" +
            "            final byte[] output = new byte[outputSize];\n" +
            "            for (int j = 0; j < output.length; j++) {\n" +
            "                output[j] = BITMAP.get((byte) data[j + startOffset]);\n" +
            "            }\n" +
            "            startOffset += output.length;\n" +
            "            values[i] = new String(output, StandardCharsets.UTF_8);\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    ///---EXPLAIN NUMBERS---\n" +
            "    public String get(int value) {\n" +
            "        return values[value];\n" +
            "    }\n" +
            "\n" +
            "    private final String[] values;\n" +
            "    private static final Map<Byte, Byte> BITMAP;\n" +
            "    final static int LANGUAGE_COUNT;\n" +
            "\n" +
            "    ///---BEGIN DYNAMIC---\n" +
            "    static {\n" +
            "        LANGUAGE_COUNT = 2;\n" +
            "        BITMAP = new HashMap<>();\n" +
            "        BITMAP.put((byte) 0x00, (byte) 0x01);\n" +
            "    }\n" +
            "    ///---END DYNAMIC---\n" +
            "}";
    private String packageName;
    private String className;
    private List<String> stringKeys;

    private List<String> languages;
    private Map<String, Object> stringValues;

    public TemplatedCodeBuilder() {

    }

    public TemplatedCodeBuilder setPackageName(@NotNull String aPackageName) {
        this.packageName = aPackageName;
        return this;
    }

    public TemplatedCodeBuilder setClassName(@NotNull String aClassName) {
        this.className = aClassName;
        return this;
    }

    public TemplatedCodeBuilder setStringKeys(@NotNull Set<String> aStringKeys) {
        stringKeys = new ArrayList<>(aStringKeys);
        return this;
    }

    public TemplatedCodeBuilder setStringValues(@NotNull Map<String, Object> aStringValues) {
        stringValues = aStringValues;
        return this;
    }

    public TemplatedCodeBuilder setLanguages(@NotNull List<String> aLanguages) {
        languages = aLanguages;
        return this;
    }

    @SuppressWarnings("unchecked")
    public String build() throws ValidationException {
        String temp = TEMPLATE
                .replace("package org.langneticus.reference;", "package " + packageName + ";")
                .replace("I18n", className);
        final StringBuilder explanation = new StringBuilder();
        for (int i = 0; i < languages.size(); i++) {
            explanation.append("    // get(").append(i).append(") - Gets strings for language ").append(languages.get(i)).append("\n");
        }

        temp = temp.replace("    ///---EXPLAIN NUMBERS---\n", explanation.toString());
        final int start = temp.indexOf("///---BEGIN DYNAMIC---");
        final int end = temp.indexOf("///---END DYNAMIC---") + 20;
        final StringBuilder toReturn = new StringBuilder();
        toReturn.append(temp, 0, start);
        toReturn.append("\n    static {\n")
                .append("        BITMAP = new HashMap<>();\n")
                .append("        LANGUAGE_COUNT = ").append(languages.size()).append(";\n");
        final Map<Byte, Byte> substitutionMap = ByteUtil.createSubstitutionBox();
        final Map<Byte, Byte> reverseSubstitutionMap = ByteUtil.createReverseSubstitutionBox(substitutionMap);
        for (final Byte b : reverseSubstitutionMap.keySet()) {
            toReturn.append("        BITMAP.put((byte) ").append(b).append(",(byte) ").append(reverseSubstitutionMap.get(b)).append(");\n");
        }

        toReturn.append("\n    }\n");
        for (final String stringKey : stringKeys) {
            final Map<String, String> stringValues = (Map<String, String>) this.stringValues.get(stringKey);
            for (final String language : languages) {
                toReturn.append("\n    /*").append(language).append(" : ").append(stringValues.getOrDefault(language, "missing-" + stringKey)).append("*/");
            }
            toReturn.append("\n    public static ").append(className).append(" ").append(stringKey).append(" = new ").append(className).append("(").append(obfuscate(stringKey, stringValues, substitutionMap)).append(");");
        }
        toReturn.append(temp, end, temp.length());
        return toReturn.toString();
    }

    private String obfuscate(@NotNull final String fallbackValue, @NotNull Map<String, String> stringVariants, @NotNull Map<Byte, Byte> substitutionMap) throws ValidationException {
        final List<byte[]> unicodeBytesCollection = new LinkedList<>();
        for (final String language : languages) {
            unicodeBytesCollection.add(stringVariants.getOrDefault(language, "$missing-" + language + "$" +fallbackValue).getBytes(StandardCharsets.UTF_8));
        }
        final List<Integer> toReturn = new LinkedList<>();
        final AtomicInteger futureOffset = new AtomicInteger(languages.size());
        unicodeBytesCollection.forEach(aBinaryTranslation -> toReturn.add(futureOffset.getAndAdd(aBinaryTranslation.length)));
        for (final byte[] binaryTranslation : unicodeBytesCollection) {
            final byte[] transformedBinaryTranslation = ByteUtil.transform(substitutionMap, binaryTranslation);
            for (final byte b : transformedBinaryTranslation) {
                toReturn.add((int) b);
            }
        }
        return toReturn.stream().map(String::valueOf).collect(Collectors.joining(", "));
    }

    public File saveTo(@NotNull File anOutDir) throws IOException, ValidationException {
        final File theFile = new File(anOutDir, className + ".java");
        if (theFile.exists()) {
            final boolean deleted = theFile.delete();
            if (!deleted) {
                throw new ValidationException("Failed to delete " + theFile.getAbsolutePath());
            }
        }
        FileUtil.writeMultibyteString(theFile, build());
        return theFile;
    }


}
