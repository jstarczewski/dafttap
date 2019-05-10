package com.clakestudio.pc.dafttapchallange.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, exportSchema = false, entities = [DbScore::class])
abstract class ScoresDatabase : RoomDatabase() {

    abstract fun topScoresDao(): TopScoresDao

    companion object {

        private var INSTANCE: ScoresDatabase? = null
        private val lock = Any()


        fun getInstance(context: Context): ScoresDatabase {
            synchronized(lock) {
                if (INSTANCE == null)
                    INSTANCE = Room.databaseBuilder(context.applicationContext, ScoresDatabase::class.java, "scores.db")
                        .build()
            }
            return INSTANCE!!
        }

    }

}