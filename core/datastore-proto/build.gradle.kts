import dev.sethdegay.buildlogic.findLibraryId

plugins {
    alias(libs.plugins.hict7.core)
    alias(libs.plugins.protobuf)
}

android {
    namespace = "dev.sethdegay.hict7.core.datastore.proto"
}

protobuf {
    protoc {
        artifact = findLibraryId("protobuf-protoc")
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    api(libs.protobuf.kotlin.lite)
}