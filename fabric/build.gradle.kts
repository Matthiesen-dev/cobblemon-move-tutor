plugins {
    id("com.gradleup.shadow")
    id("dev.architectury.loom")
    id("architectury-plugin")
    id("move-tutor.shadow-platform-conventions")
}

architectury {
    platformSetupLoomIde()
    fabric()
}

val shadowCommon: Configuration by configurations.creating

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())
    implementation(project(":common", configuration = "namedElements"))
    "developmentFabric"(project(":common", configuration = "namedElements"))
    shadowCommon(project(":common", configuration = "transformProductionFabric"))
    add("modImplementation", libs.bundles.fabricModImplementation)
    libs.bundles.fabricModImplementationNoTransitive.get().forEach { dependency ->
        modImplementation(dependency.copy()) { isTransitive = false }
    }

    // Fix for Cobblemon dev on Fabric
    modRuntimeOnly("org.graalvm.js:js:22.3.0")
    modRuntimeOnly("org.graalvm.sdk:graal-sdk:22.3.0")
    modRuntimeOnly("org.graalvm.regex:regex:22.3.0")
    modRuntimeOnly("org.graalvm.truffle:truffle-api:22.3.0")
    modRuntimeOnly("com.ibm.icu:icu4j:71.1")

    add("modCompileOnly", libs.bundles.commonModCompileOnly)
    add("modRuntimeOnly", libs.bundles.fabricModRuntimeOnly)
}

tasks {
    processResources {
        filesMatching("fabric.mod.json") {
            expand(project.properties)
        }
    }

    shadowJar {
        configurations = listOf(shadowCommon)
    }
}
