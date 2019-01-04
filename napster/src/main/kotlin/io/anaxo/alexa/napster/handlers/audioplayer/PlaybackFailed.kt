package io.anaxo.alexa.napster.handlers.audioplayer

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.Response
import com.amazon.ask.model.interfaces.audioplayer.PlaybackFailedRequest
import com.amazon.ask.request.Predicates
import io.anaxo.alexa.napster.handlers.AbstractDeviceSession
import io.anaxo.alexa.napster.model.DeviceSession
import io.anaxo.alexa.napster.repository.DeviceSessionRepository
import org.slf4j.LoggerFactory
import java.util.*


class PlaybackFailed(deviceSessionRepository: DeviceSessionRepository) : AbstractDeviceSession(deviceSessionRepository) {

    private val LOGGER = LoggerFactory.getLogger(PlaybackFailed::class.java)

    fun canHandle(input: HandlerInput): Boolean {
        return input.matches(Predicates.requestType(PlaybackFailedRequest::class.java))
    }

    override fun handle(input: HandlerInput, deviceSession: DeviceSession): Optional<Response> {
        deviceSession.playlist?.clear()

        val request = input.requestEnvelope.request as PlaybackFailedRequest
        val error = request.getError().getMessage()
        val type = request.getError().getType()

        LOGGER.warn("Playback of item failed: {},{}. Playlist cleared.", type, error)
        return Optional.empty()
    }
}
