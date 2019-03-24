package com.pryjda.service_c.controller

import com.pryjda.service_c.model.MessagePriority
import com.pryjda.service_c.model.MessageRequest
import com.pryjda.service_c.model.MessageResponse
import com.pryjda.service_c.service.CreateMessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.async.DeferredResult
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.logging.Logger

@RestController
class CreateMessageController(@Autowired @Qualifier("create") val createMessageService: CreateMessageService) {

    val LOGGER: Logger = Logger.getLogger(CreateMessageController::class.toString())

    @PostMapping("/messages")
    fun createMessage(@RequestBody messageRequest: MessageRequest): DeferredResult<ResponseEntity<MessageResponse>> {
        LOGGER.info("sending deferred object")
        LOGGER.info("got messageRequest: ${messageRequest.toString()}")

        return createMessageService.createDeferred(messageRequest)
    }

    @GetMapping("/messages-request")
    fun getMessageExampleRequest() = MessageRequest("test", MessagePriority.COMMON, "12345")

    @GetMapping("/emitter")
    fun getEmitter(@RequestParam emitterNumber: String): SseEmitter? =
            createMessageService.getEmitter(emitterNumber)
}