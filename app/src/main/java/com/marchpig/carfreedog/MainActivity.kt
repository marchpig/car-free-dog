package com.marchpig.carfreedog

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var alarmSettings: AlarmSettings? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeCarNumberSpinner()
        val preference = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE)
        val alarmSettingsWidgets = AlarmSettingsWidgets(carNumberSpinner,
                preAlarmSwitch, dayAlarmSwitch, alarmHolidaySwitch)
        val carNumbers = resources.getStringArray(R.array.car_number_array)
        alarmSettings = AlarmSettings(preference, alarmSettingsWidgets, carNumbers)
    }

    override fun onPause() {
        super.onPause()
        alarmSettings?.save()
        sendBroadcast(Intent(Constants.ACTION_REGISTER_ALARM))
    }

    private fun initializeCarNumberSpinner() {
        carNumberSpinner.adapter = ArrayAdapter.createFromResource(applicationContext,
                R.array.car_number_array, android.R.layout.simple_spinner_item).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }
}
