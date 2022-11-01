package com.example.nawanolza.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nawanolza.createGame.db.GameInfoDao
import com.example.nawanolza.createGame.db.GameInfoEntity

@Database(entities = arrayOf(GameInfoEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getGameInfoDao() : GameInfoDao

    companion object{
        val databaseName = "db_room"
        var appDatabase: AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase? {
            if(appDatabase == null){
                appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, databaseName).build()
            }
            return appDatabase
        }
    }
}
