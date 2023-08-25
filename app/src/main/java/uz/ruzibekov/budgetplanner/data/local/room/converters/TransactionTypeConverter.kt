package uz.ruzibekov.budgetplanner.data.local.room.converters

import androidx.room.TypeConverter
import uz.ruzibekov.budgetplanner.ui.screens.transaction.TransactionType


class TransactionTypeConverter {

    @TypeConverter
    fun fromTransactionType(transactionType: TransactionType) = transactionType.name

    @TypeConverter
    fun toTransactionType(name: String) = enumValueOf<TransactionType>(name)
}