package com.marchpig.carfreedog

import android.content.SharedPreferences
import com.marchpig.carfreedog.data.HolidayDao
import org.junit.*
import org.junit.Assert.*
import org.mockito.Mockito.*
import java.util.Calendar

class AlarmTimerTest {
    private val prefNotSet = mock(SharedPreferences::class.java)
    private val pref_0_9_15_F = mock(SharedPreferences::class.java)
    private val pref_1_13_0_F = mock(SharedPreferences::class.java)
    private val pref_4_7_0_F = mock(SharedPreferences::class.java)
    private val pref_4_8_30_F = mock(SharedPreferences::class.java)
    private val pref_0_9_15_T = mock(SharedPreferences::class.java)
    private val pref_1_13_0_T = mock(SharedPreferences::class.java)
    private val pref_4_7_0_T = mock(SharedPreferences::class.java)
    private val pref_4_8_30_T = mock(SharedPreferences::class.java)
    private val holidayDao = mock(HolidayDao::class.java)

    @Before
    fun before() {
        `when`(prefNotSet.getInt(eq(Constants.CAR_NUMBER), anyInt()))
                .thenReturn(-1)

        `when`(pref_0_9_15_F.getInt(eq(Constants.CAR_NUMBER),anyInt()))
                .thenReturn(0)
        `when`(pref_0_9_15_F.getInt(eq(Constants.DAY_ALARM_HOUR), anyInt()))
                .thenReturn(9)
        `when`(pref_0_9_15_F.getInt(eq(Constants.DAY_ALARM_MINUTE), anyInt()))
                .thenReturn(15)
        `when`(pref_0_9_15_F.getBoolean(eq(Constants.ALARM_HOLIDAY), anyBoolean()))
                .thenReturn(false)

        `when`(pref_1_13_0_F.getInt(eq(Constants.CAR_NUMBER),anyInt()))
                .thenReturn(1)
        `when`(pref_1_13_0_F.getInt(eq(Constants.DAY_ALARM_HOUR), anyInt()))
                .thenReturn(13)
        `when`(pref_1_13_0_F.getInt(eq(Constants.DAY_ALARM_MINUTE), anyInt()))
                .thenReturn(0)
        `when`(pref_1_13_0_F.getBoolean(eq(Constants.ALARM_HOLIDAY), anyBoolean()))
                .thenReturn(false)

        `when`(pref_4_7_0_F.getInt(eq(Constants.CAR_NUMBER),anyInt()))
                .thenReturn(4)
        `when`(pref_4_7_0_F.getInt(eq(Constants.DAY_ALARM_HOUR), anyInt()))
                .thenReturn(7)
        `when`(pref_4_7_0_F.getInt(eq(Constants.DAY_ALARM_MINUTE), anyInt()))
                .thenReturn(0)
        `when`(pref_4_7_0_F.getBoolean(eq(Constants.ALARM_HOLIDAY), anyBoolean()))
                .thenReturn(false)

        `when`(pref_4_8_30_F.getInt(eq(Constants.CAR_NUMBER),anyInt()))
                .thenReturn(4)
        `when`(pref_4_8_30_F.getInt(eq(Constants.DAY_ALARM_HOUR), anyInt()))
                .thenReturn(8)
        `when`(pref_4_8_30_F.getInt(eq(Constants.DAY_ALARM_MINUTE), anyInt()))
                .thenReturn(30)
        `when`(pref_4_8_30_F.getBoolean(eq(Constants.ALARM_HOLIDAY), anyBoolean()))
                .thenReturn(false)

        `when`(pref_0_9_15_T.getInt(eq(Constants.CAR_NUMBER),anyInt()))
                .thenReturn(0)
        `when`(pref_0_9_15_T.getInt(eq(Constants.DAY_ALARM_HOUR), anyInt()))
                .thenReturn(9)
        `when`(pref_0_9_15_T.getInt(eq(Constants.DAY_ALARM_MINUTE), anyInt()))
                .thenReturn(15)
        `when`(pref_0_9_15_T.getBoolean(eq(Constants.ALARM_HOLIDAY), anyBoolean()))
                .thenReturn(true)

        `when`(pref_1_13_0_T.getInt(eq(Constants.CAR_NUMBER),anyInt()))
                .thenReturn(1)
        `when`(pref_1_13_0_T.getInt(eq(Constants.DAY_ALARM_HOUR), anyInt()))
                .thenReturn(13)
        `when`(pref_1_13_0_T.getInt(eq(Constants.DAY_ALARM_MINUTE), anyInt()))
                .thenReturn(0)
        `when`(pref_1_13_0_T.getBoolean(eq(Constants.ALARM_HOLIDAY), anyBoolean()))
                .thenReturn(true)

        `when`(pref_4_7_0_T.getInt(eq(Constants.CAR_NUMBER),anyInt()))
                .thenReturn(4)
        `when`(pref_4_7_0_T.getInt(eq(Constants.DAY_ALARM_HOUR), anyInt()))
                .thenReturn(7)
        `when`(pref_4_7_0_T.getInt(eq(Constants.DAY_ALARM_MINUTE), anyInt()))
                .thenReturn(0)
        `when`(pref_4_7_0_T.getBoolean(eq(Constants.ALARM_HOLIDAY), anyBoolean()))
                .thenReturn(true)

        `when`(pref_4_8_30_T.getInt(eq(Constants.CAR_NUMBER),anyInt()))
                .thenReturn(4)
        `when`(pref_4_8_30_T.getInt(eq(Constants.DAY_ALARM_HOUR), anyInt()))
                .thenReturn(8)
        `when`(pref_4_8_30_T.getInt(eq(Constants.DAY_ALARM_MINUTE), anyInt()))
                .thenReturn(30)
        `when`(pref_4_8_30_T.getBoolean(eq(Constants.ALARM_HOLIDAY), anyBoolean()))
                .thenReturn(true)
    }

