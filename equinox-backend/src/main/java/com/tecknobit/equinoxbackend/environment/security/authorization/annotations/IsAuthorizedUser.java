package com.tecknobit.equinoxbackend.environment.security.authorization.annotations;

import com.tecknobit.equinoxcore.annotations.FutureEquinoxApi;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@FutureEquinoxApi(
        releaseVersion = "2.0.0"
)

@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("#userId == authentication.principal.id")
public @interface IsAuthorizedUser {

}
