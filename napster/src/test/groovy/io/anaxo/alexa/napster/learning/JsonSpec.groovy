package io.anaxo.alexa.napster.learning

import com.amazon.ask.model.Intent
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.RequestEnvelope
import com.amazon.ask.util.JacksonSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.context.ApplicationContext
import io.micronaut.context.DefaultApplicationContext
import spock.lang.Specification

class JsonSpec extends Specification {

    ApplicationContext applicationContext
    ObjectMapper mapper

    void setup(){
        applicationContext = new DefaultApplicationContext("test").start()
        mapper = applicationContext.getBean(ObjectMapper.class)
    }

    void cleanup(){
        applicationContext?.close()
    }

    void "check how micronaut serialize RequestEnvelop"() {

        given:
        def envelope = RequestEnvelope.builder()
            .withRequest(
            IntentRequest.builder().withIntent(
                Intent.builder().withName("AMAZON.HelpIntent").build()
            ).build()
        ).build()

        expect:
        mapper.writeValueAsString(envelope) == new JacksonSerializer().serialize(envelope)
    }

    void "deserialize launch request"() {
        given:
        String launchRequest = new File("src/test/resources/launch.json").text

        expect:
        mapper.readValue(launchRequest, RequestEnvelope) == new JacksonSerializer().deserialize(launchRequest, RequestEnvelope)
    }

}
