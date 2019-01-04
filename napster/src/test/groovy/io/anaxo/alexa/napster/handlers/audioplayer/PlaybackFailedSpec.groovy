package io.anaxo.alexa.napster.handlers.audioplayer

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.Request
import com.amazon.ask.model.RequestEnvelope
import com.amazon.ask.model.interfaces.audioplayer.Error
import com.amazon.ask.model.interfaces.audioplayer.ErrorType
import com.amazon.ask.model.interfaces.audioplayer.PlaybackFailedRequest
import io.anaxo.alexa.napster.model.DeviceSession
import io.anaxo.alexa.napster.model.Playlist
import io.anaxo.alexa.napster.repository.DeviceSessionRepository
import spock.lang.Specification
import spock.lang.Subject

class PlaybackFailedSpec extends Specification {

    Playlist playlist = GroovyMock(Playlist)
    DeviceSession deviceSession = GroovyMock(DeviceSession)
    DeviceSessionRepository deviceSessionRepository

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

    @Subject
    PlaybackFailed handler

    void setup() {
        deviceSession.getPlaylist() >> playlist
        deviceSessionRepository = GroovyMock()
        deviceSessionRepository.getDeviceSession(_ as HandlerInput) >> deviceSession

        handler = new PlaybackFailed(deviceSessionRepository)
    }

    void "can handle PlaybackFailed intent" () {
        expect:
        handler.canHandle(createTestInput())
    }

    void handle() {
        when:
        !handler.handle(createTestInput()).present

        then:
        1 * playlist.clear()
    }

    Request createDefaultTestRequest() {
        return PlaybackFailedRequest.builder()
            .withError(Error.builder()
            .withMessage("TestMessage")
            .withType(ErrorType.MEDIA_ERROR_INTERNAL_DEVICE_ERROR)
            .build())
            .build()
    }
}
