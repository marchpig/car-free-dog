package com.marchpig.carfreedog

import com.marchpig.carfreedog.data.*
import io.mockk.*
import org.junit.*
import org.junit.Assert.*
import org.mockito.Mockito.*
import java.util.*

class HolidayCheckerTest {

    private val holidayDao = mock(HolidayDao::class.java)

    @Test
    fun isHoliday_WhenWeekendIsPassed() {
        `when`(holidayDao.findByYearAndMonth(2018, 1))
                .thenReturn(arrayListOf(0, 1))
        Calendar.getInstance().apply { set(2018, 0, 6) }.let {
            assertTrue(HolidayChecker(holidayDao).isHoliday(it))
        }
        Calendar.getInstance().apply { set(2018, 0, 7) }.let {
            assertTrue(HolidayChecker(holidayDao).isHoliday(it))
        }
        verify(holidayDao, never()).findByYearAndMonth(anyInt(), anyInt())
        verify(holidayDao, never()).insertAll(anyList())
    }

    @Test
    fun isHoliday_WhenNationalHolidayIsPassedAndAlreadyInDB() {
        `when`(holidayDao.findByYearAndMonth(2018, 1))
                .thenReturn(arrayListOf(0, 1))
        Calendar.getInstance().apply { set(2018, 0, 1) }.let {
            assertTrue(HolidayChecker(holidayDao).isHoliday(it))
        }
        verify(holidayDao, times(1)).findByYearAndMonth(anyInt(), anyInt())
        verify(holidayDao, never()).insertAll(anyList())
    }

    @Test
    fun isHoliday_WhenNationalHolidayIsPassedAndDataOfTheMonthNotInDB() {
        objectMockk(NationalHolidayProvider).use {
            every { NationalHolidayProvider.get(2018, 1) } returns arrayListOf(
                    Holiday(2018, 1, 0),
                    Holiday(2018, 1, 1)
            )
            Calendar.getInstance().apply { set(2018, 0, 1) }.let {
                assertTrue(HolidayChecker(holidayDao).isHoliday(it))
            }
        }
        verify(holidayDao, times(1)).findByYearAndMonth(anyInt(), anyInt())
        verify(holidayDao, times(1)).insertAll(anyList())
    }

    @Test
    fun isHoliday_WhenWeekdayIsPassedAndDataOfTheMonthNotInDB() {
        objectMockk(NationalHolidayProvider).use {
            every { NationalHolidayProvider.get(2018, 1) } returns arrayListOf(
                    Holiday(2018, 1, 0),
                    Holiday(2018, 1, 1)
            )
            Calendar.getInstance().apply { set(2018, 0, 2) }.let {
                assertFalse(HolidayChecker(holidayDao).isHoliday(it))
            }
        }
        verify(holidayDao, times(1)).findByYearAndMonth(anyInt(), anyInt())
        verify(holidayDao, times(1)).insertAll(anyList())
    }

    @Test
    fun isHoliday_WhenWeekdayIsPassedAndDataOfTheMonthAlreadyInDB() {
        `when`(holidayDao.findByYearAndMonth(2018, 1))
                .thenReturn(arrayListOf(0, 1))
        Calendar.getInstance().apply { set(2018, 0, 2) }.let {
            assertFalse(HolidayChecker(holidayDao).isHoliday(it))
        }
        verify(holidayDao, times(1)).findByYearAndMonth(anyInt(), anyInt())
        verify(holidayDao, never()).insertAll(anyList())
    }
}