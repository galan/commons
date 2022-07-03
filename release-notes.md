# 2.0.1

* Improved Instants for nano precision of 9 digits.

# 2.0.0

* Updated to Java 17

# 1.4.0

* Update dependencies
* Removed commons-codec dependency
* Added `Sugar.first(..)`
* Added Say env-var to remove braces in messages for fields (experimental)
* Added smaller convenience methods in `Durations`
* Deprecated `Sugar.not(..)`

# 1.3.0

* Update dependency (including log4shell)

# 1.2.1

* Update parent dependency

# 1.2.0

* Improved logs when incorrect amount of arguments is passed.

+ Removed commons-io:commons-io

* Marked de.galan.commons.time.Dates for removal. Use de.galan.commons.time.Instants instead.
* Added FileSupport and IOSupport class
* Updated libraries and parent
* Removed deprecated de.galan.commons.util.RetriableTask
* Removed deprecated de.galan.commons.test.ApplicationClockResetRule

# 1.1.0

* Added exception metadata to MDC in logging payload

# 1.0.1

* Support for nanos in Instants class
* Jackson security update

# 1.0.0

* First major version
* Requires Java 11
