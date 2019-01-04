package io.anaxo.alexa.napster.repository

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import java.util.*


abstract class AbstractAttributesRepository<T>(private val attributeKey: String,
                                               private val attributesSupplier: (HandlerInput) -> MutableMap<String, T>) {

    companion object {
        fun <T> PERSISTENT() = { input: HandlerInput -> input.attributesManager.persistentAttributes as MutableMap<String, T> }
        fun <T> SESSION() = { input: HandlerInput -> input.attributesManager.sessionAttributes }
        fun <T> REQUEST() = { input: HandlerInput -> input.attributesManager.requestAttributes }
    }

    private fun getAttributes(input: HandlerInput): MutableMap<String, T> {
        return attributesSupplier.invoke(input)
    }

    protected operator fun get(input: HandlerInput): T? {
        return getAttributes(input)[attributeKey]
    }

    protected fun put(value: T, input: HandlerInput): T? {
        val oldValue = getAttributes(input).put(attributeKey, value)
        if (PERSISTENT<T>().equals(attributesSupplier) && !Objects.equals(value, oldValue)) {
            input.attributesManager.requestAttributes["persistentAttributesDirty"] = java.lang.Boolean.TRUE
            input.attributesManager.savePersistentAttributes()
        }
        return oldValue
    }

    protected fun getOrDefault(input: HandlerInput, defaultValueSupplier: (HandlerInput) -> T): T {
        return get(input) ?: return defaultValueSupplier.invoke(input)
    }
}
