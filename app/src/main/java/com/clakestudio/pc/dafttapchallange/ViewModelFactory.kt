package com.clakestudio.pc.dafttapchallange

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.clakestudio.pc.dafttapchallange.data.local.TopScoresDataSource
import com.clakestudio.pc.dafttapchallange.ui.game.GameViewModel
import com.clakestudio.pc.dafttapchallange.ui.records.RecordsFragment
import com.clakestudio.pc.dafttapchallange.ui.records.RecordsViewModel
import com.clakestudio.pc.dafttapchallange.util.Injection

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(
    private val topScoresDataSource: TopScoresDataSource
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(GameViewModel::class.java) ->
                    GameViewModel(topScoresDataSource)
                isAssignableFrom(RecordsViewModel::class.java) ->
                    RecordsViewModel(topScoresDataSource)

                else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T


    companion object {

        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) = INSTANCE
            ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE
                    ?: ViewModelFactory(Injection.provideTopScoresLocalDataSource(application))

            }

    }
}