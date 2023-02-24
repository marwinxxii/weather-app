package aa.weather.app

import aa.weather.navigation.navigator.NavigatorHolderViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelInitializer
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
internal class RootModule {
    @Provides
    fun provideVMFactory(
        navigatorViewModel: Provider<NavigatorHolderViewModel>,
        rootViewModel: Provider<RootViewModel>,
    ): ViewModelProvider.Factory = ViewModelProvider.Factory.from(
        ViewModelInitializer(NavigatorHolderViewModel::class.java) { navigatorViewModel.get() },
        ViewModelInitializer(RootViewModel::class.java) { rootViewModel.get() },
    )
}
