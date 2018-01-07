package com.marchpig.carfreedog.data

import android.arch.persistence.room.*

@Entity(primaryKeys = ["year", "month", "day"])
data class Holiday(
        val year: Int,
        val month: Int,
        val day: Int
)