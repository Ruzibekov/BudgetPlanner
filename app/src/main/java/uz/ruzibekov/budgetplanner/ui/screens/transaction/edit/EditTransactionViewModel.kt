package uz.ruzibekov.budgetplanner.ui.screens.transaction.edit

import uz.ruzibekov.budgetplanner.ui.base.BaseViewModel
import uz.ruzibekov.budgetplanner.ui.screens.transaction.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uz.ruzibekov.budgetplanner.data.local.room.dao.TransactionDao
import uz.ruzibekov.budgetplanner.data.local.entity.transaction.TransactionEntity
import javax.inject.Inject

@HiltViewModel
class EditTransactionViewModel @Inject constructor(
    private val transactionDao: TransactionDao,
) : BaseViewModel() {

    var transactionId: Long = -1L
    var transactionType: TransactionType = TransactionType.EXPENSE
    val transactionUpdateStateFlow: MutableStateFlow<Boolean?> = MutableStateFlow(null)

    val transactionStateFlow: MutableStateFlow<TransactionEntity?> = MutableStateFlow(null)

    private val scope = CoroutineScope(Dispatchers.IO)

    fun getTransaction(id: Long) {
        scope.launch {
            transactionStateFlow.value = transactionDao.getTransactionById(id)
        }
    }

    fun updateTransaction(
        id: Long,
        amount: String,
        description: String,
    ) {

        if (amount.isNotBlank()) {
            var transactionAmount = amount.getAmountWithoutSpaces()

            scope.launch {

                transactionAmount =
                    if (transactionType == TransactionType.EXPENSE) -transactionAmount
                    else transactionAmount

                transactionDao.updateTransactionById(
                    id = id,
                    amount = transactionAmount,
                    description = description
                )

                transactionUpdateStateFlow.value = true
            }
        } else
            transactionUpdateStateFlow.value = false
    }
}

private fun String.getAmountWithoutSpaces(): Long {
    return replace(" ", "").toLong()
}