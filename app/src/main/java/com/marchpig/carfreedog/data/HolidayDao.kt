package com.marchpig.carfreedog.data

import android.arch.persistence.room.*

@Dao
interface HolidayDao {

    @Insert
    fun insertAll(holidays: List<Holiday>)

    @Query("SELECT day FROM holiday WHERE year = :year and month = :realMonth")
    fun findByYearAndMonth(year: Int, realMonth: Int): List<Int>
}