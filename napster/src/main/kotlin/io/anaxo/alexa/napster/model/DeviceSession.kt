package io.anaxo.alexa.napster.model

data class DeviceSession(val deviceId: String) {

    val playlist: Playlist = Playlist()
    val lastAudioPlayerToken: String? = null
    val lastAudioPlayerOffsetInMilliseconds: Long? = null
}
