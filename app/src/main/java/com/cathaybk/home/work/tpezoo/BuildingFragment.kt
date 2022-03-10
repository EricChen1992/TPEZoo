package com.cathaybk.home.work.tpezoo

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.browser.customtabs.CustomTabsIntent
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cathaybk.home.work.tpezoo.adapter.CustomRecyclerPlantAdapter
import com.cathaybk.home.work.tpezoo.api.PlantResponse
import com.cathaybk.home.work.tpezoo.api.TaipeiZooApi
import com.cathaybk.home.work.tpezoo.db.AppDatabase
import com.cathaybk.home.work.tpezoo.db.ZooBuildingEntity


class BuildingFragment : Fragment(), DataContract.IBuildingView {
    private lateinit var buildingPresenter: BuildingPresenter
    private lateinit var mainView: View
    private lateinit var fBuildingImageView: ImageView
    private lateinit var fBuildingToolbar: Toolbar
    private lateinit var fBuildCardView: View
    private lateinit var fBuildingRecyclerView: RecyclerView
    private lateinit var fBuildingProgressView: ProgressBar
    private lateinit var fBuildingPlantHint: TextView
    private lateinit var cardViewInfo: TextView
    private lateinit var cardViewCategory: TextView
    private lateinit var cardViewMemo: TextView
    private lateinit var cardVieUrl: TextView
    private lateinit var plantCardView: View
    private lateinit var plantTitle: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_building, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        kotlin.run {
            mainView = view

            buildingPresenter = BuildingPresenter(this, DataRepository(AppDatabase.getInstance(requireActivity())!!, TaipeiZooApi()))
            this
        }.run {
            initView()
            this
        }.run {

            arguments?.let { buildingPresenter.getArguments(FRAGMENT_ALL_INFO,it) }
        }

    }

    private fun initView(){
        fBuildingImageView = mainView.findViewById<ImageView>(R.id.fragment_building_icon)

        fBuildingToolbar = mainView.findViewById<Toolbar>(R.id.fragment_building_bar)

        fBuildCardView = mainView.findViewById(R.id.fragment_card)
        cardViewInfo = fBuildCardView.findViewById(R.id.b_cardview_info)
        cardViewCategory = fBuildCardView.findViewById(R.id.b_cardview_category)
        cardViewMemo = fBuildCardView.findViewById(R.id.b_cardview_memo)
        cardVieUrl = fBuildCardView.findViewById(R.id.b_cardview_Url)

        plantCardView = mainView.findViewById(R.id.fragment_plant_card)
        plantTitle = plantCardView.findViewById(R.id.b_cardview_plant_title)

        fBuildingRecyclerView = mainView.findViewById<RecyclerView>(R.id.fragment_building_recyclerview)
        fBuildingProgressView = mainView.findViewById<ProgressBar>(R.id.fragment_building_progress)
        fBuildingPlantHint = mainView.findViewById<TextView>(R.id.fragment_building_hint)
    }

    private fun setValueToView(fragmentList: ArrayList<String>){
        fBuildingProgressView.visibility = View.VISIBLE
        buildingPresenter.getBuildingPlant(fragmentList[1])

        fBuildingImageView?.run {
            Glide.with(requireActivity())
                .load(fragmentList[0].replace("http://", "https://").trim())
                .error(R.drawable.tpezoo_icon)
                .into(this)
        }

        fBuildingToolbar?.run {
            fBuildingToolbar.title = fragmentList[1]
            fBuildingToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        }
        fBuildCardView?.run {
            visibility = View.VISIBLE
            cardViewInfo?.run {
                cardViewInfo.text = "  ${fragmentList[2]}"
            }

            cardViewCategory?.run {
                cardViewCategory.text = fragmentList[4]
            }

            cardViewMemo?.run {
                cardViewMemo.text = fragmentList[3]
            }

            cardVieUrl?.run {
                cardVieUrl.text = "瞭解更多"
                setOnClickListener {
                    buildingPresenter.getBuildingWebUrl()
                }
            }
        }

        plantTitle?.run {
            text = "館內植物簡介"
        }

        fBuildingPlantHint?.run {
            text = "此館區沒有植物資訊"
            visibility = View.GONE
        }
    }

    override fun onShowInitView(fragmentList: ArrayList<String>) {
        requireActivity()?.run {
            setValueToView(fragmentList)
        }
    }

    companion object{
        private const val FRAGMENT_ID = "fragmentID"
        private const val FRAGMENT_ALL_INFO = "fragmentAllInfo"
        fun forBuildId(zooBuilding: ZooBuildingEntity) : BuildingFragment {
            return BuildingFragment().apply {
                arguments = Bundle().apply {
                    putInt(FRAGMENT_ID, zooBuilding.bId)
                    putStringArrayList(FRAGMENT_ALL_INFO, arrayListOf(zooBuilding.bPicURL,
                                                                    zooBuilding.bName,
                                                                    zooBuilding.bInfo,
                                                                    zooBuilding.bMemo,
                                                                    zooBuilding.bCategory,
                                                                    zooBuilding.bUrl))
                }
            }
        }
    }

    override fun onShowPlantList(plantList: List<PlantResponse.ResultsItem>?) {
        requireActivity()?.runOnUiThread {
            if (plantList?.size != 0) {
                fBuildingProgressView.visibility = View.GONE
                fBuildingPlantHint.visibility = View.GONE
                fBuildingRecyclerView?.let {
                    it.hasFixedSize()
                    it.adapter = CustomRecyclerPlantAdapter(buildingPresenter)
                }
            } else {
                fBuildingProgressView.visibility = View.GONE
                fBuildingPlantHint.visibility = View.VISIBLE
                fBuildingRecyclerView.visibility = View.GONE
            }
        }
    }

    override fun onShowBrowser(url: String) {
        var builder= CustomTabsIntent.Builder()
        var customtabintent = builder.build()
        customtabintent.launchUrl(requireContext(), Uri.parse(url.replace("http://", "https://").trim()))
    }

    override fun <T> onItemClick(cObject: T) {
        //Click Plant
        requireActivity()?.run {
            with(this as MainActivity){
                showPlantFragment(cObject as PlantResponse.ResultsItem)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        buildingPresenter.onDestroy()
    }
}