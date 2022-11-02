package com.example.nawanolza.retrofit

import java.io.Serializable

data class CharacterLocationResponseItem(
    val characterId : Long,
    val lat : Double,
    val lng : Double,
    val markerId : Long,
    val questType: Int,
    val rare: Boolean,
    val time : Int
): Serializable {
    constructor(): this(0L,0.0,0.0,0L,0,false,0)
}