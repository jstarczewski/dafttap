package com.clakestudio.pc.dafttapchallange.ui.records

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.clakestudio.pc.dafttapchallange.data.Score
import com.clakestudio.pc.dafttapchallange.data.local.TopScoresDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RecordsViewModel(private val topScoresLocalDataSource: TopScoresDataSource) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _scores = MutableLiveData<List<Score>>()
    private val _min = MutableLiveData<Int>()
    val min = _min
    val scores: LiveData<List<Score>> = _scores

    fun init() = compositeDisposable.add(topScoresLocalDataSource
        .getTopScores()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe {
            if (!it.isNullOrEmpty()) {
                if (it.size >= 5) {
                    _scores.value = it.sortedBy { it.score }.reversed().subList(0, 5)
                    _min.value = it.sortedBy { it.score }.reversed()[4].score
                } else {
                    _scores.value = it.sortedBy { it.score }.reversed()
                    _min.value = 0
                }
            }

        })

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
