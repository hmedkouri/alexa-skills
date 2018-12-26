package io.anaxo.alexa.trivia

import com.amazon.speech.json.SpeechletRequestEnvelope
import com.amazon.speech.json.SpeechletResponseEnvelope
import com.amazon.speech.slu.Intent
import com.amazon.speech.speechlet.IntentRequest
import com.amazon.speech.speechlet.IntentRequest.DialogState
import com.amazon.speech.speechlet.Session
import com.amazon.speech.speechlet.SpeechletRequest
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.client.RestClientException
import java.util.*


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AlexSpringBootWebApplicationTests {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @LocalServerPort
    private val port: Int = 0

    @Autowired
    private val mapper: ObjectMapper? = null

    @Test
    @Throws(RestClientException::class, JsonProcessingException::class)
    fun testRandomYearIntent() {
        val uri = "http://localhost:$port/alexa"

        val envelope  = SpeechletRequestEnvelope.builder<SpeechletRequest>()
                .withVersion("1.8.1")
                .withSession(Session.builder()
                        .withIsNew(true)
                        .withSessionId(UUID.randomUUID().toString())
                        .build())
                .withRequest(IntentRequest.builder()
                        .withRequestId(UUID.randomUUID().toString())
                        .withIntent(Intent.builder()
                                .withName("RandomYearIntent")
                                .build())
                        .withTimestamp(Date())
                        .withLocale(Locale.US)
                        .withDialogState(DialogState.STARTED)
                        .build())
                .build()

        val response = this.restTemplate.exchange(
                uri,
                HttpMethod.POST,
                getPostRequestHeaders(envelope),
                SpeechletResponseEnvelope::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body!!.getResponse().getCard().getTitle()).isEqualTo("Random Trivia")
    }

    @Throws(JsonProcessingException::class)
    private fun <T> getPostRequestHeaders(request: T): HttpEntity<String> {
        val acceptTypes = ArrayList<MediaType>()
        acceptTypes.add(MediaType.APPLICATION_JSON_UTF8)

        val reqHeaders = HttpHeaders()
        reqHeaders.contentType = MediaType.APPLICATION_JSON_UTF8
        reqHeaders.accept = acceptTypes

        return HttpEntity(mapper!!.writeValueAsString(request), reqHeaders)
    }

}
