package com.example.nawanolza.retrofit

import java.io.Serializable

data class QuestSuccessResponse(val message: String, val getCharacterId: Long, val characterName: String, val success:Boolean): Serializable {
    constructor(): this("", 0L, "", false)

}
