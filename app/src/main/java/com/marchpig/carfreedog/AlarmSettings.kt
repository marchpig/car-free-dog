package com.marchpig.carfreedog

import android.content.SharedPreferences

class AlarmSettings(private val preference: SharedPreferences,
                    private val alarmSettingsWidgets: AlarmSettingsWidgets,
                    private val carNumbers: Array<String>) {

    init {
        with(alarmSettingsWidgets) {
            carNumbers.indexOf(preference.getInt(
                    Constants.CAR_NUMBER,
                    Constants.CAR_NUMBER_DEFAULT
            ).toString()).let { carNumberSpinner.setSelection(it) }
            preAlarmSwitch.isChecked = preference.getBoolean(
                    Constants.PRE_ALARM,
                    Constants.PRE_ALARM_DEFAULT
            )
            dayAlarmSwitch.isChecked = preference.getBoolean(
                    Constants.DAY_ALARM,
                    Constants.DAY_ALARM_DEFAULT
            )
            alarmHolidaySwitch.isChecked = preference.getBoolean(
                    Constants.ALARM_HOLIDAY,
                    Constants.ALARM_HOLIDAY_DEFAULT
            )
        }
    }

    fun updatePreAlarmButtonText() {
        val text = getZeroPaddedString(getPreAlarmHourOfDay()) +
                ":" + getZeroPaddedString(getPreAlarmMinute())
        alarmSettingsWidgets.preAlarmButton.text = text
    }

    fun updateDayAlarmButtonText() {
        val text = getZeroPaddedString(getDayAlarmHourOfDay()) +
                ":" + getZeroPaddedString(getDayAlarmMinute())
        alarmSettingsWidgets.dayAlarmButton.text = text
    }

    fun getPreAlarmHourOfDay() =
            preference.getInt(Constants.PRE_ALARM_HOUR, Constants.PRE_ALARM_HOUR_DEFAULT)

    fun getPreAlarmMinute() =
            preference.getInt(Constants.PRE_ALARM_MINUTE, Constants.PRE_ALARM_MINUTE_DEFAULT)

    fun getDayAlarmHourOfDay() =
            preference.getInt(Constants.DAY_ALARM_HOUR, Constants.DAY_ALARM_HOUR_DEFAULT)

    fun getDayAlarmMinute() =
            preference.getInt(Constants.DAY_ALARM_MINUTE, Constants.DAY_ALARM_MINUTE_DEFAULT)

    private fun getZeroPaddedString(digit: Int) =
            digit.toString().padStart(2, '0')

    fun save() {
        with(alarmSettingsWidgets) {
            preference.edit().let {
                it.putInt(Constants.CAR_NUMBER, carNumberSpinner.selectedItemPosition)
                it.putBoolean(Constants.PRE_ALARM, preAlarmSwitch.isChecked)
                it.putBoolean(Constants.DAY_ALARM, dayAlarmSwitch.isChecked)
                it.putBoolean(Constants.ALARM_HOLIDAY, alarmHolidaySwitch.isChecked)
                it.apply()
            }
        }
    }
}