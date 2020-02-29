plugins {
    java
    maven
    kotlin("jvm") version "1.3.61"
}

allprojects {
    group = "one.xjcyan1de"
    version = "1.0-SNAPSHOT"

    apply(plugin = "java")
    apply(plugin = "maven")
    apply(plugin = "kotlin")

    repositories {
        mavenCentral()
        jcenter()
        maven { setUrl("https://jitpack.io") }
        maven { setUrl("https://papermc.io/repo/repository/maven-public/") }
    }

    dependencies {
        compileOnly(kotlin("stdlib-jdk8"))
        compileOnly("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.3.3")
        compileOnly("org.jetbrains.kotlinx", "kotlinx-io-jvm", "0.1.16")
        compileOnly("com.github.xjcyan1de", "CyanLibZ", "1.4.12")
        compileOnly("org.tukaani", "xz", "1.8")
        compileOnly("com.destroystokyo.paper", "paper-api", "1.15.2-R0.1-SNAPSHOT")
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    tasks {
        compileKotlin { kotlinOptions.jvmTarget = "1.8" }
        compileTestKotlin { kotlinOptions.jvmTarget = "1.8" }
        compileJava { options.encoding = "UTF-8" }
    }
}