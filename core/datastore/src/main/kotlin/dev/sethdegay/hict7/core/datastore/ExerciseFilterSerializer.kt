package dev.sethdegay.hict7.core.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class ExerciseFilterSerializer @Inject constructor() : Serializer<ExerciseFilter> {
    override val defaultValue: ExerciseFilter = ExerciseFilter.newBuilder()
        .setShowWarmUp(true)
        .setShowRest(true)
        .setShowCoolDown(true)
        .build()

    override suspend fun readFrom(input: InputStream): ExerciseFilter =
        try {
            ExerciseFilter.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto", e)
        }

    override suspend fun writeTo(t: ExerciseFilter, output: OutputStream) {
        t.writeTo(output)
    }
}