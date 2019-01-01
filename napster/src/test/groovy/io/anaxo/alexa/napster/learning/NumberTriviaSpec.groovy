package io.anaxo.alexa.napster.learning


import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class NumberTriviaSpec extends Specification {

    @Shared @AutoCleanup EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)

    void "basic test"() {
        given:
        NumberTriviaClient client = embeddedServer.applicationContext.getBean(NumberTriviaClient)

        expect:
        client.trivia(1986) != null
    }
}
