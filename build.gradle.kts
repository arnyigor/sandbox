import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    kotlin("kapt") version "1.5.10"
}

group = "1.0-SNAPSHOT"
version = "1.0"

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
    implementation("io.ktor:ktor-client-android:1.5.3")
    implementation("commons-io:commons-io:2.6")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
    implementation("org.apache.poi:poi:3.9")
    implementation("io.reactivex.rxjava2:rxjava:2.2.19")
    implementation("io.reactivex.rxjava2:rxkotlin:2.4.0")
    val retrofit = "2.8.1"
    implementation("com.squareup.retrofit2:converter-gson:$retrofit")
    implementation("com.squareup.retrofit2:retrofit:$retrofit")
    implementation("com.squareup.retrofit2:adapter-rxjava2:$retrofit")
    implementation("com.squareup.okhttp3:okhttp:4.2.2")
    implementation("com.squareup.okhttp3:logging-interceptor:3.14.1")
    implementation("joda-time:joda-time:2.10.3")
    implementation("net.iakovlev:timeshape:2020a.10")
    implementation("org.eclipse.mylyn.github:org.eclipse.egit.github.core:2.1.5")
    implementation("org.jsoup:jsoup:1.13.1")
    implementation("com.google.code.gson:gson:2.8.6")
    val dagger = "2.35.1"
    implementation("com.google.dagger:dagger:$dagger")
    annotationProcessor("com.google.dagger:dagger-compiler:$dagger")
    kapt("com.google.dagger:dagger-compiler:$dagger")
    implementation("net.bramp.ffmpeg:ffmpeg:0.6.2")
    implementation("com.github.kokorin.jaffree:jaffree:0.9.7")
    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("com.google.firebase:firebase-admin:7.2.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }
    withType<org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}
