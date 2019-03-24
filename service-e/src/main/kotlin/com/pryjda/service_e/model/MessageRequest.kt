package com.pryjda.service_e.model

data class MessageRequest(val name: String,
                          val priority: MessagePriority = MessagePriority.COMMON,
                          val emitterNumber: String)