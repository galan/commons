# Abstract
Instants provides a fluent interface to create Instant (and Date) objects with ease, and typical pitfalls when dealing with Instant and Date can be avoided. It is best used with static imports and IDE support, so you have to make sure that you have the `de.galan.commons.time.Instants` class in eg. for eclipse in your [Java Content Assist Favorites Preferences](http://help.eclipse.org/luna/index.jsp?topic=%2Forg.eclipse.jdt.doc.user%2Freference%2Fpreferences%2Fjava%2Feditor%2Fref-preferences-content-assist-favorites.htm). 

An Instant created with Instants will be using ApplicationClock, so testing time-affected methods become quite simple.

# Notation

## Builder methods

| method | parameter | description |
|---|---|---|
|  `from` | Date | Creates a builder, so the following methods can be chained to create a date relative to the given date. For predefined dates see below. |
| `in` | int amount, DatetimeUnit unit | Shifts the date amount units to the future. |
| `before` | int amount, DatetimeUnit unit | Shifts the date amount units to the past. |
| `next` | DatetimeUnit unit | Shifts the date one unit to the future and resets all smaller units. |
| `previous` | DatetimeUnit unit | Shifts the date one unit to the past and resets all smaller units. |
| `next` | WeekdayUnit unit | Shifts the date one unit to the future. |
| `previous` | WeekdayUnit unit | Shifts the date one unit to the past. |
| `at` | int hour, int minute, int second | Sets the time to the given amount of hours, minutes and seconds. |
| `at` | String time | Sets the time to the given time in the format HH:mm:ss. |
| `atMidnight` |  | Sets the time to "00:00:00". |
| `atNoon` |  | Sets the time to "12:00:00". |
| `till` | Date | Returns the time in millis till the given second date. |
| `toDate` |  | Returns the date from the builder. |
| `toString` |  | Return a String representation from the builder in the form "yyyy-MM-dd HH:mm:ss". |
| `toString` | String format | Return a String representation from the builder in the given format. |
| `toIso8601Utc` |  | Returns a String representation from the builder in the form "yyyy-MM-dd'T'HH:mm:ss'Z", set to UTC timezone. |

## Date creation methods
* now()
* tomorrow()
* yesterday()
* date(String date) - In the form "yyyy-MM-dd HH:mm:ss"
* date(int year, int month, int day, int hour, int minute, int second)

The methods can be used by from(Date), but also independently of DateDsl, which makes the code even more helpful.

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

    from(date("2012-01-01 12:00:00")).in(2, month()).in(1, hour()).toString() // 2012-03-01 13:00:00

The order can be important:

    from(now()).next(day()).in(2, hours()).toDate() != from(now()).in(2, hours()).next(day()).toDate()
