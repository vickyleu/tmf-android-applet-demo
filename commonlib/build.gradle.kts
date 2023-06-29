@file:Suppress("UnstableApiUsage")
plugins {
    id("com.android.library")
    kotlin("android")
//    id("kotlin-android")
//    kotlin("kapt")
}

android {
    namespace = "com.tencent.tmf.common"
    compileSdk = 34
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    lint {
        abortOnError=false
        checkReleaseBuilds=false
        checkGeneratedSources=false
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
//    implementation("androidx.appcompat:appcompat:1.6.1")
    /////////////////////////////////////////////demo工程所需依赖
    api("com.google.android.material:material:1.9.0")
    //组件化库
    api("com.tencent.tmf.android:portal-annotations:3.0.0.0")
    api("com.tencent.tmf.android:portal-core:3.0.0.0") {
        exclude(group = "com.tencent.tmf.android",module = "base-core")
        exclude(group = "com.tencent.tmf.android",module = "shark")
        exclude(group = "com.tencent.tmf.android",module = "wup")
    }
    compileOnly("com.tencent.tmf.android:share:2.0.0.8-322-75d19ab9-SNAPSHOT")
    compileOnly("com.tencent.tmf.android:WeChatSDK:6.8.0")
    /////////////////////////////////////////////demo工程所需依赖
}