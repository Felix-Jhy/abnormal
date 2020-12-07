package com.abnormal

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Abnormals {
    companion object {
        lateinit var messageQueue: MessageQueue
        fun prepare(context: Any) {
            messageQueue = MessageQueue()
            messageQueue.prepare(context)
        }
    }

    suspend fun <T> abnormalMessage(name: String, nul: suspend () -> Unit = {}, block: suspend () -> T) {
        val onAbnormalListener = object : OnAbnormalListener {
            override suspend fun onLaunch() {
                block()
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

suspend fun <T> abnormalNull(name: String, nul: suspend () -> Unit = {}, block: suspend () -> T) {
    var boolean: Boolean = true
    var count: Int = 0
    while (boolean) {
        block().checkNull({
            if (count == 10) {
                abnormalMessage(name, nul, block)
                return
            }
            count++
            println("Then Abnormals BuildRetry is Count = : " + count)
        }, {
            boolean = false
        })
    }
}

inline fun <T> T?.checkNull(nul: () -> Unit = {}, noNull: T.() -> Unit) {
    if (this == null) nul()
    else noNull()
}

