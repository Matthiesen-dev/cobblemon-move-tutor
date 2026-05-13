plugins {
    id("dev.architectury.loom")
    id("architectury-plugin")
}

architectury {
    common("neoforge", "fabric")
}

loom {
    enableTransitiveAccessWideners.set(true)
    silentMojangMappingsLicense()
}

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    mappings(loom.officialMojangMappings())
    compileOnly("org.spongepowered:mixin:0.8.5")
    modImplementation("dev.matthiesen:matthiesen-lib-common:${property("matthiesen_lib_version")}") { isTransitive = false }
    modImplementation("com.cobblemon:mod:${property("cobblemon_version")}") { isTransitive = false }
    modApi(files("${rootProject.rootDir}/jars/molang-${property("molang_version")}.jar"))
    modCompileOnly("maven.modrinth:cobbledollars:${property("cobbledollars_version")}-fabric,1.21.1")
    modCompileOnly("maven.modrinth:impactor:${property("impactor_version")}-Fabric-fabric,1.21.1")
    implementation("net.kyori:adventure-key:4.13.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:${property("junit_version")}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${property("junit_version")}")
}

tasks {
    test {
        useJUnitPlatform()
    }

    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        inputs.property("mod_name", project.property("mod_name").toString())
        filesMatching("pack.mcmeta") {
            expand(project.properties)
        }
    }

    remapSourcesJar {
        archiveBaseName.set("${rootProject.property("archives_base_name")}-${project.name}")
        archiveVersion.set("${project.version}")
        archiveClassifier.set("sources")
    }
}
