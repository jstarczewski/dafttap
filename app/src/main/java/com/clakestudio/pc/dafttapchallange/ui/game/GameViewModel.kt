package com.clakestudio.pc.dafttapchallange.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.clakestudio.pc.dafttapchallange.data.Score
import com.clakestudio.pc.dafttapchallange.data.local.TopScoresDataSource
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class GameViewModel(private val topScoresLocalDataSource: TopScoresDataSource) : ViewModel() {

    private val prepareItems = arrayListOf("3", "2", "1")
    private var tapsNumber = 0

    private val _taps: MutableLiveData<Int> = MutableLiveData()
    val taps: LiveData<Int> = _taps

    private val _time: MutableLiveData<String> = MutableLiveData()
    val time: LiveData<String> = _time

    private val _play: MutableLiveData<String> = MutableLiveData()
    val play: LiveData<String> = _play

    private val _dialog: MutableLiveData<String> = MutableLiveData()
    val dialog: LiveData<String> = _dialog

    private val _remainingTime: MutableLiveData<Long> = MutableLiveData()
    val remainingTime: LiveData<Long> = _remainingTime

    private val _isRunning: MutableLiveData<Boolean> = MutableLiveData()
    val isRunning: LiveData<Boolean> = _isRunning

    private var date: String = String()

    val min = MutableLiveData<Int>()

    private var wasInterruptedDuringPrepare = false

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun incrementTapsNumber() {
        if (_isRunning.value != null && _isRunning.value!!)
            _taps.value = ++tapsNumber
    }

    private fun saveScore(score: Int) {
        Flowable.fromCallable {
            topScoresLocalDataSource.saveScore(Score(score, date))
        }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe()

    }


    /*
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

*/


    fun resumeGame() {
        prepare()
    }

    fun pauseGame() {
        _isRunning.value = false
    }

    fun endGame() {
        _time.value = "0"
        _isRunning.value = false
        saveScore(tapsNumber)
        setDialogMessage()
    }

    fun setRemainingTime(timeRemaining: Long) {
        _remainingTime.value = timeRemaining
        _time.value = (timeRemaining / 1000).toString()
    }

    fun init() {
        _taps.value = 0
        _time.value = "5"
        _remainingTime.value = 5000
        _isRunning.value = false
    }

    private fun setDialogMessage() {
        if (tapsNumber > min.value!!)
            _dialog.value = "You made it to records list with score of:"
        else
            _dialog.value = "Score:"
    }

    fun clear() {
        compositeDisposable.clear()
    }

    private fun saveGameStartTime() {
        date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Calendar.getInstance().time)
    }

    private fun prepare() = compositeDisposable.add(Flowable.interval(0, 1, TimeUnit.SECONDS, Schedulers.io())
            .take(3, TimeUnit.SECONDS)
            .map { prepareItems[if (it.toInt() == 3) it.toInt() - 1 else it.toInt()] }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                if (!wasInterruptedDuringPrepare) {
                    _isRunning.value = true
                    _play.value = "PLAY"
                    if (date.isEmpty())
                        saveGameStartTime()
                    wasInterruptedDuringPrepare = false
                }
            }
            .subscribe {
                _play.value = it
            })

}




