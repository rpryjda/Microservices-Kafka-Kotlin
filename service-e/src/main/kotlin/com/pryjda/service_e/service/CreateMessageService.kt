package com.pryjda.service_e.service

import com.pryjda.service_e.model.CreateMessageCommand
import com.pryjda.service_e.model.CreatedMessageEvent

interface CreateMessageService {

    fun createMessage(createMessageCommand: CreateMessageCommand): CreatedMessageEvent
    fun send(createdMessageEvent: CreatedMessageEvent)
}