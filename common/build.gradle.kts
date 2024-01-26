plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization")
    id("com.google.osdetector") version "1.7.3"
}

group = "com.kashif"

version = "1.0-SNAPSHOT"

kotlin {
    androidTarget()
    iosArm64()
    iosSimulatorArm64()
    jvm("desktop") { compilations.all { kotlinOptions.jvmTarget = "11" } }

    applyDefaultHierarchyTemplate()
    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../ios/Podfile")
        framework {
            baseName = "common"
            isStatic = true
        }
        pod("youtube-ios-player-helper")
    }

    sourceSets {

        commonMain.dependencies {
            implementation(kotlin("stdlib-common"))
            implementation("co.touchlab:kermit:2.0.0-RC4")
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material)
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
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
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
        }

        androidNativeTest.dependencies {
            implementation(libs.junit)
        }


        val desktopMain by getting {
            dependencies {
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


            }
        }
        val desktopTest by getting

        iosMain.dependencies {
            implementation(libs.ktor.ios)
        }
        iosMain.get().dependsOn(commonMain.get())


    }
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res", "src/commonMain/resources")
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

