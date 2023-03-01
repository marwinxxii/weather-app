package aa.weather.i18n

import aa.weather.i18n.api.TranslationProvider
import android.content.Context
import javax.inject.Inject

internal class ContextTranslationProvider @Inject constructor(
    private val context: Context
) : TranslationProvider {
    override fun getTranslation(key: Int): String = context.getString(key)
}
