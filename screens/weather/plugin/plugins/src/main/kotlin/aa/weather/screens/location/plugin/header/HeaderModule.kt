package aa.weather.screens.location.plugin.header

import aa.weather.screens.location.plugin.api.Plugin
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module
private interface HeaderPluginPrivateModule {
    @Binds
    @IntoSet
    fun bindPlugin(instance: HeaderPlugin): Plugin<*>
}

@Module(includes = [HeaderPluginPrivateModule::class])
interface HeaderModule
