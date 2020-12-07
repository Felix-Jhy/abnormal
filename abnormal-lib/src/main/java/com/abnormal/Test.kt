package com.abnormal

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun main() {
    var activityOne = Activity_One()
    val reqeust = Reqeust()
    reqeust.onRequest()
    Thread.sleep(10000)
}

class Activity_One {
    init {
        Abnormals.prepare(this)
    }

    @Abnormal
    fun onError(message: Message) {
        println("Activity_One-Abnormal"+message.name)
        GlobalScope.launch {
            message.onAbnormalListener!!.onLaunch()
            message.onAbnormalListener!!.onCancel()
        }
    }
}

class Reqeust {
    fun onRequest() {
      val ff:Ff?=null
        GlobalScope.launch {
            abnormalNull("Reqeust",{
                println("-----Reqeust-error----")

            },{
                println("-----Reqeust-success---")
                ff
            })

        }
    }
}

class Ff{}

