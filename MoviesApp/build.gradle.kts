import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization")
    id("com.android.application")
    id("org.jetbrains.compose")
    id("com.google.osdetector") version "1.7.3"
    alias(libs.plugins.compose.compiler)
}

group = "com.kashif"

version = "1.0-SNAPSHOT"

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    jvm()
    applyDefaultHierarchyTemplate()
    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../ios/Podfile")
        framework {
            baseName = "MovieApp"
            isStatic = true
        }
        pod("youtube-ios-player-helper")
    }

    sourceSets {
        commonMain.dependencies {

            implementation(libs.kermit)
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material)
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(libs.koin.core)
            implementation(libs.ktor.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization)
            implementation(libs.ktor.contentnegotiation)
            implementation(libs.ktor.serialization.json)
            implementation(libs.kotlin.serialization)
            implementation(libs.material.icon.extended)
            implementation(libs.compose.util)
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.screenmodel)
            implementation(libs.voyager.transitions)
            implementation(libs.voyager.bottomSheetNavigator)
            implementation(libs.voyager.tabNavigator)
            implementation(libs.koin.compose.mp)
            implementation(libs.coil)
            implementation(libs.coil.network.ktor)
            implementation(libs.coil.compose.core)
            implementation(libs.coil.compose)
            implementation(libs.stately.common)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.material3)
            implementation(compose.runtime)

        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            api(libs.androidx.appcompat)
            api(libs.androidx.coreKtx)
            implementation(libs.ktor.android)
            implementation(libs.youtube.player.core)
            implementation(libs.system.ui.controller)
            implementation(libs.androidx.activity.compose)
            implementation(libs.system.ui.controller)

        }
        androidNativeTest.dependencies {
            implementation(libs.junit)
        }

        jvmMain.dependencies {
            api(compose.preview)
            implementation(libs.koin.core)
            implementation(libs.ktor.java)

            implementation(compose.desktop.currentOs)
            // https://stackoverflow.com/questions/73187027/use-javafx-in-kotlin-multiplatform
            // As JavaFX have platform-specific dependencies, we need to add them manually
            val fxSuffix = when (osdetector.classifier) {
                "linux-x86_64" -> "linux"
                "linux-aarch_64" -> "linux-aarch64"
                "windows-x86_64" -> "win"
                "osx-x86_64" -> "mac"
                "osx-aarch_64" -> "mac-aarch64"
                else -> throw IllegalStateException("Unknown OS: ${osdetector.classifier}")
            }
            implementation("org.openjfx:javafx-base:21.0.1:${fxSuffix}")
            implementation("org.openjfx:javafx-graphics:21.0.1:${fxSuffix}")
            implementation("org.openjfx:javafx-controls:21.0.1:${fxSuffix}")
            implementation("org.openjfx:javafx-swing:21.0.1:${fxSuffix}")
            implementation("org.openjfx:javafx-web:21.0.1:${fxSuffix}")
            implementation("org.openjfx:javafx-media:21.0.1:${fxSuffix}")
            implementation(libs.kotlinx.coroutines.swing)
        }
        jvmMain.get().dependsOn(commonMain.get())

        iosMain.dependencies {
            implementation(libs.ktor.ios)
        }
        iosMain.get().dependsOn(commonMain.get())
    }
}


android {
    namespace = "com.kashif.android"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        applicationId = "com.kashif.android"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0-SNAPSHOT"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }

    }

    dependencies {
        debugImplementation("androidx.compose.ui:ui-tooling:1.6.8")
        implementation("androidx.compose.ui:ui-tooling-preview:1.6.8")
    }
}

compose.desktop {
    application {
        mainClass = "com.kashif.common.presentation.MainKt"
        nativeDistributions {
            modules(
                "java.instrument",
                "java.net.http",
                "jdk.jfr",
                "jdk.jsobject",
                "jdk.unsupported",
                "jdk.unsupported.desktop",
                "jdk.xml.dom"
            )
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "KMPMovies"
            packageVersion = "1.0.0"
        }
    }
}
compose.resources {
    publicResClass = true
}