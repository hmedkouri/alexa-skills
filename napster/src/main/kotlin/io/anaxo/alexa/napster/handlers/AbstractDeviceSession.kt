package io.anaxo.alexa.napster.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.Response
import io.anaxo.alexa.napster.model.DeviceSession
import io.anaxo.alexa.napster.repository.DeviceSessionRepository
import java.util.*


abstract class AbstractDeviceSession(val deviceSessionRepository: DeviceSessionRepository) {

    protected abstract fun handle(input: HandlerInput, deviceSession: DeviceSession): Optional<Response>

    fun handle(input: HandlerInput): Optional<Response> {
        val deviceSession = deviceSessionRepository.getDeviceSession(input)
        try {
            return handle(input, deviceSession)
        } finally {
            deviceSessionRepository.saveDeviceSession(deviceSession, input)
        }
    }
}
