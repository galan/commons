# Abstract
Say is a convenient logging-facade, that support parameterized messages, reduces boilerplate code and embraces Logstash via MDC/ThreadContext.
The underlying api relies on the log4j2-api, which enables usage of any logging framework such as slf4j, logback, log4j and log4j2 itself, of course.

# Logging
A Logger class declaration, as required with other popular logging-frameworks, is not needed. The calling class is determined at runtime with no or nearly no performance impact.

## Simple logging-message
Example call:

    Say.info("Hello, world!");
    => Hello, world!

Call with Exception

    Say.error("Foobar", ex);
    => Foobar
    => <Stacktrace...>

## Parameterized logging-messages
Parameters in the log-message are substituted when passed to the var-args at the end of the logging methods, the message uses `{}` as placeholder, eg.:<br/>
 
    Say.info("Hello {}", "world");
    => Hello {world}

    Say.info("Hello {} {}", "beautiful", "world");
    => Hello {beautiful} {world}

Exceptions still remain on the first position, before the var-args:

    Say.error("Something failed: {}", ex, "do'h");
    => Something failed: {do'h}
    => <Stacktrace...>

## Individual fields for Logstash thru MDC/Logstash 
If the parameters should be available as json-encoded metadata for eg. logstash, you can provide names to the parameters. If fields are passed, Say will automatically provide the fields in a field called `payload` encoded as json, which can easily consumed by logstash. Example:

    Say.info("Hello {location}", "world");
    => Hello {world}
    // payload: {"location": "world"}
 
Additionally you can provide fields using a fluent interface using field(..) or f(..), which are added to the payload, but are not used in the message:

    Say.field("key", "value").field("other", 42L).info("Hello {location}", "world");
    => Hello {world}
    // payload: {"key": "value", "other": 42, "location": "world"}

