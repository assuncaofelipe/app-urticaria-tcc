import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
}

android {
    namespace = "com.assuncao.ufsc.urticaria.data"
    compileSdk = 35
    defaultConfig { minSdk = 26 }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

kotlin {
    jvmToolchain(21)
    androidTarget()

    val xcf = XCFramework("UrticariaShared")
    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach { target ->
        target.binaries.framework {
            baseName = "UrticariaShared"
            export(project(":domain"))
            xcf.add(this)
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":domain"))
            implementation(libs.coroutines.core)
        }
    }
}
