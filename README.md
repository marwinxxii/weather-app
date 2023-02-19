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
