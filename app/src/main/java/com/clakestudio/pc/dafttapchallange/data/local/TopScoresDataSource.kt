package com.clakestudio.pc.dafttapchallange.data.local

import com.clakestudio.pc.dafttapchallange.data.Score
import io.reactivex.Flowable

interface TopScoresDataSource {

    fun saveScore(score: Score)

    fun getTopScores() : Flowable<List<Score>>

}