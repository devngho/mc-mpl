import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    kotlin("jvm") version "1.6.10"
    id("xyz.jpenilla.run-paper") version "1.0.6"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
}

group = "com.github.devngho"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }

}

dependencies {
    library(kotlin("stdlib"))
    library("com.google.code.gson:gson:2.9.0")
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
    runServer {
        minecraftVersion("1.18.1")
    }
}

bukkit {
    main = "com.github.devngho.mcmpl.Plugin"
    apiVersion = "1.18"
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    authors = listOf("ngho")
}