package aa.weather.screens.locations

import aa.weather.entities.location.LocationsService
import aa.weather.screen.api.FragmentScope
import aa.weather.subscription.service.api.SubscriptionService
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelInitializer
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Component(modules = [LocationsFragmentModule::class])
@FragmentScope
internal interface LocationsFragmentComponent {
    fun inject(fragment: LocationsFragment)

    // factory ensures that all external dependencies are provided
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance subscriptionService: SubscriptionService,
            @BindsInstance locationsService: LocationsService,
        ): LocationsFragmentComponent
    }
}

@Module
private object LocationsFragmentModule {
    @Provides
    fun provideVMFactory(
        viewModel: Provider<LocationsViewModel>,
    ): ViewModelProvider.Factory = ViewModelProvider.Factory.from(
        ViewModelInitializer(LocationsViewModel::class.java) { viewModel.get() },
    )
}
