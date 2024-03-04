package io.github.langneticus.plugin;


import io.github.langneticus.core.exceptions.FailedToCreateDirectoryException;
import io.github.langneticus.core.util.DirectoryUtil;
import io.github.langneticus.core.util.FileUtil;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    private final static String PLUGIN_ID = "io.github.ctrlaltdeletemenot.langneticus";
    private final static String TASK_GENERATE_CLASSES = "langneticus";

    @Test
    public void success_Using_Gradle_8_3() throws IOException, FailedToCreateDirectoryException {
        final TestOptions testOptions = new TestOptions();
        testOptions.gradleVersion = "8.3";
        testOptions.prefix = "success";
        testOptions.languages = "[\"en-US\",\"ro-RO\"]";
        assertFalse(callPlugin(testOptions).contains("failure"));
    }

    @Test
    public void success_Single_Language_Using_Gradle_8_3() throws IOException, FailedToCreateDirectoryException {
        final TestOptions testOptions = new TestOptions();
        testOptions.gradleVersion = "8.3";
        testOptions.prefix = "success_Single_Language";
        testOptions.languages = "[\"en-US\"]";
        assertFalse(callPlugin(testOptions).contains("failure"));
    }

    @Test
    public void fails_Single_Non_Existing_Language_Using_Gradle_8_3() {
        final TestOptions testOptions = new TestOptions();
        testOptions.gradleVersion = "8.3";
        testOptions.prefix = "fails_Single_Non_Existing_Language";
        testOptions.languages = "[\"mk-UL\"]";
        assertThrows(org.gradle.testkit.runner.UnexpectedBuildFailure.class,()->callPlugin(testOptions));
    }

    @Test
    public void fails_Empty_Array_Language_Using_Gradle_8_3() {
        final TestOptions testOptions = new TestOptions();
        testOptions.gradleVersion = "8.3";
        testOptions.prefix = "fails_Empty_Array_Language";
        testOptions.languages = "[]";
        assertThrows(org.gradle.testkit.runner.UnexpectedBuildFailure.class,()->callPlugin(testOptions));
    }

    @Test
    public void success_Using_Gradle_8_2() throws IOException, FailedToCreateDirectoryException {
        final TestOptions testOptions = new TestOptions();
        testOptions.gradleVersion = "8.2";
        testOptions.prefix = "success";
        testOptions.languages = "[\"en-US\",\"ro-RO\"]";
        assertFalse(callPlugin(testOptions).contains("failure"));
    }

    @Test
    public void success_Using_Gradle_4_3() throws IOException, FailedToCreateDirectoryException {
        final TestOptions testOptions = new TestOptions();
        testOptions.gradleVersion = "4.3";
        testOptions.prefix = "success";
        testOptions.languages = "[\"en-US\",\"ro-RO\"]";
        assertFalse(callPlugin(testOptions).contains("failure"));
    }

    @Test
    public void success_Using_Gradle_2_8() throws IOException, FailedToCreateDirectoryException {
        final TestOptions testOptions = new TestOptions();
        testOptions.gradleVersion = "2.8";
        testOptions.prefix = "success";
        testOptions.languages = "[\"en-US\",\"ro-RO\"]";
        assertFalse(callPlugin(testOptions).contains("failure"));
    }

    @Test
    public void warns_Using_Gradle_8_3() throws IOException, FailedToCreateDirectoryException {
        final TestOptions testOptions = new TestOptions();
        testOptions.gradleVersion = "8.3";
        testOptions.prefix = "warns";
        testOptions.jsonContent = "{\n" +
                "    \"ACCOUNT\": {\n" +
                "        \"ro-RO\": \"$_ACCOUNT_$\",\n" +
                "    },\n" +
                "    \"SETTINGS\": {\n" +
                "        \"ro-RO\": \"$_SETTINGS_$\",\n" +
                "        \"en-US\": \"$_SETTINGS_$\"\n" +
                "    }\n" +
                "}";
        testOptions.languages = "[\"en-US\",\"ro-RO\"]";
        String output = callPlugin(testOptions);
        assertTrue(output.contains("[-] Warning, a translation is missing for key ACCOUNT, language en-US."));
    }

    @Test
    public void warns_No_Translation_For_One_Label_Using_Gradle_8_3() throws IOException, FailedToCreateDirectoryException {
        final TestOptions testOptions = new TestOptions();
        testOptions.gradleVersion = "8.3";
        testOptions.prefix = "warns_No_Translation_For_One_Label";
        testOptions.jsonContent = "{\n" +
                "    \"ACCOUNT\": {\n" +
                "    },\n" +
                "    \"EMPTY\": {\n" +
                "        \"ro-RO\": \"\",\n" +
                "        \"en-US\": \"\"\n" +
                "    },\n" +
                "    \"ONE\": {\n" +
                "        \"ro-RO\": \"ONE-RO-TEST\",\n" +
                "    },\n" +
                "    \"SETTINGS\": {\n" +
                "        \"ro-RO\": \"$_RO_SETTINGS_$\",\n" +
                "        \"en-US\": \"$_EN_US_SETTINGS_$\"\n" +
                "    }\n" +
                "}";
        testOptions.languages = "[\"en-US\",\"ro-RO\"]";
        String output = callPlugin(testOptions);
        assertTrue(output.contains("[-] Warning, a translation is missing for key ACCOUNT, language en-US."));
    }

    @Test
    public void warns_No_Translation_For_Any_Label_Using_Gradle_8_3() throws IOException, FailedToCreateDirectoryException {
        final TestOptions testOptions = new TestOptions();
        testOptions.gradleVersion = "8.3";
        testOptions.prefix = "warns_No_Translation_For_Any_Label";
        testOptions.jsonContent = "{\n" +
                "    \"ACCOUNT\": {\n" +
                "    },\n" +
                "    \"SETTINGS\": {\n" +
                "    }\n" +
                "}";
        testOptions.languages = "[\"en-US\",\"ro-RO\"]";
        String output = callPlugin(testOptions);
        assertTrue(output.contains("[-] Warning, a translation is missing for key ACCOUNT, language en-US."));
    }

    @Test
    public void fails_Label_Too_Short_Using_Gradle_8_3()  {
        final TestOptions testOptions = new TestOptions();
        testOptions.gradleVersion = "8.3";
        testOptions.prefix = "fails_Label_Too_Short";
        testOptions.jsonContent = "{\n" +
                "    \"A\": {\n" +
                "        \"ro-RO\": \"$_ACCOUNT_$\",\n" +
                "    },\n" +
                "    \"SETTINGS\": {\n" +
                "        \"ro-RO\": \"$_SETTINGS_$\",\n" +
                "        \"en-US\": \"$_SETTINGS_$\"\n" +
                "    }\n" +
                "}";
        testOptions.languages = "[\"en-US\",\"ro-RO\"]";
        assertThrows(org.gradle.testkit.runner.UnexpectedBuildFailure.class, () -> callPlugin(testOptions));
    }

    @Test
    public void fails_Label_Starts_With_Number_Using_Gradle_8_3() {
        final TestOptions testOptions = new TestOptions();
        testOptions.gradleVersion = "8.3";
        testOptions.prefix = "fails_Label_Starts_With_Number";
        testOptions.jsonContent = "{\n" +
                "    \"000A\": {\n" +
                "        \"ro-RO\": \"$_ACCOUNT_$\",\n" +
                "    },\n" +
                "    \"SETTINGS\": {\n" +
                "        \"ro-RO\": \"$_SETTINGS_$\",\n" +
                "        \"en-US\": \"$_SETTINGS_$\"\n" +
                "    }\n" +
                "}";
        testOptions.languages = "[\"en-US\",\"ro-RO\"]";
        assertThrows(org.gradle.testkit.runner.UnexpectedBuildFailure.class, () -> callPlugin(testOptions));
    }

    @Test
    public void fails_Empty_Json_Using_Gradle_8_3() {
        final TestOptions testOptions = new TestOptions();
        testOptions.gradleVersion = "8.3";
        testOptions.prefix = "fails_Empty_Json";
        testOptions.jsonContent = "{}";
        testOptions.languages = "[\"en-US\",\"ro-RO\"]";
        assertThrows(org.gradle.testkit.runner.UnexpectedBuildFailure.class, () -> callPlugin(testOptions));
    }

    @Test
    public void fails_Malformed_Json_Using_Gradle_8_3() {
        final TestOptions testOptions = new TestOptions();
        testOptions.gradleVersion = "8.3";
        testOptions.prefix = "fails_Malformed_Json";
        testOptions.jsonContent = "{}}";
        testOptions.languages = "[\"en-US\",\"ro-RO\"]";
        assertThrows(org.gradle.testkit.runner.UnexpectedBuildFailure.class, () -> callPlugin(testOptions));
    }


    private String callPlugin(final TestOptions aTestOptions) throws IOException, FailedToCreateDirectoryException {
        //Set up the test build
        final File projectDir = new File(System.getProperty("integration.test.dir"), String.format(Locale.US, "%s_%s", aTestOptions.prefix, aTestOptions.gradleVersion));
        DirectoryUtil.create(projectDir);
        final File settingsFile = new File(projectDir, "settings.gradle");
        final File buildFile = new File(projectDir, "build.gradle");
        FileUtil.writeMultibyteString(settingsFile, "");
        FileUtil.writeMultibyteString(buildFile, "plugins { id('" + PLUGIN_ID + "')}\nlangneticus{\n\tinputJsonFile = project.file(\"I18N_DATA/strings.langneticus.json\")\n\toutDir = project.file(\"GEN\")\n\tpackageName=\"org.langneticus.gen\"\n\tsupportedLanguages="+aTestOptions.languages+"\n\tclassName='Lm'\n}");
        //Create json if required
        if (aTestOptions.jsonContent != null) {
            final File jsonDir = new File(projectDir, "I18N_DATA");
            DirectoryUtil.create(jsonDir);
            final File jsonFile = new File(jsonDir, "strings.langneticus.json");
            FileUtil.writeMultibyteString(jsonFile, aTestOptions.jsonContent);
        }
        // Run the build
        GradleRunner runner = GradleRunner.create();
        runner.forwardOutput();
        runner.withPluginClasspath();
        runner.withGradleVersion(aTestOptions.gradleVersion);
        runner.withArguments(TASK_GENERATE_CLASSES, "--stacktrace");
        runner.withProjectDir(projectDir);
        BuildResult resultGenerateClasses = runner.build();
        return resultGenerateClasses.getOutput();
        //assertFalse(resultGenerateClasses.getOutput().contains("failure"));
    }

    static class TestOptions {
        String gradleVersion;
        String prefix;
        String jsonContent;
        String languages;
    }

}

