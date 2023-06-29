@file:Suppress("UnstableApiUsage")
pluginManagement {
    repositories {
        all {
            if (this is MavenArtifactRepository) {
                val url = url.toString()
                if (
                    (url.startsWith("https://plugins.gradle.org")) ||
                    (url.startsWith("https://repo.gradle.org"))
                ) {
                    remove(this)
                }
            }
        }
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
//    plugins {
///*        val agpVersion: String by settings
//        val kotlinVersion: String by settings
//        id("com.android.application") version agpVersion apply false
//        id("com.android.library") version agpVersion apply false
////        id("kotlin-android") version kotlinVersion apply false
//        kotlin("android") version kotlinVersion apply false*/
//    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        all {
            if (this is MavenArtifactRepository) {
                val url = url.toString()
                if (
                    (url.startsWith("https://plugins.gradle.org")) ||
                    (url.startsWith("https://repo.gradle.org"))
                ) {
                    remove(this)
                }
            }
        }
        maven {
            url = uri("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
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
}

rootProject.name = "TMF"


include(":DEMO")
include(":module-applet")
include(":commonlib")
