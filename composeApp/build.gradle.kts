import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.serialization)
}

kotlin {
    compilerOptions {
        if (System.getProperty("idea.active") == "true") {
            println("Enable coroutine debugging")
            freeCompilerArgs = listOf("-Xdebug")
        }
    }

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    jvm("desktop")


    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.android.koin)
            implementation(libs.androidx.activity.compose)
        }

        commonMain.dependencies {
            implementation("io.matthewnelson.kmp-file:file:0.1.1")

            implementation(libs.composeapp.compose.common.filekit)
            implementation(libs.composeapp.core.common.filekit)
            implementation("io.github.pdvrieze.xmlutil:core:0.91.1")
            implementation("io.github.pdvrieze.xmlutil:serialization:0.90.3")
            implementation(libs.core.kotlinx.datetime)
            implementation(libs.core.ktor.client.logging)
            implementation(libs.core.ktor.client.serialization)
            implementation(libs.core.ktor.client.content.negotiation)
            implementation(libs.core.ktor.serialization.kotlinx.json)
            implementation(libs.core.ktor.client)
            implementation(libs.core.kotlinx.coroutines)
            implementation(libs.core.multiplatform.settings)
            implementation(libs.core.multiplatform.settings.serialization)
            implementation(libs.core.kotlinx.datetime)
            implementation(libs.composeapp.common.icons.tlaster.eva)
//            implementation(libs.composeapp.common.icons.tlaster.eva)
            implementation(libs.composeapp.common.androidx.navigation)
            implementation(libs.composeapp.common.koin)
            implementation(libs.composeapp.common.koin.viewmodel)
            implementation(libs.composeapp.common.koin.viewmodel.navigation)
            implementation(libs.composeapp.common.window)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(projects.shared)


        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        desktopMain.dependencies {
            implementation(compose.components.resources)
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}

android {
    namespace = "com.example.template"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.example.template"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.example.template.MainKt"
        buildTypes.release.proguard {
            obfuscate.set(true)
            configurationFiles.from(project.file("compose-desktop.pro"))
        }
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.AppImage)
            packageName = "CheckAllPages"
            packageVersion = "1.0.0"

            linux {
                modules("jdk.security.auth")
                iconFile.set(project.file("src/commonMain/composeResources/drawable/logo_png.png"))
            }
            windows {
                // Configurações específicas para Windows
                shortcut = true // Cria atalho no Menu Iniciar
                iconFile.set(project.file("src/commonMain/composeResources/drawable/logo_ico.ico"))
            }
        }
    }
}
