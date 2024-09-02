package com.tecknobit.equinox.annotations;

import com.tecknobit.equinox.environment.records.EquinoxUser;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * The {@code CustomParametersOrder} annotation is useful to manage the custom parameters order for a better readability
 * of the code and to work with that parameters correctly
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote This annotation is useful when the {@link EquinoxUser} has been customized
 * @since 1.0.3
 */
@Target(METHOD)
@Retention(SOURCE)
public @interface CustomParametersOrder {

    /**
     * The order of the custom parameters of the custom {@link EquinoxUser}
     */
    String[] order();

}
