plugins {
    Plugins.apply {
        id(androidApplication)
        id(kotlinAndroid)
        id(daggerHilt)
        id(safeArgs)
        id(kotlinKapt)
        id(kotlinKsp)
        id(kotlinParcelize)
    }
}

android {
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = ConfigData.appId
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = Debug.versionCode
        versionName = Debug.versionName

        testInstrumentationRunner = ConfigData.testInstrumentationRunner
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", Secrets.baseUrl)
            buildConfigField("String", "API_KEY", Secrets.apiKey)
            buildConfigField("String", "API_VERSION", Secrets.apiVersion)
        }
        release {
            // isShrinkResources = true
            isMinifyEnabled = false
            buildConfigField("String", "BASE_URL", Secrets.baseUrl)
            buildConfigField("String", "API_KEY", Secrets.apiKey)
            buildConfigField("String", "API_VERSION", Secrets.apiVersion)
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    /*kotlin.sourceSets.all {
        languageSettings {
            optIn("kotlin.RequiresOptIn")
            optIn("com.google.maps.android.compose.MapsComposeExperimentalApi")
            optIn("androidx.compose.animation.ExperimentalAnimationApi")
            optIn("androidx.compose.animation.ExperimentalAnimationApi")
            optIn("androidx.compose.ExperimentalComposeApi")
            optIn("androidx.compose.foundation.ExperimentalFoundationApi")
            optIn("androidx.compose.material3.ExperimentalMaterialApi")
            optIn("androidx.compose.material3.ExperimentalMaterial3Api")
            optIn("androidx.compose.runtime.ExperimentalComposeApi")
            optIn("androidx.compose.ui.ExperimentalComposeUiApi")
            optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
            optIn("kotlinx.coroutines.InternalCoroutinesApi")
            optIn("com.google.accompanist.pager.ExperimentalPagerApi")
            optIn("com.google.accompanist.permissions.ExperimentalPermissionsApi")
        }
    }*/


    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    namespace = ConfigData.appId
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.bundles.material3)
    implementation(libs.bundles.coroutines)
    implementation(libs.constraintlayout)
    implementation(libs.bundles.compose)
    debugImplementation(libs.compose.ui.tooling.debug)
    debugImplementation(libs.compose.ui.test.manifest.debug)

    implementation(libs.bundles.navigation)
    implementation(libs.bundles.lifecycle)

    implementation(libs.dagger.hilt.android)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.dagger.hilt.compiler)

    implementation(libs.timber)

    implementation(libs.bundles.retrofit)
    ksp(libs.moshi.codegen)

    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.okhttp)


    implementation(libs.bundles.coil)
    testImplementation(libs.bundles.testLibs)
    androidTestImplementation(libs.bundles.androidTestLibs)

    kaptTest(libs.bundles.kaptTestLibs)
    kaptAndroidTest(libs.bundles.kaptAndroidTestLibs)

}