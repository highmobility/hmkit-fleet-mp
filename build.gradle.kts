plugins {
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

nexusPublishing {
    repositories {
        sonatype()
    }
}

group = "com.high-mobility"

subprojects {
    repositories {
        mavenCentral()
    }

    group = "com.high-mobility"
}