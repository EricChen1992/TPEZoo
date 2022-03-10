package com.cathaybk.home.work.tpezoo.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Building")
class ZooBuildingEntity {
    @PrimaryKey @ColumnInfo(name = "id")
    var bId: Int = 0
    var bPicURL: String = ""
    var bInfo: String = ""
    var bCategory: String = ""
    var bCategoryId: Int = 0
    var bMemo: String = ""
    var bNo: String = ""
    var bName: String = ""
    var bUrl: String = ""
    var bGeo: String = ""

    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    var created_at: String = ""

    constructor(
        id: Int,
        picURL: String,
        info: String,
        category: String,
        categoryId: Int,
        memo: String,
        no: String,
        name: String,
        url: String,
        geo: String
    ) {
        this.bId = id
        this.bPicURL = picURL
        this.bInfo = info
        this.bCategory = category
        this.bCategoryId = categoryId
        this.bMemo = memo
        this.bNo = no
        this.bName = name
        this.bUrl = url
        this.bGeo = geo
        this.created_at = created_at
    }
    constructor()
}
