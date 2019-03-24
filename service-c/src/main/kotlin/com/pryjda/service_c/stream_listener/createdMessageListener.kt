package com.pryjda.service_c.stream_listener

import com.pryjda.service_c.model.CreatedMessageEvent
import com.pryjda.service_c.service.CreateMessageService
import com.pryjda.service_c.stream_in.InStreamCreatedMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import java.util.logging.Logger


@Component
class CreatedMessageListener(@Autowired val createMessageService: CreateMessageService) {

    val LOGGER: Logger = Logger.getLogger(CreatedMessageListener::class.toString())

    @StreamListener(InStreamCreatedMessage.INPUT)
    fun handle(@Payload createdMessageEvent: CreatedMessageEvent) {
        LOGGER.info("handling createMessageEvent: ${createdMessageEvent.toString()}")
        createMessageService.handle(createdMessageEvent)
    }
}