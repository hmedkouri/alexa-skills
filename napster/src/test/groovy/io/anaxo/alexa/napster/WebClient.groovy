package io.anaxo.alexa.napster

import com.amazon.ask.model.RequestEnvelope
import com.amazon.ask.model.ResponseEnvelope
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
/**
 * @author graemerocher
 * @since 1.0
 */
@Client("/napster")
interface WebClient {

    @Post("/")
    ResponseEnvelope alexa(@Body RequestEnvelope request)

}
