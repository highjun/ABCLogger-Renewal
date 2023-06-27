plugins {
    id("com.android.application")
    kotlin("android")

    id("com.google.gms.google-services")
    id("com.google.devtools.ksp") version "1.8.21-1.0.11"
}

android {
    namespace = "kaist.iclab.abclogger"
    compileSdk = 33

    defaultConfig {
        applicationId = "kaist.iclab.abclogger"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0.0-2023-06-30"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro")
            )
        }
    }
    sourceSets{
           getByName("main"){
               java.srcDirs("src/main/kotlin/")
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
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Basic Android Library
    implementation("androidx.core:core-ktx:1.10.1")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2022.10.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // Support for Androidx Compose Navigation
    val navigationVersion = "2.5.3"
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-compose:$navigationVersion")

    // Firebase and Google related Library
    // For Crashlytics, Auth, Fitness

    // Koin for Dependency Injection
    implementation("io.insert-koin:koin-androidx-compose:3.4.5")

    // Room DB & DataStore
    // Datastore for having configuration as a key-value
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    // RoomDB for storing collected Data
    implementation("androidx.room:room-common:2.5.2")
    implementation("androidx.room:room-ktx:2.5.2")
    ksp("androidx.room:room-compiler:2.5.2")
    // gRPC Communication for optimized network usage


    // Testing Library
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2022.10.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


}