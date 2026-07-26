plugins {
    id("com.gradleup.shadow")
    id("dev.architectury.loom")
    id("architectury-plugin")
    id("move-tutor.shadow-platform-conventions")
}

architectury {
    platformSetupLoomIde()
    neoForge()
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/groups/public/")
    maven("https://thedarkcolour.github.io/KotlinForForge/")
    maven("https://maven.neoforged.net/releases/")
}

val shadowBundle: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

dependencies {
    minecraft(libs.minecraft.net)
    mappings(loom.officialMojangMappings())
    neoForge(libs.neoforge)
    implementation(project(":common", configuration = "namedElements"))
    "developmentNeoForge"(project(":common", configuration = "namedElements")) { isTransitive = false }
    shadowBundle(project(":common", configuration = "transformProductionNeoForge"))
    libs.bundles.neoforgeModImplementationNoTransitive.get().forEach { dependency ->
        modImplementation(dependency.copy()) { isTransitive = false }
    }
    forgeRuntimeLibrary(libs.kotlinforforge) {
        exclude("net.neoforged.fancymodloader", "loader")
    }
    add("modCompileOnly", libs.bundles.neoforgeModCompileOnly)
}

tasks {
    processResources {
        filesMatching("META-INF/neoforge.mods.toml") {
            expand(project.properties)
        }
    }

    shadowJar {
        exclude("fabric.mod.json")
        configurations = listOf(shadowBundle)
    }
}
