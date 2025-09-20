package dev.sethdegay.hict7.core.timer

import kotlin.time.Duration

interface TimerEventReceiver<T> {
    fun onStart(index: Int)
    fun onTick(time: Duration)
    fun onDurationRequest(element: T): Duration
    fun onStartIndexRequest(): Int
    fun onError(e: Exception)
    fun onFinish()
}