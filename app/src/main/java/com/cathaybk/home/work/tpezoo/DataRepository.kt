package com.cathaybk.home.work.tpezoo

import android.util.Log
import com.cathaybk.home.work.tpezoo.api.*
import com.cathaybk.home.work.tpezoo.db.AppDatabase
import com.cathaybk.home.work.tpezoo.db.ZooBuildingEntity
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface IDataRepository{
    //Get DB
    fun getAllBuildingDB(callback: LoadDbCallback)
    interface LoadDbCallback{
        fun onResult(result: List<String>?)
    }

    //Get Zoo Building
    fun getAnimalBuilding(loadAnimalCallback: LoadAnimalCallback)
    interface LoadAnimalCallback{
        fun onResult(result: List<AnimalBuildingResponse.ResultsItem>?)
        fun onShowTabLayoutMediator(tabNameList : List<String>?)
    }

    //Get Build Category
    fun getBuildingCategory(categoryFragment: Int, loadCategoryCallback: LoadCategoryCallback)
    interface LoadCategoryCallback{
        fun onResult(categoryList: List<ZooBuildingEntity>)
    }

    fun getBuildingPlant(buildingName: String, loadPlantCallback: LoadPlantCallback)
    interface LoadPlantCallback{
        fun onResult(plantList: ArrayList<PlantResponse.ResultsItem>?)
    }
}


class DataRepository( appDatabase: AppDatabase, private val taipeiZooApi: TaipeiZooApi) : IDataRepository{
    private val aDB = appDatabase

    override fun getAllBuildingDB(callback: IDataRepository.LoadDbCallback) {
        CoroutineScope(Dispatchers.IO).launch {
            callback.onResult(getCategoryWithDB(aDB.zooBuildingDao().getAllBuilding()))
        }
    }

    override fun getAnimalBuilding(loadAnimalCallback: IDataRepository.LoadAnimalCallback) {
        taipeiZooApi.getAnimalData(object : ITaipeiZoo.LoadAnimalDataCallBack {
            override fun onGetDataResult(listBuild: String) {
                //do jsonArray
                try {
                    val gson = Gson()
                    val jsonResult = gson.fromJson(listBuild, AnimalBuildingResponse.Response::class.java)
                    val buildingList : List<AnimalBuildingResponse.ResultsItem>? = jsonResult.result.results
                    buildingList?.run {
                        insertAllBuilding(this)
                        this
                    }.run {
                        loadAnimalCallback.onResult(this)
                        loadAnimalCallback.onShowTabLayoutMediator(getCategory(this))
                    }

                } catch (e:Exception){
                    loadAnimalCallback.onResult(listOf())
                    e.stackTrace
                }
            }
        })

    }

    private fun insertAllBuilding(list: List<AnimalBuildingResponse.ResultsItem>?){
        aDB.zooBuildingDao().delAll()
        CoroutineScope(Dispatchers.IO).launch {
            list?.forEach {

                it.run {
                    var eCategoryId : Int = 0
                    if (this.eCategory == "室內區") eCategoryId = 1
                    if (this.eCategory == "戶外區") eCategoryId = 2
                    eCategoryId
                }.run {
                    aDB.zooBuildingDao().insert(ZooBuildingEntity(it.Id, it.ePicURL, it.eInfo, it.eCategory, this, it.eMemo, it.eNo, it.eName, it.eUrl, it.eGeo))
                }
            }
        }
    }


    private fun getCategoryWithDB(buildingList : List<ZooBuildingEntity>?): List<String>? {
        return buildingList?.run {
            var arr = ArrayList<String>()
            this.forEach {
                arr.add(it.bCategory)
            }
            arr.toSortedSet().toList()
        }
    }

    private fun getCategory(buildingList : List<AnimalBuildingResponse.ResultsItem>?): List<String>? {
        return buildingList?.run {
                        var arr = ArrayList<String>()
                        this.forEach {
                            arr.add(it.eCategory)
                        }
                        arr.toSortedSet().toList()
                    }
    }

    override fun getBuildingCategory(categoryFragment: Int, loadCategoryCallback: IDataRepository.LoadCategoryCallback) {
        CoroutineScope(Dispatchers.IO).launch {
            loadCategoryCallback.onResult(aDB.zooBuildingDao().getCategoryBuilding(categoryFragment))
        }
    }

    override fun getBuildingPlant(buildingName: String, loadPlantCallback: IDataRepository.LoadPlantCallback) {
        taipeiZooApi.getPlantData(buildingName, object : ITaipeiZoo.LoadPlantDataCallBack {
            override fun onGetDataResult(listPlant: String) {
                //do jsonArray
                try {
                    val gson = Gson()
                    val jsonResult = gson.fromJson(listPlant, PlantResponse.Response::class.java)
                    val plantList : List<PlantResponse.ResultsItem>? = jsonResult.result.results

                    loadPlantCallback.onResult(ArrayList(plantList))

                } catch (e:Exception){
                    loadPlantCallback.onResult(ArrayList<PlantResponse.ResultsItem>())
                    e.stackTrace
                }
            }
        })
    }
}