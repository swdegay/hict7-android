plugins {
    alias(libs.plugins.hict7.core)
    alias(libs.plugins.hict7.hilt)
}

android {
    namespace = "dev.sethdegay.hict7.core.audio"
}

dependencies {
    api(projects.core.common)
}

kotlin.compilerOptions.freeCompilerArgs.add("-Xannotation-default-target=param-property")
