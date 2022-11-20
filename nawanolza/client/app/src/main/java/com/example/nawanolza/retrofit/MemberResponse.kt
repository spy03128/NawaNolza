package com.example.nawanolza.retrofit

import java.io.Serializable

data class MemberResponse(var authorization: String, var member: Member): Serializable {
    constructor(): this("", Member())

    override fun toString(): String {
        return "MemberResponse(authorization='$authorization', member=$member)"
    }
}