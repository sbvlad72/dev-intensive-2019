package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern:String="HH:mm:ss dd.MM.yy"):String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time +=when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    var iDiff: Float
    iDiff = (Date().getTime() - this.getTime()).toFloat() / SECOND
    if (iDiff < 2){ return iDiff.toString()+"|только что" }
    else{
        if (iDiff < 46) {return iDiff.toString()+"|несколько секунд назад"}
        else{
            if (iDiff < 76) {return iDiff.toString()+"|минуту назад"}
            else{
                iDiff = ceil(iDiff / 60) //MINUTE
                if (iDiff <= 45) {return iDiff.toInt().toString()+" минут назад"}
                else {
                    if (iDiff <= 75) {return iDiff.toString()+"|час назад"}
                    else{
                        iDiff = ceil(iDiff / 60) //HOUR
                        if (iDiff <= 22){return iDiff.toInt().toString()+" часов назад"}
                        else{
                            if (iDiff <= 26) {return iDiff.toInt().toString()+" день назад"}
                            else{
                                iDiff = ceil(iDiff / 24) //DAY
                                if (iDiff < 360) {return iDiff.toString()+"|дней назад"}
                                else{
                                    return "более года назад"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


enum class TimeUnits{
    SECOND,
    MINUTE,
    HOUR,
    DAY
}