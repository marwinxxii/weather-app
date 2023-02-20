package aa.weather.entities.weather.repository

interface PersistedStorage {
    suspend fun <T> getPersistedData(key: Any): T?

    fun persist(key: Any, data: Any)
}
