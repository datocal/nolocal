import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// I'm new to gradle and kotlin so this file will need a rework, for sure...

plugins {
    id("org.springframework.boot") version "2.5.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.unbroken-dome.test-sets") version "4.0.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
    id("info.solidsoft.pitest") version "1.7.0"
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.spring") version "1.5.31"
    jacoco
}

group = "com.datocal"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2020.0.4"
extra["testcontainersVersion"] = "1.16.0"

testSets {
    "integrationTest" {
        dirName = "integration-test"
        systemProperty("spring.profiles.active", "test")
    }
}
tasks.check {
    dependsOn("integrationTest", "jacocoTestReport", "jacocoIntegrationTestReport")
}

tasks.getByName("jacocoIntegrationTestReport") {
    this as JacocoReport
    reports.xml.required.set(true)
}
tasks.jacocoTestReport {
    reports.xml.required.set(true)
}

pitest {
    junit5PluginVersion.set("0.14")
    testSourceSets.set(setOf(sourceSets.test.get(), testSets["integrationTest"].sourceSet))
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.javacord:javacord:3.3.2")
    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("io.rest-assured:spring-mock-mvc:4.4.0")
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
