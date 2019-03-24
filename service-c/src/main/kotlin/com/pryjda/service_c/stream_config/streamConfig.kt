package com.pryjda.service_c.stream_config

import com.pryjda.service_c.stream_in.InStreamCreatedMessage
import com.pryjda.service_c.stream_out.OutStreamCreateMessage
import org.springframework.cloud.stream.annotation.EnableBinding

@EnableBinding(OutStreamCreateMessage::class,
        InStreamCreatedMessage::class)
class streamConfig