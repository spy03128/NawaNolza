package com.example.nawanolza

import android.content.Context
import com.example.nawanolza.retrofit.Member
import com.example.nawanolza.retrofit.MemberResponse
import com.google.gson.GsonBuilder

class LoginUtil {

    companion object {
        fun setMemberInfo(context: Context, memberResponse: MemberResponse) {
            val sharedPreferences = context.getSharedPreferences("memberInfo", 0)
            val editor = sharedPreferences.edit()

            editor.putString("accessToken", memberResponse.authorization)
            editor.putString("member", GsonBuilder().create().toJson(memberResponse.member))
            editor.putString("memberInfo", GsonBuilder().create().toJson(memberResponse))

            editor.apply()
        }

        fun getAccessToken(context: Context) : String? {
            return context.getSharedPreferences("memberInfo", 0).getString("accessToken", null)
        }

        fun isLogin(context: Context): Boolean {
            return context.getSharedPreferences("memberInfo", 0).contains("accessToken")
        }

        fun getMember(context: Context) : Member? {
            return GsonBuilder().create().fromJson(context.getSharedPreferences("memberInfo", 0).getString("member", null), Member::class.java)
        }

        fun getMemberInfo(context: Context) : MemberResponse {
            return GsonBuilder().create().fromJson(context.getSharedPreferences("memberInfo", 0).getString("memberInfo", null), MemberResponse::class.java)
        }
    }
}