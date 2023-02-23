package aa.weather.persisted.storage

import aa.weather.component.di.AppPlugin
import aa.weather.component.di.AppPluginKey
import aa.weather.persisted.storage.api.PersistedStorage
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named
import javax.inject.Singleton

@Module
object PersistedStorageModule {
    @Provides
    @Singleton
    fun providePersistedStorage(
        context: Context,
        @Named("IO") dispatcher: CoroutineDispatcher,
    ): PersistedStorage = JsonFilePersistedStorage(context, dispatcher)

    @Provides
    @IntoMap
    @AppPluginKey(PersistedStorage::class)
    fun providePersistedStoragePlugin(instance: PersistedStorage): AppPlugin = instance
}
