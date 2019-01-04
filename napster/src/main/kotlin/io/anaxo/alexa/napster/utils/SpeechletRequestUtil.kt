package io.anaxo.alexa.napster.utils

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.Request
import com.amazon.ask.model.RequestEnvelope
import com.amazon.ask.model.interfaces.audioplayer.AudioPlayerState
import com.amazon.ask.model.interfaces.system.SystemState
import org.apache.commons.lang3.StringUtils
import java.lang.reflect.InvocationTargetException
import java.util.*


class SpeechletRequestUtil {

    private fun SpeechletRequestUtil(){}

    companion object {

        private val LOCALE_DEFAULT = Locale.ENGLISH
        val MESSAGE_NOUSERID = "RequestContext.NoUserId"
        val MESSAGE_NODEVICEID = "RequestContext.NoDeviceId"

        val MESSAGEKEY_NOT_PLAYING = "AudioPlayer.NotPlaying"

        private val UNKNOWN = "unknown"

        private fun getSystemState(requestEnvelope: RequestEnvelope): Optional<SystemState> {
            return Optional.of(requestEnvelope)
                    .map { t -> t.context }
                    .map { t -> t.system }
        }

        private fun shortenId(theId: String): String {
            // TODO: are rightmost characters variable enough?
            return StringUtils.right(theId, 7)
        }


        fun getUserId(requestEnvelope: RequestEnvelope): String {
            return getSystemState(requestEnvelope)
                    .map { it.user }
                    .map { it.userId }
                    .orElseThrow { RuntimeException(MESSAGE_NOUSERID) }
        }

        fun getShortUserId(requestEnvelope: RequestEnvelope): String {
            try {
                return shortenId(getUserId(requestEnvelope))
            } catch (ignored: Exception) {
                return UNKNOWN
            }
        }

        fun getDeviceId(requestEnvelope: RequestEnvelope): String {
            return getSystemState(requestEnvelope)
                    .map { it.device }
                    .map { it.deviceId }
                    .orElseThrow { RuntimeException(MESSAGE_NODEVICEID) }
        }

        fun getShortDeviceId(requestEnvelope: RequestEnvelope): String {
            try {
                return shortenId(getDeviceId(requestEnvelope))
            } catch (ignored: Exception) {
                return UNKNOWN
            }
        }

        private fun getLocaleString(request: Request): String? {
            try {
                // Stupid stupid stupid. Many request types have getLocale(),
                // but there is no common interface or base class
                return request.javaClass.getMethod("getLocale").invoke(request) as String?
            } catch (ex: NoSuchMethodException) {
                return null
            } catch (ex: IllegalAccessException) {
                return null
            } catch (ex: InvocationTargetException) {
                return null
            }

        }

        private fun getLocale(requestEnvelope: RequestEnvelope): Locale {
            return Optional.of(requestEnvelope)
                    .map { it.request }
                    .map { getLocaleString(it) }
                    .map { Locale.forLanguageTag(it) }
                    .orElse(LOCALE_DEFAULT)
        }

        fun getLocale(input: HandlerInput): Locale {
            return getLocale(input.requestEnvelope)
        }

        fun getAudioPlayerState(requestEnvelope: RequestEnvelope): Optional<AudioPlayerState> {
            return Optional.of(requestEnvelope)
                    .map { it.context }
                    .map { it.audioPlayer }
        }

        fun getAudioPlayerToken(requestEnvelope: RequestEnvelope): String {
            return getAudioPlayerState(requestEnvelope)
                    .map { it.token }
                    .orElseThrow { RuntimeException(MESSAGEKEY_NOT_PLAYING) }
        }

        fun getAudioPlayerOffsetInMilliseconds(requestEnvelope: RequestEnvelope): Long {
            return getAudioPlayerState(requestEnvelope)
                    .map { it.offsetInMilliseconds }
                    .orElseThrow { RuntimeException(MESSAGEKEY_NOT_PLAYING) }
        }

    }
}
