plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "jOS.ThemeEngine"
    compileSdk = 35

    defaultConfig {
        applicationId = "jOS.ThemeEngine"
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
    implementation("io.github.dot166:j-Lib:3.3.4")
}
