package io.anaxo.alexa.trivia.handlers

import com.amazon.speech.slu.Intent
import com.amazon.speech.speechlet.IntentRequest
import com.amazon.speech.speechlet.Session
import com.amazon.speech.speechlet.SpeechletResponse
import io.anaxo.alexa.trivia.utils.AlexaUtils
import org.springframework.stereotype.Component

@Component
class AmazonCancelIntentHandler : IntentHandler {

    override fun handleIntent(intent: Intent, request: IntentRequest, session: Session): SpeechletResponse {

        val speechText = "OK. Goodbye"

        val card = AlexaUtils.newCard("See ya later...", speechText)
        val speech = AlexaUtils.newSpeech(speechText, false)

        return AlexaUtils.newSpeechletResponse(card, speech, session, true)
    }

}
