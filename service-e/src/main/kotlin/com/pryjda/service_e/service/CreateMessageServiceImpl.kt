package com.pryjda.service_e.service

import com.pryjda.service_e.model.CreateMessageCommand
import com.pryjda.service_e.model.CreatedMessageEvent
import com.pryjda.service_e.model.MessageResponse
import com.pryjda.service_e.stream_out.OutStreamCreatedMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class CreateMessageServiceImpl(@Autowired val outStreamCreatedMessage: OutStreamCreatedMessage,
                               @Autowired val createPdfService: CreatePdfService) : CreateMessageService {

    val LOGGER: Logger = Logger.getLogger(CreateMessageServiceImpl::class.toString())

    override fun send(createdMessageEvent: CreatedMessageEvent) {
        outStreamCreatedMessage.outCreateMessage().send(MessageBuilder
                .withPayload(createdMessageEvent)
                .build())
        LOGGER.info("sent via channel createdMessageEvent: ${createdMessageEvent}")
    }

    override fun createMessage(createMessageCommand: CreateMessageCommand): CreatedMessageEvent {

        val additionalText: String = "INSIDE service-E"
        val anyNumber: String = "some number"
        var messageResponse: MessageResponse = MessageResponse(createMessageCommand.messageRequest.name,
                createMessageCommand.messageRequest.priority,
                createMessageCommand.messageRequest.emitterNumber,
                additionalText,
                anyNumber)
        val output = createPdfService.createPdf()
        return CreatedMessageEvent(messageResponse, createMessageCommand.uniqueNumber, output)
    }
}