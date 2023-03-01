package aa.weather.i18n

import aa.weather.component.di.AppPlugin
import aa.weather.component.di.AppPluginKey
import aa.weather.i18n.api.TranslationProvider
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
object I18NModule {
    @Provides
    fun provideTranslationProvider(context: Context): TranslationProvider =
        ContextTranslationProvider(context)

    @Provides
    @IntoMap
    @AppPluginKey(TranslationProvider::class)
    fun provideTranslationPlugin(instance: TranslationProvider): AppPlugin = instance
}
