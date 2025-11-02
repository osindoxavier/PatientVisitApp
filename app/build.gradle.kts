plugins {
    // Core Android and Kotlin support
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    // Kotlin Serialization for JSON parsing (used with Ktor)
    id("org.jetbrains.kotlin.plugin.serialization")

    // KSP (Kotlin Symbol Processing) - preferred over kapt for Room
    id("com.google.devtools.ksp")

    id("org.jetbrains.kotlin.plugin.compose")

}

android {
    namespace = "com.intellisoft.patientvisit"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.Intellisoft.patientvisit"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            // Dev server (can point to staging)
            buildConfigField("String", "BASE_URL", "\"https://patientvisitapis.intellisoftkenya.com/api/\"")
        }
        release {
            // Production server (same or different)
            buildConfigField("String", "BASE_URL", "\"https://patientvisitapis.intellisoftkenya.com/api/\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    }
    android.buildFeatures.buildConfig = true
}

dependencies {
    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Room (with KSP)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Ktor + Serialization
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging)

    // --- Koin Dependency Injection ---
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    //Material Icon
    implementation(libs.androidx.material.icons.extended)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}