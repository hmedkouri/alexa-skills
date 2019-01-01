package io.anaxo.alexa.napster.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates.intentName
import java.util.*

class Exit: RequestHandler {

    override fun canHandle(input: HandlerInput?): Boolean {
        return when (input) {
            null -> false
            else -> input.matches(intentName("AMAZON.StopIntent")
                    .or(intentName("AMAZON.CancelIntent")))
        }
    }

    override fun handle(input: HandlerInput?): Optional<Response> {
        val speechText = "goodbye"
        return when (input) {
            null -> Optional.empty()
            else -> input.responseBuilder
                    .withSpeech(speechText)
                    .withSimpleCard("Finalising", speechText)
                    .build()
        }
    }
}
