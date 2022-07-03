package com.echo.common

import android.os.CountDownTimer
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class QuizTimer(millisInFuture: Long, countDownInterval: Long) {
    private val _isFinished: MutableLiveData<Boolean> = MutableLiveData()
    val isFinished: LiveData<Boolean> = _isFinished

    @OptIn(ExperimentalCoroutinesApi::class)
    private val tick: Flow<Long> = callbackFlow {

        if (Looper.myLooper() == null) {
            throw IllegalStateException("Can't create TimerFlow inside thread that has not called Looper.prepare() Just use Dispatchers.Main")
        }

        object : CountDownTimer(millisInFuture, countDownInterval) {
            override fun onFinish() {
                offer(0L)
                _isFinished.postValue(true)
                cancel()
            }

            override fun onTick(millisUntilFinished: Long) {
                offer(millisUntilFinished)
            }
        }.start()

        awaitClose()
    }

    companion object {
        fun update() = QuizTimer(30000, 1000).tick
    }



}