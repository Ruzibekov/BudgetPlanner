package uz.ruzibekov.budgetplanner.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import uz.ruzibekov.budgetplanner.data.model.entity.account.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Insert
    fun insertAccount(account: AccountEntity): Long

    @Query("SELECT * FROM accounts")
    fun getAccounts(): Flow<List<AccountEntity>>

    @Query("SELECT name FROM accounts")
    fun getAllAccountUsernames(): Flow<List<String>>

    @Query("SELECT * FROM accounts WHERE id=:id")
    fun getAccountById(id: Long): Flow<AccountEntity>

}