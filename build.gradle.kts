plugins {
  id("org.jetbrains.kotlin.multiplatform") version "1.9.10" apply false
  id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
  id("org.jetbrains.dokka") version "1.9.10" apply false
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
