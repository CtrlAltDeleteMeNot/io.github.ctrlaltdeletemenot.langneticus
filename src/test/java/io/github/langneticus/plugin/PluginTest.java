package io.github.langneticus.plugin;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PluginTest {
    @Test
    public void success_Applying_Plugin(){
        Project project = ProjectBuilder.builder().build();
        project.getPluginManager().apply("io.github.ctrlaltdeletemenot.langneticus");

        assertTrue(project.getPluginManager()
                .hasPlugin("io.github.ctrlaltdeletemenot.langneticus"));

        final Task generate = project.getTasks().getByName("langneticus");
        assertNotNull(generate);
    }
}
