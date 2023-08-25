package uz.ruzibekov.budgetplanner.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.ruzibekov.budgetplanner.data.local.room.converters.TransactionTypeConverter
import uz.ruzibekov.budgetplanner.data.local.room.dao.TransactionDao
import uz.ruzibekov.budgetplanner.data.local.entity.transaction.TransactionEntity

@Database(entities = [TransactionEntity::class], version = 1)
@TypeConverters(TransactionTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao

}