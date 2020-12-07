package com.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abnormal.initAbnormalPrepare
import com.abnormal.sendAbnormalMessage

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sendAbnormalMessage(""){
            suspend fun getRequest(){}
        }
    }

    suspend fun getRequest(){}

}