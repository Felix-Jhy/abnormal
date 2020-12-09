package com.abnormal

public interface OnAbnormalListener {
    suspend fun onLaunch(): Boolean
    suspend fun onCancel()
}