package uz.ruzibekov.budgetplanner.ui.screens.main

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.ruzibekov.budgetplanner.R
import uz.ruzibekov.budgetplanner.ui.base.BaseActivity
import uz.ruzibekov.budgetplanner.ui.screens.main.adapter.TransactionsAdapter
import uz.ruzibekov.budgetplanner.ui.screens.transaction.create.CreateTransactionActivity
import uz.ruzibekov.budgetplanner.ui.screens.transaction.details.DetailsTransactionActivity

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()

    private var tvAccountAmount: TextView? = null
    private var fbAddTransaction: FloatingActionButton? = null
    private var rvTransaction: RecyclerView? = null
    private var adapter = TransactionsAdapter()

    private var exitDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initViews()

        initObservers()

        initExitDialog()

        initListeners()
    }

    override fun onStart() {
        super.onStart()

        tvAccountAmount?.text = localManager.amount.toString()

        viewModel.fetch()
    }

    private fun initViews() {
        tvAccountAmount = findViewById(R.id.tv_account_amount)
        fbAddTransaction = findViewById(R.id.fb_add_transaction)
        rvTransaction = findViewById(R.id.rv_transactions)
        rvTransaction?.adapter = adapter
    }

    private fun initObservers() {
        viewModel.transactionsFlow
            .onEach {

                adapter.items = it

                rvTransaction?.visibility = if (it.isEmpty()) View.GONE
                else View.VISIBLE

            }
            .launchIn(lifecycleScope)
    }

    private fun initExitDialog() {
        val builder = AlertDialog.Builder(this)

        builder.apply {
            setTitle(R.string.title_exit_app)
            setMessage(R.string.body_exit_app)

            setPositiveButton(R.string.btn_yes) { _, _ ->
                finishAffinity()
            }

            setNegativeButton(R.string.btn_no) { _, _ ->
                exitDialog?.dismiss()
            }
        }

        exitDialog = builder.create()
    }

    private fun initListeners() {

        fbAddTransaction?.setOnClickListener {
            val intent = Intent(this, CreateTransactionActivity::class.java)
            startActivity(intent)
        }

        adapter.onClick = {
            val intent = Intent(this, DetailsTransactionActivity::class.java)
            intent.putExtra(DetailsTransactionActivity.EXTRA_TRANSACTION_DATA, Gson().toJson(it))
            startActivity(intent)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        exitDialog?.show()
    }
}