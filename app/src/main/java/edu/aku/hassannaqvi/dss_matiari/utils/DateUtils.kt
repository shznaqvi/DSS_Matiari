package edu.aku.hassannaqvi.dss_matiari.utils

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

fun monthsBetweenDates(startDate: Date, endDate: Date): Int {
    val start = Calendar.getInstance()
    start.time = startDate
    val end = Calendar.getInstance()
    end.time = endDate
    var monthsBetween = 0
    var dateDiff = end[Calendar.DAY_OF_MONTH] - start[Calendar.DAY_OF_MONTH]
    if (dateDiff < 0) {
        val borrrow = end.getActualMaximum(Calendar.DAY_OF_MONTH)
        dateDiff = end[Calendar.DAY_OF_MONTH] + borrrow - start[Calendar.DAY_OF_MONTH]
        monthsBetween--
        if (dateDiff > 0) {
            monthsBetween++
        }
    }
    monthsBetween += end[Calendar.MONTH] - start[Calendar.MONTH]
    monthsBetween += (end[Calendar.YEAR] - start[Calendar.YEAR]) * 12
    return monthsBetween
}

fun ageInYears(day: Int, month: Int, year: Int, minYear: Int): String {
    val dob = Calendar.getInstance()
    val today = Calendar.getInstance()
    if (year < minYear) return "0"
    dob[year, month] = day
    var age = today[Calendar.YEAR] - dob[Calendar.YEAR]
    val age_month = today[Calendar.MONTH] - dob[Calendar.MONTH]
    if (today[Calendar.DAY_OF_YEAR] < dob[Calendar.DAY_OF_YEAR]) {
        age--
    }
    val ageInt = if (age < 0) 0 else age
    return Integer.toString(ageInt)
}

fun ageInMonths(year: String, month: String): Long {
    return (year.toInt() * 12 + month.toInt()).toLong()
}

fun convertDateFormat(dateStr: String): String {
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
    try {
        val d = sdf.parse(dateStr)
        return SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(d)
    } catch (ex: ParseException) {
        ex.printStackTrace()
    }
    return ""
}

fun getDateFormat(dtFormat: String, dateStr: String): Date {
    val sdf = SimpleDateFormat(dtFormat, Locale.ENGLISH)
    try {
        val parse = sdf.parse(dateStr)
        parse?.let { return it } ?: Date()
    } catch (ex: ParseException) {
        ex.printStackTrace()
    }
    return Date()
}

fun getMonthAndYearFromStr(start: String, end: String): Pair<Int, Int> {
    val totalMonths = monthsBetweenDates(
            getDateFormat("yyyy-MM-dd", start),
            getDateFormat("dd/MM/yyyy", end)
    )
    val years = totalMonths / 12
    val months = totalMonths - years * 12

    return Pair(years, months)
}

fun getYearsBack(format: String, year: Int): String {
    val cal = Calendar.getInstance()
    cal.time = cal.time
    cal.add(Calendar.YEAR, year)
    return SimpleDateFormat(format, Locale.ENGLISH).format(cal.time) //"dd-MM-yyyy HH:mm"
}

fun addYearsByDate(cal: Calendar, format: String, year: Int): String {
    cal.time = cal.time
    cal.add(Calendar.YEAR, year)
    return SimpleDateFormat(format, Locale.ENGLISH).format(cal.time) //"dd-MM-yyyy HH:mm"
}

fun getMonthsBack(format: String, month: Int): String {
    val cal = Calendar.getInstance()
    cal.time = cal.time
    cal.add(Calendar.MONTH, month)
    return SimpleDateFormat(format, Locale.ENGLISH).format(cal.time) //"dd-MM-yyyy HH:mm"
}

fun getDaysBack(format: String, day: Int): String {
    val cal = Calendar.getInstance()
    cal.time = cal.time
    cal.add(Calendar.DAY_OF_YEAR, day)
    return SimpleDateFormat(format, Locale.ENGLISH).format(cal.time) //"dd-MM-yyyy HH:mm"
}

