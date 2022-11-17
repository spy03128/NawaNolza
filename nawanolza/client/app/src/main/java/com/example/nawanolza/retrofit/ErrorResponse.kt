package com.example.nawanolza.retrofit

import java.io.Serializable

data class ErrorResponse(
    val timeStamp: String,
    val status: String,
    val error: String,
    val className: String,
    val message: String
): Serializable
