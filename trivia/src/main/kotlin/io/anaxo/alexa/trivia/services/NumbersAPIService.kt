package io.anaxo.alexa.trivia.services

import io.anaxo.alexa.trivia.models.NumberTrivia
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class NumbersAPIService {

    fun getYearTrivia(year: Int): NumberTrivia? {
        val url = StringBuilder()
                .append("http://numbersapi.com/")
                .append(year)
                .append("/year?json")
                .toString()

        val restTemplate = RestTemplate()
        val response = restTemplate.getForEntity(url, NumberTrivia::class.java)

        return response.body
    }
}
