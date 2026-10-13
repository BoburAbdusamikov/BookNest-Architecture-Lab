plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "uz.ttpu.booknest.core.testing"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    api(project(":core:model"))
    api(project(":data:books"))
    api(project(":data:orders"))
    api(libs.junit)
    api(libs.kotlinx.coroutines.test)
}
