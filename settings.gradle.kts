rootProject.name = "GharKeKaam"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        maven("https://gitlive.github.io/firebase-kotlin-sdk/maven")
    }
}

include(":composeApp")
include(":core:data")
include(":core:ui")
include(":feature:auth")
include(":feature:house")
include(":navigation")