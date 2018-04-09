package com.marchpig.carfreedog

import android.app.*
import android.arch.persistence.room.Room
import android.content.*
import android.graphics.Color
import android.media.RingtoneManager
import android.support.v4.app.*
import android.support.v7.app.AppCompatActivity
import com.marchpig.carfreedog.data.AppDatabase
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.*
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver(), AnkoLogger {

    override fun onReceive(context: Context, intent: Intent) { launch {
        val preference = context.getSharedPreferences(Constants.PREFS_NAME,
                AppCompatActivity.MODE_PRIVATE)
        val db = Room
                .databaseBuilder(context, AppDatabase::class.java, Constants.DB_NAME)
                .build()
        val dayAlarmTime = AlarmTimer(HolidayChecker(db.holidayDao()), preference)
                .getNextTime(Calendar.getInstance())?: return@launch
        val preAlarmTime = createPreAlarmTime(preference, dayAlarmTime.clone() as Calendar)
        when (intent.action) {
            Constants.ACTION_REGISTER_ALARM,
            Constants.ACTION_BOOT_COMPLETED -> {
                registerAlarm(context, preAlarmTime, dayAlarmTime)
            }
            Constants.ACTION_NOTIFY_PRE_ALARM -> {
                if (preference.getBoolean(Constants.PRE_ALARM, Constants.PRE_ALARM_DEFAULT)) {
                    val roundNumber = preference.getInt(
                            Constants.ROUND_NUMBER,
                            Constants.ROUND_NUMBER_DEFAULT
                    )
                    notifyPreAlarm(context, roundNumber)
                }
            }
            Constants.ACTION_NOTIFY_DAY_ALARM -> {
                if (preference.getBoolean(Constants.DAY_ALARM, Constants.DAY_ALARM_DEFAULT)) {
                    val roundNumber = preference.getInt(
                            Constants.ROUND_NUMBER,
                            Constants.ROUND_NUMBER_DEFAULT
                    )
                    notifyDayAlarm(context, roundNumber, dayAlarmTime)
                }
                registerAlarm(context, preAlarmTime, dayAlarmTime)
            }
        }
        db.close()
    }}

    private fun createPreAlarmTime(preference: SharedPreferences,
                                   dayAlarmTime: Calendar): Calendar {
        dayAlarmTime.add(Calendar.DAY_OF_MONTH, -1)
        dayAlarmTime.set(
                Calendar.HOUR_OF_DAY,
                preference.getInt(Constants.PRE_ALARM_HOUR, Constants.PRE_ALARM_HOUR_DEFAULT)
        )
        dayAlarmTime.set(
                Calendar.MINUTE,
                preference.getInt(Constants.PRE_ALARM_MINUTE, Constants.PRE_ALARM_MINUTE_DEFAULT)
        )
        return dayAlarmTime
    }

    private fun notifyPreAlarm(context: Context, roundNumber: Int) {
        val mBuilder = NotificationCompat.Builder(context)
                .setContentTitle("내일 ${roundNumber}부제")
                .setContentText("내일은 ${roundNumber}부제 날입니다!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setVibrate(longArrayOf(0, 500, 500, 500, 500))
                .setLights(Color.BLUE, 3000, 3000)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))

        NotificationManagerCompat.from(context).notify(Constants.PRE_ALARM_ID, mBuilder.build())
    }

    private fun notifyDayAlarm(context: Context, roundNumber: Int, nextDayAlarmTime: Calendar) {
        val simpleDateFormat = SimpleDateFormat("MMM d일 EEE요일", Locale.KOREA)
        val mBuilder = NotificationCompat.Builder(context)
                .setContentTitle("오늘 ${roundNumber}부제")
                .setContentText("오늘은 ${roundNumber}부제 날입니다! (다음 ${roundNumber}부제: " +
                        "${simpleDateFormat.format(nextDayAlarmTime.time)})")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setVibrate(longArrayOf(0, 500, 500, 500, 500))
                .setLights(Color.BLUE, 3000, 3000)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))

        NotificationManagerCompat.from(context).notify(Constants.DAY_ALARM_ID, mBuilder.build())
    }

    private fun registerAlarm(context: Context, preAlarmTime: Calendar, dayAlarmTime: Calendar) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (preAlarmTime > Calendar.getInstance()) {
            PendingIntent.getBroadcast(
                    context,
                    Constants.PRE_ALARM_ID,
                    Intent(Constants.ACTION_NOTIFY_PRE_ALARM),
                    PendingIntent.FLAG_UPDATE_CURRENT
            ).let {
                AlarmManagerCompat.setExact(
                        alarmManager,
                        AlarmManager.RTC_WAKEUP,
                        preAlarmTime.timeInMillis,
                        it
                )
                info { "Pre alarm registered at ${preAlarmTime.time}" }
            }
        }

        PendingIntent.getBroadcast(
                context,
                Constants.DAY_ALARM_ID,
                Intent(Constants.ACTION_NOTIFY_DAY_ALARM),
                PendingIntent.FLAG_UPDATE_CURRENT
        ).let {
            AlarmManagerCompat.setExact(
                    alarmManager,
                    AlarmManager.RTC_WAKEUP,
                    dayAlarmTime.timeInMillis,
                    it
            )
            info {"Day alarm registered at ${dayAlarmTime.time}"}
        }
    }


}
