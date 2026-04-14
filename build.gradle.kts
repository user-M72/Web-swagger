import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    java
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.github.spotbugs") version "6.0.8"
    id("com.diffplug.spotless") version "6.25.0"
}

group = "uz.company"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.security:spring-security-oauth2-jose:6.4.4")
    implementation("com.nimbusds:nimbus-jose-jwt:9.40")

//    implementation('org.springframework.boot:spring-boot-starter-data-jpa')

    // https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${rootProject.extra.get("openApiVersion")}")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("org.mapstruct:mapstruct:${rootProject.extra.get("mapstructVersion")}")
    annotationProcessor("org.mapstruct:mapstruct-processor:${rootProject.extra.get("mapstructVersion")}")
    annotationProcessor("org.immutables:value")
    compileOnly("org.immutables:builder")
    compileOnly("org.immutables:value-annotations")

    // https://mvnrepository.com/artifact/org.telegram/telegrambots-spring-boot-starter
    implementation("org.telegram:telegrambots-spring-boot-starter:6.9.7.1")

    implementation ("io.github.cdimascio:java-dotenv:5.2.2")

    // https://mvnrepository.com/artifact/org.springframework.amqp/spring-rabbit
    implementation("org.springframework.amqp:spring-rabbit:3.2.5")

    implementation ("org.springframework.boot:spring-boot-starter-amqp")
    implementation ("org.springframework.boot:spring-boot-starter-webflux")

    //  PDF
    implementation ("com.itextpdf:itext7-core:7.2.5")

//  Excel
    implementation ("org.apache.poi:poi:5.2.3")
    implementation ("org.apache.poi:poi-ooxml:5.2.3")
    implementation ("org.springframework.boot:spring-boot-starter-actuator")

    runtimeOnly("io.micrometer:micrometer-registry-prometheus")

}

dependencyManagement{
    imports {
        mavenBom("org.immutables:bom:2.10.1")
    }
}


spotbugs {
    toolVersion.set("${rootProject.extra.get("spotBugsVersion")}")
    excludeFilter.set(file("${project.rootDir}/findbugs-exclude.xml"))
}

spotless {
    java {
        // https://github.com/google/google-java-format/releases/latest
        googleJavaFormat("1.21.0")
    }
}

tasks {
    spotbugsMain {
        effort.set(com.github.spotbugs.snom.Effort.MAX)
        reports.create("html") {
            enabled = true
        }
    }

    spotbugsTest {
        ignoreFailures = true
        reportLevel.set(com.github.spotbugs.snom.Confidence.HIGH)
        effort.set(com.github.spotbugs.snom.Effort.MIN)
        reports.create("html") {
            enabled = true
        }
    }
}

tasks.compileJava {
    dependsOn("processResources")
    options.release.set(21)
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf("-Xlint:deprecation"))
}

tasks.processResources {
    val tokens = mapOf(
        "application.version" to project.version,
        "application.description" to project.description
    )
    filesMatching("**/*.yml") {
        filter<ReplaceTokens>("tokens" to tokens)
    }
}

tasks.test {
    failFast = false
    enableAssertions = true

    // Enable JUnit 5 (Gradle 4.6+).
    useJUnitPlatform()

    testLogging {
        events("PASSED", "STARTED", "FAILED", "SKIPPED")
        // Set to true if you want to see output from tests
        showStandardStreams = false
        setExceptionFormat("FULL")
    }

    systemProperty("io.netty.leakDetectionLevel", "paranoid")
}


tasks.withType<Test> {
    useJUnitPlatform()
}
