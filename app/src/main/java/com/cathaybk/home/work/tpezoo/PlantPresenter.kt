package com.cathaybk.home.work.tpezoo

import android.os.Bundle

class PlantPresenter(
    private val fragmentView: DataContract.IPlantView
) : DataContract.IPlantPresenter{
    private lateinit var fragmentList: ArrayList<String>

    override fun getArguments(key: String, bundle: Bundle) {
        bundle?.let {
            fragmentList = it.getStringArrayList(key) as ArrayList<String>
            fragmentView.onShowInitView(fragmentList)
        }
    }
}