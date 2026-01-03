plugins {
    // No plugins applied at the root level
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.5.0")
        classpath(kotlin("gradle-plugin", version = "2.0.0"))
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
