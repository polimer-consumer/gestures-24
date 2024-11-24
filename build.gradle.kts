plugins {
    id("java")
    id("application")
    id("org.jetbrains.kotlin.jvm") version "2.0.0"
    id("org.javamodularity.moduleplugin") version "1.8.12"
    id("org.openjfx.javafxplugin") version "0.0.13"
}

group = "com.polimerconsumer"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val junitVersion: String by extra("5.10.3")

application {
    mainModule.set("com.polimerconsumer.gestures24")
    mainClass.set("com.polimerconsumer.gestures24.HelloApplication")
}

javafx {
    version = "17"
    modules("javafx.controls", "javafx.fxml")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    implementation("org.openjfx:javafx-controls:17")
    implementation("org.openjfx:javafx-fxml:17")
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
        options.encoding = "UTF-8"
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    test {
        useJUnitPlatform()
    }
}
