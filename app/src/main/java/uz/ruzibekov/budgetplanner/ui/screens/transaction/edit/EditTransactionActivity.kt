package uz.ruzibekov.budgetplanner.ui.screens.transaction.edit

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import uz.ruzibekov.budgetplanner.utils.extensions.toPositive
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.ruzibekov.budgetplanner.R
import uz.ruzibekov.budgetplanner.ui.base.BaseActivity
import uz.ruzibekov.budgetplanner.ui.helpers.TextWatchers
import uz.ruzibekov.budgetplanner.ui.screens.main.MainActivity
import uz.ruzibekov.budgetplanner.ui.screens.transaction.TransactionType
import uz.ruzibekov.budgetplanner.utils.TextFormatter

class EditTransactionActivity : BaseActivity() {

    private val viewModel: EditTransactionViewModel by viewModels()

    private var toolbar: Toolbar? = null
    private var tabLayout: TabLayout? = null
    private var etAmount: EditText? = null
    private var etDescription: EditText? = null
    private var tvDate: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_edit_transaction)

        initViews()

        initExtraData()

        viewModel.initObservers()

        initListeners()
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        tabLayout = findViewById(R.id.tab_layout)
        etAmount = findViewById(R.id.et_amount)
        etDescription = findViewById(R.id.et_description)
        tvDate = findViewById(R.id.tv_date)
    }

    private fun initExtraData() {
        viewModel.transactionId = intent.getLongExtra(EXTRA_TRANSACTION_ID, -1L)

        if (viewModel.transactionId != -1L) {
            viewModel.getTransaction(viewModel.transactionId)
        }
    }

    private fun EditTransactionViewModel.initObservers() {

        transactionStateFlow.onEach {
            it?.let { transactionData ->
                etDescription?.setText(transactionData.description)

                if (transactionData.amount.toPositive()) {
                    etAmount?.setText(TextFormatter.spaceBetween3Numbers(transactionData.amount))
                    tabLayout?.getTabAt(0)?.select()
                } else {
                    etAmount?.setText(TextFormatter.spaceBetween3Numbers(-transactionData.amount))
                    tabLayout?.getTabAt(1)?.select()
                }
            }
        }.launchIn(lifecycleScope)

        transactionUpdateStateFlow
            .onEach { isCreated ->

                isCreated?.let {
                    if (it) {
                        val intent = Intent(this@EditTransactionActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else showToast(getString(R.string.transaction_data_invalid))
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun initListeners() {

        toolbar?.setNavigationOnClickListener {
            finish()
        }

        toolbar?.setOnMenuItemClickListener {
            updateTransaction()
            true
        }

        tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                changeTabIndicatorByPosition(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })


        val textWatchers = TextWatchers(etAmount)
        etAmount?.addTextChangedListener(textWatchers.etAmountTextWatcher)
    }

    private fun updateTransaction() {
        viewModel.updateTransaction(
            id = viewModel.transactionId,
            amount = etAmount?.text.toString(),
            description = etDescription?.text.toString(),
        )
    }

    private fun changeTabIndicatorByPosition(position: Int) {
        when (position) {
            0 -> {
                viewModel.transactionType = TransactionType.INCOME
                tabLayout?.setSelectedTabIndicatorColor(Color.BLUE)
            }

            1 -> {
                viewModel.transactionType = TransactionType.EXPENSE
                tabLayout?.setSelectedTabIndicatorColor(Color.RED)
            }
        }
    }

    companion object {
        const val EXTRA_TRANSACTION_ID = "extra-transaction-id"
    }
}