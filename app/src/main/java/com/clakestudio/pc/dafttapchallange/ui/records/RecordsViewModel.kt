package com.clakestudio.pc.dafttapchallange.ui.records

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.clakestudio.pc.dafttapchallange.data.Score
import com.clakestudio.pc.dafttapchallange.data.local.TopScoresDataSource
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RecordsViewModel(private val topScoresLocalDataSource: TopScoresDataSource) : ViewModel() {
    // TODO: Implement the ViewModel

    private val compositeDisposalbe = CompositeDisposable()
    private val _scores = MutableLiveData<List<Score>>()
    val scores: LiveData<List<Score>> = _scores

    fun init() = topScoresLocalDataSource
        .getTopScores()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe {
            if (!it.isNullOrEmpty()) {
                it.sortedBy { it.score }
            }
            if (it.size > 5)
                _scores.value = it.reversed().subList(0,5)
            else
                _scores.value = it.reversed()
        }


}
