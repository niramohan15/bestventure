package com.company.notes.transport

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.company.notes.R
import com.squareup.picasso.Picasso

class VehicleRVAdapter(private val vehicleRVModalArrayList: ArrayList<VehicleModel>, val context: Context, val vehicleClickInterface: VehicleClickInterface) : RecyclerView.Adapter<VehicleRVAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rv_vehicle_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvvehicleTitle.text = vehicleRVModalArrayList[position].vehicleName
        holder.tvvehiclePrice.text = vehicleRVModalArrayList[position].vehiclePrice
        Picasso.get().load(vehicleRVModalArrayList[position].vehicleImageLink).into(holder.ivvehicleImage)
        holder.itemView.setOnClickListener {
            vehicleClickInterface.onVehicleClick(position)
        }
    }

    override fun getItemCount(): Int {
        return vehicleRVModalArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivvehicleImage: ImageView
        var tvvehicleTitle: TextView
        var tvvehiclePrice: TextView

        init {
            ivvehicleImage = itemView.findViewById(R.id.ivvehicleImage)
            tvvehicleTitle = itemView.findViewById(R.id.tvvehicleTitle)
            tvvehiclePrice = itemView.findViewById(R.id.tvvehiclePrice)
        }
    }
    interface VehicleClickInterface {
        fun onVehicleClick(position: Int)
    }
}