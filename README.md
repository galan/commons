[![Build Status](https://img.shields.io/travis/galan/commons.svg?style=flat)](https://travis-ci.org/galan/commons)
[![Maven Central](https://img.shields.io/maven-central/v/de.galan/commons.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/de.galan/commons)
[![License](https://img.shields.io/github/license/galan/commons.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0.html)

# commons
Generic functionality, utility classes and test-helper, that can be used to accomplish typical common tasks in a java project.

# Integration with Maven
Just add the following dependency (see [releases](https://github.com/galan/commons/releases) for latest version):

    <dependency>
    	<groupId>de.galan</groupId>
    	<artifactId>commons</artifactId>
    	<version>x.y.z</version>
    </dependency>

# Overview

## de.galan.commons.func.supplier
Different `Supplier` for eg. Long-sequences and random alphanumeric strings. 

## de.galan.commons.logging
Logging facades and utilities, based on log4j2-api so they can be used with any logging-framework such as log4j, log4j2, logback, slf4j, etc..

* Say - Facade to log without the need to declare a logger. Messages paramters can be passed as MDC/ThreadContext json-field (useful for logstash). Take a look at the examples.
* Slogr - Retrieves the Slf4j Logger without passing the actual calling class, which is a typical copy&paste  pitfall. Can be avoided by using Say.

## de.galan.commons.net
* CommonProxy - Simple wrapper for a proxy in form [username[:password]]@host[:port]
* UrlUtil - Decoding/encoding urls with `Charset`

## de.galan.commons.net.flux
* [Flux](https://github.com/galan/commons/blob/master/documentation/net.flux.Flux.md) - A fluent http/rest-client interface for Java (originated from own project)

## de.galan.commons.net.mail
* MailMessenger - Sending multipart mails using a fluent interface.

## de.galan.commons.test
* AbstractTestParent - Resets the `ApplicationClock` and prints the name of each test-method executed
* FixedDateSupplier - Sets the `ApplicationClock` to a fixed time (best used with Instants)
* SimpleWebserverTestParent - Test containing an lightweight http-server ([simpleframework](http://www.simpleframework.org/)).
* Tests - Helping methods in tests for eg. loading and comparing test resources, some date assertions, test directory handling.

## de.galan.commons.time
* ApplicationClock - Clock that acts as time emitter for an application. Uses a java.time.clock.SystemClock (UTC) by default.
* Durations - Utility class to handle and calculate human readable time durations, such as "2h 30m"
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
* MessageBox - Draws a nice messagebox to the logged output.
* Pair - Simplified key/value class
* RetriableTask - Runs a 'Callable` until it runs without Exception at least for the specified times of retries.

