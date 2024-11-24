plugins {
    id("java")
    id("application")
    id("org.jetbrains.kotlin.jvm") version "1.8.22"
    id("org.javamodularity.moduleplugin") version "1.8.12"
    id("org.openjfx.javafxplugin") version "0.0.13"
}

group = "com.polimerconsumer"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val junitVersion: String by extra("5.10.0")

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainModule.set("com.polimerconsumer.gestures24")
    mainClass.set("com.polimerconsumer.gestures24.HelloApplication")
}

kotlin {
    jvmToolchain(21)
}

javafx {
    version = "21"
    modules("javafx.controls", "javafx.fxml")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

tasks.test {
    useJUnitPlatform()
}
