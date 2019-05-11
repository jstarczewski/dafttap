package com.clakestudio.pc.dafttapchallange.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class DbScore(
    @PrimaryKey
    val score: Int,
    val time: String
)