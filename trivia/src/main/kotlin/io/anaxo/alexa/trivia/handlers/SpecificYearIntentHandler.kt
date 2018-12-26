package io.anaxo.alexa.trivia.handlers

import com.amazon.speech.slu.Intent
import com.amazon.speech.speechlet.IntentRequest
import com.amazon.speech.speechlet.Session
import com.amazon.speech.speechlet.SpeechletResponse
import io.anaxo.alexa.trivia.services.NumbersAPIService
import io.anaxo.alexa.trivia.utils.AlexaUtils
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SpecificYearIntentHandler : IntentHandler {

    private val logger = LoggerFactory.getLogger(SpecificYearIntentHandler::class.java)

    @Autowired
    private val numbersService: NumbersAPIService? = null


    override fun handleIntent(intent: Intent, request: IntentRequest, session: Session): SpeechletResponse {

        val speechText = StringBuffer()

        // Get the Talent slot
        val yearSlot = intent.getSlot("Year")
        val yearStr = if (yearSlot == null) null else StringUtils.trimToNull(yearSlot.getValue())

        if (yearStr != null) {

            if (logger.isInfoEnabled)
                logger.info("Got year slot value = '$yearStr'.")

            try {
                // parse the year as an Integer and lookup trivia
                val year = Integer.parseInt(yearStr)
                val trivia = numbersService!!.getYearTrivia(year)

                speechText.append(trivia!!.text)
            } catch (e: NumberFormatException) {
                speechText.append("I do not understand what you mean by $yearStr.  Please say a year.")
            }

        } else {
            speechText.append("I didn't hear which year.  Please say something like \"Tell me something about the year nineteen eighty-four.\"")
        }

        val card = AlexaUtils.newCard("Trivia", speechText.toString())
        val speech = AlexaUtils.newSpeech(speechText.toString(), AlexaUtils.inConversationMode(session))

        return AlexaUtils.newSpeechletResponse(card, speech, session, false)
    }
}
