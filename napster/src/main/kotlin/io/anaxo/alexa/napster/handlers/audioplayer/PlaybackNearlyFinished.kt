package io.anaxo.alexa.napster.handlers.audioplayer

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.Response
import com.amazon.ask.model.interfaces.audioplayer.PlayBehavior.ENQUEUE
import com.amazon.ask.model.interfaces.audioplayer.PlaybackNearlyFinishedRequest
import com.amazon.ask.request.Predicates
import io.anaxo.alexa.napster.handlers.AbstractDeviceSession
import io.anaxo.alexa.napster.model.DeviceSession
import io.anaxo.alexa.napster.repository.DeviceSessionRepository
import org.slf4j.LoggerFactory
import java.util.*


class PlaybackNearlyFinished(deviceSessionRepository: DeviceSessionRepository) : AbstractDeviceSession(deviceSessionRepository) {

    private val LOGGER = LoggerFactory.getLogger(PlaybackNearlyFinished::class.java)

    fun canHandle(input: HandlerInput): Boolean {
        return input.matches(Predicates.requestType(PlaybackNearlyFinishedRequest::class.java))
    }

    override fun handle(input: HandlerInput, deviceSession: DeviceSession): Optional<Response> {
        val request = input.requestEnvelope.request as PlaybackNearlyFinishedRequest

        val currentToken = request.token
        val playlist = deviceSession.playlist

        if (!playlist.hasItem(currentToken)) {
            LOGGER.error("Current Playlist has no item with the token {}.", currentToken)
            return Optional.empty()
        }

        if (!playlist.hasNext(currentToken)) {
            LOGGER.debug("Playlist finished with token {}.", currentToken)
            return Optional.empty()
        }

        val currentUrl = playlist.get(currentToken)
        val nextUrl = playlist.nextOf(currentToken)

        return input.responseBuilder
                .addAudioPlayerPlayDirective(ENQUEUE, null, currentToken, nextUrl, nextUrl)
                .build()
    }
}
