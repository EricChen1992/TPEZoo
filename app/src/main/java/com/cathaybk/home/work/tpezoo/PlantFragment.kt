package com.cathaybk.home.work.tpezoo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.cathaybk.home.work.tpezoo.api.PlantResponse
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class PlantFragment : Fragment(), DataContract.IPlantView{
    private lateinit var mainView: View
    private lateinit var plantPresenter: PlantPresenter
    private lateinit var fPlantImageView: ImageView
    private lateinit var fPlantToolbar: Toolbar
    private lateinit var fNameTittle: TextInputLayout
    private lateinit var fName: TextInputEditText
    private lateinit var fSubNameTittle: TextInputLayout
    private lateinit var fSubName: TextInputEditText
    private lateinit var fBriefTittle: TextInputLayout
    private lateinit var fBrief: TextInputEditText
    private lateinit var fFeatureTittle: TextInputLayout
    private lateinit var fFeature: TextInputEditText
    private lateinit var fFunctionApplicationTittle: TextInputLayout
    private lateinit var fFunctionApplication: TextInputEditText
    private lateinit var fUpdate: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        kotlin.run {
            mainView = view

            plantPresenter = PlantPresenter(this)
            this
        }.run {
            initView()
            this
        }.run {
            arguments?.let {
                plantPresenter.getArguments(FRAGMENT_ALL_INFO, it)
            }
        }
    }

    fun initView(){
        fPlantToolbar = mainView.findViewById(R.id.fragment_plant_bar)

        fPlantImageView = mainView.findViewById(R.id.fragment_plant_icon)

        fNameTittle = mainView.findViewById(R.id.f_plant_name_title)
        fSubNameTittle = mainView.findViewById(R.id.f_plant_subName_title)
        fBriefTittle = mainView.findViewById(R.id.f_plant_brief_title)
        fFeatureTittle = mainView.findViewById(R.id.f_plant_feature_title)
        fFunctionApplicationTittle = mainView.findViewById(R.id.f_plant_function_application_title)

        fName = mainView.findViewById(R.id.f_plant_name_edit)
        fSubName = mainView.findViewById(R.id.f_plant_subName_edit)
        fBrief = mainView.findViewById(R.id.f_plant_brief_edit)
        fFeature = mainView.findViewById(R.id.f_plant_feature_edit)
        fFunctionApplication = mainView.findViewById(R.id.f_plant_function_application_edit)
        fUpdate = mainView.findViewById(R.id.f_plant_update)
    }

    fun setValueToView(fragmentList: ArrayList<String>){

        fPlantToolbar?.run {
            fPlantToolbar.title = "植物資訊"
            fPlantToolbar.setOnClickListener { requireActivity().onBackPressed() }
        }

        fPlantImageView?.run {
            Glide.with(requireActivity())
                .load(fragmentList[0])
                .error(R.drawable.tpezoo_icon)
                .into(this)
        }
        fNameTittle?.run {
            hint = "名稱"

        }
        fName?.run {
            setText("${fragmentList[1]}\n${fragmentList[2]}")
        }

        fSubNameTittle?.run {
            hint = "別名"
        }

        fSubName?.run {
            if (fragmentList[3].isEmpty()) visibility = View.GONE
            setText("${fragmentList[3]}")
        }

        fBriefTittle?.run {
            hint = "簡介"
        }

        fBrief?.run {
            if (fragmentList[4].isEmpty()) visibility = View.GONE
            setText("${fragmentList[4]}")
        }

        fFeatureTittle?.run {
            if (fragmentList[5].isEmpty()) visibility = View.GONE
            hint = "辨認方式"
        }

        fFeature?.run {
            if (fragmentList[5].isEmpty()) visibility = View.GONE
            setText("${fragmentList[5]}")
        }

        fFunctionApplicationTittle?.run {
            if (fragmentList[6].isEmpty()) visibility = View.GONE
            hint = "功能性"
        }

        fFunctionApplication?.run {
            if (fragmentList[6].isEmpty()) visibility = View.GONE
            setText("${fragmentList[6]}")
        }

        fUpdate?.run {
            text = "更新時間: ${fragmentList[7]}"
        }

    }

    companion object {
        private const val FRAGMENT_ALL_INFO = "plantAllInfo"
        fun forPlant(pResult : PlantResponse.ResultsItem) : PlantFragment {
            return PlantFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(FRAGMENT_ALL_INFO, arrayListOf(pResult.fPic01URL,
                                                                    pResult.fNameCh,
                                                                    pResult.fNameEn,
                                                                    pResult.fAlsoKnown,
                                                                    pResult.fBrief,
                                                                    pResult.fFeature,
                                                                    pResult.fFunctionApplication,
                                                                    pResult.fUpdate))
                }
            }
        }
    }

    override fun onShowInitView(fragmentList: ArrayList<String>) {
        requireActivity()?.runOnUiThread {
            fragmentList?.let {
                if (it.size != 0){
                    setValueToView(it)
                }
            }
        }
    }
}