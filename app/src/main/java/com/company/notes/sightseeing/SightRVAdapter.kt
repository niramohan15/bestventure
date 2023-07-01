package com.company.notes.sightseeing

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.company.notes.R
import com.squareup.picasso.Picasso

class SightRVAdapter(private val sightRVModalArrayList: ArrayList<SightModel>, val context: Context, val sightClickInterface: SightClickInterface) : RecyclerView.Adapter<SightRVAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rv_sight_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvsightTitle.text = sightRVModalArrayList[position].sightName
        holder.tvsightPrice.text = sightRVModalArrayList[position].sightPrice
        Picasso.get().load(sightRVModalArrayList[position].sightImageLink).into(holder.ivsightImage)
        holder.itemView.setOnClickListener {
            sightClickInterface.onSightClick(position)
        }
    }

    override fun getItemCount(): Int {
        return sightRVModalArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivsightImage: ImageView
        var tvsightTitle: TextView
        var tvsightPrice: TextView

        init {
            ivsightImage = itemView.findViewById(R.id.ivsightImage)
            tvsightTitle = itemView.findViewById(R.id.tvsightTitle)
            tvsightPrice = itemView.findViewById(R.id.tvsightPrice)
        }
    }
    interface SightClickInterface {
        fun onSightClick(position: Int)
    }
}