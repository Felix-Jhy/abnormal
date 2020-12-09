package com.abnormal

open  class Message {
      var name: String?=null
      var onAbnormalListener: OnAbnormalListener?=null
      var execute:Execute?=null
}
 enum class Execute{
       SUCCESS,ERROR
}