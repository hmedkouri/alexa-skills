package io.anaxo.alexa.napster.handlers

import com.amazon.ask.attributes.persistence.PersistenceAdapter
import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.Context
import com.amazon.ask.model.Device
import com.amazon.ask.model.Request
import com.amazon.ask.model.RequestEnvelope
import com.amazon.ask.model.interfaces.system.SystemState
import de.jodamob.kotlin.testrunner.OpenedPackages
import de.jodamob.kotlin.testrunner.SpotlinTestRunner
import io.anaxo.alexa.napster.model.DeviceSession
import io.anaxo.alexa.napster.model.Playlist
import io.anaxo.alexa.napster.repository.DeviceSessionOperations
import org.junit.runner.RunWith
import spock.lang.Specification

@RunWith(SpotlinTestRunner)
@OpenedPackages("io.anaxo.alexa.napster")
abstract class AbstractDeviceSessionSpec extends Specification {

    Playlist playlist = Mock()
    DeviceSession deviceSession = Mock(DeviceSession)
    DeviceSessionOperations deviceSessionRepository = Mock(DeviceSessionOperations)

    void setup() {
        deviceSession.getPlaylist() >> playlist
        deviceSessionRepository.getDeviceSession(_ as HandlerInput) >> deviceSession
    }

    abstract Request createDefaultTestRequest()

    HandlerInput createTestInput(final String deviceId) {
        createTestInput(createDefaultTestRequest(), deviceId)
    }

    HandlerInput createTestInput(final Request request, final String deviceId) {
        PersistenceAdapter adapter = Mock(PersistenceAdapter)
        adapter.getAttributes(_) >> Optional.empty()
        HandlerInput.builder()
            .withRequestEnvelope(
            RequestEnvelope.builder()
                .withRequest(request)
                .withContext(
                Context.builder()
                    .withSystem(
                    SystemState.builder()
                        .withDevice(
                        Device.builder()
                            .withDeviceId(deviceId)
                            .build()
                    ).build()
                ).build())
                .build()
        ).withPersistenceAdapter(adapter)
            .build()
    }
}
