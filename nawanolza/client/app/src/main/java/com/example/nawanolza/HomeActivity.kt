package com.example.nawanolza

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        val memberInfo: MemberResponse = intent.getSerializableExtra("memberInfo") as MemberResponse

        characterButton.setOnClickListener{
            val intent = Intent(this@HomeActivity, MapActivity::class.java)
            intent.apply {
                putExtra("memberInfo", memberInfo)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            startActivity(intent)
        }



        Log.d(TAG, "init: ${memberInfo}")
    }




}