package aa.weather.screens.locations

internal sealed interface ScreenState {
    object Loading : ScreenState

    data class Loaded(
        val locations: List<LocationUIModel>,
    ) : ScreenState
}
