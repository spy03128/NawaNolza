package com.example.nawanolza.retrofit

import java.io.Serializable

data class Quiz(
    val answer: Boolean,
    val context: String,
    val id: Int
): Serializable {
    constructor(): this(false, "", 0)
}