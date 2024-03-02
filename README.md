# Langneticus gradle plugin

## Usage
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