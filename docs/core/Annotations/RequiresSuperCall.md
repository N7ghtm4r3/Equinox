This annotation is useful to indicate that an inherited or an overridden method to work correctly
and completely requires that also the original method is invoked like the [@CallSuper](https://developer.android.com/reference/androidx/annotation/CallSuper)
annotation

## Usage

```kotlin
open class Vehicle {

    @RequiresSuperCall
    open fun startEngine() { 
        // general logic to start the engine
    }

}

class Car : Vehicle() { 
    
    @RequiresSuperCall
    override fun startEngine() {
        super.startEngine() // required for a complete and correct logic
        // Car specific logic to start engine
    }

}
```