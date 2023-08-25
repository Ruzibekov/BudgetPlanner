package uz.ruzibekov.budgetplanner.ui.screens.transaction.create

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uz.ruzibekov.budgetplanner.data.local.room.dao.TransactionDao
import uz.ruzibekov.budgetplanner.data.local.entity.transaction.TransactionEntity
import uz.ruzibekov.budgetplanner.ui.screens.transaction.TransactionType
import javax.inject.Inject

@HiltViewModel
class CreateTransactionViewModel @Inject constructor(
    private val transactionDao: TransactionDao,
) : ViewModel() {

    var transactionTypeStateFlow: MutableStateFlow<TransactionType> =
        MutableStateFlow(TransactionType.EXPENSE)

    private val scope = CoroutineScope(Dispatchers.IO)

    fun createTransaction(
        amount: String,
        description: String,
        transactionType: TransactionType,
        currentDateInMillis: Long,
        onSuccess: (Long) -> Unit
    ) {
        if (amount.isNotBlank() && amount != "0") {

            var accountAmount = amount.getAmountWithoutSpaces()

            scope.launch {

                accountAmount = if (transactionType == TransactionType.EXPENSE)
                    -accountAmount
                else
                    accountAmount

                val transaction = TransactionEntity(
                    amount = accountAmount,
                    date = currentDateInMillis,
                    description = description,
                )

                transactionDao.insert(transaction)

                onSuccess(accountAmount)
            }
        }
    }

    private fun String.getAmountWithoutSpaces(): Long {
        return replace(" ", "").toLong()
    }

}

