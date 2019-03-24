package com.pryjda.service_c.model

data class MessageRequest(val name: String,
                          val priority: MessagePriority = MessagePriority.COMMON,
                          val emitterNumber: String)