plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.smartheusys.eurochem"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.18")

    implementation("com.google.inject:guice:7.0.0")
    implementation("org.apache.poi:poi:5.3.0")
    implementation("org.apache.poi:poi-ooxml:5.3.0")

    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation("org.apache.logging.log4j:log4j-api:2.20.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks {
    withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        archiveClassifier.set("") // Removes the "-all" suffix
        manifest {
            attributes(
                "Main-Class" to "com.smartheusys.eurochem.App" // Replace with your main class
            )
        }
    }
}

// Ensure the fat JAR is created on build
tasks {
    "build" {
        dependsOn("shadowJar")
    }
}