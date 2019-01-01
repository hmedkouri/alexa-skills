package io.anaxo.alexa.napster.learning


import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
/**
 * @author graemerocher
 * @since 1.0
 */
@Client("/trivia")
interface NumberTriviaClient {

    @Post("/")
    String trivia(Integer year)
}
