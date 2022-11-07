package com.shinonometn.toybrick.build

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication

fun Project.publishToShinonomeTN(publishing: PublishingExtension) = with(publishing) {
    repositories { repos ->
        repos.maven { maven ->
            maven.name = "shinonometn"
            val releasesUrl = uri("https://nexus.shinonometn.com/repository/maven-releases/")
            val snapshotsUrl = uri("https://nexus.shinonometn.com/repository/maven-snapshots/")
            maven.url = if (project.version.toString().endsWith("SNAPSHOT")) snapshotsUrl else releasesUrl

            maven.credentials { credentials ->
                credentials.username = System.getenv("MAVEN_USERNAME")
                credentials.password = System.getenv("MAVEN_PASSWORD")
            }
        }
    }

    publications { container ->
        container.create("maven", MavenPublication::class.java) { mvnPub ->
            mvnPub.from(components.getByName("java"))
        }
    }
}