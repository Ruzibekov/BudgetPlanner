package uz.ruzibekov.budgetplanner.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.ruzibekov.budgetplanner.data.local.entity.transaction.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data: TransactionEntity)

    @Query("SELECT * FROM transactions ORDER BY date")
    fun getTransactions(): Flow<List<TransactionEntity>>

    @Query("DELETE FROM transactions WHERE id = :id")
    fun deleteTransactionById(id: Long)

    @Query("SELECT * FROM transactions WHERE id = :id")
    fun getTransactionById(id: Long): TransactionEntity

    @Query("UPDATE transactions SET amount = :amount, description = :description WHERE id = :id")
    fun updateTransactionById(id: Long, amount: Long, description: String)

}