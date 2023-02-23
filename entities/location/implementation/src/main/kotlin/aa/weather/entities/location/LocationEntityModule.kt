package aa.weather.entities.location

import aa.weather.component.di.AppPlugin
import aa.weather.component.di.AppPluginKey
import aa.weather.persisted.storage.api.PersistedStorage
import aa.weather.subscription.service.plugin.api.SubscribableDataProvider
import aa.weather.subscription.service.plugin.api.SubscribableDataProviderKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named
import javax.inject.Singleton

@Module
private object PrivateLocationEntityModule {
    @Provides
    @IntoMap
    @SubscribableDataProviderKey(UserLocations::class)
    fun provideUserLocationsProvider(instance: UserLocationsProvider): SubscribableDataProvider =
        instance

    @Provides
    @Singleton
    fun provideUserLocationsService(
        persistedStorage: PersistedStorage,
        @Named("IO") dispatcher: CoroutineDispatcher,
    ) = UserLocationsService(persistedStorage, dispatcher)

    @Provides
    fun provideLocationsService(instance: UserLocationsService): LocationsService = instance
}

@Module(includes = [PrivateLocationEntityModule::class])
interface LocationEntityModule {
    @Binds
    @IntoMap
    @AppPluginKey(LocationsService::class)
    fun provideLocationsServicePlugin(instance: LocationsService): AppPlugin
}
