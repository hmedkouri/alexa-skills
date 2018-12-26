package io.anaxo.alexa.trivia.handlers

import com.amazon.speech.json.SpeechletRequestEnvelope
import com.amazon.speech.speechlet.*
import io.anaxo.alexa.trivia.utils.AlexaUtils
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HandlerSpeechlet : SpeechletV2 {

    private val logger = LoggerFactory.getLogger(HandlerSpeechlet::class.java)

    @Autowired
    private val beanFactory: BeanFactory? = null

    override fun onSessionStarted(requestEnvelope: SpeechletRequestEnvelope<SessionStartedRequest>) {

        // This is invoked when a new Alexa session is started.  Any initialization logic would go here.
        // You can store stuff in the Alexa session, for example, by calling:
        // 		Session session = requestEnvelope.getSession();
    }

    override fun onLaunch(requestEnvelope: SpeechletRequestEnvelope<LaunchRequest>): SpeechletResponse {

        // This is called when the skill is started with no specific intent.
        // Such as "Alexa, ask MyDemoApp."
        // When this happens, we should provide a welcome message and prompt
        // the user to ask a question.

        // Set a session variable so that we know we're in conversation mode.
        val session = requestEnvelope.session
        AlexaUtils.setConversationMode(session, true)

        // Create the initial greeting speech.
        val speechText = "Hello. " + AlexaUtils.SamplesHelpText

        val card = AlexaUtils.newCard("Welcome!", speechText)
        val speech = AlexaUtils.newSpeech(speechText, false)

        return AlexaUtils.newSpeechletResponse(card, speech, session, false)
    }

    override fun onIntent(requestEnvelope: SpeechletRequestEnvelope<IntentRequest>): SpeechletResponse {

        // This is invoked whenever an intent is invoked for our application.  We need
        // to figure out when intent it is, then delegate to a handler for that specific
        // intent.
        val request = requestEnvelope.request
        val session = requestEnvelope.session

        // Get the intent
        val intent = request.intent
        if (intent != null) {
            // Derive the handler's bean name
            val intentName = intent.name
            var handlerBeanName = intentName + "Handler"

            // If this is an Amazon Intent, change the handler name to better
            // match up to a Spring bean name.  For example, the intent AMAZON.HelpIntent should
            // be changed to AmazonHelpIntent.
            handlerBeanName = StringUtils.replace(handlerBeanName, "AMAZON.", "Amazon")
            handlerBeanName = handlerBeanName.substring(0, 1).toLowerCase() + handlerBeanName.substring(1)

            if (logger.isInfoEnabled)
                logger.info("About to invoke handler '$handlerBeanName' for intent '$intentName'.")

            // Handle the intent by delegating to the designated handler.
            try {
                val handlerBean = beanFactory!!.getBean(handlerBeanName)

                if (handlerBean != null) {
                    if (handlerBean is IntentHandler) {
                        return handlerBean.handleIntent(intent, request, session)
                    }
                }
            } catch (e: Exception) {
                logger.error("Error handling intent $intentName", e)
            }

        }


        // Handle unknown intents.  Ask the user for more info.
        // Start a conversation (if not started already) and say that we did not understand the intent
        AlexaUtils.setConversationMode(session, true)

        val errorText = "I don't know what that means. " + AlexaUtils.SamplesHelpText

        val card = AlexaUtils.newCard("Dazed and Confused", errorText)
        val speech = AlexaUtils.newSpeech(errorText, false)

        return AlexaUtils.newSpeechletResponse(card, speech, session, false)
    }

    override fun onSessionEnded(requestEnvelope: SpeechletRequestEnvelope<SessionEndedRequest>) {

    }
}
