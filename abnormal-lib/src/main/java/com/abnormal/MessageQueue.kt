package com.abnormal

import android.content.Context
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentLinkedQueue

class MessageQueue {

    private val queue: ConcurrentLinkedQueue<Message> = ConcurrentLinkedQueue()
    private lateinit var context: Any
    fun prepare(context: Any) {
        this.context = context
        ergodic()
    }

    fun enqueueMessage(msg: Message) {
        GlobalScope.launch {
            queue.offer(msg)
        }
    }

    fun removeMessages(msg: Message) {

    }

    fun ergodic() {
        GlobalScope.launch {
            while (true) {
                if (!queue.isEmpty()) {
                    ExecuteAbnormal().also {
                        it.execute(context, queue.poll()!!)
                        delay(1000)
                    }
                }
            }
        }
    }
}