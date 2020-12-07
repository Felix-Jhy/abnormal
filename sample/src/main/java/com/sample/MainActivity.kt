package com.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abnormal.abnormalNull
import com.abnormal.initPrepare

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    suspend fun getRequest(){}

}