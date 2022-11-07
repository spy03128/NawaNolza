package com.example.nawanolza.retrofit

import java.io.Serializable

data class QuestFailResponse(val event: Boolean): Serializable {
    constructor(): this(false)

}
