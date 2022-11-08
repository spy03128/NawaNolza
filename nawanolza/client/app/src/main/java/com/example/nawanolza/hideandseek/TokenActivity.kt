package com.example.nawanolza.hideandseek

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nawanolza.databinding.ActivityTokenBinding
import com.example.nawanolza.firebase.MyFirebaseMessagingService

class TokenActivity : AppCompatActivity() {
    lateinit var binding: ActivityTokenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTokenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFirebase()
    }

    private fun initFirebase() {
        MyFirebaseMessagingService().getFirebaseToken()
        initDynamicLink()

    }

    private fun initDynamicLink() {
        val dynamicLinkData = intent.extras
        if (dynamicLinkData != null) {
            var dataStr = "DynamicLink 수신받은 값\n"
            for (key in dynamicLinkData.keySet()) {
                dataStr += "key: $key / value: ${dynamicLinkData.getString(key)}\n"
            }
        }
    }
}