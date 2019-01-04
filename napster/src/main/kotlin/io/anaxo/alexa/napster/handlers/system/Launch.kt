package io.anaxo.alexa.napster.handlers.system

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.LaunchRequest
import com.amazon.ask.model.Response
import com.amazon.ask.model.interfaces.audioplayer.PlayBehavior
import com.amazon.ask.request.Predicates.requestType
import java.util.*


class Launch : RequestHandler {

    val music : String = "https://freemusicdownloads.world/wp-content/uploads/2017/05/Justin-Bieber-Sorry-PURPOSE-The-Movement.mp3"
    override fun canHandle(input: HandlerInput): Boolean {
        return input.matches(requestType(LaunchRequest::class.java))
    }

    override fun handle(input: HandlerInput): Optional<Response> {
        val speechText = "Welcome to sample playing Alexa music. I will play music now."
        return input.responseBuilder
                .withSpeech(speechText)
                .addAudioPlayerPlayDirective(PlayBehavior.REPLACE_ALL,
                0L, null, "0", music)
                .withShouldEndSession(true).build()
    }

}
