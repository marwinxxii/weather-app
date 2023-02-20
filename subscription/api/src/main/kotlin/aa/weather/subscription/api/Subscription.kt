package aa.weather.subscription.api

data class Subscription<T : Subscribable>(
    val topicClass: Class<T>,
    val dataFilters: Set<SubscriptionFilter> = emptySet(),
    val arguments: SubscriptionArguments? = null,
)

fun <T : SubscriptionFilter> Subscription<*>.hasDataFilter(filterClass: Class<T>) =
    dataFilters.firstOrNull { it.javaClass === filterClass } != null

fun <T : Subscribable> Subscription<T>.addDataFilter(filter: SubscriptionFilter) =
    copy(dataFilters = dataFilters.toMutableSet().apply { add(filter) }.toSet())

// type is checked by takeIf
@Suppress("UNCHECKED_CAST")
fun <P : Subscribable> Subscription<*>.takeIfTopic(expected: Class<P>): Subscription<P>? =
    takeIf { topicClass === expected } as? Subscription<P>
