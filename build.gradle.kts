import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.21"
    id("com.jfrog.bintray") version "1.8.4"
    `maven-publish`
    signing
}

group = "com.github.mdashl"
version = "3.1.1"

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.1")
    implementation("net.dv8tion:JDA:4.ALPHA.0_33") {
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
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])
        }
    }
}

signing {
    sign(publishing.publications["BintrayRelease"])
}

bintray {
    user = System.getenv("BINTRAY_USER")
    key = System.getenv("BINTRAY_API_KEY")
    setPublications("BintrayRelease")

    with(pkg) {
        repo = "maven"
        name = "KDA"
        setLicenses("MIT")
        vcsUrl = "https://gitlab.com/mdashlw/kda.git"
        publish = true
    }
}
