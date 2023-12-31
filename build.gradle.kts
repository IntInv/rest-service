val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
	kotlin("jvm") version "1.9.21"
	id("io.ktor.plugin") version "2.3.7"
}

group = "com.intinv"
version = "0.0.1"

application {
	mainClass.set("com.intinv.ApplicationKt")

	val isDevelopment: Boolean = project.ext.has("development")
	applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("io.ktor:ktor-server-core-jvm")
	implementation("io.ktor:ktor-server-content-negotiation-jvm")
	implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
	implementation("io.ktor:ktor-server-netty-jvm")
	implementation("io.ktor:ktor-serialization-gson:$ktor_version")

	implementation("ch.qos.logback:logback-classic:$logback_version")
	implementation ("com.fasterxml.jackson.core:jackson-databind:2.13.1")
	implementation ("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")

	implementation("redis.clients:jedis:5.0.0")

	testImplementation("io.ktor:ktor-server-tests-jvm")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

ktor {
	docker {
		// TODO - docker config
	}
}
