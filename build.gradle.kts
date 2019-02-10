import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.21"
    id("com.jfrog.bintray") version "1.8.4"
    `maven-publish`
}

group = "com.github.mdashl"
version = "3.2.1"

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.1")
    implementation("net.dv8tion:JDA:4.ALPHA.0_34") {
        exclude(module = "opus-java")
    }
    implementation("ch.qos.logback:logback-classic:1.2.3")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.register<Jar>("sourcesJar") {
    from(sourceSets.main.get().allSource)
    archiveClassifier.set("sources")
}

tasks.register<Jar>("javadocJar") {
    from(tasks.javadoc)
    archiveClassifier.set("javadoc")
}

publishing {
    publications {
        create<MavenPublication>("BintrayRelease") {
        	artifactId = "KDA"

            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])

            pom {
                name.set("KDA")
                description.set("Kotlin-specific features for JDA (Java Discord API)")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://choosealicense.com/licenses/mit/")
                    }
                }
                developers {
                    developer {
                        id.set("mdashlw")
                        name.set("Mdashlw")
                        email.set("mdashlw@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:https://gitlab.com/mdashlw/kda.git")
                }
            }
        }
    }
}

bintray {
    user = System.getenv("BINTRAY_USER")
    key = System.getenv("BINTRAY_API_KEY")
    setPublications("BintrayRelease")

    publish = true
    override = true

    with(pkg) {
        repo = "maven"
        name = "KDA"
        desc = "Kotlin-specific features for JDA (Java Discord API)"
        setLicenses("MIT")
        websiteUrl = "https://gitlab.com/mdashlw/kda"
        issueTrackerUrl = "https://gitlab.com/mdashlw/kda/issues"
        vcsUrl = "https://gitlab.com/mdashlw/kda.git"
    }
}
