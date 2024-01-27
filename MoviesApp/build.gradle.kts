import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization")
    id("com.android.application")
    id("com.google.osdetector") version "1.7.3"
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
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            api(libs.androidx.appcompat)
            api(libs.androidx.coreKtx)
            implementation(libs.ktor.android)
            implementation(libs.koin.compose)
            implementation(libs.youtube.player.core)
            implementation(libs.system.ui.controller)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.compose)
            implementation(libs.system.ui.controller)
        }
        androidMain.get().dependsOn(commonMain.get())

        androidNativeTest.dependencies {
            implementation(libs.junit)
        }

        jvmMain.dependencies {
            api(compose.preview)
            implementation(libs.koin.core)
            implementation(libs.ktor.java)
            implementation(libs.koin.compose)
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
            implementation("org.openjfx:javafx-base:19:${fxSuffix}")
            implementation("org.openjfx:javafx-graphics:19:${fxSuffix}")
            implementation("org.openjfx:javafx-controls:19:${fxSuffix}")
            implementation("org.openjfx:javafx-swing:19:${fxSuffix}")
            implementation("org.openjfx:javafx-web:19:${fxSuffix}")
            implementation("org.openjfx:javafx-media:19:${fxSuffix}")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    composeOptions { kotlinCompilerExtensionVersion = "1.5.7" }

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