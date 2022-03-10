package com.cathaybk.home.work.tpezoo.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cathaybk.home.work.tpezoo.BuildingPresenter
import com.cathaybk.home.work.tpezoo.R

class CustomRecyclerPlantAdapter(private val buildingPresenter: BuildingPresenter) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CustomViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.plant_item_view, parent, false))
    }

    override fun getItemCount(): Int = buildingPresenter.getListSize()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        buildingPresenter.onCustomBindHolder(holder as CustomViewHolder, position)
        holder.itemView.setOnClickListener {
            buildingPresenter.setCustomHolderClick(position)
        }
    }
}