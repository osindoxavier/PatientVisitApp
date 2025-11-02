// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Top-level build.gradle.kts
// -----------------------------------------------
// Global Gradle configuration for your project.
// -----------------------------------------------

plugins {
    // Android Application plugin
    alias(libs.plugins.android.application) apply false

    // Kotlin Android core support
    alias(libs.plugins.kotlin.android) apply false

    // Kotlin Serialization plugin (for JSON parsing with Ktor)
    alias(libs.plugins.kotlin.serialization) apply false

    // Google KSP plugin (for Room annotation processing)
    alias(libs.plugins.google.ksp) apply false

    alias(libs.plugins.kotlin.compose.compiler)
}