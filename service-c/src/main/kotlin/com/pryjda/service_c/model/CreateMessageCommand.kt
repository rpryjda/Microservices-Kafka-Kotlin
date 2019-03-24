package com.pryjda.service_c.model

import java.util.*

data class CreateMessageCommand(val messageRequest: MessageRequest,
                                val uniqueNumber: UUID)