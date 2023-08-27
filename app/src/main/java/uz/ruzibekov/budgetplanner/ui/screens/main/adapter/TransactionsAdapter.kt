package uz.ruzibekov.budgetplanner.ui.screens.main.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import uz.ruzibekov.budgetplanner.R
import uz.ruzibekov.budgetplanner.data.local.entity.transaction.TransactionEntity
import uz.ruzibekov.budgetplanner.utils.DateFactory
import uz.ruzibekov.budgetplanner.utils.TextFormatter

class TransactionsAdapter : RecyclerView.Adapter<TransactionsAdapter.ViewHolder>() {

    var items: List<TransactionEntity> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onClick: ((TransactionEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_transaction, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position]) {
            onClick?.invoke(items[position])
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var tvDate: TextView? = null
        private var tvAmountType: TextView? = null
        private var tvAmount: TextView? = null
        private var cvIcon: CardView? = null
        private var ivIcon: ImageView? = null
        private var tvName: TextView? = null

        init {
            tvDate = itemView.findViewById(R.id.tv_date)
            tvAmount = itemView.findViewById(R.id.tv_amount)
            cvIcon = itemView.findViewById(R.id.cv_icon)
            ivIcon = itemView.findViewById(R.id.iv_icon)
            tvName = itemView.findViewById(R.id.tv_name)
        }

        @SuppressLint("SetTextI18n")
        fun bind(data: TransactionEntity, onClick: () -> Unit) {

            when {
                data.amount > 0 -> {
                    tvAmount?.setTextColor(Color.BLUE)
                    tvAmount?.text = TextFormatter.spaceBetween3Numbers(data.amount)
                    ivIcon?.setImageResource(R.drawable.ic_income)
                    cvIcon?.setCardBackgroundColor(Color.BLUE)
                    tvName?.text = itemView.context.getString(R.string.income)
                }

                data.amount < 0 -> {
                    tvAmount?.setTextColor(Color.RED)
                    tvAmount?.text = TextFormatter.spaceBetween3Numbers(data.amount)
                    ivIcon?.setImageResource(R.drawable.ic_expense)
                    cvIcon?.setCardBackgroundColor(Color.RED)
                    tvName?.text = itemView.context.getString(R.string.expense)
                }
            }

            tvDate?.text = DateFactory.getFormattedDate(data.date)

            itemView.setOnClickListener { onClick() }
        }
    }
}