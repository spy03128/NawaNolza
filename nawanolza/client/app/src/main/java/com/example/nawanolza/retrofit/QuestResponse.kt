package com.example.nawanolza.retrofit

import java.io.Serializable

data class QuestResponse(val accessible: Boolean, val quiz: Quiz): Serializable {
    constructor(): this(false, Quiz())

}
