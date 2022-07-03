[![Build Status](https://img.shields.io/travis/galan/commons.svg?style=flat)](https://travis-ci.org/galan/commons)
[![Maven Central](https://img.shields.io/maven-central/v/de.galan/commons.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/de.galan/commons)
[![License](https://img.shields.io/github/license/galan/commons.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0.html)

# commons

Supportive basic but missing functionality, that simplifies Java delopment.


# Integration with Maven

Just add the following dependency (see [releases](https://github.com/galan/commons/releases) for latest version):

    <dependency>
    	<groupId>de.galan</groupId>
    	<artifactId>commons</artifactId>
    	<version>x.y.z</version>
    </dependency>


# Overview

## de.galan.commons.func.supplier

Various `Supplier` for eg. Long-sequences and random alphanumeric strings.

## de.galan.commons.logging

Logging facades and utilities, based on log4j2-api so they can be used with any logging-framework such as log4j, log4j2, logback, slf4j, etc..

* [Say](https://github.com/galan/commons/blob/master/documentation/logging.Say.md) - Facade to log without the need to declare a logger. Messages paramters can be passed as
  MDC/ThreadContext json-field (useful for logstash). Take a look at the examples.
* Slogr - Retrieves the Slf4j Logger without passing the actual calling class, which is a typical copy&paste pitfall. Can be avoided by using Say.

## de.galan.commons.net

* CommonProxy - Simple wrapper for a proxy in the format [username[:password]@]host[:port]
* UrlUtil - Decoding/encoding urls with `Charset`

## de.galan.commons.net.flux

* [Flux](https://github.com/galan/commons/blob/master/documentation/net.flux.Flux.md) - A fluent http/rest-client interface for Java

## de.galan.commons.net.mail

* MailMessenger - Sending multipart mails using a fluent interface.

## de.galan.commons.test

* AbstractTestParent - Resets the `ApplicationClock` and prints the name of each test-method executed
* FixedDateSupplier - Sets the `ApplicationClock` to a fixed time (best used with Instants)
* SimpleWebserverTestParent - Test containing an lightweight
  http-server ([simpleframework](http://www.simpleframework.org/)).
* Tests - Helping methods in tests for eg. loading and comparing test resources, some date assertions, test directory handling.

## de.galan.commons.time

* ApplicationClock - Clock that acts as time emitter for an application. Uses a java.time.clock.SystemClock (UTC) by default.
* [Durations](https://github.com/galan/commons/blob/master/documentation/time.Durations.md) - Utility class to handle and calculate human readable time durations, such as "2h 30m"
* [Instants](https://github.com/galan/commons/blob/master/documentation/time.Instants.md) - Fluent simplified time creation and modification.
* [Times](https://github.com/galan/commons/blob/master/documentation/time.Times.md) - Fluent time comparision
* Sleeper - Sleeps a duration (long or human readable String - as in `Durations`) without throwing an InterruptedException

## de.galan.commons.util

Various common functionality, eg.

* BOM - Adding and remove BOM (byteordermark) UTF-8 headers
* Contained - Checks if an element is in an (vararg) array
* Generics - Helper for generics, eg casting to get rid of unchecked warnings.
* JvmUtil - Provides access to information about the currently running JVM and some process-control (eg. fluent Termination of JVM, ShutdownHook methods).
* JmxUtil - Helpermethod to register MBeans
* [Measure](https://github.com/galan/commons/blob/master/documentation/util.Measure.md) - Measures and logs duration of Callable or Runnables, if required only every n-invokations.
* MessageBox - Draws a nice messagebox to the logged output.
* Pair - Simplified key/value class
* [Retryable](https://github.com/galan/commons/blob/master/documentation/util.Retryable.md) - Runs a `Callable` or `ExceptionalRunnable` (`Runnable` with Exceptions) until it runs
  without Exception at least for the specified times of retries in a compact fluent manner.


# Changelog

Read the [CHANGELOG.md](CHANGELOG.md) for curated release information.


# Compatibility

* Latest Java 8 version: 0.16.4
* Latest Java 11 version: 1.4.0
* Starting Java 17 with 2.0.0
