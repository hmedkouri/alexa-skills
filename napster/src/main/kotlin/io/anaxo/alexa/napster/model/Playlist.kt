package io.anaxo.alexa.napster.model

import java.util.*



class Playlist(var items: List<String> = listOf()) {

    constructor(vararg values: String) : this() {
        items = values.asList()
    }

    fun clear() {
        items = listOf()
    }

    fun add(entry : String) {
        items += listOf(entry)
    }

    fun hasItem(token: String): Boolean {
        return find(token).isPresent
    }

    private fun find(token: String): Optional<String> {
        return items.stream().filter { it == token }.findAny()
    }

    fun hasNext(token: String): Boolean {
        return findNextOf(token).isPresent
    }

    fun nextOf(token: String): String? {
        return findNextOf(token)
                .orElseThrow{ Exception("MESSAGEKEY_LAST_SONG") }
    }

    private fun findNextOf(token: String): Optional<String> {
        return Optional.of(get(token))
                .map { items.indexOf(token) }
                .map { index -> if (index >= 0) index + 1 else null }
                .map { index -> if (index != null && index < items.size) items[index] else null }
    }

    fun get(token: String): Any {
        return find(token)
                .orElseThrow {
                    NoSuchElementException("No playlist entry with $token found.")
                }
    }
}
