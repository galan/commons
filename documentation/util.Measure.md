# Abstract
With `Measure` one can record the duraction of a given code-block.

# Example

## One-time measuring a Runnable

    Measure.measure("example").run(() -> Sleeper.sleep("1s"));

## One-time measuring a Callable<String>

    String result = Measure.measure("example").call(() -> "result");

## Measuring multiple invokations

    Measure measure = Measure.measure("example").every(1000);
    for (int i = 0; < 10_000; i++) {
    	measure.run(() -> Sleeper.sleep("100ms"));
    }
    measure.finish(); // optional

