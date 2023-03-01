# Weather app

## Feature requirements

1. Primary focus is on the main screen with a weather for current location
2. Location weather items:
  - 2.1 Can change often (new items added, configurable per user, experimentation with existing)
  - 2.2 Update on their own with time (e.g. current weather)
3. Support for multiple locations, different language, different presentation options (Celsius, Fahrenheit)
4. Latest downloaded data is available in offline

## Product to technical requirements mapping

- 2.2 ⇒ Each item has different update frequency.
  Some might need to be updated in "real" time, some less often.
- 3. ⇒ Language & presentation options should be transparent for the implementation 
  Location selection / management should be offloaded to some central place
- 4. ⇒ There needs to be an abstraction in place over the source of data
- Most of the data is provided in the publish-subscribe model

## Missing parts
- Implement TTL / updating of content
- Keep track of requests running in the background: don't cancel them and re-subscribe if needed
- Background update is not implemented, but design support is in place
- Error reporting
- No verification that all app plugins can be provided in runtime
- Due to frequently changing implementation only E2E UI tests are implemented (using mock data), to
be able to iterate quickly, but have regression testing
- No option to select Celcius / Fahrenheit for a user

## Architecture
App follows
[microkernel with plugins architecture](https://www.oreilly.com/library/view/fundamentals-of-software/9781492043447/ch12.xhtml)
and uses unidirectional data flow.
It is split into multiple modules to ensure proper boundaries between layers and components.

Modules are divided into API (can be used anywhere) & implementation
(can be used only in the app and/or test utilities).

## Repo structure
- [entities](/marwinxxii/weather-app/tree/main/entities) contains data models and interfaces for retrieving them.
  They are generic enough to be re-used across the app and screens can be built using them.
- [platform](/marwinxxii/weather-app/tree/main/platform) contains generic parts which do not belong to features
- [screens](/marwinxxii/weather-app/tree/main/screens) contains screen implementations and if applicable their plugins
