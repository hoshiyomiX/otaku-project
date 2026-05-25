# ProGuard rules for NukeCodes OTA Generator v3.19
# ================================
# R8/ProGuard is now ENABLED for release builds.

# ── Kotlin Coroutines ──────────────────────────────────────────────
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# ── Application & Bridge classes (reflection, JNI, serialization) ──
-keep class com.hoshiyomi.payloadtoolkit.PayloadBridge { *; }
-keep class com.hoshiyomi.payloadtoolkit.PythonBridge { *; }
-keep class com.hoshiyomi.payloadtoolkit.PayloadResult { *; }
-keep class com.hoshiyomi.payloadtoolkit.ExecResult { *; }
-keep class com.hoshiyomi.payloadtoolkit.ProgressUpdate { *; }
-keep class com.hoshiyomi.payloadtoolkit.PyBridge { *; }
-keep class com.hoshiyomi.payloadtoolkit.PyBridge$PyResult { *; }
-keep class com.hoshiyomi.payloadtoolkit.PythonBridge$InitResult { *; }
-keep class com.hoshiyomi.payloadtoolkit.PythonBridge$DepCheckResult { *; }

# ── Application class (declared in AndroidManifest) ────────────────
-keep class com.hoshiyomi.payloadtoolkit.PayloadToolkitApp { *; }

# ── Data classes (declared in AndroidManifest backup) ──────────────
-keep class com.hoshiyomi.payloadtoolkit.data.** { *; }

# ── MainActivity (toolbar menu, view binding) ──────────────────────
-keepclassmembers class com.hoshiyomi.payloadtoolkit.MainActivity {
    public void on*(android.view.MenuItem);
    public boolean on*(android.view.Menu);
}

# ── AndroidX ────────────────────────────────────────────────────────
-keep class androidx.** { public *; }

# ── Material Design Components ──────────────────────────────────────
-keep class com.google.android.material.** { public *; }

# ═══════════════════════════════════════════════════════════════════
# Suppress warnings for libraries we don't control
# ═══════════════════════════════════════════════════════════════════
-dontwarn androidx.**
-dontwarn com.google.android.material.**
-dontwarn kotlin.coroutines.jvm.internal.**
