import org.lineageos.generatebp.GenerateBpPlugin
import org.lineageos.generatebp.GenerateBpPluginExtension
import org.lineageos.generatebp.models.Module

apply {
    plugin<GenerateBpPlugin>()
}

buildscript {
    repositories {
        maven("https://raw.githubusercontent.com/dot166/gradle-generatebp/main/.m2")
    }

    dependencies {
        classpath("org.lineageos:gradle-generatebp:+")
    }
}

configure<GenerateBpPluginExtension> {
    targetSdk.set(extra.get("targetSdk") as Int)
    availableInAOSP.set { module: Module ->
        when {
            module.group == "androidx.databinding" -> false
            module.name == "preference-ktx" -> false
            module.group == "com.google.accompanist" -> false
            module.group.startsWith("androidx.compose") -> false
            module.group.startsWith("androidx") -> true
            module.group.startsWith("com.google") -> true
            module.group == "io.github.dot166" -> true
            else -> false
        }
    }
}