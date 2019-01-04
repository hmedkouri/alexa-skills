package io.anaxo.alexa.napster.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.Request
import com.amazon.ask.model.RequestEnvelope
import io.anaxo.alexa.napster.model.DeviceSession
import io.anaxo.alexa.napster.model.Playlist
import io.anaxo.alexa.napster.repository.DeviceSessionRepository
import spock.lang.Specification

abstract class AbstractDeviceSessionSpec extends Specification {

    Playlist playlist = GroovyMock(Playlist)
    DeviceSession deviceSession = GroovyMock(DeviceSession)
    DeviceSessionRepository deviceSessionRepository

    void setup() {
        deviceSession.getPlaylist() >> playlist
        deviceSessionRepository = GroovyMock(){
            getDeviceSession(_ as HandlerInput) >> deviceSession
        }
    }

    abstract Request createDefaultTestRequest()

    HandlerInput createTestInput() {
        createTestInput(createDefaultTestRequest())
    }

    HandlerInput createTestInput(final Request request) {
        HandlerInput.builder()
            .withRequestEnvelope(RequestEnvelope.builder()
            .withRequest(request)
            .build())
            .build()
    }
}
