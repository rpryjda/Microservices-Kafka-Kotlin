package com.pryjda.service_e.stream_out

import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel

interface OutStreamCreatedMessage {

    companion object {
        const val OUTPUT: String = "created-message"
    }

    @Output(OUTPUT)
    fun outCreateMessage(): MessageChannel
}