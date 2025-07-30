import java.util.Properties

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.serialization)
    application
}

group = "com.example.template"
version = "1.0.0"
application {
    mainClass.set("com.example.template.app.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(libs.server.exposed.datetime)
//    implementation(libs.jvm.kotlinx.datetime)
//    implementation(libs.core.kotlinx.datetime)
    implementation(libs.server.dotenv)
    implementation(libs.server.postgres)
    implementation(libs.server.slf4j.logger)
    implementation(libs.server.expose.core)
    implementation(libs.server.expose.jdbc)
    implementation(libs.server.expose.dao)
    implementation(libs.server.ktor.content.negotiation)
    implementation(libs.server.koin)
    implementation(libs.server.koin.logger)

    implementation(libs.core.ktor.client)
    implementation(libs.cio.ktor.client)
    implementation(libs.core.ktor.client.content.negotiation)
    implementation(libs.core.ktor.serialization.kotlinx.json)

    implementation(libs.java.langchain4j)
    implementation(libs.kotlin.koog)
    implementation(libs.java.langchain4j.gemini)

    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.cors)
    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.serverNetty)
    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlin.testJunit)
}


/*
 * Carrega as propriedades do local.properties
 * e as define como propriedades de sistema para a tarefa 'run'.
 */
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local-service.properties") // Ajuste o caminho se necessário

if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { fis ->
        localProperties.load(fis)
    }
}


/*
 * Para cada propriedade carregada do local.properties,
 * define uma propriedade de sistema JVM.
 * Por exemplo, se local.properties tem MY_API_KEY=secret,
 * isso adicionará -DMY_API_KEY=secret aos argumentos da JVM.
 */
tasks.withType<JavaExec> {

    localProperties.forEach { key, value ->
        systemProperty(key.toString(), value.toString())
    }
}