package com.cathaybk.home.work.tpezoo.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cathaybk.home.work.tpezoo.CategoryPresenter
import com.cathaybk.home.work.tpezoo.R

class CustomRecyclerAdapter(private val categoryPresenter: CategoryPresenter) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CustomViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.category_item_view, parent, false))
    }

    override fun getItemCount(): Int {
        return categoryPresenter.getListSize()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        categoryPresenter.onCustomBindHolder(holder as CustomViewHolder, position)
        holder.itemView.setOnClickListener {
            categoryPresenter.setCustomHolderClick(position)
        }
    }


}