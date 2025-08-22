This API allows to handle repetitive retrieval routines and execute them on background via coroutines

## Usage

### Use directly the Retriever

```kotlin
class ExampleClass {

    private val retrieverScope = CoroutineScope(Dispatchers.Default)
    
    private val retriever = Retriever(
        retrieverScope = retrieverScope
    )

    fun example() {
        retriever.execute(
            currentContext = this::class,
            routine = {
                // your routine
            },
            repeatRoutine = repeatRoutine,
            refreshDelay = delay
        )
    }
    
    fun example1() {
          retriever.suspend()
    }

}
```

### Use the wrapper interface

Creating a super class from which the other classes will be inheriting. This way is how the 
[EquinoxViewModel](../../../compose/APIs/EquinoxViewModel.md) integrates `Retriever`

```kotlin
abstract class AbstractClass : RetrieverWrapper {

    protected val retrieverScope = CoroutineScope(Dispatchers.Default)

    private val retriever = Retriever(
        retrieverScope = retrieverScope
    )

    override fun canRetrieverStart(): Boolean {
        return retriever.canStart()
    }

    override fun suspendRetriever() {
        retriever.suspend()
    }

    override fun restartRetriever() {
        retriever.restart()
    }

    override fun continueToRetrieve(
        currentContext: Class<*>
    ): Boolean {
        return retriever.continueToRetrieve(currentContext)
    }

    override fun execRefreshingRoutine(
        currentContext: Class<*>,
        routine: () -> Unit,
        repeatRoutine: Boolean,
        refreshDelay: Long
    ) {
        retriever.execute(
            currentContext = currentContext,
            routine = routine,
            repeatRoutine = repeatRoutine,
            refreshDelay = delay
        ) 
    }
}
```

Creating the inherit class example

```kotlin
class ExampleClass : AbstractClass() {

    fun example() {
        retrieve(
            currentContext = this::class,
            routine = {
                // your routine
            }
        )
    }

    fun example1() {
        suspendRetriever()
    }

}
```