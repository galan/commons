# Abstract
Durations simplify working with string formatted periods. It is a conversion utility class to deal with a period of milliseconds in a human readable format. Eg. setting in a configuration file the duration of a timeout to `150000` ms is not as obvious as writing `2m 30s`.

Supported units:

| Unit | Parameter | Constant |
|---|---|---|
| (none) | milliseconds | MS_MILLISECOND |
| `ms`| milliseconds | MS_MILLISECOND |
| `s` | seconds | MS_SECOND |
| `m` | minute | MS_MINUTE |
| `h` | hour | MS_HOUR |
| `d` | day | MS_DAY |
| `w` | week | MS_WEEK |

# Learning by examples
## Transformations

From millis to human readable format:

    Durations.humanize(150000L); // "2m30s"
    Durations.humanize(9823749237L); // "113d16h49m9s237ms"
    Durations.humanize(9823749237L, " "); // "113d 16h 49m 9s 237ms"
    Durations.humanize(0L); // "0ms"

From human readable format to millis:

    Durations.dehumanize("2m30s"); // 150000L
    Durations.dehumanize("113d16h49m9s237ms"); // 9823749237L
    Durations.dehumanize("113d 16h 49m 9s 237ms  "); // Spaces can be used
    Durations.dehumanize("0ms"); // 0L
    Durations.dehumanize("10"); // 10L
    Durations.dehumanize("0"); // 0L

## Time ago/left

    Instant endTime = ...; // Date also possible
    Durations.timeLeft(endTime); // depends on current time, eg. "12s 510ms

    Instant pointInPast = ...; // Date also possible
    Durations.timeAgo(pointInPast); // depends on current time, eg. "31s 745ms

## Interoperability

Conversion from and to `java.time.Duration`:

    Duration d = Durations.toDuration("2m10s");
    String t = Durations.fromDuration(d); // "2m10s"

