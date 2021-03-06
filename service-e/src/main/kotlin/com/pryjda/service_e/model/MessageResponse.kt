package com.pryjda.service_e.model

data class MessageResponse(val name: String,
                           val priority: MessagePriority = MessagePriority.COMMON,
                           val emitterNumber: String,
                           val id: String,
                           val additionalText: String)