    @Test
    fun getNextTime_WhenCarNumberIsNotSet_ReturnsNull() {
        assertNull(AlarmTimer(holidayDao, prefNotSet).getNextTime(Calendar.getInstance()))
    }

    @Test
    fun getNextTime_WhenCarNumberIs0AndAlarmHolidayOff() {
        val calendar = Calendar.getInstance()
        calendar.set(2017,11,30, 8, 30) // 2017 Dec 30th, 08:30

        val nextTime1 = AlarmTimer(holidayDao, pref_0_9_15_F).getNextTime(calendar)
        assertEquals("2018_0_10_9_15_0", nextTime1!!.toStringForAssert())

        val nextTime2 = AlarmTimer(holidayDao, pref_0_9_15_F).getNextTime(nextTime1)
        assertEquals("2018_0_30_9_15_0", nextTime2!!.toStringForAssert())

        val nextTime3 = AlarmTimer(holidayDao, pref_0_9_15_F).getNextTime(nextTime2)
        assertEquals("2018_1_20_9_15_0", nextTime3!!.toStringForAssert())

        val nextTime4 = AlarmTimer(holidayDao, pref_0_9_15_F).getNextTime(nextTime3)
        assertEquals("2018_2_20_9_15_0", nextTime4!!.toStringForAssert())
    }

    @Test
    fun getNextTime_WhenCarNumberIs1AndAlarmHolidayOff() {
        val calendar = Calendar.getInstance()
        calendar.set(2017,11,30, 17, 30) // 2017 Dec 30th, 17:30

        val nextTime1 = AlarmTimer(holidayDao, pref_1_13_0_F).getNextTime(calendar)
        assertEquals("2018_0_1_13_0_0", nextTime1!!.toStringForAssert())

        val nextTime2 = AlarmTimer(holidayDao, pref_1_13_0_F).getNextTime(nextTime1)
        assertEquals("2018_0_11_13_0_0", nextTime2!!.toStringForAssert())

        val nextTime3 = AlarmTimer(holidayDao, pref_1_13_0_F).getNextTime(nextTime2)
        assertEquals("2018_1_1_13_0_0", nextTime3!!.toStringForAssert())

        val nextTime4 = AlarmTimer(holidayDao, pref_1_13_0_F).getNextTime(nextTime3)
        assertEquals("2018_1_21_13_0_0", nextTime4!!.toStringForAssert())
    }

    @Test
    fun getNextTime_WhenCarNumberIs4AndAlarmHolidayOff() {
        val calendar = Calendar.getInstance()
        calendar.set(2017,11,30, 17, 30) // 2017 Dec 30th, 17:30

        val nextTime1 = AlarmTimer(holidayDao, pref_4_7_0_F).getNextTime(calendar)
        assertEquals("2018_0_4_7_0_0", nextTime1!!.toStringForAssert())

        val nextTime2 = AlarmTimer(holidayDao, pref_4_7_0_F).getNextTime(nextTime1)
        assertEquals("2018_0_24_7_0_0", nextTime2!!.toStringForAssert())

        val nextTime3 = AlarmTimer(holidayDao, pref_4_8_30_F).getNextTime(nextTime2)
        assertEquals("2018_0_24_8_30_0", nextTime3!!.toStringForAssert())

        val nextTime4 = AlarmTimer(holidayDao, pref_4_7_0_F).getNextTime(nextTime3)
        assertEquals("2018_1_14_7_0_0", nextTime4!!.toStringForAssert())
    }

