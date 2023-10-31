apply(plugin = "maven-publish")
apply(plugin = "signing")

fun Project.signing(configure: SigningExtension.() -> Unit): Unit = configure(configure)
fun Project.publishing(action: PublishingExtension.() -> Unit) = configure(action)

val deploySiteUrl = "https://github.com/highmobility/hmkit-fleet"
val deployGitUrl = "https://github.com/highmobility/hmkit-fleet"
val deployGroupId = "com.high-mobility"
val deployLicenseName = "MIT"
val deployLicenseUrl = "https://opensource.org/licenses/MIT"
val deployId = "hmkit-fleet-mp"

val publications: PublicationContainer =
    (extensions.getByName("publishing") as PublishingExtension).publications

val javaDocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

publishing {
    publications.all {
        group = "com.high-mobility"
        version = "0.0.0"
    }

    publications.withType<MavenPublication>().all {
        artifact(javaDocJar.get())

        pom {
            name.set(deployId)
            url.set(deploySiteUrl)
            inceptionYear.set("2020")
            description.set(deployId)
            licenses {
                license {
                    name.set(deployLicenseName)
                    url.set(deployLicenseUrl)
                }
            }
            developers {
                developer {
                    id.set("tonis")
                    name.set("Tonis Tiganik")
                    email.set("ttiganik@high-mobility.com")
                }
                developer {
                    id.set("kevin")
                    name.set("Kevin Valdek")
                    email.set("kevin@high-mobility.com")
                }
            }
            scm {
                connection.set(deployGitUrl)
                developerConnection.set(deployGitUrl)
                url.set(deploySiteUrl)
            }
        }
    }
}

signing {
    if (project.hasProperty("signingKey")) {
        val signingKey = findProperty("signingKey")
        val signingPassword = findProperty("signingPassword")
//        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(publications)
    } else {
        println("WARNING: No property \'signingKey\' found. Artifact signing will be skipped.")
    }
}