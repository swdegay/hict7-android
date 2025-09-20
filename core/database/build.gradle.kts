plugins {
    alias(libs.plugins.hict7.core)
    alias(libs.plugins.hict7.hilt)
    alias(libs.plugins.hict7.room)
}

android {
    namespace = "dev.sethdegay.hict7.core.database"
}

dependencies {
    api(projects.core.common)
    api(projects.core.model)
}
