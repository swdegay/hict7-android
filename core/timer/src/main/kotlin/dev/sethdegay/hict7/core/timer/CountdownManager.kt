package dev.sethdegay.hict7.core.timer

import dev.sethdegay.hict7.core.common.state.InitState
import dev.sethdegay.hict7.core.common.state.ManagedInstance
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

abstract class CountdownManager<T>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) : TimerEventReceiver<T>, ManagedInstance {

    companion object {
        private val INDEX_DEFAULT: Int? = null
        private val TIME_DEFAULT = Duration.ZERO
        private val STATE_DEFAULT = State.STOPPED
        private fun <T> getDefaultList() = emptyList<T>()
    }

    enum class State { STARTED, PAUSED, STOPPED; }

    private var _countdownJob: Job? = null

    private val _list: MutableStateFlow<List<T>> = MutableStateFlow(getDefaultList())
    val list: StateFlow<List<T>>
        get() = _list

    private val _index: MutableStateFlow<Int?> = MutableStateFlow(INDEX_DEFAULT)
    val index: StateFlow<Int?>
        get() = _index

    private val _time: MutableStateFlow<Duration> = MutableStateFlow(TIME_DEFAULT)
    val time: StateFlow<Duration>
        get() = _time

    private val _state: MutableStateFlow<State> = MutableStateFlow(STATE_DEFAULT)
    val state: StateFlow<State>
        get() = _state

    private val _initState: MutableStateFlow<InitState> = MutableStateFlow(InitState.Initializing)
    override val initState: StateFlow<InitState>
        get() = _initState

    override fun initialize() {
        throw NotImplementedError("Use setList(List<T>) as initializer")
    }

    override fun onStart(index: Int) {
        _index.value = index
    }

    override fun onTick(time: Duration) {
        _time.value = time
    }

    override fun onStartIndexRequest(): Int {
        return index.value!!
    }

    fun setList(list: List<T>) {
        if (list.isEmpty()) {
            _initState.value = InitState.Error(message = "Can't initialize CountdownManager")
            return
        }
        release()
        _list.value = list
        _index.value = 0
        _initState.value = InitState.Success
    }

    fun pauseTimer() {
        _countdownJob?.cancel()
        _countdownJob = null
        _state.value = State.PAUSED
    }

    fun startTimer(startIndex: Int? = _index.value) {
        if (initState.value != InitState.Success
            || startIndex == null
            || startIndex > list.value.lastIndex
            || startIndex < 0
        ) {
            onError(IllegalArgumentException())
            return
        }
        pauseTimer()

        if (startIndex != _index.value) {
            _index.value = startIndex
            _time.value = 0.seconds
        }

        _countdownJob = CoroutineScope(dispatcher).launch {
            list.value.countdown(this@CountdownManager)
        }
        _state.value = State.STARTED
    }

    fun moveToNext() {
        index.value?.apply { startTimer(this + 1) } ?: onError(IllegalArgumentException())
    }

    fun moveToPrevious() {
        index.value?.apply { startTimer(this - 1) } ?: onError(IllegalArgumentException())
    }

    override fun release() {
        _countdownJob?.cancel()
        _countdownJob = null
        _list.value = getDefaultList()
        _index.value = INDEX_DEFAULT
        _time.value = TIME_DEFAULT
        _state.value = State.STOPPED
        _initState.value = InitState.Initializing
    }
}
