package com.cathaybk.home.work.tpezoo

import android.util.Log
import com.cathaybk.home.work.tpezoo.api.AnimalBuildingResponse
import com.cathaybk.home.work.tpezoo.db.ZooBuildingEntity

class MainPresenter (
    private val mainFragment: DataContract.IMainView,
    private val repository: DataRepository
) : DataContract.IMainPresenter {
    override fun getAnimalBuilding() {
        //Check DB Data
        repository.getAllBuildingDB(object : IDataRepository.LoadDbCallback{
            override fun onResult(result: List<String>?) {
                result?.run {
                    if(size != 0){
                        mainFragment.onShowTabLayoutMediator(result)
                    } else {
                        //No data
                        //Use repository get animal building data
                        repository.getAnimalBuilding(object : IDataRepository.LoadAnimalCallback {
                            override fun onResult(result: List<AnimalBuildingResponse.ResultsItem>?) {
                                mainFragment.onGetResult(result)
                            }

                            override fun onShowTabLayoutMediator(tabNameList: List<String>?) {
                                mainFragment.onShowTabLayoutMediator(tabNameList)
                            }
                        })
                    }
                }
            }
        })
    }

}