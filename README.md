# Langneticus gradle plugin
## Compiling the plugin
In order to prevent committing a jar to repo, maintainers should execute the following command from the root of the project:
```
gradle wrapper --warning-mode all
```
## Compiling the plugin
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