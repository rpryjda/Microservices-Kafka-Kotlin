package com.pryjda.service_e.model

import java.util.*

data class CreateMessageCommand(val messageRequest: MessageRequest,
                                val uniqueNumber: UUID)