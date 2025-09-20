package dev.sethdegay.hict7.core.timer

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

private fun countdown(
    duration: Duration,
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
): Flow<Long> = flow {
    (duration.inWholeMilliseconds downTo 1_000L step 1_000L).forEach {
        emit(it)
        delay(1_000L)
    }
    emit(0)
}.flowOn(dispatcher)

suspend fun <T> List<T>.countdown(
    timerEventReceiver: TimerEventReceiver<T>,
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    if (isEmpty()) {
        timerEventReceiver.onError(IllegalArgumentException())
        return
    }

    val startIndex = timerEventReceiver.onStartIndexRequest()
    if (startIndex > lastIndex) {
        timerEventReceiver.onError(IllegalArgumentException())
        return
    }

    try {
        for (i in startIndex..lastIndex) {
            timerEventReceiver.onStart(i)
            val element = get(i)
            countdown(
                duration = timerEventReceiver.onDurationRequest(element),
                dispatcher = dispatcher,
            ).collect {
                timerEventReceiver.onTick(it.milliseconds)
            }
        }
        timerEventReceiver.onFinish()
    } catch (e: Exception) {
        Log.d("COUNTDOWN", e.message, e)
    }
}