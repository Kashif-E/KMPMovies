import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.kashif"
version = "1.0-SNAPSHOT"


kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":common"))
                implementation(compose.desktop.currentOs)
                implementation(libs.koin.compose)

            }
            configurations.all {
                // some dependencies contains it, this causes an exception to initialize the Main dispatcher in desktop for image loader
                exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-android")
            }
        }
        val jvmTest by getting

    }
}

compose.desktop {
    application {
        mainClass = "com.kashif.MainKt"
        nativeDistributions {
            modules("java.instrument", "java.net.http", "jdk.jfr", "jdk.jsobject", "jdk.unsupported", "jdk.unsupported.desktop", "jdk.xml.dom")
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "KMPMovies"
            packageVersion = "1.0.0"
        }
    }
}
