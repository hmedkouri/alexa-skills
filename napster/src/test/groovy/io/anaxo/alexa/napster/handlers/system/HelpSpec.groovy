package io.anaxo.alexa.napster.handlers.system


import com.amazon.ask.model.Response
import io.anaxo.alexa.napster.handlers.system.Help
import spock.lang.Specification
import spock.lang.Subject

import static io.anaxo.alexa.napster.Fixture.handlerInputForIntent

class HelpSpec extends Specification {

    @Subject
    def help = new Help()

    def "help generate happy path" () {
        given:
        Optional<Response> actual = help.handle(handlerInputForIntent("AMAZON.HelpIntent"))

        expect:
        actual.ifPresent {
            !it.shouldEndSession
            it.outputSpeech.ssml == help.helpText
            it.reprompt != null
        }
    }

    def "handles help" () {
        expect:
        help.canHandle(handlerInputForIntent("AMAZON.HelpIntent"))
    }
}
