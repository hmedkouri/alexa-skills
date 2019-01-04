package io.anaxo.alexa.napster.handlers.system

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import java.util.*

class Help: RequestHandler {

    val helpText : String = "This skill provides you control of Napster. What would you like to do next?"

    override fun canHandle(input: HandlerInput?): Boolean {
        return when (input) {
            null -> false
            else -> input.matches(Predicates.intentName("AMAZON.HelpIntent"))
        }
    }

    override fun handle(input: HandlerInput?): Optional<Response> {
        return when (input) {
            null -> Optional.empty()
            else -> input.responseBuilder
                    .withSpeech(helpText)
                    .withSimpleCard("Finalising", helpText)
                    .withReprompt(helpText)
                    .build()
        }
    }
}
