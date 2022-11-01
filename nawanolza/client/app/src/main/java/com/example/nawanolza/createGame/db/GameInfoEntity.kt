package com.example.nawanolza.createGame.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GameInfoEntity(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name="gameTime") val gameTime: String,
    @ColumnInfo(name="hideTime") val hideTime: String,
    @ColumnInfo(name="lat") val lat: Double,
    @ColumnInfo(name="lng") val lng: Double,
    @ColumnInfo(name="range") val range: Int
)
