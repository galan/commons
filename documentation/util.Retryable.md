# Abstract
Invokes a Callable/ExceptionalRunnable until it runs without Exception, will be retried for a specified amount and time between the retries.

# Examples

## Invoking Callable<String> for at least 10 times

    String result = Retryable.retry(10L).timeToWait("100ms").call(()->...);

## Invoking Callable<String> for at least 10 times, providing custom message

    String result = Retryable.retry(10L).timeToWait("100ms").message("connection").call(()->...);

## Invoking ExceptionalRunnable

    Retryable.run(()->...);

## Infinite retries with 5s between

    Retryable.infinite().timeToWait("5s").run(()->...);
