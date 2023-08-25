package uz.ruzibekov.budgetplanner.data.local.entity.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo("amount") var amount: Long,
    @ColumnInfo("date") var date: Long,
    @ColumnInfo("description") val description: String
)

