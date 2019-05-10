package com.clakestudio.pc.dafttapchallange.ui.data

interface TopScoresDataSource {

    fun saveScore(record: String)

    fun getTopScores() : List<String>

}