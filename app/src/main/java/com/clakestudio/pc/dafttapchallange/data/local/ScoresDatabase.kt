package com.clakestudio.pc.dafttapchallange.data.local

import androidx.room.Database

@Database(version = 1, exportSchema = false, entities = [DbScore::class])
abstract class ScoresDatabase {
}