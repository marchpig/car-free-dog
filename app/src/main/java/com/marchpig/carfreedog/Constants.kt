package com.marchpig.carfreedog

object Constants {
    const val PREFS_NAME = "carfreedog"
    const val DB_NAME = "carfreedog"

    const val CAR_NUMBER = "carNumber"
    const val PRE_ALARM = "preAlarm"
    const val PRE_ALARM_HOUR = "preAlarmHour"
    const val PRE_ALARM_MINUTE = "preAlarmMinute"
    const val DAY_ALARM = "dayAlarm"
    const val DAY_ALARM_HOUR = "dayAlarmHour"
    const val DAY_ALARM_MINUTE = "dayAlarmMinute"
    const val ALARM_HOLIDAY = "alarmHoliday"
    const val ROUND_NUMBER = "roundNumber"

    const val CAR_NUMBER_DEFAULT = 0
    const val PRE_ALARM_DEFAULT = true
    const val PRE_ALARM_HOUR_DEFAULT = 22
    const val PRE_ALARM_MINUTE_DEFAULT = 0
    const val DAY_ALARM_DEFAULT = true
    const val DAY_ALARM_HOUR_DEFAULT = 7
    const val DAY_ALARM_MINUTE_DEFAULT = 0
    const val ALARM_HOLIDAY_DEFAULT = false
    const val ROUND_NUMBER_FIVE = 5
    const val ROUND_NUMBER_TEN = 10
    const val ROUND_NUMBER_DEFAULT = ROUND_NUMBER_TEN

    const val PRE_ALARM_ID = 0
    const val DAY_ALARM_ID = 1

    const val ACTION_REGISTER_ALARM = "com.marchpig.carfreedog.REGISTER_ALARM"
    const val ACTION_NOTIFY_PRE_ALARM = "com.marchpig.carfreedog.NOTIFY_PRE_ALARM"
    const val ACTION_NOTIFY_DAY_ALARM = "com.marchpig.carfreedog.NOTIFY_DAY_ALARM"
    const val ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED"

    const val DATA_GO_KR_SERVICE_KEY = "secret"
}