    @Test
    fun getNextTime_WhenCarNumberIs0AndAlarmHolidayOn() {
        val calendar = Calendar.getInstance()
        calendar.set(2017,11,30, 8, 30) // 2017 Dec 30th, 08:30

        val nextTime1 = AlarmTimer(holidayDao, pref_0_9_15_T).getNextTime(calendar)
        assertEquals("2017_11_30_9_15_0", nextTime1!!.toStringForAssert())

        val nextTime2 = AlarmTimer(holidayDao, pref_0_9_15_T).getNextTime(nextTime1)
        assertEquals("2018_0_10_9_15_0", nextTime2!!.toStringForAssert())

        val nextTime3 = AlarmTimer(holidayDao, pref_0_9_15_T).getNextTime(nextTime2)
        assertEquals("2018_0_20_9_15_0", nextTime3!!.toStringForAssert())

        val nextTime4 = AlarmTimer(holidayDao, pref_0_9_15_T).getNextTime(nextTime3)
        assertEquals("2018_0_30_9_15_0", nextTime4!!.toStringForAssert())
    }

    @Test
    fun getNextTime_WhenCarNumberIs1AndAlarmHolidayOn() {
        val calendar = Calendar.getInstance()
        calendar.set(2017,11,30, 17, 30) // 2017 Dec 30th, 17:30

        val nextTime1 = AlarmTimer(holidayDao, pref_1_13_0_T).getNextTime(calendar)
        assertEquals("2018_0_1_13_0_0", nextTime1!!.toStringForAssert())

        val nextTime2 = AlarmTimer(holidayDao, pref_1_13_0_T).getNextTime(nextTime1)
        assertEquals("2018_0_11_13_0_0", nextTime2!!.toStringForAssert())

        val nextTime3 = AlarmTimer(holidayDao, pref_1_13_0_T).getNextTime(nextTime2)
        assertEquals("2018_0_21_13_0_0", nextTime3!!.toStringForAssert())

        val nextTime4 = AlarmTimer(holidayDao, pref_1_13_0_T).getNextTime(nextTime3)
        assertEquals("2018_1_1_13_0_0", nextTime4!!.toStringForAssert())
    }

    @Test
    fun getNextTime_WhenCarNumberIs4AndAlarmHolidayOn() {
        val calendar = Calendar.getInstance()
        calendar.set(2017,11,30, 17, 30) // 2017 Dec 30th, 17:30

        val nextTime1 = AlarmTimer(holidayDao, pref_4_7_0_T).getNextTime(calendar)
        assertEquals("2018_0_4_7_0_0", nextTime1!!.toStringForAssert())

        val nextTime2 = AlarmTimer(holidayDao, pref_4_7_0_T).getNextTime(nextTime1)
        assertEquals("2018_0_14_7_0_0", nextTime2!!.toStringForAssert())

        val nextTime3 = AlarmTimer(holidayDao, pref_4_8_30_T).getNextTime(nextTime2)
        assertEquals("2018_0_14_8_30_0", nextTime3!!.toStringForAssert())

        val nextTime4 = AlarmTimer(holidayDao, pref_4_7_0_T).getNextTime(nextTime3)
        assertEquals("2018_0_24_7_0_0", nextTime4!!.toStringForAssert())
    }

    @Test
    fun getNextTime_WhenCombinationOfCarNumbersAndAlarmHoliday() {
        val calendar = Calendar.getInstance()
        calendar.set(2018,2,8, 6, 0) // 2018 Mar 8th, 06:00

        val nextTime1 = AlarmTimer(holidayDao, pref_4_7_0_T).getNextTime(calendar)
        assertEquals("2018_2_14_7_0_0", nextTime1!!.toStringForAssert())

        val nextTime2 = AlarmTimer(holidayDao, pref_4_8_30_F).getNextTime(nextTime1)
        assertEquals("2018_2_14_8_30_0", nextTime2!!.toStringForAssert())

        val nextTime3 = AlarmTimer(holidayDao, pref_0_9_15_T).getNextTime(nextTime2)
        assertEquals("2018_2_20_9_15_0", nextTime3!!.toStringForAssert())

        val nextTime4 = AlarmTimer(holidayDao, pref_1_13_0_F).getNextTime(nextTime3)
        assertEquals("2018_2_21_13_0_0", nextTime4!!.toStringForAssert())

        val nextTime5 = AlarmTimer(holidayDao, pref_4_7_0_T).getNextTime(nextTime4)
        assertEquals("2018_2_24_7_0_0", nextTime5!!.toStringForAssert())
    }

    private fun Calendar.toStringForAssert(): String {
        return "${get(Calendar.YEAR)}" +
                "_${get(Calendar.MONTH)}" +
                "_${get(Calendar.DAY_OF_MONTH)}" +
                "_${get(Calendar.HOUR_OF_DAY)}" +
                "_${get(Calendar.MINUTE)}" +
                "_${get(Calendar.SECOND)}"
    }
}
