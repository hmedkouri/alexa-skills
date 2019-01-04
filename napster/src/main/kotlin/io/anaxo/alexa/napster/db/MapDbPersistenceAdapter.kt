package io.anaxo.alexa.napster.db

import com.amazon.ask.attributes.persistence.PersistenceAdapter
import com.amazon.ask.exception.PersistenceException
import com.amazon.ask.model.RequestEnvelope
import org.mapdb.DB
import org.mapdb.HTreeMap
import org.mapdb.Serializer
import java.util.*


class MapDbPersistenceAdapter(dbFactory: DbFactory,
                              val collectionName: String,
                              val partitionKeyGenerator: (RequestEnvelope) -> String) : PersistenceAdapter {

    private val database : DB = dbFactory.createDb()
    private val keySerializer = Serializer.STRING
    private val valueSerializer = Serializer.ELSA

    private fun createMap(): HTreeMap<String, Any> {
        val hashMap : DB.HashMapMaker<String, Any> = database.hashMap(collectionName, keySerializer, valueSerializer)
        return hashMap.createOrOpen()
    }

    @Throws(PersistenceException::class)
    override fun getAttributes(envelope: RequestEnvelope): Optional<Map<String, Any>> {
        val partitionKey = partitionKeyGenerator.invoke(envelope)
        return Optional.ofNullable(createMap().get(partitionKey)) as Optional<Map<String, Any>>
    }

    @Throws(PersistenceException::class)
    override fun saveAttributes(
            envelope: RequestEnvelope,
            attributes: Map<String, Any>
    ) {
        val partitionKey = partitionKeyGenerator.invoke(envelope)
        createMap().put(partitionKey, attributes)
        database.commit()
    }
}
