package uz.ruzibekov.budgetplanner.ui.screens.transaction.details

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uz.ruzibekov.budgetplanner.data.local.room.dao.TransactionDao
import uz.ruzibekov.budgetplanner.data.local.entity.transaction.TransactionEntity
import uz.ruzibekov.budgetplanner.ui.base.BaseViewModel
import javax.inject.Inject


@HiltViewModel
class TransactionDetailsViewModel @Inject constructor(
    private val transactionDao: TransactionDao,
) : BaseViewModel() {

    val transactionStateFlow: MutableStateFlow<TransactionEntity?> = MutableStateFlow(null)

    private val scope = CoroutineScope(Dispatchers.IO)

    fun deleteTransaction(onSuccess: () -> Unit) {
        scope.launch {
            transactionStateFlow.value?.let {
                transactionDao.deleteTransactionById(it.id)
                onSuccess()
            }
        }
    }
}