package com.example.nawanolza.retrofit

import java.io.Serializable

data class CharacterDetailResponse(
    val description: String,
    val history: List<History>,
    val level: Int,
    val name: String,
    val rare: Boolean,
    val type: List<String>
): Serializable {
    constructor(): this("", ArrayList(),0,"",false, ArrayList())
}
