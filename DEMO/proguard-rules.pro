# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keep class **_FragmentFinder { *; }
-keep class androidx.fragment.app.* { *; }

-keep class com.qmuiteam.qmui.arch.record.RecordIdClassMap { *; }
-keep class com.qmuiteam.qmui.arch.record.RecordIdClassMapImpl { *; }

-keep class com.qmuiteam.qmui.arch.scheme.SchemeMap {*;}
-keep class com.qmuiteam.qmui.arch.scheme.SchemeMapImpl {*;}


#-optimizationpasses 5
#-keepattributes Exceptions,InnerClasses,Signature
#
#-dontwarn com.example.tissue.**
#-dontwarn com.tencent.smtt.**
#-dontwarn com.tencent.tissue.**
#-dontwarn com.tencent.mobileqq.triton.**
#-dontwarn io.flutter.**
#-dontwarn com.qq.e.**
#-dontwarn okio.**
#-dontwarn okhttp3.**
#-dontwarn org.slf4j.**
#-dontwarn com.tencent.map.geolocation.internal.**
#-dontwarn c.t.m.g.**
#
#-keepattributes *Annotation*
#
## Keep names - Native method names. Keep all native class/method names.
#-keepclasseswithmembers class * {
#    native <methods>;
#}
#
## Keep parcelable
#-keep class * implements android.os.Parcelable {
#  public static final android.os.Parcelable$Creator *;
#}
#
## Keep class members annotated with @MiniKepp
#-keep,allowobfuscation @interface com.tencent.qqmini.sdk.annotation.MiniKeep
#-keep @com.tencent.qqmini.sdk.annotation.MiniKeep class *
#-keepclassmembers @com.tencent.qqmini.sdk.annotation.MiniKeep class ** {
#    public <methods>; <fields>;
#}
#-keepclassmembers class * {
#    @com.tencent.qqmini.sdk.annotation.MiniKeep *;
#}
#
## Keep class members annotated with @JsEvent
#-keep,allowobfuscation @interface com.tencent.qqmini.sdk.annotation.JsEvent
#-keepclassmembers class * {
#    @com.tencent.qqmini.sdk.annotation.JsEvent *;
#}
#
## Keep sdk auto-generated class
#-keep class com.tencent.qqmini.sdk.core.generated.** { *; }
#
##BroadcastReceiver
#-keep class com.tencent.qqmini.sdk.receiver.** {* ;}
#
#-keepclassmembers class com.tencent.qqmini.sdk.** {
#    @android.webkit.JavascriptInterface <methods>;
#}
#
## protocol: should keep field name because reflection
#-keep class cooperation.** { *; }
#-keep class gdt.** { *; }
#-keep class com.tencent.mobileqq.pb.MessageMicro { *; }
#-keepclassmembers class * extends com.tencent.mobileqq.pb.MessageMicro {
#    <fields>;
#}
#
##小程序X5 需要
#-keep class com.tencent.smtt.** { *; }
#-keep class com.tencent.tbs.video.interfaces.**  {* ;}
#-keep class MTT.**  {* ;}
#
####---------------Begin: proguard configuration for Gson  ----------
##这里很奇怪即便是用了注解SerializedName，也需要配置如下如下keep，否则有的地方解析无法解析
## Gson uses generic type information stored in a class file when working with fields. Proguard
## removes such information by default, so configure it to keep all of it.
#-keepattributes Signature
#
## For using GSON @Expose annotation
#-keepattributes *Annotation*
#
## Gson specific classes
#-dontwarn sun.misc.**
##-keep class com.google.gson.stream.** { *; }
#
## Application classes that will be serialized/deserialized over Gson
##-keep class com.google.gson.examples.android.model.** { <fields>; }
#
## Prevent proguard from stripping interface information from TypeAdapterFactory,
## JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
#-keep class * implements com.google.gson.TypeAdapterFactory
#-keep class * implements com.google.gson.JsonSerializer
#-keep class * implements com.google.gson.JsonDeserializer
#
## Prevent R8 from leaving Data object members always null
#-keepclassmembers,allowobfuscation class * {
#  @com.google.gson.annotations.SerializedName <fields>;
#}
###---------------End: proguard configuration for Gson  ----------



