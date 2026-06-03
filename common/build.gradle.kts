plugins {
    id("dev.architectury.loom")
    id("architectury-plugin")
    id("move-tutor.minecraft-module-conventions")
//    id("move-tutor.publishing-conventions")
}

architectury {
    common("neoforge", "fabric")
}

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())
    compileOnly(libs.sponge.mixin)
    libs.bundles.commonModImplementationNoTransitive.get().forEach { dependency ->
        modImplementation(dependency.copy()) { isTransitive = false }
    }
    modApi(files("${rootProject.rootDir}/jars/molang-${libs.versions.molang.get()}.jar"))
    add("modCompileOnly", libs.bundles.commonModCompileOnly)
    implementation(libs.adventure.key)
}

tasks {
    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        inputs.property("mod_name", project.property("mod_name").toString())
        filesMatching("pack.mcmeta") {
            expand(project.properties)
        }
    }
}
