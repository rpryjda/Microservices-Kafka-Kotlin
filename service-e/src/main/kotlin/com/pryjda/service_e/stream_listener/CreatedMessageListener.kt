package com.pryjda.service_e.stream_listener

import com.pryjda.service_e.model.CreateMessageCommand
import com.pryjda.service_e.service.CreateMessageService
import com.pryjda.service_e.stream_in.InStreamCreateMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import java.util.logging.Logger


@Component
class CreatedMessageListener(@Autowired val createMessageService: CreateMessageService) {

    val LOGGER: Logger = Logger.getLogger(CreatedMessageListener::class.toString())

    @StreamListener(InStreamCreateMessage.INPUT)
    fun process(@Payload createMessageCommand: CreateMessageCommand) {
        LOGGER.info("got createMessageCommand : c${createMessageCommand.toString()}")
        val createdMessageEvent = createMessageService.createMessage(createMessageCommand)
        LOGGER.info("before sending createdMessageEvent : c${createdMessageEvent.toString()}")
        createMessageService.send(createdMessageEvent)
    }



}