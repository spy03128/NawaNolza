package com.example.nawanolza

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.nawanolza.createGame.GameIntro
import com.example.nawanolza.hideandseek.MessageSenderService
import com.example.nawanolza.retrofit.MemberResponse
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_login.*
import java.time.LocalDateTime

private const val TAG = "HomeActivity_싸피"
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        init()
    }
    
    private fun init() {
        characterButton.setOnClickListener{
            val intent = Intent(this@HomeActivity, MapActivity::class.java)

            startActivity(intent)
        }

        gameButton.setOnClickListener{
            val intent = Intent(this@HomeActivity, GameIntro::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        finishAffinity()
    }
}