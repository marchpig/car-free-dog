package com.marchpig.carfreedog

import android.widget.*

data class AlarmSettingsWidgets(
        val carNumberSpinner: Spinner,
        val preAlarmSwitch: Switch,
        val dayAlarmSwitch: Switch,
        val alarmHolidaySwitch: Switch,
        val preAlarmButton: Button,
        val dayAlarmButton: Button,
        val roundFiveRadioButton: RadioButton,
        val roundTenRadioButton: RadioButton
)