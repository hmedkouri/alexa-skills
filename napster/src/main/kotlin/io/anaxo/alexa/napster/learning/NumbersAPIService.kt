package io.anaxo.alexa.napster.learning

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import javax.inject.Singleton


@Singleton
class NumbersAPIService(@param:Client("http://numbersapi.com/") private val httpClient: RxHttpClient) {

    fun getYearTrivia(year: Int): NumberTrivia? {
        val url = StringBuilder()
                .append(year)
                .append("/year?json")
                .toString()

        val req = HttpRequest.GET<Any>(url)
        val response = httpClient.retrieve(req, NumberTrivia::class.java)

        return response.firstElement().blockingGet()
    }
}
