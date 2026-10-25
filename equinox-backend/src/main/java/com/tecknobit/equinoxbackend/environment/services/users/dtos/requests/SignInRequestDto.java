package com.tecknobit.equinoxbackend.environment.services.users.dtos.requests;

import com.tecknobit.equinoxbackend.annotations.EmptyConstructor;
import com.tecknobit.equinoxbackend.annotations.dto.DtoRequest;
import com.tecknobit.equinoxbackend.environment.validation.annotations.Email;
import com.tecknobit.equinoxbackend.environment.validation.annotations.UserLanguage;
import com.tecknobit.equinoxbackend.environment.validation.annotations.UserPassword;
import com.tecknobit.equinoxcore.annotations.Structure;

import java.util.Locale;

import static com.tecknobit.equinoxcore.helpers.InputsValidator.DEFAULT_LANGUAGE;

/**
 * The {@code SignInRequestDto} class is useful to carry the sign-in request data
 *
 * <p>The controller validates the request constraints before invoking the users service.
 * The email getter normalizes the address with {@link Locale#ROOT}
 *
 * <p>To receive custom request properties through the inherited endpoint, configure the JSON
 * deserializer to instantiate your request subclass
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
@Structure
@DtoRequest
public class SignInRequestDto {

    /**
     * {@code email} the email address of the user
     */
    @Email
    protected String email;

    /**
     * {@code password} the plain-text password of the user
     */
    @UserPassword
    protected String password;

    /**
     * {@code language} the language used to localize the request
     */
    @UserLanguage
    protected String language;

    /**
     * Constructor to init the {@link SignInRequestDto} class for JSON deserialization
     *
     * <p>The language is initialized to {@code DEFAULT_LANGUAGE}. A language supplied in the payload
     * replaces this default
     */
    @EmptyConstructor
    public SignInRequestDto() {
        this.language = DEFAULT_LANGUAGE;
    }

    /**
     * Constructor to init the {@link SignInRequestDto} class with the default language
     *
     * @param email    The email address of the user
     * @param password The plain-text password of the user
     */
    public SignInRequestDto(String email, String password) {
        this(email, password, DEFAULT_LANGUAGE);
    }

    /**
     * Constructor to init the {@link SignInRequestDto} class
     *
     * @param email    The email address of the user
     * @param password The plain-text password of the user
     * @param language The language used to localize the request
     */
    public SignInRequestDto(String email, String password, String language) {
        this.email = email;
        this.password = password;
        this.language = language;
    }

    /**
     * Method used to retrieve the email normalized with {@link Locale#ROOT}
     *
     * @return the email address in lowercase as {@link String}
     */
    public String getEmail() {
        return email.toLowerCase(Locale.ROOT);
    }

    /**
     * Method used to get the {@link #password} instance
     *
     * @return the {@link #password} instance as {@link String}
     */
    public String getPassword() {
        return password;
    }

    /**
     * Method used to get the {@link #language} instance
     *
     * @return the {@link #language} instance as {@link String}
     */
    public String getLanguage() {
        return language;
    }

}
