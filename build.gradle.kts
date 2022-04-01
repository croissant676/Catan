plugins {
    java
    application
    id("org.openjfx.javafxplugin") version "0.0.12"
}

group = "dev.kason"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

javafx {
    version = "17.0.1"
    modules = listOf("controls", "fxml").map { "javafx.$it" }
}

dependencies {
    implementation("com.google.guava:guava:28.2-jre")
    annotationProcessor("org.openjfx:javafx-annotations:17.0.0")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.6")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}