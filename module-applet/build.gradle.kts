@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.tencent.tmf.applet.demo"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
//        targetSdk = 33
//        consumerProguardFiles =  "consumer-rules.pro"
    }
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
    implementation("androidx.appcompat:appcompat:1.6.1")
    /////////////////////demo依赖 start////////////////////////////
    api(fileTree("libs") { include("*.jar","*.aar") })

    kapt("com.tencent.tmf.android:portal-processor:3.0.0.0")

    implementation(project(":commonlib"))
    implementation("com.github.niorgai:StatusBarCompat:2.3.3") {
        exclude(group = "androidx.appcompat:appcompat")
        exclude(group = "com.google.android.material:material")
    }
    implementation("com.tencent.tmf.android:qmui:1.4.2")
    implementation("com.tencent.tmf.android:qmui-arch:1.4.1")
    kapt("com.qmuiteam:arch-compiler:2.0.0-alpha10")
    implementation("com.github.bumptech.glide:glide:4.11.0") {
        exclude(group = "com.android.support")
    }

    /////////////////////tmf组件依赖 start////////////////////////////
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.core:core-ktx:1.10.1")
    //gosn
    implementation("com.google.code.gson:gson:2.10.1")
    // ok-http
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.3")
    // x5内核 start
    //开源版内核依赖
//    implementation("com.tencent.tbs:tbssdk:44286")
//    implementation("com.tencent.tmf.android:mini_extra_public_x5:1.4.3.0")

    //静态版内核依赖
    implementation("com.tencent.tmf.android:tbscore:20230412_185455_20230412_185455")
    implementation("com.tencent.tmf.android:mini_extra_static_x5:1.4.4.0")
    //动态版内核依赖
//    implementation("com.tencent.tmf.android:dynamicx5:1.2.4.12-SNAPSHOT")
//    implementation("com.tencent.tmf.android:mini_extra_dynamic_x5:1.4.3.0")
    // x5内核 end
    // mini app start

    kapt("com.tencent.tmf.android:mini_annotation_processor:1.4.1.1-102-af590ec8-SNAPSHOT")//1.4.1.0

    implementation("com.tencent.tmf.android:mini_core:1.4.83.5")

    //扫码扩展组件
    implementation("com.tencent.tmf.android:mini_extra_qrcode:1.4.3.0")

    //腾讯地图start
    implementation("com.tencent.tmf.android:mini_extra_map:1.4.2.0")
    implementation("com.tencent.map:tencent-map-vector-sdk:4.5.10")
    // 地图组件库，包括小车平移、点聚合等组件功能，详见开发指南。
    implementation("com.tencent.map:sdk-utilities:1.0.7")
    // 定位组件
    implementation("com.tencent.map.geolocation:TencentLocationSdk-openplatform:7.4.7")
    //腾讯地图end
    //直播流组件
    implementation("com.tencent.tmf.android:mini_extra_trtc_live:1.4.2-SNAPSHOT")
    //v8库，一般不需要添加，特殊场景才需要
//    implementation("com.tencent.tmf.android:mini_extra_v8:1.4.0.0")
    // mini app end
}