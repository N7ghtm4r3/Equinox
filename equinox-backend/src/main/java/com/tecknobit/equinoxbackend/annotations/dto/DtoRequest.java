package com.tecknobit.equinoxbackend.annotations.dto;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.RECORD_COMPONENT;
import static java.lang.annotation.ElementType.TYPE;

/**
 * The {@code DtoRequest} annotation is useful to mark types and record components that represent incoming request data
 * For example:
 *
 * <pre>
 * {@code
 * @DtoRequest
 * public record CreateItemRequest(String name) {
 * }
 * }
 * </pre>
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
@Target(value = {TYPE, RECORD_COMPONENT})
@Retention(RetentionPolicy.SOURCE)
public @interface DtoRequest {
}
