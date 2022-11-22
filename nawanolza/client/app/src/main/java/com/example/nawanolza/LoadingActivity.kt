package com.example.nawanolza

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nawanolza.login.LoginActivity
import com.example.nawanolza.login.LoginUtil

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        val intent : Intent =
            if (LoginUtil.isLogin(this)) Intent(this@LoadingActivity, HomeActivity::class.java)
            else Intent(this@LoadingActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}