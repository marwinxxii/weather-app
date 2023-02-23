package aa.weather.component.di

import dagger.MapKey
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class AppPluginKey(val pluginClass: KClass<out AppPlugin>)
