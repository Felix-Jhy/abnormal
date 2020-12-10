package com.abnormal

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

fun main() {

    var activityOne = Activity_One1()

    GlobalScope.launch {
        val reqeust2 = Reqeust12()
        reqeust2.onRequest()
    }

    Thread.sleep(100000)
}

class Activity_One1 {
    init {
       initPrepare(this, 2)
    }

    @Abnormal
    fun onError(message: Message) {
        when (message.execute) {
            Execute.SUCCESS -> println(message.name + "   SUCCESS")
            Execute.ERROR -> println(message.name + "     ERROR")
        }
    }
}

class Reqeust12 {
    fun onRequest() {
        var ff: Ff? = null
        GlobalScope.launch {
            var i: Int = 0
            isAbnormalMessage("Reqeust", {
               println("Reqeust-eeror")
            }, {
                if (i >= 10) {
                    ff = Ff()
                }
                i++
                println("Reqeust-sucess")
                false
            })

        }
    }
}


