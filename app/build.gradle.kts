plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "io.github.dot166.ThemeEngine"
    compileSdk = 35

    defaultConfig {
        applicationId = "io.github.dot166.ThemeEngine"
        minSdk = 24
        targetSdk = 35
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"))
        }
    }

    aaptOptions.additionalParameters.add("--auto-add-overlay")
}

dependencies {
    implementation("io.github.dot166:j-Lib:3.3.2")
}
