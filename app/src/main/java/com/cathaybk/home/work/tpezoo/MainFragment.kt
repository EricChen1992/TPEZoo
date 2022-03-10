package com.cathaybk.home.work.tpezoo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.cathaybk.home.work.tpezoo.adapter.ViewPageAdapter
import com.cathaybk.home.work.tpezoo.api.AnimalBuildingResponse
import com.cathaybk.home.work.tpezoo.api.TaipeiZooApi
import com.cathaybk.home.work.tpezoo.db.AppDatabase
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment(),DataContract.IMainView {
    val TAG : String = "Main Fragment"
    private lateinit var mainPresenter: MainPresenter
    private lateinit var mainView: View
    private lateinit var viewPageAdapter: ViewPageAdapter
    private var tabLayoutMediator:TabLayoutMediator?= null
    private var indoorFragment:CategoryFragment?= null
    private var outdoorFragment:CategoryFragment?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainPresenter = MainPresenter(this, DataRepository(AppDatabase.getInstance(requireActivity())!!,TaipeiZooApi()))
        mainView = view

    }

    override fun onGetResult(result: List<AnimalBuildingResponse.ResultsItem>?) {
        result?.let {
            (requireActivity() as MainActivity).showProgress(false)
        }
    }

    override fun onShowTabLayoutMediator(tabNameList: List<String>?) {
        tabNameList?.run {
            initViewPage(tabNameList)
            (requireActivity() as MainActivity).showProgress(false)
        }
    }

    private fun initViewPage(list: List<String>?){
        requireActivity().runOnUiThread {

            viewPageAdapter = ViewPageAdapter(requireActivity())

            indoorFragment = CategoryFragment("indoor")
            outdoorFragment = CategoryFragment("outdoor")

            viewPageAdapter.addFragment(indoorFragment!!)
            viewPageAdapter.addFragment(outdoorFragment!!)

            val viewPager2 = mainView.findViewById<ViewPager2>(R.id.main_viewPage2)
            viewPager2.adapter = viewPageAdapter

            val tableLayout = mainView.findViewById<TabLayout>(R.id.main_tabLayout)
            tableLayout.visibility = View.VISIBLE
            tabLayoutMediator = TabLayoutMediator(tableLayout, viewPager2) {
                    tab, position -> tab.text = list?.get(position)
            }.also {
                it.attach()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        (this.requireActivity() as MainActivity).showProgress(true)
        mainPresenter.getAnimalBuilding()
    }

    override fun onDestroy() {
        super.onDestroy()
        tabLayoutMediator?.detach()
    }
}