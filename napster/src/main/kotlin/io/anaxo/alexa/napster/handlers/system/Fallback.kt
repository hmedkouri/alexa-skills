package io.anaxo.alexa.napster.handlers.system;

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates.intentName
import java.util.*
import javax.inject.Singleton

@Singleton
class Fallback: RequestHandler {

     override fun canHandle(input: HandlerInput?) : Boolean {
         return when (input) {
             null -> false
             else -> input.matches(intentName("AMAZON.FallbackIntent"))
         }
     }

     override fun handle(input: HandlerInput?) : Optional<Response> {
         val speechText = "Sorry, I don't know that. You can say try saying help!"
         return when (input) {
             null -> Optional.empty()
             else -> input.getResponseBuilder()
                 .withSpeech(speechText)
                 .withSimpleCard("HelloWorld", speechText)
                 .withReprompt(speechText)
                 .build()
         }
     }
 }
