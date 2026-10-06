pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/") { name = "Fabric" }
        maven("https://maven.kikugie.dev/releases") { name = "KikuGie Releases" }
        maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.7.11"
}

stonecutter {
    create(rootProject) {
        versions("1.20.1", "1.21.1")
        vcsVersion = "1.20.1"
    }
}

rootProject.name = "locate-plus"
