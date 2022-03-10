package com.cathaybk.home.work.tpezoo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cathaybk.home.work.tpezoo.adapter.CustomRecyclerAdapter
import com.cathaybk.home.work.tpezoo.api.TaipeiZooApi
import com.cathaybk.home.work.tpezoo.db.AppDatabase
import com.cathaybk.home.work.tpezoo.db.ZooBuildingEntity

class CategoryFragment(tag: String) : Fragment(), DataContract.ICategoryView{
    var tagStr : String? = tag
    private lateinit var categoryPresenter: CategoryPresenter
    private lateinit var mainView: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainView = view
        categoryPresenter = CategoryPresenter(this, DataRepository(AppDatabase.getInstance(requireActivity())!!, TaipeiZooApi()))

        //1:indoor, 2:outdoor
        if (tagStr == "indoor")
            categoryPresenter.getBuildingCategory(1)
        if (tagStr == "outdoor")
            categoryPresenter.getBuildingCategory(2)

    }

    override fun onShowCategoryList(categoryList: List<ZooBuildingEntity>) {
        requireActivity().runOnUiThread {
            mainView.findViewById<RecyclerView>(R.id.category_fragment_recycler_view)?.let {
                it.hasFixedSize()
                it.adapter = CustomRecyclerAdapter(categoryPresenter)
            }
        }
    }

    override fun <T> onItemClick(cObject: T) {
        //Set mainActivity show fragment
        requireActivity()?.run {
            with(this as MainActivity){
                cObject?.run {
                    showFragment(cObject as ZooBuildingEntity)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        categoryPresenter.onDestroy()
    }
}