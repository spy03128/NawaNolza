package com.example.nawanolza.createGame.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WaitingEntity (
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name="title") var title: String,
    @ColumnInfo(name="username") var username: String
)
