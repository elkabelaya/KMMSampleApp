plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlin-parcelize")
    id("dev.icerock.mobile.multiplatform-resources")
}

kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            if (System.getenv("XCODE_VERSION_MAJOR") == "1500") {
                linkerOpts += "-ld64"
            }
            export("com.arkivanov.decompose:decompose:2.0.1")
            export("com.arkivanov.essenty:lifecycle:1.1.0")
            export("dev.icerock.moko:resources:0.23.0")
            export("dev.icerock.moko:graphics:0.9.0") // toUIColor here
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api("com.arkivanov.decompose:decompose:2.0.1")
                api("com.arkivanov.essenty:lifecycle:1.1.0")
                //implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = "com.elkabelaya.kmmsampleapp"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
}

dependencies {
    commonMainApi("dev.icerock.moko:resources:0.23.0")
    commonTestImplementation("dev.icerock.moko:resources-test:0.23.0")
}

multiplatformResources {
    multiplatformResourcesPackage = "com.elkabelaya.kmmsampleapp.MR" // required
}
