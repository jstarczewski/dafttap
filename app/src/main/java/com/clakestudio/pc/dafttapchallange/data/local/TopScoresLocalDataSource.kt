package com.clakestudio.pc.dafttapchallange.data.local

class TopScoresLocalDataSource(scoresDao: TopScoresDao) : TopScoresDataSource {

    override fun saveScore(record: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTopScores(): List<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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