@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "Hict7"

include(":app")
include(":core:audio")
include(":core:common")
include(":core:common-res")
include(":core:data")
include(":core:database")
include(":core:datastore")
include(":core:datastore-prefs")
include(":core:datastore-proto")
include(":core:designsystem")
include(":core:model")
include(":core:timer")
include(":core:ui")
include(":feature:editor")
include(":feature:home")
include(":feature:settings")
include(":feature:timer")
