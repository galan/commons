# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).


## [2.1.1] - 2024-02-25

### Changes

* Improved log-level evaluation in `Say` for performance.

## [2.1.0] - 2023-06-18

### Changes

* Improved average calculation and logging for `Measure`.
* Extended `JvmUtil.terminate()` with optional fluent method call for a throwable.
* Updated parent
* Updated libraries and plugins


## [2.0.2] - 2022-12-01

### Added

* Added `de.galan.io.resources.Resources` to read files more conveniently

### Changes

* Added `Pair.of(..)` method
* Bumped Jackson library version


## [2.0.1] - 2022-07-03

### Added

* Added CHANGELOG.md (and removed release-notes.md)

### Changes

* Improved Instants support for nano precision of 9 digits.


## [2.0.0] - 2022-07-01

### Changes

* Updated to Java 17


## [1.4.0] - 2022-06-16

### Added

* Added `Sugar.first(..)`
* Added Say env-var to remove braces in messages for fields (experimental)
* Added smaller convenience methods in `Durations`

### Changes

* Update dependencies

### Deprecated

* Deprecated `Sugar.not(..)`

### Removed

* Removed commons-codec dependency


## [1.3.0] - 2022-01-04

### Security

* Update dependency (including log4shell)


## [1.2.1] - 2021-03-03

### Changes

* Update parent dependency


## [1.2.0] - 2021-02-01

### Added

* Added FileSupport and IOSupport class

### Changes

* Improved logs when incorrect amount of arguments is passed.
* Updated libraries and parent

### Deprecated

* Marked `de.galan.commons.time.Dates` for removal. Use `de.galan.commons.time.Instants` instead.

### Removed

* Removed commons-io:commons-io
* Removed deprecated `de.galan.commons.util.RetriableTask`
* Removed deprecated `de.galan.commons.test.ApplicationClockResetRule`


## [1.1.0] - 2020-01-01

### Added

* Added exception metadata to MDC in logging payload


## [1.0.1] - 2019-10-02

### Added

* Support for nanos in Instants class

### Security

* Jackson security update


## [1.0.0] - 2019-08-12

### Changes

* First major version
* Requires Java 11


[Unreleased]: https://github.com/galan/commons/compare/v2.1.1...HEAD

[2.1.1]: https://github.com/galan/commons/compare/v2.1.0...v2.1.1

[2.1.0]: https://github.com/galan/commons/compare/v2.0.2...v2.1.0

[2.0.2]: https://github.com/galan/commons/compare/v2.0.1...v2.0.2

[2.0.1]: https://github.com/galan/commons/compare/v2.0.0...v2.0.1

[2.0.0]: https://github.com/galan/commons/compare/v1.4.0...v2.0.0

[1.4.0]: https://github.com/galan/commons/compare/v1.2.1...v1.4.0

[1.2.1]: https://github.com/galan/commons/compare/v1.2.0...v1.2.1

[1.2.0]: https://github.com/galan/commons/compare/v1.1.0...v1.2.0

[1.1.0]: https://github.com/galan/commons/compare/v1.0.1...v1.1.0

[1.0.1]: https://github.com/galan/commons/compare/v1.0.0...v1.0.1

[1.0.0]: https://github.com/galan/commons/releases/tag/v1.0.0
