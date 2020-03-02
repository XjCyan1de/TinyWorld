plugins {
    kotlin("jvm") version "1.3.61"
    maven
    `maven-publish`
}

group = "com.github.xjcyan1de"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    maven { setUrl("https://jitpack.io") }
    maven { setUrl("https://papermc.io/repo/repository/maven-public/") }
}

dependencies {
    compileOnly("org.tukaani", "xz", "1.8")
    compileOnly(platform("org.jetbrains.kotlin:kotlin-bom"))
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compileOnly("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.3.3")
    compileOnly("org.jetbrains.kotlinx", "kotlinx-io-jvm", "0.1.16")
    compileOnly("com.github.xjcyan1de", "CyanLibZ", "1.5.2")
    compileOnly("it.unimi.dsi", "fastutil", "8.3.1")
    compileOnly("com.destroystokyo.paper", "paper-api", "1.15.2-R0.1-SNAPSHOT")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    compileKotlin { kotlinOptions.jvmTarget = "1.8" }
    compileJava { options.encoding = "UTF-8" }
}