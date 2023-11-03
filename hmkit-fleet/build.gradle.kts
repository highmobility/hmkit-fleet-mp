import org.jetbrains.dokka.gradle.DokkaTask

plugins {
  id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
  id("org.jetbrains.kotlin.multiplatform")
  id("org.jetbrains.dokka")
}


apply(from = rootProject.file("./hmkit-fleet/publish.gradle.kts"))

group = "com.highmobility"
version = "1.0-SNAPSHOT"

val kotlinVersion = "1.9.10"
val coroutinesVersion = "1.7.3"
val koinVersion = "3.4.3"
val ktorVersion = "2.3.3"

repositories {
  mavenCentral()
}

kotlin {
  jvm {
    withJava()
    testRuns["test"].executionTask.configure {
      useJUnitPlatform()
    }

    kotlin {
      jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
      }
    }
  }
  js {
    useEsModules()
    nodejs {

    }
    binaries.library()
    generateTypeScriptDefinitions()
  }
  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
        implementation("io.insert-koin:koin-core:$koinVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

        implementation("io.ktor:ktor-client-core:$ktorVersion")
        implementation("io.ktor:ktor-client-logging:$ktorVersion")

        implementation("io.matthewnelson.encoding:base64:2.0.0")
      }
    }
    val commonTest by getting {
      dependencies {

      }
    }
    val jvmMain by getting {
      dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$coroutinesVersion")
        api("io.ktor:ktor-client-okhttp:$ktorVersion")
        implementation("com.high-mobility:hmkit-crypto-telematics:0.1")

      }
    }
    val jvmTest by getting {
      dependencies {
        //        testImplementation deps.autoApi
        implementation("io.insert-koin:koin-test:$koinVersion")

        implementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
        runtimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")

        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")

        implementation("org.slf4j:slf4j-simple:1.7.36")
        implementation("io.mockk:mockk:1.12.5")
      }
    }
    val jsMain by getting {
      dependencies {
        implementation("io.ktor:ktor-client-js:$ktorVersion")
        implementation(npm("jsrsasign", "10.8.6"))
        implementation(npm("uuid", "9.0.1"))
      }
    }
    val jsTest by getting {

    }
  }
}

tasks.withType<DokkaTask>().configureEach {
  dokkaSourceSets {
    configureEach {
      perPackageOption {
        matchingRegex.set("platform")
        suppress.set(true)
      }
    }
  }
}