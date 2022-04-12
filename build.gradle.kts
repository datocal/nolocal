import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// I'm new to gradle and kotlin so this file will need a rework, for sure...

plugins {
    id("org.springframework.boot") version "2.6.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.unbroken-dome.test-sets") version "4.0.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    id("info.solidsoft.pitest") version "1.7.4"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    jacoco
}

group = "com.datocal"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2021.0.1"
extra["testcontainersVersion"] = "1.16.3"

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
    junit5PluginVersion.set("0.15")
    testSourceSets.set(setOf(sourceSets.test.get(), testSets["integrationTest"].sourceSet))
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:3.1.1")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    runtimeOnly("io.micrometer:micrometer-registry-new-relic")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter:1.16.3")
    testImplementation("io.rest-assured:spring-mock-mvc:5.0.1")
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
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
