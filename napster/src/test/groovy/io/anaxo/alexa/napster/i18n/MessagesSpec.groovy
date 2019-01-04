package io.anaxo.alexa.napster.i18n

import spock.lang.Specification

class MessagesSpec extends Specification {

    void "simple test"() {
        given:
        Messages messages = new Messages()

        expect:
        messages.getMessage(Messages.FollowUserIntent_noPlayback, Locale.UK.toLanguageTag()) == "I am not playing any music currently and can't follow anyone."
    }
}
