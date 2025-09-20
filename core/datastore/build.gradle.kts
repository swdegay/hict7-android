plugins {
    alias(libs.plugins.hict7.core)
    alias(libs.plugins.hict7.hilt)
}

android {
    namespace = "dev.sethdegay.hict7.core.datastore"
    defaultConfig {
        consumerProguardFiles("consumer-proguard-rules.pro")
    }
}

dependencies {
    api(libs.androidx.datastore)
    api(projects.core.datastoreProto)
    api(projects.core.model)
}
