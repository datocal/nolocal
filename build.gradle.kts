import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar


plugins {
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.jlleitschuh.gradle.ktlint") version "12.0.2"
    id("info.solidsoft.pitest") version "1.15.0"
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.spring") version "1.9.21"
    jacoco
    id("org.jetbrains.kotlinx.kover") version "0.7.5"
}

group = "com.datocal"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.0")
    implementation("redis.clients:jedis:5.1.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter:1.19.3")
    testImplementation("io.rest-assured:spring-mock-mvc:5.3.2")
    testImplementation("org.testcontainers:testcontainers:1.19.3")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")
    testImplementation("org.mockito:mockito-inline:5.2.0")
    testImplementation("org.testcontainers:mockserver:1.19.3")
    testImplementation("org.mock-server:mockserver-client-java:5.15.0")
    testImplementation("org.apache.commons:commons-text:1.11.0")
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

generateJacocoXmlReportsOnCheck()

configureMutationTests()

fun generateJacocoXmlReportsOnCheck() {
    tasks.check {
        dependsOn("jacocoTestReport")
    }

    tasks.jacocoTestReport {
        reports.xml.required.set(true)
    }
}

fun configureMutationTests() {
    pitest {
        junit5PluginVersion.set("1.0.0")
    }
}

tasks.withType<BootJar> {
    archiveVersion.set("")
}
