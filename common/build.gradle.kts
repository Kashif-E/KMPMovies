plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization")
}

group = "com.kashif"
version = "1.0-SNAPSHOT"

fun composeDependency(groupWithArtifact: String) = "$groupWithArtifact:${libs.versions.compose}"

kotlin {
    android()
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }

    ios()
    iosSimulatorArm64()

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
    }



    sourceSets {
        val commonMain by getting {
            dependencies {
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
                api(libs.image.loader)
                implementation(libs.accompanist.pager)
                implementation(libs.compose.util)
                implementation(libs.voyager.navigator)
                implementation(libs.voyager.transitions)
                implementation(libs.voyager.bottomSheetNavigator)
                implementation(libs.voyager.tabNavigator)



            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))


            }
        }
        val androidMain by getting {
            dependencies {
                api(libs.androidx.appcompat)
                api(libs.androidx.coreKtx)
                implementation(libs.ktor.android)
                implementation(libs.koin.compose)
                implementation ("androidx.media3:media3-exoplayer:1.0.0")
                implementation ("androidx.media3:media3-exoplayer-dash:1.0.0")
                implementation ("androidx.media3:media3-ui:1.0.0")
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(libs.junit)
            }
        }
        val desktopMain by getting {
            dependencies {
                api(compose.preview)
                implementation(libs.koin.core)
                implementation(libs.ktor.java)
                implementation(libs.koin.compose)

            }
        }
        val desktopTest by getting

        val iosMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(libs.ktor.ios)
            }
        }
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }

    }
}


android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
dependencies {
    implementation("androidx.compose.ui:ui-text-google-fonts:1.3.3")
}
