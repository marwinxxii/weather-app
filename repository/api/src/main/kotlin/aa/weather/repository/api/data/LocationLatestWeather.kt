package aa.weather.repository.api.data

data class LocationLatestWeather(
    val locationName: String,
    val weather: Set<Weather>,
    val currentTemperature: Temperature,
) : ManagedData
