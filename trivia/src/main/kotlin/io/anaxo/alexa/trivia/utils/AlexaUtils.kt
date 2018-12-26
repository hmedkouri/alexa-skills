package io.anaxo.alexa.trivia.utils

import com.amazon.speech.speechlet.Session
import com.amazon.speech.speechlet.SpeechletResponse
import com.amazon.speech.ui.Card
import com.amazon.speech.ui.PlainTextOutputSpeech
import com.amazon.speech.ui.Reprompt
import com.amazon.speech.ui.StandardCard
import java.text.SimpleDateFormat
import java.util.*


class AlexaUtils {

    companion object {

        val SESSION_CONVERSATION_FLAG = "conversation"
        val SamplesHelpText = "Here are some things you can say: Tell me something about a random year.  Or, what happened in nineteen eighty-nine?"
        val RepromptText = "What else can I tell you?  Say \"Help\" for some suggestions."

        fun newCard(cardTitle: String?, cardText: String): Card {

            val card = StandardCard()
            card.setTitle(cardTitle ?: "MyDemoApp")
            card.setText(cardText)

            /*
            Image cardImage = new Image();
            cardImage.setSmallImageUrl("https://www.cutlerstew.com/static/images/cutlerstew-720x480.png");
            cardImage.setLargeImageUrl("https://www.cutlerstew.com/static/images/cutlerstew-1200x800.png");

            card.setImage(cardImage);
            */

            return card
        }

        fun newSpeech(speechText: String, appendRepromptText: Boolean): PlainTextOutputSpeech {

            val speech = PlainTextOutputSpeech()
            speech.setText(if (appendRepromptText) speechText + "\n\n" + RepromptText else speechText)

            return speech
        }

        fun newSpeechletResponse(card: Card, speech: PlainTextOutputSpeech, session: Session, shouldEndSession: Boolean): SpeechletResponse {

            // Say it...
            if (inConversationMode(session) && !shouldEndSession) {
                val repromptSpeech = PlainTextOutputSpeech()
                repromptSpeech.setText(RepromptText)

                val reprompt = Reprompt()
                reprompt.setOutputSpeech(repromptSpeech)

                return SpeechletResponse.newAskResponse(speech, reprompt, card)
            } else {
                return SpeechletResponse.newTellResponse(speech, card)
            }
        }


        fun spokenDayOfWeek(date: Date, zone: TimeZone): String {

            val sdf = SimpleDateFormat("EEEE")
            sdf.timeZone = zone

            return sdf.format(date)
        }

        fun spokenDate(date: Date, zone: TimeZone): String {

            val sdf = SimpleDateFormat("MMMM d")
            sdf.timeZone = zone

            return sdf.format(date)
        }

        fun spokenTime(date: Date, zone: TimeZone): String {

            val sdf = SimpleDateFormat("hh:mm a")
            sdf.timeZone = zone

            return sdf.format(date)
        }


        fun setConversationMode(session: Session, conversationMode: Boolean) {
            if (conversationMode)
                session.setAttribute(SESSION_CONVERSATION_FLAG, "true")
            else
                session.removeAttribute(SESSION_CONVERSATION_FLAG)
        }

        fun inConversationMode(session: Session): Boolean {
            return session.getAttribute(SESSION_CONVERSATION_FLAG) != null
        }

        fun randomInt(min: Int, max: Int): Int {
            val r = Random(System.currentTimeMillis())
            return r.nextInt(max - min + 1) + min
        }
    }
}
