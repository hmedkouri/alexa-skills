package io.anaxo.alexa.napster

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.Intent
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Request
import com.amazon.ask.model.RequestEnvelope

class Fixture {

    static HandlerInput handlerInputForIntent(String intentName) {
        handlerInputForRequest(
            IntentRequest.builder().withIntent(
                Intent.builder().withName(intentName).build()

            ).build()
        )
    }

    static HandlerInput handlerInputForRequest(Request request) {
        HandlerInput.builder().withRequestEnvelope(
            RequestEnvelope.builder().withRequest(request).build()
        ).build()
    }
}
