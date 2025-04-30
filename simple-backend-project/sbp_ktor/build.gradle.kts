plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
}

group = "com.yiyongc.sbp-ktor"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    // Ktor
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.config.yaml)

    // Logging
    implementation(libs.logback.classic)

    // Database
    implementation(libs.postgresql)
    implementation(libs.kotlin.exposed.core)
    implementation(libs.kotlin.exposed.dao)
    implementation(libs.kotlin.exposed.jdbc)
    implementation(libs.kotlin.exposed.datetime)
    implementation(libs.kotlinx.datetime)

    // Test
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}
