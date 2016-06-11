# Abstract

Times helps creating time comparisions with Date or long (timestamp) types. It offers a fluent interface that is described in the next section. Instants can of course be used to complement [Times](https://github.com/galan/commons/blob/master/documentation/Times.md).

# Notation
| Method | Parameter | Result | Description |
|---|---|---|---|
| `when` | Date | TimeBuilder | Creates a builder, so the following methods can be chained to create a boolean relative to the given date. There are several builder that might be created for different comparisions. The Date used here is refered as the "reference Date". |
| `equalsExactly` | Date | boolean | Compares two Dates including ms. |
| `equals` | Date | boolean | Compares two Dates ignoring ms. |
| `after` | Date | boolean | Determines that the reference Date is after the given Date. |
| `between` | Date | BetweenTimeBuilder | Starts a between-comparison, that the reference Date is between the given and the following Date. |
| `isAtLeast` | String | IsTimeBuilder | Lets check that the reference Date is at least after or before another Date. |
| `isAtLeast` | long | IsTimeBuilder | Lets check that the reference Date is at least after or before another Date. |
| `isAtMost` | String | IsTimeBuilder | Lets check that the reference Date is at most after or before another Date. |
| `isAtMost` | long | IsTimeBuilder | Lets check that the reference Date is at most after or before another Date. |
| `isWeekday` | WeekdayUnit | boolean | Checks that the given date equals to a day of the week (monday-sunday). |

# Visualized

`when(ref).isAtLeast(n).before(date)`

              ref                                    date
    ----------|-----------------|--------------------|-----------------------> time
                                |<---------n-------->|
    \\\\\\\\\\\\\\\\\\ true \\\\|//// false //////////////////////////////////

`when(ref).isAtMost(n).before(date)`

                                      ref            date
    ----------------------------|-----|--------------|-----------------------> time
                                |<----+------n------>|
    ///////////////// false ////|\\\\\\\ true \\\\\\\|//// false /////////////


`when(ref).isAtLeast(n).after(date)`

                                                     date         ref
    ----------|-----------------|--------------------|------------|----------> time
                                |<---------n-------->|
    ////////////////////////////////////// false ////|\\\\ true \\\\\\\\\\\\\\


`when(ref).isAtMost(n).after(date)`

                                date     ref
    ----------------------------|--------|-----------|-----------------------> time
                                |<-------+-----n---->|
    ///////////////// false ////|\\\\\\\ true \\\\\\\|//// false /////////////



# Examples
Check that now is between a certain range:

    when(now()).between(datePast).and(dateFuture)

Checks that a date/instant is in the future:

    when(date).after(now())

Checks that a date/instant is in the past:

    when(date).before(now())

Checks that a date/instant is at least 10 seconds in the future:

    when(date).isAtLeast("10s").after(now())
