package io.anaxo.alexa.trivia

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AlexSpringBootWebApplication

fun main(args: Array<String>) {
    runApplication<AlexSpringBootWebApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}
