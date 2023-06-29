@file:Suppress("UnstableApiUsage")
import java.text.SimpleDateFormat
import java.util.Date

plugins {
    id("com.android.application")
//    id("com.tencent.tmf.android.portal")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.tencent.tmf.applet.demo1"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.tencent.tmf.miniapp.demo"
        minSdk = 21
        targetSdk = 34
        multiDexEnabled = true
        ndk {
            abiFilters.apply {
                clear()
                addAll(arrayListOf("armeabi"))
            }
        }
//        consumerProguardFiles("consumer-rules.pro")
    }
    signingConfigs {
        create("release") {
            keyAlias = "jason"
            keyPassword = "1234abcd"
            storeFile = file("./tmf.jks")
            storePassword = "1234abcd"
        }
    }
    android.applicationVariants.all {
        assembleProvider.configure {
            doLast {
                val builder = StringBuilder()
                builder.append("${project.buildDir}")
                    .append(File.separator)
                    .append("build-app-output")
                outputs.files.forEach { outputFile ->
                    println("outputFile=${outputFile.name}")
                    if (outputFile?.name?.endsWith(".apk") == true) {
                        val file = File(builder.toString())
                        if (!file.exists()) {
                            file.mkdirs()
                        }
                        copy {
                            from(outputFile)
                            into(builder.toString())
                            rename {
                                SimpleDateFormat("yyyy-MM-dd").format(Date()) +
                                        "-tmf-applet-demo.apk"
                            }
                        }
                    }
                }
            }
        }
    }
    packagingOptions {
        resources {
            jniLibs.pickFirsts.add("**/*/libc++_shared.so")
            jniLibs.pickFirsts.add("**/*/libmarsxlog.so")
            jniLibs.pickFirsts.add("**/*/libv8jni.so")
            excludes += arrayListOf()
        }
    }
//    packaging {
//
//    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            signingConfig = signingConfigs.getByName("release")
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
    implementation("androidx.multidex:multidex:2.0.1")
    implementation(project(":commonlib"))
    implementation(project(":module-applet"))
}