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

# ===================================================================
# Retrofit y OkHttp
# ===================================================================

-keep interface com.baubap.challenge.data.api.ApiService { *; }

# ===================================================================
# Gson (DTOs)
# ===================================================================

-keep class com.baubap.challenge.data.api.dto.** { *; }
-keepclassmembers class com.baubap.challenge.data.api.dto.** {
    <fields>;
    <init>();
}

# ===================================================================
# Kotlinx Serialization
# ===================================================================
-keep @kotlinx.serialization.Serializable class * { *; }

-keepclassmembers class * {
    public static final kotlinx.serialization.KSerializer serializer(...);
}
-keepclassmembers class * {
    public static final ** Companion;
}

# ===================================================================
# Dagger - Hilt
# ===================================================================

-keep class * implements dagger.hilt.internal.GeneratedComponent { *; }
-keep class * implements dagger.hilt.internal.GeneratedEntryPoint { *; }
-keep class * implements dagger.hilt.internal.GeneratedComponentManager { *; }

-keep @dagger.hilt.android.HiltAndroidApp class * { *; }
-keep @dagger.hilt.android.WithFragmentBindings class * { *; }
-keep @dagger.hilt.android.lifecycle.HiltViewModel class * { *; }

# ===================================================================
# Orbit
# ===================================================================

-keep class org.orbitmvi.orbit.** { *; }
-keep interface org.orbitmvi.orbit.** { *; }
-keepclassmembers class * implements org.orbitmvi.orbit.ContainerHost {
    public final org.orbitmvi.orbit.Container getContainer();
}

# ===================================================================
# Jetpack Compose
# ===================================================================

-keepclassmembers class * {
    @androidx.compose.runtime.Composable <methods>;
}