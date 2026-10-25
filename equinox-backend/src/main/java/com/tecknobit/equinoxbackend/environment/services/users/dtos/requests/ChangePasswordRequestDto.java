package com.tecknobit.equinoxbackend.environment.services.users.dtos.requests;

import com.tecknobit.equinoxbackend.annotations.EmptyConstructor;
import com.tecknobit.equinoxbackend.annotations.dto.DtoRequest;
import com.tecknobit.equinoxbackend.environment.validation.annotations.UserPassword;
import com.tecknobit.equinoxcore.annotations.Structure;

/**
 * The {@code ChangePasswordRequestDto} class is useful to carry the password change request data
 *
 * <p>The password is carried in plain text and is hashed by the users service before persistence
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
@Structure
@DtoRequest
public class ChangePasswordRequestDto {

    /**
     * {@code password} the new plain-text password to assign to the user
     */
    @UserPassword
    protected String password;

    /**
     * Constructor to init the {@link ChangePasswordRequestDto} class for JSON deserialization
     */
    @EmptyConstructor
    public ChangePasswordRequestDto() {
    }

    /**
     * Constructor to init the {@link ChangePasswordRequestDto} class
     *
     * @param password The new plain-text password to assign to the user
     */
    public ChangePasswordRequestDto(String password) {
        this.password = password;
    }

    /**
     * Method used to get the {@link #password} instance
     *
     * @return the {@link #password} instance as {@link String}
     */
    public String getPassword() {
        return password;
    }

}
