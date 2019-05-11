package com.clakestudio.pc.dafttapchallange.ui.records

import androidx.lifecycle.ViewModel;
import com.clakestudio.pc.dafttapchallange.data.local.TopScoresDataSource
import io.reactivex.disposables.CompositeDisposable

class RecordsViewModel(topScoresLocalDataSource: TopScoresDataSource) : ViewModel() {
    // TODO: Implement the ViewModel

    private val compositeDisposalbe = CompositeDisposable()

    fun init(){}


}
