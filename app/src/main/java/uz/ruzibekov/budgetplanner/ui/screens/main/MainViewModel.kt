package uz.ruzibekov.budgetplanner.ui.screens.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uz.ruzibekov.budgetplanner.data.local.entity.transaction.TransactionEntity
import uz.ruzibekov.budgetplanner.data.local.room.dao.TransactionDao
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val transactionDao: TransactionDao) : ViewModel() {

    val transactionsFlow: MutableStateFlow<List<TransactionEntity>> = MutableStateFlow(emptyList())

    private val scope = CoroutineScope(Dispatchers.IO)

    fun fetch() = scope.launch {
        transactionDao.getTransactions().collect { transactions ->

            transactionsFlow.value = transactions.reversed()
        }
    }

}