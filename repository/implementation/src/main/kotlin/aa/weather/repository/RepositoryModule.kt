package aa.weather.repository

import aa.weather.repository.api.DataRepository
import dagger.Module
import dagger.Provides

@Module
object RepositoryModule {
    @Provides
    fun provideRepository(): DataRepository = MockRepository()
}
