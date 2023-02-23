package aa.weather.persisted.storage.api

import aa.weather.component.di.AppPlugin
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy

interface PersistedStorage : AppPlugin {
    suspend fun <T : Any> getPersistedData(
        key: String,
        deserializationStrategy: DeserializationStrategy<T>,
    ): T?

    fun <T : Any> persist(
        configuration: PersistenceConfiguration<T>,
        serializationStrategy: SerializationStrategy<T>,
        data: T,
    )
}

suspend fun <T : Any> PersistedStorage.getOrPersist(
    configuration: PersistenceConfiguration<T>,
    deserializationStrategy: DeserializationStrategy<T>,
    serializationStrategy: SerializationStrategy<T>,
    producer: suspend () -> T?,
): T? =
    getPersistedData(configuration.key, deserializationStrategy)
        ?: producer()?.also { persist(configuration, serializationStrategy, it) }
