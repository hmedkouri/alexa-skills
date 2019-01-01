package io.anaxo.alexa.napster.handlers


import com.amazon.ask.model.Response
import spock.lang.Specification
import spock.lang.Subject

import static io.anaxo.alexa.napster.Fixture.*

class ExitSpec extends Specification {

    @Subject
    def exit = new Exit()

    def "exit generate happy path" () {
        given:
        Optional<Response> actual = exit.handle(handlerInputForIntent("AMAZON.CancelIntent"))

        expect:
        actual.ifPresent {
            !it.shouldEndSession
            it.outputSpeech.ssml == "goodbye"
        }
    }

    def "handles stop" () {
        expect:
        exit.canHandle(handlerInputForIntent("AMAZON.StopIntent"))
    }

    def "handles cancel" () {
        expect:
        exit.canHandle(handlerInputForIntent("AMAZON.CancelIntent"))
    }
}
