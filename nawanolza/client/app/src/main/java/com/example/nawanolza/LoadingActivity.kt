package com.example.nawanolza

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        println("토큰 정보 보기 : " + LoginUtil.getAccessToken(this))

        val intent : Intent =
            if (LoginUtil.isLogin(this)) Intent(this@LoadingActivity, HomeActivity::class.java)
            else Intent(this@LoadingActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}