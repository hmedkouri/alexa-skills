package io.anaxo.alexa.napster

import com.amazon.ask.model.Intent
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.LaunchRequest
import com.amazon.ask.model.RequestEnvelope
import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

import java.time.OffsetDateTime
import java.time.ZoneId

class ApplicationSpec extends Specification {

    @Shared @AutoCleanup EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)

    void "help intent should return goodbye"() {
        given:
        WebClient client = embeddedServer.applicationContext.getBean(WebClient)

        expect:
        client.alexa(
            RequestEnvelope.builder()
                .withRequest(
                    IntentRequest.builder().withIntent(
                         Intent.builder().withName("AMAZON.HelpIntent").build()
                    ).build()
                ).build()
            ) != null
    }

    void "handle launch request"() {
        given:
        def LOCALE = "en-US"
        OffsetDateTime timestamp = OffsetDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault())
        def launchRequest = LaunchRequest.builder().withRequestId("rId").withLocale(LOCALE).withTimestamp(timestamp).build()
        WebClient client = embeddedServer.applicationContext.getBean(WebClient)

        expect:
        client.alexa(RequestEnvelope.builder().withRequest(launchRequest).build()) != null
    }
}
