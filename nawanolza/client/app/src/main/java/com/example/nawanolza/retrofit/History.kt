package com.example.nawanolza.retrofit

import java.io.Serializable

data class History(
    val createdAt: String,
    val level: Int
): Serializable {
    constructor(): this("",  0)
}