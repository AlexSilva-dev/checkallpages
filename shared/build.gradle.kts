import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.buildKonfig)
    alias(libs.plugins.serialization)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

//    iosX64()
//    iosArm64()
//    iosSimulatorArm64()

    jvm()


    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation("io.github.pdvrieze.xmlutil:core:0.91.1")
            implementation("io.github.pdvrieze.xmlutil:serialization:0.90.3")

            implementation(libs.core.multiplatform.settings)
            implementation(libs.core.multiplatform.settings.serialization)
            implementation(libs.core.kotlinx.datetime)
            implementation(libs.core.ktor.client.logging)
            implementation(libs.core.ktor.client.serialization)
            implementation(libs.core.ktor.client.content.negotiation)
            implementation(libs.core.ktor.serialization.kotlinx.json)
            implementation(libs.core.ktor.client)
            implementation(libs.core.kotlinx.coroutines)
            implementation(libs.common.koin)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            implementation(libs.android.koin)
            implementation(libs.android.ktor.client.okhttp)
            implementation(libs.core.kotlinx.coroutines.android)
        }

//        iosMain.dependencies {
//            implementation(libs.darwin.ktor.client)
//        }
//
        jvmMain.dependencies {
            implementation(libs.cio.ktor.client)
        }


        wasmJsMain.dependencies {
            implementation(libs.kotlinx.browser)
            implementation(libs.wasmjs.ktor.client)
        }


    }
}

android {
    namespace = "com.example.template.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
dependencies {
    implementation(libs.androidx.games.activity)
}

buildkonfig {
    packageName = "com.example.template.app"

    defaultConfigs {
        val envShared = gradleLocalProperties(
            projectRootDir = rootDir,
            providers = providers
        )

        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "SERVER_PORT",
            value = envShared.getProperty("server.port")
        )

        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "SERVER_HOST",
            value = envShared.getProperty("server.host")
        )
    }

    targetConfigs {

    }
}