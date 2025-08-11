plugins {
    kotlin("jvm") version "2.2.0"
    application
}

application{
    mainClass.set("com.gokul.ApplicationKt")
}
group = "com.gokul"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.dropwizard:dropwizard-core:1.3.29")
    implementation(kotlin("stdlib"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}