fun calculatedDate(date: String, dateFormat: String, q: Int, period: String): String? {
    // q = quantity or add (substract)
    // p = period :: d=day, m=month, y=year
    val cal = Calendar.getInstance()
    val s = SimpleDateFormat(dateFormat, Locale.ENGLISH)
    try {
        cal.time = s.parse(date)
        when (period) {
            "d" -> cal.add(Calendar.DAY_OF_YEAR, q)
            "m" -> cal.add(Calendar.MONTH, q)
            "y" -> cal.add(Calendar.YEAR, q)
        }
        return s.format(cal.time) //"dd-MM-yyyy HH:mm"
    } catch (e: ParseException) {
        Log.e("TAG", "Error in Parsing Date : " + e.message)
    }
    return null
}

fun getDOB(format: String, year: Int, month: Int, day: Int): String {
    val totalmonths = year * 12 + month
    val cal = Calendar.getInstance()
    cal.time = cal.time
    cal.add(Calendar.MONTH, -totalmonths)
    cal.add(Calendar.DAY_OF_MONTH, -day)
    return SimpleDateFormat(format, Locale.ENGLISH).format(cal.time)
}

fun getAgeInYears(year: Int): Int {
    val cal = Calendar.getInstance()
    val currentYear = cal[Calendar.YEAR]
    return currentYear - year
}

fun getCalendarDate(value: String): Calendar {
    val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH)
    val calendar = Calendar.getInstance()
    try {
        val date = sdf.parse(value)
        calendar.time = date
        return calendar
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return calendar
}

fun getDate(value: String): Calendar {
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
    val calendar = Calendar.getInstance()
    try {
        val date = sdf.parse(value)
        calendar.time = date
        return calendar
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return calendar
}

fun ageInYearByDOB(dateStr: String): Long {
    val cal = getCalendarDate(dateStr)
    val dob = cal.time
    val today = Date()
    val diff = today.time - dob.time
    return diff / (24 * 60 * 60 * 1000) / 365
}

fun getDateDiff(date1: Date, date2: Date, timeUnit: TimeUnit): Long {
    val diffInMillies = date2.time - date1.time
    return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS)
}

fun ageInMonthsByDOB(cal: Calendar): Long {
    val dob = cal.time
    val today = Date()
    val diff = (today.time - dob.time).toFloat()
    val ageInMonths = diff / (24 * 60 * 60 * 1000) / 30.4375
    return Math.floor(ageInMonths).toLong()
}

fun dobDiffInMonths(cal: Calendar, cal2: Calendar): Long {
    val dob = cal.time
    val visitDate = cal2.time
    val diff = (visitDate.time - dob.time).toFloat()
    val ageInMonths = diff / (24 * 60 * 60 * 1000) / 30.4375
    return Math.floor(ageInMonths).toLong()
}

fun dobDiffInDays(cal: Calendar, cal2: Calendar): Int {
    val dob = cal.time
    val visitDate = cal2.time
    val diff = (visitDate.time - dob.time).toFloat()
    return (diff / (24 * 60 * 60 * 1000)).roundToInt()
}

fun ageInDaysByDOB(dateStr: String): Long {
    val cal = getCalDate(dateStr)
    val dob = cal.time
    val today = Date()
    val diff = today.time - dob.time
    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
}

fun getCalDate(value: String): Calendar {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val calendar = Calendar.getInstance()
    try {
        val date = sdf.parse(value)
        calendar.time = date
        return calendar
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return calendar
}

fun getYearsAndMonthsBack(format: String, month: Int, year: Int): String {
    val cal = Calendar.getInstance()
    cal.time = cal.time
    cal.add(Calendar.YEAR, year)
    cal.add(Calendar.MONTH, month)
    return SimpleDateFormat(format, Locale.ENGLISH).format(cal.time)
}