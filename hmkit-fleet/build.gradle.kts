buildscript {
  extra.apply {
    set("kotlin_version", "1.9.10")
    set("coroutinesVersion", "1.7.3")
    set("koinVersion", "3.4.3")
    set("ktor_version", "2.3.3")
  }
}

plugins {
  id("org.jetbrains.kotlin.multiplatform") version "${extra["kotlin_version"]}"
  id("org.jetbrains.kotlin.plugin.serialization") version "${extra["kotlin_version"]}"
}

apply(from = rootProject.file("./hmkit-fleet/publish.gradle.kts"))

group = "com.highmobility"
version = "1.0-SNAPSHOT"

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
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${extra["coroutinesVersion"]}")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
        implementation("io.insert-koin:koin-core:${extra["koinVersion"]}")
        implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

        implementation("io.ktor:ktor-client-core:${extra["ktor_version"]}")
        implementation("io.ktor:ktor-client-logging:${extra["ktor_version"]}")

        implementation("io.matthewnelson.encoding:base64:2.0.0")
      }
    }
    val commonTest by getting {
    }
    val jvmMain by getting {
      dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${extra["kotlin_version"]}")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${extra["coroutinesVersion"]}")
        api("io.ktor:ktor-client-okhttp:${extra["ktor_version"]}")
        implementation("com.high-mobility:hmkit-crypto-telematics:0.1")
        implementation("ch.qos.logback:logback-classic:1.2.3")
      }
    }
    val jvmTest by getting {

    }
    val jsMain by getting {
      dependencies {
        implementation("io.ktor:ktor-client-js:${extra["ktor_version"]}")
        implementation(npm("jsrsasign", "10.8.6"))
        implementation(npm("uuid", "9.0.1"))
      }
    }
    val jsTest by getting {

    }
  }
}
