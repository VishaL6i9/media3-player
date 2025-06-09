plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.vishal.media3player"
    compileSdk = 35

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/versions/9/OSGI-INF/MANIFEST.MF"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/NOTICE.md"
        }
    }

    defaultConfig {
        applicationId = "com.vishal.media3player"
        minSdk = 35
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }
    buildToolsVersion = "36.0.0"
    lint {
        checkReleaseBuilds = false
        abortOnError = false
        lintConfig = null
        checkDependencies = false
        warningsAsErrors = false
        ignoreWarnings = true
        disable.add("ALL")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.lifecycle.runtime.ktx)

        val media3_version = "1.7.1"

        // For media playback using ExoPlayer
        implementation("androidx.media3:media3-exoplayer:$media3_version")
        // For building media playback UIs using Compose
        implementation("androidx.media3:media3-ui-compose:$media3_version")
        // For building media playback UIs using Views
        implementation("androidx.media3:media3-ui:$media3_version")
        // For building media playback UIs using Jetpack Compose
        implementation("androidx.media3:media3-ui-compose:$media3_version")
        // Common functionality used across multiple media libraries
        implementation("androidx.media3:media3-common:$media3_version")
        // Common Kotlin-specific functionality
        implementation("androidx.media3:media3-common-ktx:$media3_version")
        implementation ("androidx.lifecycle:lifecycle-viewmodel-compose")
        implementation ("androidx.lifecycle:lifecycle-runtime-ktx")
        implementation ("androidx.lifecycle:lifecycle-livedata-ktx")

}