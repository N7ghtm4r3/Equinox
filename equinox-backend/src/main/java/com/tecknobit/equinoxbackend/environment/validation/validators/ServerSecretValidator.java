package com.tecknobit.equinoxbackend.environment.validation.validators;

import com.tecknobit.apimanager.apis.ServerProtector;
import com.tecknobit.equinoxbackend.annotations.ConstraintValidatorDefinition;
import com.tecknobit.equinoxbackend.environment.configuration.EquinoxBackendConfiguration;
import com.tecknobit.equinoxbackend.environment.configuration.EquinoxEnvironmentService;
import com.tecknobit.equinoxbackend.environment.validation.annotations.ServerSecret;
import com.tecknobit.equinoxcore.annotations.Validator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * The {@code ServerSecretValidator} class is useful to validate server secrets according to the backend protection configuration
 *
 * <p>When server protection is disabled, this validator accepts any value. The {@link ServerSecret} annotation separately rejects null and blank values
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see ConstraintValidator
 *
 * @since 2.0.0
 */
@ConstraintValidatorDefinition(validates = ServerSecret.class)
public class ServerSecretValidator implements ConstraintValidator<ServerSecret, String> {

    /**
     * Method used to validate server secrets according to the backend protection configuration
     *
     * @param serverSecret The server secret value to validate
     * @param context The constraint validation context, unused by this implementation
     *
     * @return whether server protection is disabled or the secret matches the configured value as {@code boolean}
     */
    @Override
    @Validator
    public boolean isValid(String serverSecret, ConstraintValidatorContext context) {
        EquinoxEnvironmentService environmentService = EquinoxEnvironmentService.getInstance();
        EquinoxBackendConfiguration backendConfiguration = environmentService.getBackendConfiguration();
        if(!backendConfiguration.serverProtectorEnabled())
            return true;

        ServerProtector serverProtector = environmentService.getServerProtector();
        return serverProtector.serverSecretMatches(serverSecret);
    }

}
