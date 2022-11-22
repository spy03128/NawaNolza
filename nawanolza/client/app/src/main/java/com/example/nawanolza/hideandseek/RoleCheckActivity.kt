package com.example.nawanolza.hideandseek

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.example.nawanolza.login.LoginUtil
import com.example.nawanolza.R
import com.example.nawanolza.createGame.Waiting
import com.example.nawanolza.databinding.ActivityRoleCheckAcitivityBinding
import kotlinx.android.synthetic.main.activity_role_check_acitivity.*
import kotlinx.android.synthetic.main.fragment_participants.*

class RoleCheckActivity : AppCompatActivity() {

    lateinit var binding: ActivityRoleCheckAcitivityBinding
    lateinit var entryCode: String

    companion object {
        val countdown = 5000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoleCheckAcitivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        entryCode = intent.getStringExtra("entryCode").toString()

        setRoleFragment()
        countStart()

    }

    //카운트 시작
    private fun countStart() {
        object : CountDownTimer(countdown, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                var time = (millisUntilFinished / 1000).toInt()
                tvCountDown.text = time.toString()
                countDown.progress = (5 - time) * 100 / 5
            }

            override fun onFinish() {
                val intent = Intent(this@RoleCheckActivity, ChattingActivity::class.java)
                intent.putExtra("entryCode", entryCode)
                intent.putExtra("flag", true)
                startActivity(intent)
                finish()
            }
        }.start()
    }


    // 술래 참여자 나눠서 fragment 보여주기
    private fun setRoleFragment() {

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        if (Waiting.tagger == LoginUtil.getMember(this)?.id){
            fragmentTransaction.replace(R.id.fragmentFrame, ItFragment())
        } else fragmentTransaction.replace(R.id.fragmentFrame, ParticipantsFragment())

        fragmentTransaction.commit()
    }

}