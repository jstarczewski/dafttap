package com.clakestudio.pc.dafttapchallange.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.clakestudio.pc.dafttapchallange.data.local.TopScoresDataSource
import com.clakestudio.pc.dafttapchallange.data.local.TopScoresLocalDataSource
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class GameViewModel(topScoresLocalDataSource: TopScoresDataSource) : ViewModel() {
    // TODO: Implement the ViewModel

    private val prepareItems = arrayListOf<String>("3", "2", "1", "5", "4", "3", "2", "1")
    private val gameRunningItems = arrayListOf<String>("5", "4", "3", "2", "1")
    private var tapsNumber = 0
    private var isGameRunning = false

    private val _taps: MutableLiveData<Int> = MutableLiveData()
    val taps: LiveData<Int> = _taps

    private val _time: MutableLiveData<String> = MutableLiveData()
    val time: LiveData<String> = _time

    private val _play: MutableLiveData<String> = MutableLiveData()
    val play: LiveData<String> = _play

    private val _dialog: MutableLiveData<String> = MutableLiveData()
    val dialog: LiveData<String> = _dialog

    fun incrementTapsNumber() {
        if (isGameRunning)
            _taps.value = ++tapsNumber
    }

    fun init() = countDown()
    /*   Observable.concatArray(
           countDown(),
           countDown()
       ).observeOn(AndroidSchedulers.mainThread())
           .subscribe({
               if (it.equals("5")) {
                   isGameRunning = true
                   _play.value = "PLAY"
               }
               if (it.equals("1"))
                   isGameRunning = false
               _time.value = it
           }, {

           }, {
           })*/

/*
    private fun countDown() =
        Observable.interval(0, 1, TimeUnit.SECONDS, Schedulers.io())
            .take(5000)
            .map { gameRunningItems[it.toInt()] }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _time.value = it
            }, {

            }, {
                isGameRunning = false
            })*/

    private fun exposeScore() {
        if (tapsNumber > 40)
            _dialog.value = "Game eneded with score $tapsNumber\nYou made it to top"
        else
            _dialog.value = "Game ended wit score $tapsNumber"
    }

    private fun countDown() = Observable.interval(0, 1, TimeUnit.SECONDS, Schedulers.io())
        .take(8, TimeUnit.SECONDS)
        .map { prepareItems[it.toInt()] }
        .observeOn(AndroidSchedulers.mainThread())
        .doOnComplete {
            _time.value = "0"
            isGameRunning = false
            exposeScore()
        }
        .subscribe {
            if (!isGameRunning) {
                _play.value = it
                _time.value = "5"
            }
            if (it == "5" || isGameRunning) {
                isGameRunning = true
                _play.value = "PLAY"
                _time.value = it
            }
        }
}




