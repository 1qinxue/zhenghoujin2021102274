import org.apache.tools.ant.util.JavaEnvUtils.VERSION_1_8

plugins {
    id("com.android.application")
}

android {
    namespace = "com.jnu.Test"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jnu.Test"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation ("com.tencent.map:tencent-map-vector-sdk:4.3.4")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.mediarouter:mediarouter:1.6.0")

    testImplementation("junit:junit:4.13.2")
    implementation ("androidx.tracing:tracing:1.2.0")

    testImplementation("org.json:json:20231013")
    androidTestImplementation("androidx.test.ext:junit:1.2.0-alpha02")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.0")

}