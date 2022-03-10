package com.cathaybk.home.work.tpezoo

import com.cathaybk.home.work.tpezoo.adapter.CustomViewHolder
import com.cathaybk.home.work.tpezoo.db.ZooBuildingEntity

class CategoryPresenter(
    private val fragmentView: DataContract.ICategoryView,
    private val repository: DataRepository
) : DataContract.ICategoryPresenter{
    private lateinit var cList: List<ZooBuildingEntity>
    private var isDestory = false

    override fun getBuildingCategory(category: Int) {

        repository.getBuildingCategory(category, object : IDataRepository.LoadCategoryCallback {
            override fun onResult(categoryList: List<ZooBuildingEntity>) {
                cList = categoryList //CustomRecyclerAdapter to use
                if (!isDestory) fragmentView.onShowCategoryList(categoryList)
            }
        })
    }

    //CustomRecyclerAdapter to use
    override fun getListSize(): Int = cList.size

    //CustomRecyclerAdapter to use
    override fun onCustomBindHolder(customViewHolder: CustomViewHolder, position: Int) {
        val zBuilding = cList[position]
        customViewHolder.onBindData(zBuilding)
    }

    override fun setCustomHolderClick(position: Int) {
        if (!isDestory) fragmentView.onItemClick(cList[position])
    }

    override fun onDestroy() {
        isDestory = true
    }
}