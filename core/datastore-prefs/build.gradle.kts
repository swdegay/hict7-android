plugins {
    alias(libs.plugins.hict7.core)
    alias(libs.plugins.hict7.hilt)
}

android {
    namespace = "dev.sethdegay.hict7.core.datastore.prefs"
}

dependencies {
    api(projects.core.model)

    implementation(libs.androidx.datastore.preferences)
}
