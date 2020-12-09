package com.abnormal

import android.content.Context

class ExecuteAbnormal {

   suspend fun  execute(context: Any, message: Message) {
        val methods = context.javaClass.methods
        methods.forEach {
            if (it.isAnnotationPresent(Abnormal::class.java)) {
                it.invoke(context, message)
            }
        }
    }
}