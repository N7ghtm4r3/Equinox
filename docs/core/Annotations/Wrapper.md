This annotation is applied to those wrapper methods that wrap the main method, facilitating access to
the latter by avoiding the need to pass some unnecessary arguments

## Usage

```kotlin
// This method is an example wrapper method
// In this case, the unnecessary argument is the `0`
@Wrapper(wrapperOf = "sum(double x, double y, int decimals)")
fun sum(
    x: Double, 
    y: Double
): Double {
    return sum(x, y, 0)
}

// This method is an example wrapped method
// In this case, the wrapped argument is `decimals`
fun sum(
    x: Double, 
    y: Double,
    decimals: Int
): Double {
    return TradingTools.roundValue(x + y, decimals)
}
```