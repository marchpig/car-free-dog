package com.marchpig.carfreedog

import android.app.*
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.format.DateFormat
import android.widget.TimePicker

class PreAlarmTimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    var preference : SharedPreferences? = null
    var alarmSettings : AlarmSettings? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return TimePickerDialog(
                activity,
                this,
                alarmSettings?.getPreAlarmHourOfDay() ?:
                        Constants.PRE_ALARM_HOUR_DEFAULT,
                alarmSettings?.getPreAlarmMinute() ?:
                        Constants.PRE_ALARM_MINUTE_DEFAULT,
                DateFormat.is24HourFormat(activity)
        )
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        preference?.edit().let {
            it?.putInt(Constants.PRE_ALARM_HOUR, hourOfDay)
            it?.putInt(Constants.PRE_ALARM_MINUTE, minute)
            it?.apply()
        }
    }
}