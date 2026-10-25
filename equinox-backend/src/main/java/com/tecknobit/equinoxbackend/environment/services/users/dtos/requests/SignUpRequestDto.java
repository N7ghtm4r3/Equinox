package com.tecknobit.equinoxbackend.environment.services.users.dtos.requests;

import com.tecknobit.equinoxbackend.annotations.EmptyConstructor;
import com.tecknobit.equinoxbackend.annotations.dto.DtoRequest;
import com.tecknobit.equinoxbackend.environment.validation.annotations.*;
import com.tecknobit.equinoxcore.annotations.Structure;

import java.util.Locale;

import static com.tecknobit.equinoxcore.helpers.InputsValidator.DEFAULT_LANGUAGE;

/**
 * The {@code SignUpRequestDto} class is useful to carry the sign-up request data
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
public class SignUpRequestDto {

    /**
     * {@code name} the name of the user
     */
    @Name
    protected String name;

    /**
     * {@code surname} the surname of the user
     */
    @Surname
    protected String surname;

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
     * {@code language} the language to assign to the user and use to localize the request
     */
    @UserLanguage
    protected String language;

    /**
     * {@code serverSecretKey} the server secret checked by request validation
     */
    @ServerSecret
    protected String serverSecretKey;

    /**
     * Constructor to init the {@link SignUpRequestDto} class for JSON deserialization
     *
     * <p>The language is initialized to {@code DEFAULT_LANGUAGE}. A language supplied in the payload
     * replaces this default
     */
    @EmptyConstructor
    public SignUpRequestDto() {
        this.language = DEFAULT_LANGUAGE;
    }

    /**
     * Constructor to init the {@link SignUpRequestDto} class with the default language
     *
     * @param name            The name of the user
     * @param surname         The surname of the user
     * @param email           The email address of the user
     * @param password        The plain-text password of the user
     * @param serverSecretKey The server secret checked by request validation
     */
    public SignUpRequestDto(String name, String surname, String email, String password, String serverSecretKey) {
        this(name, surname, email, password, DEFAULT_LANGUAGE, serverSecretKey);
    }

    /**
     * Constructor to init the {@link SignUpRequestDto} class
     *
     * @param name            The name of the user
     * @param surname         The surname of the user
     * @param email           The email address of the user
     * @param password        The plain-text password of the user
     * @param language        The language of the user
     * @param serverSecretKey The server secret checked by request validation
     */
    public SignUpRequestDto(String name, String surname, String email, String password, String language, String serverSecretKey) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.language = language;
        this.serverSecretKey = serverSecretKey;
    }

    /**
     * Method used to get the {@link #name} instance
     *
     * @return the {@link #name} instance as {@link String}
     */
    public String getName() {
        return name;
    }

    /**
     * Method used to get the {@link #surname} instance
     *
     * @return the {@link #surname} instance as {@link String}
     */
    public String getSurname() {
        return surname;
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

    /**
     * Method used to get the {@link #serverSecretKey} instance
     *
     * @return the {@link #serverSecretKey} instance as {@link String}
     */
    public String getServerSecretKey() {
        return serverSecretKey;
    }

}
