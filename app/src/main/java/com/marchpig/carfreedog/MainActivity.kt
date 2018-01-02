package com.marchpig.carfreedog

import android.content.*
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : AppCompatActivity() {

    private var alarmSettings: AlarmSettings? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeCarNumberSpinner()
        val preference = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE)
        val alarmSettingsWidgets = AlarmSettingsWidgets(
                carNumberSpinner,
                preAlarmSwitch,
                dayAlarmSwitch,
                alarmHolidaySwitch,
                preAlarmButton,
                dayAlarmButton
        )
        val carNumbers = resources.getStringArray(R.array.car_number_array)
        alarmSettings = AlarmSettings(preference, alarmSettingsWidgets, carNumbers)
        setTimePickerFragmentToButtons(preference, alarmSettings)
        setButtonTextChangeListener(preference, alarmSettings)
        setGithubLink()
    }

    override fun onResume() {
        super.onResume()
        alarmSettings?.updatePreAlarmButtonText()
        alarmSettings?.updateDayAlarmButtonText()
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

    private fun setTimePickerFragmentToButtons(preference: SharedPreferences,
                                               alarmSettings: AlarmSettings?) {
        preAlarmButton.onClick {
            PreAlarmTimePickerFragment().let {
                it.preference = preference
                it.alarmSettings = alarmSettings
                it.show(supportFragmentManager, Constants.PRE_ALARM)
            }
        }
        dayAlarmButton.onClick {
            DayAlarmTimePickerFragment().let {
                it.preference = preference
                it.alarmSettings = alarmSettings
                it.show(supportFragmentManager, Constants.DAY_ALARM)
            }
        }
    }

    private fun setButtonTextChangeListener(preference: SharedPreferences,
                                            alarmSettings: AlarmSettings?) {
        preference.registerOnSharedPreferenceChangeListener { _, key ->
            when (key) {
                Constants.PRE_ALARM_HOUR, Constants.PRE_ALARM_MINUTE ->
                    alarmSettings?.updatePreAlarmButtonText()
                Constants.DAY_ALARM_HOUR, Constants.DAY_ALARM_MINUTE ->
                    alarmSettings?.updateDayAlarmButtonText()
            }
        }
    }

    private fun setGithubLink() {
        githubLinkTextView.onClick {
            startActivity(Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(resources.getString(R.string.github_url))
            ))
        }
    }
}
