package aa.weather.screen.api

import javax.inject.Scope

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Scope
annotation class FragmentScope
