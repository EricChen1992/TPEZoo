package com.cathaybk.home.work.tpezoo.api

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL


interface ITaipeiZoo{
    interface LoadAnimalDataCallBack{
        fun onGetDataResult(listBuild: String)
    }
    fun getAnimalData(loadAnimalDataCallBack: LoadAnimalDataCallBack)

    interface LoadPlantDataCallBack{
        fun onGetDataResult(listPlant: String)
    }
    fun getPlantData(buildingName: String, loadPlantDataCallBack: LoadPlantDataCallBack)
}

class TaipeiZooApi : ITaipeiZoo {

    override fun getAnimalData(loadAnimalDataCallBack: ITaipeiZoo.LoadAnimalDataCallBack) {
        //do request
        CoroutineScope(Dispatchers.IO).launch {
            var url = URL("https://data.taipei/api/v1/dataset/5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a?scope=resourceAquire")
            var tempTry = 0;
            var returnString: String
            do {
                with(url.openConnection() as HttpURLConnection){
                    //Do loop get web api
                    try {
                        this.requestMethod = "GET"
                        this.doInput = true
//                this.doOutput = true //如果家這段會變post
                        this.connectTimeout = 1000 //實際跑的秒數
                        this.readTimeout = 5000

                        if (responseCode == 200 ){
                            inputStream.bufferedReader().use {
//                                loadAnimalDataCallBack.onGetDataResult(it.readText())
                                returnString = it.readText()
//                                Log.e("Read", returnString + " >>>> " + requestMethod)
                            }
                        } else {
//                        Log.e("Response", responseCode.toString())
//                            loadAnimalDataCallBack.onGetDataResult("Response code $responseCode requestMethod: $requestMethod")
                            returnString = "Request Error"
                            Thread.sleep(5000)
                        }
                    } catch (e : Exception){
//                        loadAnimalDataCallBack.onGetDataResult(e.toString())
                        returnString = "Request Error"
                        Thread.sleep(5000)

                    } finally {
                        disconnect()
                    }
                }
                tempTry ++

            } while (returnString == "Request Error" && tempTry <= 4)

            loadAnimalDataCallBack.onGetDataResult(returnString)

        }
    }

    override fun getPlantData(buildingName: String, loadPlantDataCallBack: ITaipeiZoo.LoadPlantDataCallBack) {
        CoroutineScope(Dispatchers.IO).launch {
            var returnString: String
            var tempTry = 0
            do {
                with(URL("https://data.taipei/api/v1/dataset/f18de02f-b6c9-47c0-8cda-50efad621c14?scope=resourceAquire&q=$buildingName&limit=1000&offset=0").openConnection() as HttpURLConnection){
                    try {
                        requestMethod = "GET"
                        doInput = true
                        connectTimeout = 1500
                        readTimeout = 5000

                        if (responseCode == 200){
                            inputStream.bufferedReader().use {
//                                loadPlantDataCallBack.onGetDataResult(it.readText())
                                returnString = it.readText()
                            }
                        } else {
                            returnString = "Request Error"
                            Thread.sleep(500)
//                            loadPlantDataCallBack.onGetDataResult("Response code $responseCode requestMethod: $requestMethod")
                        }
                    } catch (e : java.lang.Exception){
                        returnString = "Request Error"
                        Thread.sleep(500)
//                        loadPlantDataCallBack.onGetDataResult(e.toString())
                    } finally {
                        disconnect()
                    }
                }
                tempTry ++
            } while (returnString == "Request Error" && tempTry <= 4)

            loadPlantDataCallBack.onGetDataResult(returnString)

        }
    }

}