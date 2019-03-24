package com.pryjda.service_c.service

import com.pryjda.service_c.model.CreatedMessageEvent
import com.pryjda.service_c.model.MessageRequest
import com.pryjda.service_c.model.MessageResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.context.request.async.DeferredResult

interface CreateMessageService {

    fun createDeferred(messageRequest: MessageRequest): DeferredResult<ResponseEntity<MessageResponse>>
    fun handle(createdMessageEvent: CreatedMessageEvent)
}