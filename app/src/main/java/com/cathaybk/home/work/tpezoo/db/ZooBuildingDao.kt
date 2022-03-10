package com.cathaybk.home.work.tpezoo.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ZooBuildingDao {

    @Query("SELECT * FROM Building")
    fun getAllBuilding() : List<ZooBuildingEntity>

    @Query("SELECT * FROM Building WHERE bCategoryId = :categoryId")
    fun getCategoryBuilding(categoryId: Int) : List<ZooBuildingEntity>

    fun insert(zooBuildingEntity: ZooBuildingEntity){
        insertQ(zooBuildingEntity.bId,
            zooBuildingEntity.bPicURL,
            zooBuildingEntity.bInfo,
            zooBuildingEntity.bCategory,
            zooBuildingEntity.bCategoryId,
            zooBuildingEntity.bMemo,
            zooBuildingEntity.bNo,
            zooBuildingEntity.bName,
            zooBuildingEntity.bUrl,
            zooBuildingEntity.bGeo)
    }

    @Query("INSERT into Building('Id', 'bPicURL', 'bInfo', 'bCategory', 'bCategoryId', 'bMemo', 'bNo', 'bName', 'bUrl', 'bGeo') VALUES(:bid, :bPicURL, :bInfo, :bCategory, :bCategoryId, :bMemo, :bNo, :bName, :bUrl, :bGeo)")
    fun insertQ(
        bid: Int,
        bPicURL: String,
        bInfo: String,
        bCategory: String,
        bCategoryId: Int,
        bMemo: String,
        bNo: String,
        bName: String,
        bUrl: String,
        bGeo: String
    )

    @Query("DELETE FROM Building")
    fun delAll()
}