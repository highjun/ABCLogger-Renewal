plugins {
    application
    kotlin("jvm")
}

dependencies {
    implementation(project(":stub"))
    runtimeOnly("io.grpc:grpc-netty:${rootProject.ext["grpcVersion"]}")
}

kotlin {
    jvmToolchain(8)
}


tasks.register<JavaExec>("ABCServer") {
    dependsOn("classes")
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("kaist.iclab.abclogger.ABCServerKt")
}

val abcServerStartScripts = tasks.register<CreateStartScripts>("ABCServerStartScripts") {
    mainClass.set("kaist.iclab.abclogger.ABCServerKt")
    applicationName = "abc-logger-server"
    outputDir = tasks.named<CreateStartScripts>("startScripts").get().outputDir
    classpath = tasks.named<CreateStartScripts>("startScripts").get().classpath
}
tasks.named("startScripts") {
    dependsOn(abcServerStartScripts)
}