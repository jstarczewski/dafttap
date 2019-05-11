package com.clakestudio.pc.dafttapchallange.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.clakestudio.pc.dafttapchallange.data.Score
import com.clakestudio.pc.dafttapchallange.data.local.TopScoresDataSource
import com.clakestudio.pc.dafttapchallange.data.local.TopScoresLocalDataSource
import io.reactivex.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class GameViewModel(private val topScoresLocalDataSource: TopScoresDataSource) : ViewModel() {
    // TODO: Implement the ViewModel

    private val prepareItems = arrayListOf<String>("3", "2", "1", "PLAY")
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

    private val _scores: MutableLiveData<List<Score>> = MutableLiveData()

    private val rtime: MutableLiveData<Long> = MutableLiveData()
    val remainingTime: LiveData<Long> = rtime

    private val _isRunning: MutableLiveData<Boolean> = MutableLiveData()
    val isRunning: LiveData<Boolean> = _isRunning


    fun incrementTapsNumber() {
        if (isGameRunning)
            _taps.value = ++tapsNumber
    }

    fun saveScore(score: Int) {

        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val result = formatter.format(Calendar.getInstance().time)

        val scores: ArrayList<Score> = arrayListOf()
        if (!_scores.value.isNullOrEmpty())
            scores.addAll(_scores.value!!)
        scores.add(Score(score, result))
        Flowable.fromCallable {
            scores.forEach {
                topScoresLocalDataSource.saveScore(it)
            }
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe()


        /*  if(scores.size == 5) {
             i
          }
          else {
              scores.add(Score(score,:wqa
               "12"))
          }*/

        /*
        val scores : ArrayList<Score> = arrayListOf()
        scores.addAll(_scores.value!!)
        val isIn = scores?.find { it.score == score } ?: -1
        scores?.sortedBy { it.score }
        if (isIn == score) {

        }
        else {

        }*/


    }

    fun resumeGame() {
        prepare()
    }

    fun pasueGame() {
        isGameRunning = false
        _isRunning.value = false
    }

    fun endGame() {
        _time.value = "0"
        _isRunning.value = false
        isGameRunning = false
        saveScore(tapsNumber)
        exposeScore()
    }

    fun setRemainingTime(timeRemaining: Long) {
        rtime.value = timeRemaining
        _time.value = (timeRemaining / 1000).toString()
    }

    fun getTopScores() =
        topScoresLocalDataSource.getTopScores()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                _scores.value = it
            }

    fun init() {
        rtime.value = 5000
    }

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

    private fun prepare() = Flowable.interval(0, 1, TimeUnit.SECONDS, Schedulers.io())
        .take(3, TimeUnit.SECONDS)
        .map { prepareItems[it.toInt()] }
        .observeOn(AndroidSchedulers.mainThread())
        .doOnComplete {
            isGameRunning = true
            _isRunning.value = true
        }
        .subscribe()

    private fun countDown() = Observable.interval(0, 1, TimeUnit.SECONDS, Schedulers.io())
        .take(8, TimeUnit.SECONDS)
        .map { prepareItems[it.toInt()] }
        .observeOn(AndroidSchedulers.mainThread())
        .doOnComplete {
            _time.value = "0"
            isGameRunning = false
            saveScore(tapsNumber)
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




