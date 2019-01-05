package io.anaxo.alexa.napster.repository

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import io.anaxo.alexa.napster.model.DeviceSession

interface DeviceSessionOperations {

    fun getDeviceSession(input: HandlerInput): DeviceSession

    fun saveDeviceSession(deviceSession: DeviceSession, input: HandlerInput): DeviceSession?
}
