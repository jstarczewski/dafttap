package com.clakestudio.pc.dafttapchallange.data.local

interface TopScoresDataSource {

    fun saveScore(record: String)

    fun getTopScores() : List<String>

}