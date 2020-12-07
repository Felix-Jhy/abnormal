package com.abnormal

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun main() {
    var activityOne = Activity_One()

    GlobalScope.launch {
        val reqeust2 = Reqeust3()
        reqeust2.onRequest()
    }

    GlobalScope.launch {
        val reqeust = Reqeust()
        reqeust.onRequest()
    }

    Thread.sleep(10000)
}

class Activity_One {
    init {
        Abnormals.prepare(this)
    }

    @Abnormal
    fun onError(message: Message) {
        when (message.name) {
            "Reqeust" -> println("Reqeust :Activity_One-Abnormal" + message.name)
            "Test" -> println("Test :Activity_One-Abnormal" + message.name)
        }
        message.onAbnormalListener!!.onLaunch()
        message.onAbnormalListener!!.onCancel()
    }
}

class Reqeust {
    fun onRequest() {
        val ff: Ff? = null
        GlobalScope.launch {
            abnormalNull("Reqeust", {
                println("-----Reqeust-error----")
            }, {
                println("-----Reqeust-success---")
                ff
            })

        }
    }
}

class Reqeust3 {
    fun onRequest() {
        val ff: Ff? = null
        GlobalScope.launch {
            abnormalNull("Test", {
                println("-----test-error----")
            }, {
                println("-----test-success---")
                ff
            })

        }
    }
}

class Ff {}

