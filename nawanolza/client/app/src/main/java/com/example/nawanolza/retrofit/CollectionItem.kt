package com.example.nawanolza.retrofit

import java.io.Serializable

data class CollectionItem(
    val characterId: Int,
    val level: Int,
    val rare: Int
): Serializable{
    constructor(): this(0,0,0)
}