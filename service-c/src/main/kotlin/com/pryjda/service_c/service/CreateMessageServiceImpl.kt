package com.pryjda.service_c.service

import com.pryjda.service_c.model.CreateMessageCommand
import com.pryjda.service_c.model.CreatedMessageEvent
import com.pryjda.service_c.model.MessageRequest
import com.pryjda.service_c.model.MessageResponse
import com.pryjda.service_c.stream_out.OutStreamCreateMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service
import org.springframework.web.context.request.async.DeferredResult
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.logging.Logger

@Service(value = "create")
class CreateMessageServiceImpl(@Autowired val outStreamCreateMessage: OutStreamCreateMessage) : CreateMessageService {

    val LOGGER: Logger = Logger.getLogger(CreateMessageServiceImpl::class.toString())

    val deferredMap: ConcurrentMap<UUID, DeferredResult<ResponseEntity<MessageResponse>>> = ConcurrentHashMap()

    override fun createDeferred(messageRequest: MessageRequest): DeferredResult<ResponseEntity<MessageResponse>> {
        val deferred: DeferredResult<ResponseEntity<MessageResponse>> = DeferredResult(120000)

        val uniqueNumber: UUID = UUID.randomUUID()
        deferredMap.put(uniqueNumber, deferred)

        val createMessageCommand: CreateMessageCommand = CreateMessageCommand(messageRequest, uniqueNumber)

        outStreamCreateMessage.outCreateMessage().send(MessageBuilder
                .withPayload(createMessageCommand)
                .build())

        LOGGER.info("sent via channel createMessageCommand: ${createMessageCommand.toString()}")
        LOGGER.info("sending deferred object: ${deferred.toString()}")

        return deferred
    }

    override fun handle(createdMessageEvent: CreatedMessageEvent) {
        LOGGER.info("invocing method setResult on deferred object: ${deferredMap[createdMessageEvent.uniqueNumber]}")
        deferredMap[createdMessageEvent.uniqueNumber]
                ?.setResult(ResponseEntity.ok(createdMessageEvent.messageResponse))
    }

}