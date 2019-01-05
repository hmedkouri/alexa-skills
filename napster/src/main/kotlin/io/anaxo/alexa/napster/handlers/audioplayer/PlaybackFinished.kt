package io.anaxo.alexa.napster.handlers.audioplayer

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import com.amazon.ask.model.interfaces.audioplayer.PlaybackFinishedRequest
import com.amazon.ask.request.Predicates
import java.util.*
import javax.inject.Singleton

@Singleton
class PlaybackFinished : RequestHandler {

    override fun canHandle(input: HandlerInput): Boolean {
        return input.matches(Predicates.requestType(PlaybackFinishedRequest::class.java))
    }

    override fun handle(input: HandlerInput?): Optional<Response> {
        return Optional.empty()
    }
}
