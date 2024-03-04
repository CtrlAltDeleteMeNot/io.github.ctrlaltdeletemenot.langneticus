# Langneticus gradle plugin
Langneticus is a powerful Gradle plugin designed to streamline the process of managing and generating localized strings for your Java projects.

## Compiling the plugin
To compile the Langneticus Gradle plugin, follow these steps:

1. Open a terminal and navigate to the root of the project.
2. Run the following command to generate the Gradle wrapper:
```
gradle wrapper --warning-mode all
```
This ensures that the project uses a specific version of Gradle without committing the JAR file to the repository.

## Using the plugin
To use the Langneticus Gradle plugin in your project, add the following configuration to your build.gradle file:
```
plugins { 
    id('io.github.langneticus')
}

langneticus {
    inputJsonFile = project.file("I18N_DATA/strings.langneticus.json")
    outDir = project.file("GEN")
    packageName="org.langneticus.gen"
    supportedLanguages=["en-US","ro-RO"]
    className='Lm'
}
```
Make sure to customize the parameters according to your project's needs:

- inputJsonFile: Path to the JSON file containing the localized strings data. If it does not exist, it will be generated automatically.
- outDir: Output directory for the generated files.
- packageName: Package name for the generated Java files.
- supportedLanguages: List of supported language codes (Make sure these are valid in Java and contain language and country).
- className: Name of the generated class.

Feel free to explore and adapt these configurations to fit your project requirements.