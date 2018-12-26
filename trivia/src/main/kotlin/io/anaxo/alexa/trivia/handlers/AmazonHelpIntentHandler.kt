package io.anaxo.alexa.trivia.handlers

import com.amazon.speech.slu.Intent
import com.amazon.speech.speechlet.IntentRequest
import com.amazon.speech.speechlet.Session
import com.amazon.speech.speechlet.SpeechletResponse
import io.anaxo.alexa.trivia.utils.AlexaUtils
import org.springframework.stereotype.Component

@Component
class AmazonHelpIntentHandler : IntentHandler {

    override fun handleIntent(intent: Intent, request: IntentRequest, session: Session): SpeechletResponse {

        val card = AlexaUtils.newCard("Help", AlexaUtils.SamplesHelpText)
        val speech = AlexaUtils.newSpeech(AlexaUtils.SamplesHelpText, false)

        return AlexaUtils.newSpeechletResponse(card, speech, session, false)
    }

}
