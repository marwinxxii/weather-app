package aa.weather.screens.location.plugin.forecast.daily

import aa.weather.screens.location.plugin.api.Plugin
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module
private interface PrivateDailyForecastModule {
    @Binds
    @IntoSet
    fun bindPlugin(instance: DailyForecastPlugin): Plugin<*>
}

@Module(includes = [PrivateDailyForecastModule::class])
interface DailyForecastModule
