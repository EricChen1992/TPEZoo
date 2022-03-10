package com.cathaybk.home.work.tpezoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import com.cathaybk.home.work.tpezoo.api.PlantResponse
import com.cathaybk.home.work.tpezoo.db.ZooBuildingEntity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mainFragment = MainFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.activity_fragment_container, mainFragment, mainFragment.TAG)
            .commit()

    }

    fun showFragment(zooBuilding: ZooBuildingEntity){
        val buildingFragment = BuildingFragment.forBuildId(zooBuilding)
        supportFragmentManager.beginTransaction()
            .addToBackStack("zooBuilding")
            .replace(R.id.activity_fragment_container, buildingFragment, "zooBuilding")
            .commit()
    }

    fun showPlantFragment(zooPlant: PlantResponse.ResultsItem){
        val plantFragment = PlantFragment.forPlant(zooPlant)
        supportFragmentManager.beginTransaction()
            .addToBackStack("zooPlant")
            .replace(R.id.activity_fragment_container, plantFragment, "zooPlant")
            .commit()
    }

    fun showProgress(sw:Boolean) {
        runOnUiThread {
            if (sw) {
                findViewById<View>(R.id.main_progressBar_background)?.visibility = View.VISIBLE
                findViewById<ProgressBar>(R.id.main_progressBar)?.visibility = View.VISIBLE
            } else {
                findViewById<View>(R.id.main_progressBar_background)?.visibility = View.GONE
                findViewById<ProgressBar>(R.id.main_progressBar)?.visibility = View.GONE
            }
        }
    }

}