import com.android.build.gradle.internal.crash.afterEvaluate

allprojects {


    repositories {
        google()
        gradlePluginPortal()
        mavenLocal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven {
            url = uri("https://maven.pkg.github.com/kashif-e/simple-paging")
            credentials {
                username = project.findProperty("gpr.user") as String?
                password = project.findProperty("gpr.key") as String?
            }
        }
    }
    subprojects {
        afterEvaluate {project->
            if (project.hasProperty("android")) {
                project.extensions.findByName("android")?.let { androidExtension ->
                    val android = androidExtension as com.android.build.gradle.BaseExtension
                    if (android.namespace == null) {
                        android.namespace = project.group.toString()
                    }
                }
            }
        }
    }



}


buildscript {
    repositories {
        google()
        gradlePluginPortal()
        mavenLocal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven {
            url = uri("https://maven.pkg.github.com/kashif-e/simple-paging")
            credentials {
                username = project.findProperty("gpr.user") as String?
                password = project.findProperty("gpr.key") as String?
            }
        }

    }
    dependencies {
        classpath(libs.androidGradle)
        classpath(libs.composeGradle)
        classpath(libs.kotlinGradle)
        classpath((kotlin("serialization", version = "1.8.20")))
    }
}


plugins {

    alias(libs.plugins.compose.compiler) apply false
}