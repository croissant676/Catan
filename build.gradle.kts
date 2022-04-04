plugins {
    java
    application
    id("org.openjfx.javafxplugin") version "0.0.12"
    id("io.freefair.lombok") version "6.4.1"
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
    implementation("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.2.2")
    implementation("org.slf4j:jul-to-slf4j:1.7.36")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}