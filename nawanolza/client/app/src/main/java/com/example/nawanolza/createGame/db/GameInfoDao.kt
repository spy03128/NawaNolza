package com.example.nawanolza.createGame.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameInfoDao {
    @Query("SELECT * FROM GameInfoEntity")
    fun getAll() : List<GameInfoEntity>

    @Insert
    fun insertRoom(room: GameInfoEntity)

    @Delete
    fun deleteRoom(room: GameInfoEntity)
}

