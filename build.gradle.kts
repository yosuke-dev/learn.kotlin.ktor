import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version: String by project
val koin_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.4.21"
    id("org.flywaydb.flyway") version "7.12.0"
}

buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("gradle.plugin.org.flywaydb:gradle-plugin-publishing:7.12.0")
    }
}

group = "learn.kotlin.ktor"
version = "0.0.1"

apply(plugin = "org.flywaydb.flyway")

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-locations:$ktor_version")
    implementation("io.ktor:ktor-jackson:$ktor_version")
    implementation("org.koin:koin-ktor:$koin_version")
    implementation("mysql:mysql-connector-java:8.0.26")
    implementation("org.jetbrains.exposed:exposed:0.17.7")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("javax.validation:validation-api:2.0.1.Final")
    runtimeOnly("org.hibernate.validator:hibernate-validator:6.0.17.Final")
    runtimeOnly("org.glassfish:javax.el:3.0.1-b11")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
}

flyway {
    url = "jdbc:mysql://localhost:3308/example"
    user = "kotlin"
    password = "kotlinpw"
    locations = arrayOf("filesystem:resources/db/migration")
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")
