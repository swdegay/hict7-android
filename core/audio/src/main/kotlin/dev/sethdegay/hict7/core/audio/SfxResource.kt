package dev.sethdegay.hict7.core.audio

import androidx.annotation.RawRes

internal enum class SfxResource(@RawRes val id: Int) {
    BELL(R.raw.bell),
    TICK_ODD(R.raw.tick_odd),
    TICK_EVEN(R.raw.tick_even),
}