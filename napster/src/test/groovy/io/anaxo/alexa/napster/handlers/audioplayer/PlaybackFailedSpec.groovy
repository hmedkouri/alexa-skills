package io.anaxo.alexa.napster.handlers.audioplayer

import com.amazon.ask.model.Request
import com.amazon.ask.model.interfaces.audioplayer.Error
import com.amazon.ask.model.interfaces.audioplayer.ErrorType
import com.amazon.ask.model.interfaces.audioplayer.PlaybackFailedRequest
import io.anaxo.alexa.napster.handlers.AbstractDeviceSessionSpec
import spock.lang.Subject

class PlaybackFailedSpec extends AbstractDeviceSessionSpec {

    @Subject
    PlaybackFailed handler

    void setup() {
        handler = new PlaybackFailed(deviceSessionRepository)
    }

    void "can handle PlaybackFailed intent" () {
        expect:
        handler.canHandle(createTestInput("test"))
    }

    void handle() {
        when:
        !handler.handle(createTestInput("test")).present

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
