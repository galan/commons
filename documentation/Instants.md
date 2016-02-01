# Abstract
Instants provides a fluent interface to create Instant (and Date) objects with ease, and typical pitfalls when dealing with Instant and Date can be avoided. It is best used with static imports and IDE support, so you have to make sure that you have the `de.galan.commons.time.Instants` class in eg. for eclipse in your [Java Content Assist Favorites Preferences](http://help.eclipse.org/luna/index.jsp?topic=%2Forg.eclipse.jdt.doc.user%2Freference%2Fpreferences%2Fjava%2Feditor%2Fref-preferences-content-assist-favorites.htm). 

# Notation

## Instant creation methods

| method | parameter | description |
|---|---|---|
| `now` | | Instant represeting now, using the global ApplicationClock |
| `tomorrow` | | Instant in one day from now |
| `yesterday` | | Instant before one day from now |
| `instantLocal` | String | Instant in the form "yyyy-MM-dd HH:mm:ss[.SSS]", using the local timezone |
| `instant` | String, ZoneId | Instant in the form "yyyy-MM-dd HH:mm:ss[.SSS]", using the given timezone |
| `instantUtc` | String | Instant in the form "yyyy-MM-dd HH:mm:ss[.SSS]", using UTC as timezone |
| `instant` | long | Instant using the milliseconds since the epoch |
| `date` | long | Date using the milliseconds since the epoch |
| `dateNow` | | Date representing now, using the global ApplicationClock |
| `dateLocal` | String | Date in the form "yyyy-MM-dd HH:mm:ss", using the local timezone |
| `dateUtc` | String | Date in the form ""yyyy-MM-dd'T'HH:mm:ss[.SSS]'Z'", using UTC as timezone |

The methods can be used by `from(Instant)` or `from(Date)`, but also independently of Instants, which makes the methods even more helpful.

An Instant created with Instants will be using ApplicationClock, so testing time-affected code becomes quite simple, since you don't have to mock the hell out of your code.


## Builder methods

| method | parameter | description |
|---|---|---|
| `from` | Instant/Date | Creates a builder, so the following methods can be chained to create a instant or date relative to the given value. |
| `in` | int, DatetimeUnit | Shifts the value the given amount of the given unit to the future. |
| `before` | int, DatetimeUnit | Shifts the value the given amount of the given units to the past. |
| `next` | DatetimeUnit | Shifts the value one unit to the future and resets all smaller units. |
| `previous` | WeekdayUnit | Shifts the value one unit to the past. |
| `truncate` | DatetimeUnit | Resets all values from the given unit downwards |
| `next` | WeekdayUnit | Shifts the value one unit to the future. |
| `previous` | DatetimeUnit | Shifts the value one unit to the past and resets all smaller units. |
| `at` | int hour, int minute, int second | Sets the time to the given amount of hours, minutes and seconds. |
| `at` | String | Sets the time to the given time in the format "HH:mm:ss". |
| `atMidnight` |  | Sets the time to "00:00:00". |
| `atNoon` |  | Sets the time to "12:00:00". |
| `till` | Instant/Date | Returns the time in millis till the given second value. |
| `with` | TemporalAdjuster | Applies the adjuster to the current value |
| `zone` | ZoneId | Uses the given zone |
| `toInstant` |  | Returns the Instant from the builder. |
| `toDate` |  | Returns the Date from the builder. |
| `toZdt` |  | Returns the ZonedDateTime with UTC as ZoneId from the builder. |
| `toZdt` | ZoneId | Returns the ZonedDateTime with the given ZoneId from the builder. |
| `toLong` |  | Returns the milliseconds since epoch from the builder. |
| `toString` |  | Return a String representation from the builder in the form "yyyy-MM-dd HH:mm:ss.SSS". |
| `toString` | String | Return a String representation from the builder in the given format. |
| `toStringLocal` | | Return a String representation from the builder in the form "yyy-MM-dd HH:mm:ss.SSS" in the local timezone. |
| `toStringLocal` | String | Return a String representation from the builder in the given format, set to the local timezone. |
| `toStringIsoUtc` |  | Returns a String representation from the builder in the form "yyyy-MM-dd'T'HH:mm:ss.SSS'Z", set to UTC timezone. |

## Units

### DatetimeUnit
* second(), seconds()
* minute(), minutes()
* hour(), hours()
* day(), days()
* week(), weeks()
* month(), months()
* year(), years()

### WeekdayUnit
* monday()
* tuesday()
* wednesday()
* thursday()
* friday()
* saturday()
* sunday()

# Examples
Next Monday at 12:00:00:

    from(now()).next(monday()).atNoon()

The month before:

    from(now()).previous(month())

Three days and one month later at 17:10:

    from(now()).in(3, days()).in(1, month()).at("17:10:00")

Methods are additive:

    from(date("2012-01-01 12:00:00")).in(2, month()).in(1, hour()).toString() // 2012-03-01 13:00:00.000

The order can be important:

    from(now()).next(day()).in(2, hours()).toDate() != from(now()).in(2, hours()).next(day()).toDate()
