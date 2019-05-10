package com.clakestudio.pc.dafttapchallange.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.clakestudio.pc.dafttapchallange.data.Score
import io.reactivex.Single

@Dao
interface TopScoresDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveScore(score: DbScore)

    @Query("SELECT * FROM DbScore")
    fun getTopScores(): Single<List<Score>>

}