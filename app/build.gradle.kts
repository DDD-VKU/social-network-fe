plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "vku.ddd.social_network_fe"
    compileSdk = 35

    defaultConfig {
        applicationId = "vku.ddd.social_network_fe"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    packagingOptions {
        resources {
            excludes += "META-INF/NOTICE.md"
            excludes += "META-INF/LICENSE.md"
        }
    }
}

dependencies {
    // Core Coil 3 (required)
    implementation("io.coil-kt.coil3:coil-compose:3.1.0")

    // Optional modules:
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.1.0") // OkHttp integration
    implementation("io.coil-kt.coil3:coil-gif:3.1.0") // GIF support
    implementation("io.coil-kt.coil3:coil-svg:3.1.0") // SVG support
    implementation("io.coil-kt.coil3:coil-video:3.1.0") // Video frame extraction

    // For Compose Multiplatform
    implementation("io.coil-kt.coil3:coil-network-ktor2:3.1.0")

    // ===== Android Core =====
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.process)

    // ===== Jetpack Compose =====
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("androidx.compose.material:material:1.7.8")
    implementation("androidx.compose.material:material-icons-extended:1.7.8")
    implementation("androidx.compose.foundation:foundation:1.5.4")

    // ===== Navigation =====
    implementation("androidx.navigation:navigation-compose:2.8.8")
    implementation("androidx.navigation:navigation-fragment-compose:2.8.8")

    // ===== Lifecycle & ViewModel =====
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")

    // ===== Data Persistence =====
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // ===== Networking =====
    // Retrofit (REST)
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // OkHttp (HTTP client)
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Ktor (WebSockets)
    implementation("io.ktor:ktor-client-cio:3.1.1")
    implementation("io.ktor:ktor-client-websockets:3.1.1")
    implementation("io.ktor:ktor-client-content-negotiation:3.1.1")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.1.1")
    implementation("io.ktor:ktor-client-logging:3.1.1")

    // ===== Coroutines =====
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")

    // ===== Image Loading =====
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("io.coil-kt:coil-gif:2.6.0")

    // ===== Validation =====
    implementation("jakarta.json:jakarta.json-api:2.1.3")
    implementation("org.hibernate.validator:hibernate-validator:9.0.0.CR1")
    implementation("jakarta.el:jakarta.el-api:5.0.0")

    // ===== Testing =====
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // ===== Debug Tools =====
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.12")
}