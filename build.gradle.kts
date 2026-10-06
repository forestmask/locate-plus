plugins {
    id("fabric-loom") version "1.7-SNAPSHOT"
}

val mcVersion: String = stonecutter.current.version
val javaVersion: Int = property("deps.java").toString().toInt()
val mcCompat: String = property("mc_compat").toString()

version = "${property("mod.version")}+$mcVersion"
base.archivesName = property("mod.id").toString()

repositories {
    mavenCentral()
}

dependencies {
    minecraft("com.mojang:minecraft:$mcVersion")
    mappings("net.fabricmc:yarn:${property("deps.yarn")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")

    // Only the two Fabric API modules this mod actually uses. Depending on the whole API would
    // pull in every module and remap them all, which is slow and needless for a command mod.
    for (module in listOf("fabric-command-api-v2", "fabric-lifecycle-events-v1")) {
        modImplementation(fabricApi.module(module, property("deps.fabric_api").toString()))
    }
}

// The licence text is not kept in this repo; GitHub writes LICENSE at the root when the licence
// is set there, and this packages that file into the jar so a downloaded build carries it too.
// Missing file means missing from the jar, which is what happens before the repo exists.
tasks.jar {
    from(rootProject.file("LICENSE"))
}

tasks.processResources {
    inputs.property("version", version)
    inputs.property("mc_compat", mcCompat)
    inputs.property("java_version", javaVersion)
    filesMatching("fabric.mod.json") {
        expand(mapOf(
                "version" to version,
                "mc_compat" to mcCompat,
                "java_version" to javaVersion))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release = javaVersion
    options.encoding = "UTF-8"
}

java {
    val target = JavaVersion.toVersion(javaVersion)
    sourceCompatibility = target
    targetCompatibility = target
}
