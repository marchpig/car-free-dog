package com.marchpig.carfreedog

import com.marchpig.carfreedog.data.HolidayDao
import org.jetbrains.anko.*
import java.util.*

class HolidayChecker(private val holidayDao: HolidayDao) : AnkoLogger {

    fun isHoliday(calendar: Calendar): Boolean {
        return if (isWeekend(calendar)) true else isNationalHoliday(calendar)
    }

    private fun isWeekend(calendar: Calendar): Boolean {
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        return dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY
    }

    private fun isNationalHoliday(calendar: Calendar): Boolean {
        val year = calendar.get(Calendar.YEAR)
        val realMonth = calendar.get(Calendar.MONTH) + 1
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val nationalHolidaysOfMonth = holidayDao.findByYearAndMonth(year, realMonth)
        if (nationalHolidaysOfMonth.isNotEmpty()) {
            // Even there is no holiday of the month, day '0' is stored.
            info {"Holidays of $year-$realMonth already stored in DB"}
            return nationalHolidaysOfMonth.contains(dayOfMonth)
        }

        info {"Get holidays of $year-$realMonth from data.go.kr and insert to db"}
        return try {
            val holidays = NationalHolidayProvider.get(year, realMonth)
            holidayDao.insertAll(holidays)
            holidays.map { it.day }.contains(dayOfMonth)
        } catch (ex: Exception) {
            error { ex }
            false
        }
    }
}