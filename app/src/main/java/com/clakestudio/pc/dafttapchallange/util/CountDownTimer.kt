package com.clakestudio.pc.dafttapchallange.util

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CountDownTimer<T>(
    private val time: Int,
    private val interval: Long,
    private val data: List<T>,
    private val callback: (T) -> (Unit)
) {

    fun countDown() =
        Flowable.interval(0, interval, TimeUnit.SECONDS, Schedulers.io())
            .take(time.toLong())
            .map { data[it.toInt()] }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                callback.invoke(it)
            }


}


