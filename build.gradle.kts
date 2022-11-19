import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    kotlin("jvm") version "1.7.10" apply false
}

allprojects {
    group = "com.shinonometn"
    version = "1.1-SNAPSHOT"

    repositories {
        mavenCentral()
        maven {
            url = uri("https://nexus.shinonometn.com/repository/shinonometn-repo/")
        }
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")

    if(!project.name.endsWith("-demo")) {
        apply(plugin = "maven-publish")
    }

    dependencies {
        implementation("com.fasterxml.jackson.core:jackson-databind:2.10.1")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
        testImplementation(kotlin("test"))
    }

    tasks.test {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}