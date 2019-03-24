package com.pryjda.service_c.stream_out

import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel

interface OutStreamCreateMessage {

    companion object {
        const val OUTPUT: String = "create-message"
    }

    @Output(OUTPUT)
    fun outCreateMessage(): MessageChannel
}