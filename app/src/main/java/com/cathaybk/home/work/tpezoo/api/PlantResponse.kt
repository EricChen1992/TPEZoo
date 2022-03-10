package com.cathaybk.home.work.tpezoo.api

import com.google.gson.annotations.SerializedName

class PlantResponse{

    data class Response(@SerializedName("result")
                             val result: Result)
    data class Result(@SerializedName("offset")
                      val offset: Int = 0,
                      @SerializedName("limit")
                      val limit: Int = 0,
                      @SerializedName("count")
                      val count: Int = 0,
                      @SerializedName("sort")
                      val sort: String = "",
                      @SerializedName("results")
                      val results: List<ResultsItem>?)

    data class ResultsItem(@SerializedName("﻿F_Name_Ch")
                           val fNameCh: String = "",
                           @SerializedName("F_pdf02_ALT")
                           val fPd02fALT: String = "",
                           @SerializedName("F_Name_En")
                           val fNameEn: String = "",
                           @SerializedName("F_Voice01_URL")
                           val fVoice01URL: String = "",
                           @SerializedName("F_Name_Latin")
                           val fNameLatin: String = "",
                           @SerializedName("F_Pic04_URL")
                           val fPic04URL: String = "",
                           @SerializedName("F_Summary")
                           val fSummary: String = "",
                           @SerializedName("F_Brief")
                           val fBrief: String = "",
                           @SerializedName("F_Location")
                           val fLocation: String = "",
                           @SerializedName("F_pdf02_URL")
                           val fPdf02URL: String = "",
                           @SerializedName("F_Voice01_ALT")
                           val fVoice01ALT: String = "",
                           @SerializedName("F_Pic03_ALT")
                           val fPic03ALT: String = "",
                           @SerializedName("F_Voice02_URL")
                           val fVoice02URL: String = "",
                           @SerializedName("rank")
                           val rank: Double = 0.0,
                           @SerializedName("F_Voice02_ALT")
                           val fVoice02ALT: String = "",
                           @SerializedName("F_Pic01_URL")
                           val fPic01URL: String = "",
                           @SerializedName("F_Pic02_ALT")
                           val fPic02ALT: String = "",
                           @SerializedName("F_Keywords")
                           val fKeywords: String = "",
                           @SerializedName("F_Family")
                           val fFamily: String = "",
                           @SerializedName("F_CID")
                           val fCid: String = "",
                           @SerializedName("F_Pic01_ALT")
                           val fPic01ALT: String = "",
                           @SerializedName("F_Pic02_URL")
                           val fPic02URL: String = "",
                           @SerializedName("F_Update")
                           val fUpdate: String = "",
                           @SerializedName("F_Voice03_URL")
                           val fVoice03URL: String = "",
                           @SerializedName("F_Code")
                           val fCode: String = "",
                           @SerializedName("F_Function＆Application")
                           val fFunctionApplication: String = "",
                           @SerializedName("F_Voice03_ALT")
                           val fVoice03ALT: String = "",
                           @SerializedName("F_Pic03_URL")
                           val fPic03URL: String = "",
                           @SerializedName("F_Vedio_URL")
                           val fVedioURL: String = "",
                           @SerializedName("_full_count")
                           val FullCount: String = "",
                           @SerializedName("F_pdf01_ALT")
                           val fPdf01ALT: String = "",
                           @SerializedName("F_AlsoKnown")
                           val fAlsoKnown: String = "",
                           @SerializedName("F_pdf01_URL")
                           val fPdf01URL: String = "",
                           @SerializedName("_id")
                           val Id: Int = 0,
                           @SerializedName("F_Feature")
                           val fFeature: String = "",
                           @SerializedName("F_Pic04_ALT")
                           val fPic04ALT: String = "",
                           @SerializedName("F_Genus")
                           val fGenus: String = "",
                           @SerializedName("F_Geo")
                           val fGeo: String = "")
}