// Top-level build file where you can add configuration options common to all sub-projects/modules.
rootProject.buildDir = file(rootProject.rootDir.parentFile.parentFile.absolutePath + File.separator + "buildOut")

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

//plugins {
//    /*id("com.android.application") apply false
//    id("com.android.library") apply false
//    kotlin("android") apply false*/
//}

buildscript {
    repositories {
        maven {
            url = uri("https://mirrors.cloud.tencent.com/gradle/")
        }
        maven {
            url = uri("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/gradle-plugin")
        }
        maven {
            url = uri("https://t.pinpad.qq.com/fHKFBbEjd/repository/maven-public/")
            //本地maven不能添加如下验证，否则会包错误：Authentication scheme 'basic'(BasicAuthentication) is not supported by protocol 'file
            //主要是因为上面的url采用变量的形式
            credentials {
                username = "tmf_read"
                password = "psnBSt@BVMva6#&^"
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    dependencies {
        val agpVersion: String by project
        val kotlinVersion: String by project
        classpath("com.android.application:com.android.application.gradle.plugin:$agpVersion")
        classpath("com.android.library:com.android.library.gradle.plugin:$agpVersion")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.tencent.tmf.android:portal-gradle-plugin:3.0.0.1-497-34bf28b5-SNAPSHOT")
        classpath("com.huawei.agconnect:agcp:1.8.1.300")
    }
}

allprojects {
    buildDir = file("${rootProject.buildDir.absolutePath}/${project.name}")
}