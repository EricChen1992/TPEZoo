package com.cathaybk.home.work.tpezoo.api

import com.google.gson.annotations.SerializedName

class AnimalBuildingResponse {
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

    data class ResultsItem(@SerializedName("E_Pic_URL")
                           val ePicURL: String = "",
                           @SerializedName("E_Info")
                           val eInfo: String = "",
                           @SerializedName("E_Category")
                           val eCategory: String = "",
                           @SerializedName("E_Memo")
                           val eMemo: String = "",
                           @SerializedName("E_no")
                           val eNo: String = "",
                           @SerializedName("E_Name")
                           val eName: String = "",
                           @SerializedName("_id")
                           val Id: Int = 0,
                           @SerializedName("E_URL")
                           val eUrl: String = "",
                           @SerializedName("E_Geo")
                           val eGeo: String = "")
}