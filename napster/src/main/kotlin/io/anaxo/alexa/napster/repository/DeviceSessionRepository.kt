package io.anaxo.alexa.napster.repository

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import io.anaxo.alexa.napster.model.DeviceSession
import io.anaxo.alexa.napster.utils.SpeechletRequestUtil
import javax.inject.Singleton

@Singleton
class DeviceSessionRepository : AbstractAttributesRepository<DeviceSession>("deviceSession", AbstractAttributesRepository.PERSISTENT()), DeviceSessionOperations {

    override fun getDeviceSession(input: HandlerInput): DeviceSession {
        return getOrDefault(input) { i: HandlerInput -> DeviceSession(deviceId = SpeechletRequestUtil.getDeviceId(i.requestEnvelope)) }
    }

    override fun saveDeviceSession(deviceSession: DeviceSession, input: HandlerInput): DeviceSession? {
        return put(deviceSession, input)
    }
}
