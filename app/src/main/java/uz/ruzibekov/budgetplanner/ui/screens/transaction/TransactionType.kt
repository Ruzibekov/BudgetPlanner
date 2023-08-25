package uz.ruzibekov.budgetplanner.ui.screens.transaction

import android.graphics.Color
import uz.ruzibekov.budgetplanner.R

enum class TransactionType(
    val id: Int,
    val textRes: Int,
    val color: Int
) {

    INCOME(
        id = 0,
        textRes = R.string.income,
        color = Color.BLUE,
    ),

    EXPENSE(
        id = 1,
        textRes = R.string.expense,
        color = Color.RED,
    )
}

fun Int.toTransactionTypeById(): TransactionType? {
    return TransactionType.values().find { it.id == this }
}

fun Long.toTransactionTypeByAmount(): TransactionType {
    return if (this < 0) TransactionType.EXPENSE else TransactionType.INCOME
}