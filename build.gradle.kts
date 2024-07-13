import com.android.tools.build.bundletool.model.utils.Versions

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
     repositories {
        gradlePluginPortal()
        google()
//        jcenter()
        mavenCentral()
    }
    dependencies {

//        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.48")

//        classpath ("de.mannodermaus.gradle.plugins:android-junit5:${Versions.junit5}")
    }

}



plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false

    id("com.google.dagger.hilt.android") version "2.48" apply false

}