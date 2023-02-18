package aa.weather.screens.location.plugin.header

import aa.weather.screens.location.plugin.api.PluginConfiguration
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module
private interface HeaderPluginPrivateModule {
    @Binds
    @IntoSet
    fun bindHeaderPlugin(headerPluginConfiguration: HeaderPluginConfiguration): PluginConfiguration
}

@Module(includes = [HeaderPluginPrivateModule::class])
interface HeaderPluginModule