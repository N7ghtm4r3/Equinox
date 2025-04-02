package com.tecknobit.equinoxcompose.components.splittext

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable

/**
 * The remember function used to create a [SplitTextState] for a [SplitText] component
 *
 * @param splits The number of splits used to create the [SplitText] component
 */
@Composable
fun rememberSplitTextState(
    splits: Int,
): SplitTextState {
    val splitTextState = rememberSaveable(
        stateSaver = SplitTextSaver
    ) {
        mutableStateOf(
            SplitTextState(
                splits = splits
            )
        )
    }
    return splitTextState.value
}

/**
 * The `SplitTextState` class is useful to manage a [SplitText] component giving the details currently
 * used in that component
 *
 * @property splits The number of splits used to create the component
 * @property initialSliceValues The initial values of the slices
 *
 * @author N7ghtm4r3 - Tecknobit
 */
class SplitTextState internal constructor(
    val splits: Int,
    val initialSliceValues: List<String> = List(splits) { "" },
) {

    /**
     * `textSlices` the array container of each parts of the split text
     */
    val textSlices = arrayListOf<MutableState<String>>()

    /**
     * Method used to allocate each slice of the [textSlices] with their values
     *
     */
    @Composable
    @NonRestartableComposable
    internal fun CreateSlices() {
        textSlices.clear()
        repeat(splits) { index ->
            textSlices.add(
                remember {
                    mutableStateOf(initialSliceValues[index])
                }
            )
        }
    }

    /**
     * Method used to get the complete text of the [SplitText] component
     *
     * @return each part of the [textSlices] as unique [String]
     */
    fun getCompleteText(): String {
        var entireText = ""
        textSlices.forEach { slice ->
            entireText += slice.value
        }
        return entireText
    }

}

/**
 * The `SplitTextSaver` custom saver allows the [SplitTextState] to save and restore correctly the
 * values for the [SplitText] component
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @see Saver
 */
internal object SplitTextSaver : Saver<SplitTextState, Pair<Int, List<String>>> {

    /**
     * Convert the value into a saveable one. If null is returned the value will not be saved.
     */
    override fun SaverScope.save(
        value: SplitTextState,
    ): Pair<Int, List<String>> {
        return Pair(
            first = value.splits,
            second = value.initialSliceValues
        )
    }

    /**
     * Convert the restored value back to the original Class. If null is returned the value will
     * not be restored and would be initialized again instead.
     */
    override fun restore(
        value: Pair<Int, List<String>>,
    ): SplitTextState {
        return SplitTextState(
            splits = value.first,
            initialSliceValues = value.second
        )
    }

}