import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.gradle.maven.publish)
}

android {
    namespace = "dev.ricknout.composesensors"
    compileSdk = 33
    defaultConfig {
        minSdk = 21
        consumerProguardFiles("consumer-rules.pro")
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
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.S01)
    signAllPublications()
    coordinates(
        groupId = "dev.ricknout.composesensors",
        artifactId = "composesensors",
        version = "0.1.0"
    )
    pom {
        name.set("Compose Sensors")
        description.set("A thin wrapper around the Android Sensor APIs, designed to make it easier to work with them in Jetpack Compose.")
        inceptionYear.set("2023")
        url.set("https://github.com/ricknout/compose-sensors")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("ricknout")
                name.set("Nick Rout")
                url.set("https://github.com/ricknout")
            }
        }
        scm {
            url.set("https://github.com/ricknout/compose-sensors")
            connection.set("scm:git:git://github.com/ricknout/compose-sensors.git")
            developerConnection.set("scm:git:ssh://git@github.com/ricknout/compose-sensors.git")
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit.ktx)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
}