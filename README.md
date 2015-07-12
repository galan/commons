# commons

Generic functionality, utility classes and test-helper, that can be used to accomplish typical common tasks in a java project.

# Integration with Maven
Just add the following dependency (see [releases](https://github.com/galan/commons/releases) for latest version):

    <dependency>
    	<groupId>de.galan</groupId>
    	<artifactId>commons</artifactId>
    	<version>x.y.z</version>
    </dependency>

# Documentation

## de.galan.commons.func.supplier
Different `Supplier` for eg. Long-sequences and random alphanumeric strings. 


## de.galan.commons.net
* UrlUtil - Decoding/encoding urls with `Charset` 

## de.galan.commons.net.mail
* MailMessenger - Sending multiparts mails using a fluent interface.


## de.galan.commons.test
* AbstractTestParent - Resets the `ApplicationClock` and prints the name of each test-method executed
* FixedDateSupplier - Sets the `ApplicationClock` to a fixed time (best used with Instants)
* SimpleWebserverTestParent - Test containing an lightweight http-server ([simpleframework](http://www.simpleframework.org/)).
* Tests - Helping methods in tests for eg. loading and comparing test resources, some date assertions, test directory handling.


## de.galan.commons.time
* ApplicationClock - Clock that acts as time emitter for an application. Uses a java.time.clock.SystemClock (UTC) by default.
* Durations - Utility class to handle and calculate human readable time durations, such as "2h 30m"
* [Instants](https://github.com/galan/commons/blob/master/documentation/Instants.md) - FLuent simplified time creation and modification.
* [Times](https://github.com/galan/commons/blob/master/documentation/Times.md) - Fluent time comparision
* Sleeper - Sleeps a `Duration` wihtout throwing an InterruptedException

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
