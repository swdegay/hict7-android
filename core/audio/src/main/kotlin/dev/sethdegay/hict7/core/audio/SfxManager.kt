package dev.sethdegay.hict7.core.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.sethdegay.hict7.core.common.state.InitState
import dev.sethdegay.hict7.core.common.state.ManagedInstance
import dev.sethdegay.hict7.core.common.state.runOnlyWhenInitialized
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SfxManager @Inject constructor(@ApplicationContext private val context: Context) :
    ManagedInstance {

    companion object {
        private const val SOUND_POOL_MAX_STREAMS = 1

        private val attributes: AudioAttributes by lazy {
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        }

        private fun buildSoundPoolInstance(): SoundPool = SoundPool.Builder()
            .setAudioAttributes(attributes)
            .setMaxStreams(SOUND_POOL_MAX_STREAMS)
            .build()
    }

    private val ids = hashMapOf<SfxResource, Int>()

    private val _initState: MutableStateFlow<InitState> = MutableStateFlow(InitState.Initializing)
    override val initState: StateFlow<InitState>
        get() = _initState

    private val soundPool = buildSoundPoolInstance()

    override fun initialize() {
        if (initState.value == InitState.Success) {
            return
        }
        SfxResource.entries.forEach { key ->
            ids[key] = soundPool.load(context, key.id, 1)
        }
        if (ids.size == SfxResource.entries.size) {
            _initState.value = InitState.Success
        } else {
            _initState.value =
                InitState.Error(message = "Failed to initialize sfx files with SoundPool")
            release()
        }
    }

    private fun play(
        sfxResource: SfxResource,
        leftVolume: Float = 1f,
        rightVolume: Float = 1f,
        priority: Int = 1,
        loop: Int = 0,
        rate: Float = 1f,
    ) {
        ids[sfxResource]?.apply {
            soundPool.play(this, leftVolume, rightVolume, priority, loop, rate)
        }
    }

    fun playBell() = runOnlyWhenInitialized {
        play(SfxResource.BELL)
    }

    fun playTickOdd() = runOnlyWhenInitialized {
        play(SfxResource.TICK_ODD)
    }

    fun playTickEven() = runOnlyWhenInitialized {
        play(SfxResource.TICK_EVEN)
    }

    fun resume() = runOnlyWhenInitialized {
        soundPool.autoResume()
    }

    fun pause() = runOnlyWhenInitialized {
        soundPool.autoPause()
    }

    override fun release() = runOnlyWhenInitialized {
        soundPool.release()
        _initState.value = InitState.Initializing
    }
}