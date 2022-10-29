package com.example.nawanolza


import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "5cb74336aa2cf69e85258d7ae5939538")
    }
}