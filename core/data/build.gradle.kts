plugins {
    alias(libs.plugins.hict7.core)
    alias(libs.plugins.hict7.hilt)
}

android {
    namespace = "dev.sethdegay.hict7.core.data"
}

dependencies {
    api(projects.core.database)
    api(projects.core.datastore)
    api(projects.core.datastorePrefs)
}
