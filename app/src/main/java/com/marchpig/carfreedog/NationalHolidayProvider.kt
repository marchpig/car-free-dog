package com.marchpig.carfreedog

import com.marchpig.carfreedog.data.Holiday
import org.w3c.dom.NodeList
import javax.xml.parsers.DocumentBuilderFactory

object NationalHolidayProvider {

    fun get(year: Int, realMonth: Int): List<Holiday> {
        val nationalHolidays = arrayListOf(Holiday(year, realMonth, 0))
        val document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(getUri(year, realMonth))
                .apply { documentElement.normalize() }
        val holidayNodes: NodeList = document.getElementsByTagName("locdate")
        for (i in 0 until holidayNodes.length) {
            val date = holidayNodes.item(i).textContent
            nationalHolidays.add(Holiday(
                    date.substring(0, 4).toInt(),
                    date.substring(4, 6).toInt(),
                    date.substring(6, 8).toInt()
            ))
        }
        return nationalHolidays
    }

    private fun getUri(year: Int, realMonth: Int) =
            "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo?" +
                    "ServiceKey=${Constants.DATA_GO_KR_SERVICE_KEY}&" +
                    "solYear=$year&" +
                    "solMonth=${realMonth.toString().padStart(2, '0')}"
}