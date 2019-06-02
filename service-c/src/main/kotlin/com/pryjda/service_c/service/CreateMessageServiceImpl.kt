package com.pryjda.service_c.service

import com.pryjda.service_c.model.*
import com.pryjda.service_c.stream_out.OutStreamCreateMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.InputStreamResource
import org.springframework.http.ResponseEntity
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service
import org.springframework.web.context.request.async.DeferredResult
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.File
import java.io.FileOutputStream
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.logging.Logger

@Service(value = "create")
class CreateMessageServiceImpl(@Autowired val outStreamCreateMessage: OutStreamCreateMessage) : CreateMessageService {

    val LOGGER: Logger = Logger.getLogger(CreateMessageServiceImpl::class.toString())

    val deferredMap: ConcurrentMap<UUID, DeferredResult<ResponseEntity<InputStreamResource>>> = ConcurrentHashMap<UUID, DeferredResult<ResponseEntity<InputStreamResource>>>()
    val emitterMap: ConcurrentMap<String, SseEmitter> = ConcurrentHashMap()

    override fun createDeferred(messageRequest: MessageRequest): DeferredResult<ResponseEntity<InputStreamResource>> {
        val deferred: DeferredResult<ResponseEntity<InputStreamResource>> = DeferredResult(120000)

        val uniqueNumber: UUID = UUID.randomUUID()
        deferredMap.put(uniqueNumber, deferred)

        val emitter: SseEmitter = SseEmitter()
        emitterMap.put(messageRequest.emitterNumber, emitter)

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

        val out = Base64.getDecoder().decode(createdMessageEvent.output)
        deferredMap[createdMessageEvent.uniqueNumber]
//                ?.setResult(ResponseEntity.ok(createdMessageEvent.messageResponse))
                ?.setResult(ResponseEntity.ok(InputStreamResource(out.inputStream())))

//        val destinationFile = File("output.pdf")
//        val fos = FileOutputStream(destinationFile)
//        fos.write(out)
//        fos.flush()
//        fos.close()

        emitterMap[createdMessageEvent.messageResponse.emitterNumber]
                ?.send(SseEmitter.event()
                        .data(createdMessageEvent.messageResponse)
                        .name("data set for emiter no.: ${createdMessageEvent.messageResponse.emitterNumber}"))
    }

    override fun getEmitter(emitterNumber: String): SseEmitter? = emitterMap[emitterNumber]
}