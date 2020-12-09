package com.abnormal

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
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

    GlobalScope.launch {
        val reqeust = Reqeust2()
        reqeust.onRequest()
    }

    Thread.sleep(100000)
}

class Activity_One {
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

class Reqeust {
    fun onRequest() {
        var ff: Ff? = null
        GlobalScope.launch {
            var i: Int = 0
            abnormalNull("Reqeust", {

            }, {
                if (i >= 10) {
                    ff = Ff()
                }
                i++

                ff
            })

        }
    }
}

class Reqeust3 {
    fun onRequest() {
        var ff: Ff? = null
        GlobalScope.launch {
            var i: Int = 0
            abnormalNull("Test", {
                println("-----test-error----")
            }, {
                if (i >= 10) {
                    ff = Ff()
                }
                i++
                delay(200)

                ff
            })

        }
    }
}

class Reqeust2 {
    fun onRequest() {
        var ff: Ff? = null
        GlobalScope.launch {
            var i: Int = 0
            abnormalNull("Demo", {
                println("-----Demo-error----")
            }, {
                if (i >= 10) {
                    ff = Ff()
                }
                i++
                delay(500)
                ff
            })

        }
    }
}

class Ff {}

