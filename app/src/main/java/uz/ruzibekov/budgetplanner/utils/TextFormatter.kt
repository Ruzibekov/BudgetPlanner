package uz.ruzibekov.budgetplanner.utils

object TextFormatter {

    fun spaceBetween3Numbers(number: Long?): String { //1 000 000

        val text = number.toString()

        return when {

            number == 0L -> "0"

            number == null -> ""

            text.length < 4 -> text

            else -> {
                val count: Long = if (text.length % 3 != 0) text.length / 3L
                else (text.length - 1L) / 3

                var result = ""

                for (i in 1..count) {
                    val startIndex = (text.length - 3 * i).toInt()
                    val endIndex = startIndex + 3
                    result = " " + text.substring(startIndex until endIndex) + result
                }

                val headNumber = (text.length - 3 * count).toInt()
                result = text.substring(0 until headNumber) + result

                return result
            }
        }
    }
}