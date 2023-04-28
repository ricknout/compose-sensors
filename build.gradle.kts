import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.spotless) apply false
    alias(libs.plugins.gradle.maven.publish) apply false
}

subprojects {
    apply<SpotlessPlugin>()
    extensions.configure<SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt")
            ktlint(libs.versions.ktlint.get())
        }
    }
}
