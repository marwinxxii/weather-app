package aa.weather.navigation.navigator

import aa.weather.component.di.AppPlugin
import aa.weather.component.di.AppPluginKey
import aa.weather.navigation.navigator.screen.api.ScreenNavigatorProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
private interface PrivateNavigatorModule {
    @Binds
    @IntoMap
    @AppPluginKey(ScreenNavigatorProvider::class)
    fun bindsNavigatorPlugin(instance: ScreenNavigatorProvider): AppPlugin

    @Binds
    @Singleton
    fun bindScreenNavigatorProvider(
        instance: ViewModelScreenNavigatorProvider,
    ): ScreenNavigatorProvider
}

@Module(includes = [PrivateNavigatorModule::class])
object NavigatorModule {
    @Provides
    fun provideNavigator(plugins: NavigationPlugins) = FragmentNavigator(plugins)
}
