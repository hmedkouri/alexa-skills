package io.anaxo.alexa.napster.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import io.anaxo.alexa.napster.model.DeviceSession
import io.anaxo.alexa.napster.repository.DeviceSessionOperations
import java.util.*


abstract class AbstractDeviceSession(private val deviceSessionRepository: DeviceSessionOperations) : RequestHandler {

    protected abstract fun handle(input: HandlerInput, deviceSession: DeviceSession): Optional<Response>

    override fun handle(input: HandlerInput): Optional<Response> {
        val deviceSession = deviceSessionRepository.getDeviceSession(input)
        try {
            return handle(input, deviceSession)
        } finally {
            deviceSessionRepository.saveDeviceSession(deviceSession, input)
        }
    }
}
