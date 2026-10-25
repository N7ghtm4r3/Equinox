package com.tecknobit.equinoxbackend.environment.services.users.dtos.requests;

import com.tecknobit.equinoxbackend.annotations.EmptyConstructor;
import com.tecknobit.equinoxbackend.annotations.dto.DtoRequest;
import com.tecknobit.equinoxbackend.environment.validation.annotations.Email;
import com.tecknobit.equinoxcore.annotations.Structure;

/**
 * The {@code ChangeEmailRequestDto} class is useful to carry the email change request data
 *
 * <p>The controller validates the email constraint before invoking the users service.
 * The email address is returned as supplied, without normalization
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
@Structure
@DtoRequest
public class ChangeEmailRequestDto {

    /**
     * {@code email} the new email address to assign to the user
     */
    @Email
    protected String email;

    /**
     * Constructor to init the {@link ChangeEmailRequestDto} class for JSON deserialization
     */
    @EmptyConstructor
    public ChangeEmailRequestDto() {
    }

    /**
     * Constructor to init the {@link ChangeEmailRequestDto} class
     *
     * @param email The new email address to assign to the user
     */
    public ChangeEmailRequestDto(String email) {
        this.email = email;
    }

    /**
     * Method used to get the {@link #email} instance
     *
     * @return the {@link #email} instance as {@link String}
     */
    public String getEmail() {
        return email;
    }

}
