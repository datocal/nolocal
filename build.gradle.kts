import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    id("org.springframework.boot") version "2.6.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    id("info.solidsoft.pitest") version "1.7.4"
    kotlin("jvm") version "1.6.20"
    kotlin("plugin.spring") version "1.6.20"
    jacoco
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
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("com.oracle.database.jdbc:ojdbc11:21.7.0.0")
    implementation("com.h2database:h2:2.1.214")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.4")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter:1.17.4")
    testImplementation("io.rest-assured:spring-mock-mvc:5.2.0")
    testImplementation("org.testcontainers:testcontainers:1.17.4")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.mockito:mockito-inline:4.8.0")
    testImplementation("org.testcontainers:mockserver:1.17.4")
    testImplementation("org.mock-server:mockserver-client-java:5.14.0")
    testImplementation("org.apache.commons:commons-text:1.10.0")
    testImplementation("org.testcontainers:mongodb:1.17.5")
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
        junit5PluginVersion.set("0.15")
    }
}
