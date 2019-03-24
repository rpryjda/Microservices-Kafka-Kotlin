package com.pryjda.service_e.stream_in

import org.springframework.cloud.stream.annotation.Input
import org.springframework.messaging.SubscribableChannel

interface InStreamCreateMessage {

    companion object {
        const val INPUT: String = "create-message"
    }

    @Input(INPUT)
    fun inCreateMessage(): SubscribableChannel
}