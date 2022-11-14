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

private const val TAG = "HomeActivity_싸피"
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        init()
    }
    
    private fun init() {
        val memberInfo: MemberResponse = LoginUtil.getMemberInfo(this)

        characterButton.setOnClickListener{
            val intent = Intent(this@HomeActivity, MapActivity::class.java)

            startActivity(intent)
        }

        gameButton.setOnClickListener{
            val intent = Intent(this@HomeActivity, GameIntro::class.java)
            startActivity(intent)
        }

        var dummyNumber = 0
        val messagePath = "/message_path"

        button.setOnClickListener {
            Log.i("WearableMainActivity", "Send Message Button Clicked")
            dummyNumber += 1
            MessageSenderService.sendMessageToWearable(messagePath, "Message from Wearable $dummyNumber", this)
        }

        Log.d(TAG, "init: ${memberInfo}")
    }

    override fun onPause() {
        super.onPause()
        println("===============Pause main============")
    }




}