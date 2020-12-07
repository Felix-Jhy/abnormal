package com.abnormal

public interface OnAbnormalListener {
   suspend fun onLaunch()
   suspend fun onCancel()
}