package com.pryjda.service_c.service

import com.pryjda.service_c.model.CreatedMessageEvent
import com.pryjda.service_c.model.MessageRequest
import com.pryjda.service_c.model.MessageResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.context.request.async.DeferredResult
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

interface CreateMessageService {

    fun createDeferred(messageRequest: MessageRequest): DeferredResult<ResponseEntity<MessageResponse>>
    fun handle(createdMessageEvent: CreatedMessageEvent)
    fun getEmitter(emitterNumber: String): SseEmitter?
}