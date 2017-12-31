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