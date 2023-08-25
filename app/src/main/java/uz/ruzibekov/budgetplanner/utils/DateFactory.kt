package uz.ruzibekov.budgetplanner.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateFactory {

    fun getFormattedDate(calendar: Calendar): String {

        return dateFormat("dd/MM/yyyy", calendar)
    }

    fun getFormattedDate(dateInMillis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dateInMillis

        return dateFormat("dd/MM/yyyy", calendar)
    }

    fun getFormattedTime(calendar: Calendar): String {
        return dateFormat("HH:mm", calendar)
    }

    fun getFormattedTime(hour: Int, minute: Int): String {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }

        return dateFormat("HH:mm", calendar)
    }

    private fun dateFormat(pattern: String, calendar: Calendar): String {
        return SimpleDateFormat(pattern, Locale.getDefault()).format(calendar.time)
    }
}