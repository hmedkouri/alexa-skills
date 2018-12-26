package io.anaxo.alexa.trivia.handlers

import com.amazon.speech.slu.Intent
import com.amazon.speech.speechlet.IntentRequest
import com.amazon.speech.speechlet.Session
import com.amazon.speech.speechlet.SpeechletResponse
import io.anaxo.alexa.trivia.services.NumbersAPIService
import io.anaxo.alexa.trivia.utils.AlexaUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class RandomYearIntentHandler : IntentHandler {

    val logger = LoggerFactory.getLogger(RandomYearIntentHandler::class.java)

    @Autowired
    private val numbersService: NumbersAPIService? = null


    override fun handleIntent(intent: Intent, request: IntentRequest, session: Session): SpeechletResponse {

        // Get some trivia about a random year between 1900 and today.
        val year = AlexaUtils.randomInt(1900, LocalDate.now().getYear())
        val trivia = numbersService!!.getYearTrivia(year)

        val card = AlexaUtils.newCard("Random Trivia", trivia!!.text)
        val speech = AlexaUtils.newSpeech(trivia.text, AlexaUtils.inConversationMode(session))

        return AlexaUtils.newSpeechletResponse(card, speech, session, false)
    }
}
