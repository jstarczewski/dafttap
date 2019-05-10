package com.clakestudio.pc.dafttapchallange.util

import android.content.Context
import com.clakestudio.pc.dafttapchallange.data.local.ScoresDatabase
import com.clakestudio.pc.dafttapchallange.data.local.TopScoresLocalDataSource

object Injection {

    fun provideTopScoresLocalDataSource(context: Context): TopScoresLocalDataSource =
        TopScoresLocalDataSource.getInstance(ScoresDatabase.getInstance(context).topScoresDao())


}