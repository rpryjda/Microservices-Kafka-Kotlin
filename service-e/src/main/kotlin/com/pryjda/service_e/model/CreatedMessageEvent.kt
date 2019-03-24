package com.pryjda.service_e.model

import java.util.*

data class CreatedMessageEvent(val messageResponse: MessageResponse,
                               val uniqueNumber: UUID)