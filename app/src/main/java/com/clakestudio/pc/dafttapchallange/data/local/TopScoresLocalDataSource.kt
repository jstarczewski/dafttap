package com.clakestudio.pc.dafttapchallange.data.local

import com.clakestudio.pc.dafttapchallange.data.Score
import io.reactivex.Flowable

class TopScoresLocalDataSource(private val scoresDao: TopScoresDao) : TopScoresDataSource {

    override fun saveScore(score: Score) {
        scoresDao.saveScore(DbScore(score.score, score.time))
    }


    override fun getTopScores(): Flowable<List<Score>> = scoresDao.getTopScores()

    companion object {

        private var INSTANCE: TopScoresLocalDataSource? = null

        fun getInstance(scoresDao: TopScoresDao): TopScoresLocalDataSource {
            if (INSTANCE == null)
                synchronized(TopScoresLocalDataSource::class.java) {
                    INSTANCE = TopScoresLocalDataSource(scoresDao)
                }
            return INSTANCE!!
        }

    }
}