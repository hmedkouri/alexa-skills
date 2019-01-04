package io.anaxo.alexa.napster.repository

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import io.anaxo.alexa.napster.model.DeviceSession
import io.anaxo.alexa.napster.utils.SpeechletRequestUtil

open class DeviceSessionRepository : AbstractAttributesRepository<DeviceSession>("deviceSession", AbstractAttributesRepository.PERSISTENT()) {

    fun getDeviceSession(input: HandlerInput): DeviceSession {
        return getOrDefault(input) { i: HandlerInput -> DeviceSession(deviceId = SpeechletRequestUtil.getDeviceId(i.getRequestEnvelope())) }
    }

    fun saveDeviceSession(deviceSession: DeviceSession, input: HandlerInput): DeviceSession? {
        return put(deviceSession, input)
    }
}
