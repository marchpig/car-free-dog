package com.marchpig.carfreedog

import android.content.SharedPreferences
import com.marchpig.carfreedog.data.HolidayDao
import org.jetbrains.anko.*
import java.util.*

class AlarmTimer(holidayDao: HolidayDao, preference: SharedPreferences) : AnkoLogger {

    private val carNumber = preference.getInt(Constants.CAR_NUMBER, -1)
    private val dayAlarmHour = preference.getInt(
            Constants.DAY_ALARM_HOUR,
            Constants.DAY_ALARM_HOUR_DEFAULT
    )
    private val dayAlarmMinute = preference.getInt(
            Constants.DAY_ALARM_MINUTE,
            Constants.DAY_ALARM_MINUTE_DEFAULT
    )
    private val alarmHoliday = preference.getBoolean(
            Constants.ALARM_HOLIDAY,
            Constants.ALARM_HOLIDAY_DEFAULT
    )
    private val holidayChecker = HolidayChecker(holidayDao)

    fun getNextTime(calendar: Calendar): Calendar? {
        if (carNumber == -1) {
            info {"Car number has not been set yet"}
            return null
        }
        var candidate = calendar.clone() as Calendar
        do {
            candidate = getNextTimeCandidate(candidate)
        } while (isInvalidTime(candidate))

        return candidate
    }

    private fun getNextTimeCandidate(calendar: Calendar): Calendar {
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        if (dayOfMonth % 10 != carNumber || isHourAndMinuteEqualOrPast(hourOfDay, minute)) {
            val diff = carNumber - dayOfMonth % 10
            val dayToBeAdded = if (diff > 0) diff else diff + 10
            calendar.add(Calendar.DAY_OF_MONTH, dayToBeAdded)
            if (month != calendar.get(Calendar.MONTH)) {
                calendar.set(Calendar.DAY_OF_MONTH, if (carNumber == 0) 10 else carNumber)
            }
        }

        calendar.set(Calendar.HOUR_OF_DAY, dayAlarmHour)
        calendar.set(Calendar.MINUTE, dayAlarmMinute)
        calendar.set(Calendar.SECOND, 0)

        return calendar
    }

    private fun isHourAndMinuteEqualOrPast(hourOfDay: Int, minute: Int): Boolean {
        return (dayAlarmHour < hourOfDay) ||
                (dayAlarmHour == hourOfDay && dayAlarmMinute <= minute)
    }

    private fun isInvalidTime(calendar: Calendar): Boolean {
        if (calendar.get(Calendar.DAY_OF_MONTH) == 31)
            return true
        return if (alarmHoliday) false else holidayChecker.isHoliday(calendar)
    }
}