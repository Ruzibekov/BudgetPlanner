package uz.ruzibekov.budgetplanner.ui.screens.transaction.create

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.ruzibekov.budgetplanner.R
import uz.ruzibekov.budgetplanner.ui.base.BaseActivity
import uz.ruzibekov.budgetplanner.ui.helpers.TextWatchers
import uz.ruzibekov.budgetplanner.ui.screens.transaction.TransactionType
import uz.ruzibekov.budgetplanner.ui.screens.transaction.toTransactionTypeById


class CreateTransactionActivity : BaseActivity() {

    private val viewModel: CreateTransactionViewModel by viewModels()

    private var toolbar: Toolbar? = null
    private var tvIncomeTab: TextView? = null
    private var tvExpenseTab: TextView? = null
    private var etAmount: EditText? = null
    private var etDescription: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_create_transaction)

        initViews()

        viewModel.initObservers()

        initListeners()
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        tvIncomeTab = findViewById(R.id.tv_income_tab)
        tvExpenseTab = findViewById(R.id.tv_expense_tab)
        etAmount = findViewById(R.id.et_amount)
        etDescription = findViewById(R.id.et_description)
    }

    private fun CreateTransactionViewModel.initObservers() {
        transactionTypeStateFlow
            .onEach { type ->

                changeTabPositionByTransactionType(type.id)

                toolbar?.title = getString(type.textRes)

                when (type.id) {
                    0 -> {
                        tvIncomeTab?.setBackgroundResource(R.color.blue)
                        tvIncomeTab?.setTextColor(resources.getColor(R.color.white, null))
                        tvExpenseTab?.setBackgroundResource(R.color.white)
                        tvExpenseTab?.setTextColor(resources.getColor(R.color.black, null))
                    }

                    1 -> {
                        tvIncomeTab?.setBackgroundResource(R.color.white)
                        tvIncomeTab?.setTextColor(resources.getColor(R.color.black, null))
                        tvExpenseTab?.setBackgroundResource(R.color.red)
                        tvExpenseTab?.setTextColor(resources.getColor(R.color.white, null))
                    }
                }
            }
            .launchIn(lifecycleScope)
    }

    @SuppressLint("SetTextI18n")
    private fun initListeners() {
        toolbar?.setNavigationOnClickListener {
            finish()
        }

        toolbar?.setOnMenuItemClickListener {
            createTransaction()
            true
        }

        tvIncomeTab?.setOnClickListener {
            changeTabPositionByTransactionType(TransactionType.INCOME.id)
        }

        tvExpenseTab?.setOnClickListener {
            changeTabPositionByTransactionType(TransactionType.EXPENSE.id)
        }

        val textWatchers = TextWatchers(etAmount)
        etAmount?.addTextChangedListener(textWatchers.etAmountTextWatcher)

    }

    private fun changeTabPositionByTransactionType(position: Int) {

        position.toTransactionTypeById()?.let { type ->

            if (type != viewModel.transactionTypeStateFlow.value) {

                viewModel.transactionTypeStateFlow.value = type
            }
        }
    }

    private fun createTransaction() {
        viewModel.createTransaction(
            amount = etAmount?.text.toString(),
            description = (etDescription?.text).toString(),
            transactionType = viewModel.transactionTypeStateFlow.value,
            currentDateInMillis = System.currentTimeMillis(),
            onSuccess = {
                localManager.amount = localManager.amount + it
                finish()
            }
        )
    }

}