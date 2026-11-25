plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

val pkgId = (findProperty("appId") as String?) ?: "com.anyaman.app"
val pkgName = (findProperty("appName") as String?) ?: "Anyaman App"
val pkgVersionName = (findProperty("versionName") as String?) ?: "1.0.0"
val pkgVersionCode = ((findProperty("versionCode") as String?) ?: "1").toInt()
val pkgTargetUrl = (findProperty("targetUrl") as String?) ?: "https://example.com"

android {
    namespace = "com.anyaman.wrapper"
    compileSdk = 34

    defaultConfig {
        applicationId = pkgId
        minSdk = 24
        targetSdk = 34
        versionCode = pkgVersionCode
        versionName = pkgVersionName
        buildConfigField("String", "TARGET_URL", "\"$pkgTargetUrl\"")
        resValue("string", "app_name", pkgName)
    }

    signingConfigs {
        create("release") {
            storeFile = file(System.getenv("KEYSTORE_PATH") ?: "release.jks")
            storePassword = System.getenv("KEYSTORE_PASSWORD") ?: ""
            keyAlias = System.getenv("KEY_ALIAS") ?: ""
            keyPassword = System.getenv("KEY_PASSWORD") ?: ""
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
        }
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.activity:activity:1.9.1")
}
