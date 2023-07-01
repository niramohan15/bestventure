package com.company.notes.hotel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.company.notes.R
import com.squareup.picasso.Picasso

class HotelRVAdapter(private val hotelRVModalArrayList: ArrayList<HotelModel>, val context: Context, val hotelClickInterface: HotelClickInterface) : RecyclerView.Adapter<HotelRVAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rv_hotel_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvhotelTitle.text = hotelRVModalArrayList[position].hotelName
        holder.tvhotelPrice.text = hotelRVModalArrayList[position].hotelPrice
        Picasso.get().load(hotelRVModalArrayList[position].hotelImageLink).into(holder.ivhotelImage)
        holder.itemView.setOnClickListener {
            hotelClickInterface.onHotelClick(position)
        }
    }

    override fun getItemCount(): Int {
        return hotelRVModalArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivhotelImage: ImageView
        var tvhotelTitle: TextView
        var tvhotelPrice: TextView

        init {
            ivhotelImage = itemView.findViewById(R.id.ivhotelImage)
            tvhotelTitle = itemView.findViewById(R.id.tvhotelTitle)
            tvhotelPrice = itemView.findViewById(R.id.tvhotelPrice)
        }
    }
    interface HotelClickInterface {
        fun onHotelClick(position: Int)
    }
}