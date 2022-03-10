package com.cathaybk.home.work.tpezoo

import android.os.Bundle
import com.cathaybk.home.work.tpezoo.adapter.CustomViewHolder
import com.cathaybk.home.work.tpezoo.api.AnimalBuildingResponse
import com.cathaybk.home.work.tpezoo.api.PlantResponse
import com.cathaybk.home.work.tpezoo.db.ZooBuildingEntity

class DataContract {
    //動作
    interface IMainPresenter {
        fun getAnimalBuilding()
    }

    //回覆
    interface IMainView {
        fun onGetResult(result: List<AnimalBuildingResponse.ResultsItem>?)
        fun onShowTabLayoutMediator(tabNameList : List<String>?)
    }

    interface IPresenterList{
        fun getListSize(): Int
        //Recycler View Holder presenter
        fun onCustomBindHolder(customViewHolder: CustomViewHolder, position: Int)
        fun setCustomHolderClick(position: Int)
        fun onDestroy()
    }

    interface IViewList{
        fun <T> onItemClick(cObject : T)
    }

    interface ICategoryPresenter: IPresenterList{
        fun getBuildingCategory(category: Int)
    }

    interface ICategoryView: IViewList{
        fun onShowCategoryList(categoryList: List<ZooBuildingEntity>)
    }

    //Category Recycler View Holder view
    interface ICustomItemView{
        fun <T> onBindData(itemObject: T)
    }

    interface IBuildingPresenter: IPresenterList{
        fun getArguments(key:String, bundle: Bundle)
        fun getBuildingPlant(buildingName: String)
        fun getBuildingWebUrl()
    }

    interface IBuildingView: IViewList{
        fun onShowInitView(fragmentList: ArrayList<String>)
        fun onShowPlantList(plantList: List<PlantResponse.ResultsItem>?)
        fun onShowBrowser(url: String)
    }

    interface IPlantPresenter{
        fun getArguments(key:String, bundle: Bundle)
    }

    interface IPlantView{
        fun onShowInitView(fragmentList: ArrayList<String>)
    }

}