package aa.weather.repository.api

import aa.weather.repository.api.data.ManagedData
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    fun <T: ManagedData> observe(cl: Class<T>): Flow<T>
}
