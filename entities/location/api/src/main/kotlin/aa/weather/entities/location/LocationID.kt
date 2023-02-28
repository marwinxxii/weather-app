package aa.weather.entities.location

@JvmInline
value class LocationID(val value: String) {
    override fun toString(): String = value
}
