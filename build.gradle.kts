plugins {
    kotlin("android") version "1.9.24" apply false
    id("com.android.application") version "8.3.1" apply false
    id("com.android.library") version "8.3.1" apply false
  //  id("org.jetbrains.kotlin.android") version "1.9.10" apply false
   // id("org.jetbrains.kotlin.plugin.compose") version "1.9.10" apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}