plugins {
    java
    `java-library`
    jacoco

    idea
    eclipse
}

repositories {
    mavenCentral()
    maven {
        name = "PaperMC"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "Magic"
        url = uri("https://maven.elmakers.com/repository/")
    }
    maven {
        name = "CodeMC"
        url = uri("https://repo.codemc.org/repository/maven-public/")
    }
    maven {
        name = "JitPack"
        url = uri("https://jitpack.io")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.2.build.129-stable")
    compileOnly("net.dmulloy2:ProtocolLib:5.4.0") { isTransitive = false }
    compileOnly("com.elmakers.mine.bukkit:MagicAPI:10.2") { isTransitive = false }
    compileOnly("de.tr7zw:item-nbt-api-plugin:2.8.0") { isTransitive = false }
    compileOnly("com.github.TheBusyBiscuit:Slimefun4:RC-30") { isTransitive = false }
    compileOnly("io.netty:netty-all:4.1.110.Final") {
        because("The version aligns with the version used by Minecraft itself." +
                "The minecraft server ships netty as well, so we don't need to include it in the jar.")
    }
    compileOnly("com.gmail.nossr50.mcMMO:mcMMO:2.1.217") { isTransitive = false }
    compileOnly("fr.minuskube.inv:smart-invs:1.2.7") { isTransitive = false }
    //compileOnly("com.github.CraftingStore.MinecraftPlugin:core:master-e366d322f8-1")
    compileOnly("com.github.brcdev-minecraft:shopgui-api:3.0.0") { isTransitive = false }

    testImplementation("org.junit.jupiter:junit-jupiter:5.13.4")
    testImplementation("org.assertj:assertj-core:3.27.6")
    testImplementation("net.kyori:adventure-api:5.2.0")
    testImplementation("net.kyori:adventure-text-serializer-legacy:5.2.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("io.papermc.paper:paper-api:26.2.build.129-stable")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(25)
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

configure<JacocoPluginExtension> {
    toolVersion = "0.8.14"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.test)
    violationRules {
        rule {
            element = "CLASS"
            includes = listOf("main.java.me.dniym.enums.ServerVersion*")
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.80".toBigDecimal()
            }
        }
    }
}

tasks.check {
    dependsOn(tasks.jacocoTestCoverageVerification)
}

version = "2.9.12a"
val pluginVersion = version.toString()

tasks.jar {
    archiveFileName.set("IllegalStack.jar")
}

tasks.named<Copy>("processResources") {
    filesMatching("plugin.yml") {
        expand("version" to pluginVersion)
    }
}
