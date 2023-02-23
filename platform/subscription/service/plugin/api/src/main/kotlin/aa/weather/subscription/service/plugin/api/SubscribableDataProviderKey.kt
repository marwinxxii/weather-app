package aa.weather.subscription.service.plugin.api

import aa.weather.subscription.api.Subscribable
import dagger.MapKey
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class SubscribableDataProviderKey(val dataClass: KClass<out Subscribable>)
