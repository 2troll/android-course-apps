plugins {
    id("com.android.application")
}

android {
    namespace = "com.tonyhanma.aboutpage"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.tonyhanma.aboutpage"
        minSdk = 24
        // 34: los diseños originales no contemplan el edge-to-edge obligatorio de Android 15
        targetSdk = 34
        versionCode = 2
        versionName = "2.0"
    }

    androidResources {
        localeFilters += listOf("en", "es", "ar")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
            // Firmada con la clave de depuración para poder instalar la APK publicada sin más
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
}
