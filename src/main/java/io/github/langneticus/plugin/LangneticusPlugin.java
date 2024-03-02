package io.github.langneticus.plugin;

import io.github.langneticus.core.NotNull;
import org.gradle.api.Plugin;
import org.gradle.api.Project;


public final class LangneticusPlugin implements Plugin<Project> {
    @Override
    public void apply(@NotNull final Project target) {
        target.getTasks().create("langneticus", TaskGenerateClassFromJsonFile.class);
    }
}
