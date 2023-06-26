// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    //  Plugin for Android
    id("com.android.application") version "8.0.2" apply false
    id("com.android.library") version "8.0.2" apply false
    id("org.jetbrains.kotlin.android") version "1.7.20" apply false
    //  Plugin for protobuf
    id("com.google.protobuf") version "0.9.3" apply false

    kotlin("jvm") version "1.8.21" apply false

    // gms support
    id("com.google.gms.google-services") version "4.3.4" apply false
}

ext["grpcVersion"] = "1.54.1"
ext["grpcKotlinVersion"] = "1.3.0"
ext["protobufVersion"] = "3.22.3"
ext["coroutinesVersion"] = "1.7.0"

tasks.create("assemble").dependsOn(":server:installDist")

