package com.abnormal

import android.content.Context

class Abnormals {
    companion object {
        lateinit var messageQueue: MessageQueue
        fun prepare(context: Any) {
            messageQueue = MessageQueue()
            messageQueue.prepare(context)
        }
    }

    fun <T> sendAbnormalMessage(name: String, nul: () -> Unit = {}, block: () -> T) {
        val onAbnormalListener = object : OnAbnormalListener {
            override fun onLaunch() {
                block()
            }

            override fun onCancel() {
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

    private fun sendAbnormalMessage(name: String, onAbnormalListener: OnAbnormalListener) {
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

fun <T> sendAbnormalMessage(name: String, nul: () -> Unit = {}, block: () -> T) {
    Abnormals().sendAbnormalMessage(name, nul, block)
}

fun <T : Any> initAbnormalPrepare(context: T) {
    Abnormals.prepare(context)
}