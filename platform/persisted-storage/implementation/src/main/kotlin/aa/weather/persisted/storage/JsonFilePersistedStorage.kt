package aa.weather.persisted.storage

import aa.weather.persisted.storage.api.PersistedStorage
import aa.weather.persisted.storage.api.PersistenceConfiguration
import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream

@OptIn(ExperimentalSerializationApi::class)
class JsonFilePersistedStorage(
    context: Context,
    private val dispatcher: CoroutineDispatcher,
) : PersistedStorage {
    private val cacheDir = context.cacheDir.resolve("data-cache-json")
    private val json = Json

    // TODO remove after error handling is introduced
    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    override suspend fun <T : Any> getPersistedData(
        key: String,
        deserializationStrategy: DeserializationStrategy<T>,
    ): T? {
        return resolveFile(key)
            .takeIf { it.exists() }
            ?.let {
                try {
                    json.decodeFromStream(deserializationStrategy, it.inputStream())
                } catch (e: Exception) {
                    it.delete()
                    null
                }
            }
    }

    private fun resolveFile(key: String) =
        cacheDir.resolve("$key.json")

    // block reading until it is persisted?
    override fun <T : Any> persist(
        configuration: PersistenceConfiguration<T>,
        serializationStrategy: SerializationStrategy<T>,
        data: T,
    ) {
        CoroutineScope(dispatcher).launch {
            resolveFile(configuration.key).also {
                it.parentFile!!.mkdirs()
                json.encodeToStream(serializationStrategy, data, it.outputStream())
            }
        }
    }
}
