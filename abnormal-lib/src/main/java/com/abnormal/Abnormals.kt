package com.abnormal

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Abnormals {
    companion object {
        lateinit var messageQueue: MessageQueue
        var maxCount: Int = -1
        fun prepare(context: Any) {
            this.messageQueue = MessageQueue()
            this.messageQueue.prepare(context)
        }

        fun prepare(context: Any, maxCount: Int) {
            this.messageQueue = MessageQueue()
            this.messageQueue.prepare(context)
            this.maxCount = maxCount
        }
    }

    suspend fun <T> abnormalMessage(name: String, nul: suspend () -> Unit = {}, block: suspend () -> T) {
        val onAbnormalListener = object : OnAbnormalListener {
            override suspend fun onLaunch(): Boolean {
                var boolean = true
                block().checkNull({
                    boolean = true
                }, {
                    boolean = false
                })
                return boolean
            }

            override suspend fun onCancel() {
                nul()
            }
        }
        sendAbnormalMessage(name, onAbnormalListener)
    }

    fun removeAbnormalMessage(name: String) {
        val queue = messageQueue
        val message = Message()
        message.name = name
        queue.removeMessages(message)
    }

    fun sendAbnormalMessage(name: String, onAbnormalListener: OnAbnormalListener) {
        val message = Message()
        message.name = name
        message.onAbnormalListener = onAbnormalListener
        enqueueMessage(message)
    }

    private fun enqueueMessage(message: Message) {
        val queue = messageQueue
        queue.enqueueMessage(message)
    }

}

suspend fun <T> abnormalMessage(name: String, nul: suspend () -> Unit = {}, block: suspend () -> T) {
    Abnormals().abnormalMessage(name, nul, block)
}

fun <T : Any> initPrepare(context: T) {
    Abnormals.prepare(context)
}

fun <T : Any> initPrepare(context: T,maxCount: Int) {
    Abnormals.prepare(context,maxCount)
}

suspend fun <T> abnormalNull(name: String, nul: suspend () -> Unit = {}, block: suspend () -> T) {
    var boolean: Boolean = true
    var count: Int = 0
    while (boolean) {
        block().checkNull({
            if (count == Abnormals.maxCount) {
                abnormalMessage(name, nul, block)
                return
            }
            count++
            println("Then Abnormals BuildRetry is Count = : " + count)
        }, {
            boolean = false
        })
        delay(1000)
    }
}

inline fun <T> T?.checkNull(nul: () -> Unit = {}, noNull: T.() -> Unit) {
    if (this == null) nul()
    else noNull()
}

