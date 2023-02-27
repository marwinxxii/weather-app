package aa.weather.persisted.storage.api

data class PersistenceConfiguration<T : Any>(
    val key: String,
    val ttl: Long? = null,
)
