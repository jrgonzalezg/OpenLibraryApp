# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /usr/local/android/android-sdk-linux/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:


##---------------Begin: ProGuard configuration for Dagger ----------

# Based on https://github.com/google/dagger/issues/645

-dontwarn com.google.errorprone.annotations.**

##---------------End: ProGuard configuration for Dagger ------------


##---------------Begin: ProGuard configuration for Moshi ----------

# Based on https://github.com/square/moshi#proguard

-dontwarn okio.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault

##---------------End: ProGuard configuration for Moshi ------------


##---------------Begin: ProGuard configuration for OkHttp ----------

# Based on https://github.com/square/okhttp#proguard

-dontwarn okio.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault

##---------------End: ProGuard configuration for OkHttp ------------


##---------------Begin: ProGuard configuration for Okio ----------

# Based on https://github.com/square/okio#proguard

-dontwarn okio.**

##---------------End: ProGuard configuration for Okio ------------


##---------------Begin: ProGuard configuration for Retrofit ----------

# Based on http://square.github.io/retrofit/

# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

##---------------End: ProGuard configuration for Retrofit ------------


# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile
