package aa.weather.entities.location

import aa.weather.component.di.AppPlugin

interface LocationsService : AppPlugin {
    fun setCurrentlySelectedLocation(location: LocationID)
}
