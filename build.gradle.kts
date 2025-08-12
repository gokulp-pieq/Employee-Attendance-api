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

tasks.named<JavaExec>("run") {
    args = listOf("server", "src/main/resources/config.yml")
}
dependencies {
    testImplementation(kotlin("test"))
    implementation("io.dropwizard:dropwizard-core:1.3.29")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")
    implementation("jakarta.ws.rs:jakarta.ws.rs-api:3.1.0")
    implementation(kotlin("stdlib"))
}



tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}