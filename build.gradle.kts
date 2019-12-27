import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.3.BUILD-SNAPSHOT"
	id("io.spring.dependency-management") version "1.0.8.RELEASE"
	kotlin("jvm") version "1.3.61"
	kotlin("plugin.spring") version "1.3.61"
}

group = "com.origin"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

val swaggerVersion = "2.9.2"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("io.arrow-kt:arrow-core:0.8.2")
	implementation("io.springfox:springfox-swagger-ui:$swaggerVersion")
	implementation("io.springfox:springfox-swagger2:$swaggerVersion")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
	reports {
		junitXml.isEnabled = false
		html.isEnabled = true
		html.destination = file("build/test-results/html")
	}

	testLogging {
		events.add(org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED)
		events.add(org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED)
		events.add(org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED)
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
