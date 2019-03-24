package com.pryjda.service_c.model

import java.util.*

data class CreatedMessageEvent(val messageResponse: MessageResponse,
                               val uniqueNumber: UUID)