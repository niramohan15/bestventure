package com.company.notes.payment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.company.notes.R
import com.squareup.picasso.Picasso

class PaymentRVAdapter(private val paymentRVModalArrayList: ArrayList<PaymentModel>, val context: Context, val paymentClickInterface: PaymentClickInterface) : RecyclerView.Adapter<PaymentRVAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rv_card_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvpaymentTitle.text = paymentRVModalArrayList[position].cardNumber
        holder.itemView.setOnClickListener {
            paymentClickInterface.onPaymentClick(position)
        }
    }

    override fun getItemCount(): Int {
        return paymentRVModalArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvpaymentTitle: TextView
        init {
            tvpaymentTitle = itemView.findViewById(R.id.tvcardnumber)
        }
    }
    interface PaymentClickInterface {
        fun onPaymentClick(position: Int)
    }
}