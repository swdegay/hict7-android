package dev.sethdegay.hict7.core.audio

import android.content.Context
import android.media.AudioAttributes
import android.speech.tts.TextToSpeech
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.sethdegay.hict7.core.common.state.InitState
import dev.sethdegay.hict7.core.common.state.ManagedInstance
import dev.sethdegay.hict7.core.common.state.runOnlyWhenInitialized
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class TtsManager @Inject constructor(@ApplicationContext private val context: Context) :
    ManagedInstance {

    companion object {
        private const val UTTERANCE_ID = "speak"

        private val attributes: AudioAttributes by lazy {
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        }
    }

    private val _initState: MutableStateFlow<InitState> = MutableStateFlow(InitState.Initializing)
    override val initState: StateFlow<InitState>
        get() = _initState

    private lateinit var tts: TextToSpeech

    override fun initialize() {
        if (initState.value == InitState.Success) {
            return
        }
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.setSpeechRate(1f)
                tts.setPitch(1f)
                tts.setAudioAttributes(attributes)
                _initState.value = InitState.Success
            } else {
                _initState.value = InitState.Error(status = status)
            }
        }
    }

    fun speak(text: String, queueMode: Int = TextToSpeech.QUEUE_FLUSH) = runOnlyWhenInitialized {
        tts.speak(text, queueMode, null, UTTERANCE_ID)
    }

    fun stop() = runOnlyWhenInitialized {
        tts.stop()
    }

    override fun release() = runOnlyWhenInitialized {
        stop()
        tts.shutdown()
        _initState.value = InitState.Initializing
    }
}
