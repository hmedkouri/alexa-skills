package io.anaxo.alexa.napster.i18n

import java.text.MessageFormat
import java.util.*

class Messages {

    companion object {

        internal const val FollowUserIntent_card_text = "FollowUserIntent.card.text"
        internal const val FollowUserIntent_card_title = "FollowUserIntent.card.title"
        internal const val FollowUserIntent_noPlayback = "FollowUserIntent.noPlayback"
        internal const val HelpIntent_card_text = "HelpIntent.card.text"
        internal const val HelpIntent_card_title = "HelpIntent.card.title"
        internal const val HelpIntent_reprompt_speech = "HelpIntent.reprompt.speech"
        internal const val HelpIntent_speech = "HelpIntent.speech"
        internal const val LaunchIntent_reprompt_speech = "LaunchIntent.reprompt.speech"
        internal const val LaunchIntent_speech = "LaunchIntent.speech"
        internal const val LikeTrackIntent_noPlayback = "LikeTrackIntent.noPlayback"
        internal const val LinkAccount_speech = "LinkAccount.speech"
        internal const val PlayMyFavoritesIntent_card_text = "PlayMyFavoritesIntent.card.text"
        internal const val PlayMyFavoritesIntent_noFavorites_speech = "PlayMyFavoritesIntent.noFavorites.speech"
        internal const val PlayMyStreamIntent_card_text = "PlayMyStreamIntent.card.text"
        internal const val PlayMyStreamIntent_noTracks_speech = "PlayMyStreamIntent.noTracks.speech"
        internal const val PlayMyStreamIntent_noTracks_card_text = "PlayMyStreamIntent.noTracks.card.text"
        internal const val PlayMyStreamIntent_noTracks_card_title = "PlayMyStreamIntent.noTracks.card.title"
        internal const val RepeatIntent_noPlayback = "RepeatIntent.noPlayback"
        internal const val ShuffleOffIntent_speech = "ShuffleOffIntent.speech"
        internal const val ShuffleOnIntent_speech = "ShuffleOnIntent.speech"
        internal const val StartOverIntent_noTracks = "StartOverIntent.noTracks"
        internal const val TellCurrentTrackIntent_card_text = "TellCurrentTrackIntent.card.text"
        internal const val TellCurrentTrackIntent_card_title = "TellCurrentTrackIntent.card.title"
        internal const val TellCurrentTrackIntent_noPlayback = "TellCurrentTrackIntent.noPlayback"
        internal const val UnknownIntent_speech = "UnknownIntent.speech"
        internal const val LikeTrackIntent_speech = "LikeTrackIntent.speech"
        internal const val FollowUserIntent_speech = "FollowUserIntent.speech"
        internal const val TellCurrentTrackIntent_speech = "TellCurrentTrackIntent.speech"
    }

    private val messageSource: MessageSource = MessageSource()

    fun getMessage(code: String, locale: String, args: Array<String>): String {
        return messageSource.getMessage(code, args, locale)
    }

    fun getMessage(code: String, defaultCode: String, locale: String, args: Array<String>): String {
        try {
            return messageSource.getMessage(code, args, locale)
        } catch (exception: Exception) {
            return messageSource.getMessage(defaultCode, args, locale)
        }
    }

    private class MessageSource {
        fun getMessage(code: String, args: Array<String>, locale: String = "en-UK"): String {
            val translator = Translator(Locale.forLanguageTag(locale))
            return if (args.isEmpty()) {
                translator.getTranslation(code)
            } else {
                translator.getTranslation(code, args)
            }
        }
    }

    private class Translator(private val locale: Locale) {

        private val bundle = ResourceBundle.getBundle("messages", locale)

        fun getTranslation(key: String): String = getStringAsUTF8(key)

        fun getTranslation(key: String, vararg foo: Any): String {
            val formatter = MessageFormat(getStringAsUTF8(key), locale)
            return formatter.format(foo)
        }

        /**
         * ResourceBundle assumes read Strings to be ISO encoded, so we convert it to UTF-8.
         */
        private fun getStringAsUTF8(key: String): String = String(bundle.getString(key).toByteArray(Charsets.ISO_8859_1), Charsets.UTF_8)
    }
}
