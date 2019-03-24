package com.pryjda.service_c.stream_in

import org.springframework.cloud.stream.annotation.Input
import org.springframework.messaging.SubscribableChannel

interface InStreamCreatedMessage {

    companion object {
        const val INPUT: String = "created-message"
    }

    @Input(INPUT)
    fun inCreatedMessage(): SubscribableChannel
}