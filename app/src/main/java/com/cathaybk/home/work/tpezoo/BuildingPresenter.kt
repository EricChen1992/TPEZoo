package com.cathaybk.home.work.tpezoo

import android.os.Bundle
import android.util.Log
import com.cathaybk.home.work.tpezoo.adapter.CustomViewHolder
import com.cathaybk.home.work.tpezoo.api.PlantResponse
import com.cathaybk.home.work.tpezoo.db.ZooBuildingEntity

class BuildingPresenter(
    private val fragmentView: DataContract.IBuildingView,
    private val repository: IDataRepository
) : DataContract.IBuildingPresenter{
    private lateinit var list: ArrayList<PlantResponse.ResultsItem>
    private lateinit var fragmentList: ArrayList<String>
    private var isDestory = false

    override fun getArguments(key: String, bundle: Bundle) {
        bundle.let {
            fragmentList = it.getStringArrayList(key) as ArrayList<String>
            if (!isDestory) fragmentView.onShowInitView(fragmentList)
        }
    }

    override fun getBuildingPlant(buildingName: String) {
        val strSplit = buildingName.replace("(", " ").replace(")", "").split(" ")
        if (strSplit.size >=2) {
            var resultList = ArrayList<PlantResponse.ResultsItem>()
            strSplit.forEach {
                repository.getBuildingPlant(it, object : IDataRepository.LoadPlantCallback {
                    override fun onResult(plantList: ArrayList<PlantResponse.ResultsItem>?) {
                        if (plantList != null) {
                            if (plantList?.size > 0){
                                resultList.addAll(plantList)
                                list = resultList
                                if (!isDestory) fragmentView.onShowPlantList(list)
                            }
                        }
                    }
                })
            }

        } else {
            repository.getBuildingPlant(buildingName, object : IDataRepository.LoadPlantCallback {
                override fun onResult(plantList: ArrayList<PlantResponse.ResultsItem>?) {
                    if (plantList != null) list = plantList
                    if (!isDestory) fragmentView.onShowPlantList(plantList)
                }
            })
        }
    }


    override fun getBuildingWebUrl() {
        if (!isDestory) fragmentView.onShowBrowser(fragmentList[5])
    }

    override fun getListSize(): Int = list.size

    override fun onCustomBindHolder(customViewHolder: CustomViewHolder, position: Int) {
        customViewHolder.onBindData(list[position])
    }

    override fun setCustomHolderClick(position: Int) {
        fragmentView.onItemClick(list[position])
    }

    override fun onDestroy() {
        isDestory = true
    }
}