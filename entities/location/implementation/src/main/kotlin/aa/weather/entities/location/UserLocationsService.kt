package aa.weather.entities.location

import aa.weather.persisted.storage.api.PersistedStorage
import aa.weather.persisted.storage.api.PersistenceConfiguration
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
private data class UserLocationsDto(
    val current: LocationDTO?,
    val all: List<LocationDTO>,
)

internal class UserLocationsService(
    private val persistedStorage: PersistedStorage,
    dispatcher: CoroutineDispatcher,
) : LocationsService {
    private val state = MutableStateFlow(UserLocations(current = null, all = emptyList()))
    private val scope = CoroutineScope(dispatcher)

    fun observeUserLocations(): Flow<UserLocations> =
        if (state.value.current == null) {
            flow {
                persistedStorage
                    .getPersistedData("user-locations", UserLocationsDto.serializer())
                    ?.takeIf { it.current != null }
                    ?.let { dto ->
                        UserLocations(
                            current = dto.current!!.let { Location(LocationID(it.id), it.name) },
                            all = dto.all.map { Location(LocationID(it.id), it.name) }
                        )
                    }
                    ?.also { state.tryEmit(it) }
                emitAll(state)
            }
        } else {
            state
        }

    @Synchronized
    override fun setCurrentlySelectedLocation(location: LocationID) {
        val newValue = state.value.let {
            it.copy(current = it.current?.copy(location) ?: Location(location, "Name"))
        }
        state.tryEmit(newValue)
        scope.launch { persistNewValue(newValue) }
    }

    @Synchronized
    private fun persistNewValue(newValue: UserLocations) {
        persistedStorage.persist(
            PersistenceConfiguration(key = "user-locations"),
            UserLocationsDto.serializer(),
            UserLocationsDto(
                current = newValue.current?.let {
                    LocationDTO(it.id.value, it.name)
                },
                all = newValue.all.map { LocationDTO(it.id.value, it.name) }
            ),
        )
    }
}

@Serializable
private data class LocationDTO(
    val id: String,
    val name: String,
)
