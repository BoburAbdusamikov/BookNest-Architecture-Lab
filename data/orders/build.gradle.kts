plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "uz.ttpu.booknest.data.orders"
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
    api(libs.kotlinx.coroutines.core)
}
