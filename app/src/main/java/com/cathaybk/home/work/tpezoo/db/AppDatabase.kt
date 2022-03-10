package com.cathaybk.home.work.tpezoo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ZooBuildingEntity :: class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object{
        val DB_NAME = "Zoo.db"
        @Volatile
        private var instance: AppDatabase? = null
        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                instance = create(context) //創立新的資料庫
            }
            return instance!!
        }

        private fun create(context: Context): AppDatabase{
            return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).fallbackToDestructiveMigration().build()
        }
    }
    abstract fun zooBuildingDao(): ZooBuildingDao
}