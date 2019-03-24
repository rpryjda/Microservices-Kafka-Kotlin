package com.pryjda.service_e.stream_config

import com.pryjda.service_e.stream_in.InStreamCreateMessage
import com.pryjda.service_e.stream_out.OutStreamCreatedMessage
import org.springframework.cloud.stream.annotation.EnableBinding

@EnableBinding(OutStreamCreatedMessage::class,
        InStreamCreateMessage::class)
class streamConfig