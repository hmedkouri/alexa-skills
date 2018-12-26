package io.anaxo.alexa.trivia.handlers

import com.amazon.speech.slu.Intent
import com.amazon.speech.speechlet.IntentRequest
import com.amazon.speech.speechlet.Session
import com.amazon.speech.speechlet.SpeechletResponse

interface IntentHandler {

    fun handleIntent(intent: Intent, request: IntentRequest, session: Session): SpeechletResponse

}
