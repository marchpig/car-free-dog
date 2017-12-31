package com.marchpig.carfreedog

import android.widget.Spinner
import android.widget.Switch

data class AlarmSettingsWidgets(
        val carNumberSpinner: Spinner,
        val preAlarmSwitch: Switch,
        val dayAlarmSwitch: Switch,
        val alarmHolidaySwitch: Switch
)