package io.anaxo.alexa.napster.handlers.exception

import com.amazon.ask.dispatcher.exception.ExceptionHandler
import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.Response
import io.anaxo.alexa.napster.i18n.Messages
import java.util.*
import javax.inject.Singleton

@Singleton
class NapsterException(val messages: Messages) : ExceptionHandler {

    override fun canHandle(handlerInput: HandlerInput, e: Throwable): Boolean {
        return true
    }

    override fun handle(handlerInput: HandlerInput, e: Throwable): Optional<Response> {
        return handlerInput.responseBuilder
                .withSpeech(Messages.UnknownIntent_speech)
                .withSimpleCard("Napster", Messages.UnknownIntent_speech)
                .withReprompt(Messages.UnknownIntent_speech)
                .withShouldEndSession(false)
                .build()
    }
}
