pluginManagement {
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
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        // 👇 ADD THIS REPOSITORY BLOCK 👇
        maven {
            url = uri("https://storage.googleapis.com/download.flutter.io")
        }
    }
}

rootProject.name = "ToDoListAppv2"
include(":app")
//include(":analytics_module")
//project(":analytics_module").projectDir = File(rootDir, "analytics_module/.android")

val flutterProjectRoot = File(settingsDir, "analytics_module") // Points to the folder inside your root
val pluginsFile = File(flutterProjectRoot, ".android/include_flutter.groovy")

if (pluginsFile.exists()) {
    apply(from = pluginsFile) // This script creates the project named ":flutter"
} else {
    println("❌ Error: Flutter script not found at ${pluginsFile.absolutePath}")
}