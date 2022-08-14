import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(gradleLibs.plugins.android.application) apply false
    alias(gradleLibs.plugins.android.library) apply false
    alias(gradleLibs.plugins.dagger.hilt) apply false
    alias(gradleLibs.plugins.kotlin.android) apply false
    alias(gradleLibs.plugins.bem.manes) apply true
    alias(gradleLibs.plugins.ksp) apply false
}

buildscript {
    dependencies {
        classpath(gradleLibs.navigation.safeargs)
    }
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs = listOf(
            "-use-k2"
        )
        jvmTarget = "11"
        languageVersion = "1.7"
    }
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isStableOrBeta(candidate.version).not()
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

fun isStableOrBeta(version: String): Boolean {
    val stableKeyword =
        listOf("RELEASE", "FINAL", "GA", "RC", "BETA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    return stableKeyword || regex.matches(version)
}
