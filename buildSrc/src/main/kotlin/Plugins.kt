object Plugins {
    val androidApplication by lazy { "com.android.application" }
    val androidLibrary by lazy { "com.android.library" }
    val javaLibrary by lazy { "java-library" }
    val kotlinJvm by lazy { "org.jetbrains.kotlin.jvm" }
    val kotlinAndroid by lazy { "org.jetbrains.kotlin.android" }
    val kotlinKapt by lazy { "kotlin-kapt" }
    val kotlinKsp by lazy { "com.google.devtools.ksp" }
    val kotlinParcelize by lazy { "kotlin-parcelize" }
    val daggerHilt by lazy { "dagger.hilt.android.plugin" }
    val safeArgs by lazy { "androidx.navigation.safeargs.kotlin" }
    val googleServices by lazy { "com.google.gms.google-services" }
    val secretsGradlePlugin by lazy { "com.google.android.libraries.mapsplatform.secrets-gradle-plugin" }
    val firebaseCrashlytics by lazy { "com.google.firebase.crashlytics" }
    val firebasePerformance by lazy { "com.google.firebase.firebase-perf" }
}
