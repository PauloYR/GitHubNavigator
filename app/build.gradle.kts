plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.paulo.githubnavigator"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.paulo.githubnavigator"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}



dependencies {

    implementation("androidx.core:core-ktx:${Versions.coreKtx}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.coreKtx}")
    implementation("androidx.activity:activity-compose:${Versions.activityCompose}")
    implementation(platform("androidx.compose:compose-bom:${Versions.composeBom}"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:${Versions.junit}")
    androidTestImplementation("androidx.test.ext:junit:${Versions.androidXJunit}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.espresso}")
    androidTestImplementation(platform("androidx.compose:compose-bom:${Versions.composeBom}"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}