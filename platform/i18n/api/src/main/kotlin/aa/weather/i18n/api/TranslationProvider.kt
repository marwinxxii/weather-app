package aa.weather.i18n.api

import aa.weather.component.di.AppPlugin

interface TranslationProvider : AppPlugin {
    fun getTranslation(key: Int): String
}
