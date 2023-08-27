package uz.ruzibekov.budgetplanner.ui.screens.transaction.details

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.ruzibekov.budgetplanner.R
import uz.ruzibekov.budgetplanner.data.local.entity.transaction.TransactionEntity
import uz.ruzibekov.budgetplanner.ui.base.BaseActivity
import uz.ruzibekov.budgetplanner.ui.screens.transaction.edit.EditTransactionActivity
import uz.ruzibekov.budgetplanner.ui.screens.transaction.toTransactionTypeByAmount
import uz.ruzibekov.budgetplanner.utils.DateFactory
import uz.ruzibekov.budgetplanner.utils.TextFormatter

@AndroidEntryPoint
class DetailsTransactionActivity : BaseActivity() {

    private val viewModel: TransactionDetailsViewModel by viewModels()

    private var toolbar: Toolbar? = null
    private var cvIconBackground: CardView? = null
    private var ivIcon: ImageView? = null
    private var tvAmount: TextView? = null
    private var tvDate: TextView? = null
    private var tvDescription: TextView? = null

    private var dialogDeleteTransaction: AlertDialog? = null

    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details_transaction)

        initExtraData()

        initViews()

        initListeners()

        viewModel.initObservers()
    }

    private fun initExtraData() {
        val json = intent.getStringExtra(EXTRA_TRANSACTION_DATA)
        val data = gson.fromJson(json, TransactionEntity::class.java)

        viewModel.transactionStateFlow.value = data
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        cvIconBackground = findViewById(R.id.cv_icon)
        ivIcon = findViewById(R.id.iv_icon)
        tvAmount = findViewById(R.id.tv_amount)
        tvDate = findViewById(R.id.tv_date)
        tvDescription = findViewById(R.id.tv_description)

        initDeleteAlertDialog()
    }

    private fun initDeleteAlertDialog() {
        val builder = AlertDialog.Builder(this).apply {

            setMessage(R.string.message_delete_transaction_dialog)

            setNegativeButton(R.string.btn_cancel) { _, _ ->
                dialogDeleteTransaction?.dismiss()
            }

            setPositiveButton(R.string.btn_delete) { _, _ ->
                viewModel.deleteTransaction() {
                    finish()
                }
            }
        }

        dialogDeleteTransaction = builder.create()
    }

    private fun initListeners() {

        toolbar?.setNavigationOnClickListener {
            finish()
        }

        toolbar?.setOnMenuItemClickListener { item ->
            when (item.itemId) {

                R.id.menu_item_edit -> openEditScreen()

                else -> dialogDeleteTransaction?.show()
            }
            true
        }
    }

    private fun openEditScreen() {
        val intent = Intent(this, EditTransactionActivity::class.java)

        intent.putExtra(
            EditTransactionActivity.EXTRA_TRANSACTION_ID,
            viewModel.transactionStateFlow.value?.id
        )

        startActivity(intent)
    }

    @SuppressLint("SetTextI18n")
    private fun TransactionDetailsViewModel.initObservers() {
        transactionStateFlow.onEach {
            it?.let { transaction ->

                val transactionType = transaction.amount.toTransactionTypeByAmount()
                val formattedAmount = TextFormatter.spaceBetween3Numbers(transaction.amount)
                val date = DateFactory.getFormattedDate(transaction.date)

                tvAmount?.setTextColor(transactionType.color)
                tvAmount?.text = formattedAmount

                tvDate?.text = date
                tvDescription?.text = transaction.description
            }
        }.launchIn(lifecycleScope)
    }

    companion object {
        const val EXTRA_TRANSACTION_DATA = "extra-transaction-data"
    }
}