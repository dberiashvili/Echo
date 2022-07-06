package com.echo.common

import android.os.CountDownTimer
import android.os.Looper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class QuizTimer(millisInFuture: Long, countDownInterval: Long) {
    private val _isFinished: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFinished: StateFlow<Boolean> = _isFinished

    @OptIn(ExperimentalCoroutinesApi::class)
    private val tick: Flow<Long> = callbackFlow {

        if (Looper.myLooper() == null) {
            throw IllegalStateException("Can't create TimerFlow inside thread that has not called Looper.prepare() Just use Dispatchers.Main")
        }

        object : CountDownTimer(millisInFuture, countDownInterval) {
            override fun onFinish() {
                CoroutineScope(Dispatchers.IO).launch {
                    offer(0L)
                    _isFinished.emit(true)
                    cancel()
                }

            }

            override fun onTick(millisUntilFinished: Long) {
                offer(millisUntilFinished)
            }
        }.start()

        awaitClose()
    }

    fun start() = this.tick


}