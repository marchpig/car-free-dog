package com.marchpig.carfreedog.data

import android.arch.persistence.room.*

@Database(entities = [Holiday::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun holidayDao(): HolidayDao
}