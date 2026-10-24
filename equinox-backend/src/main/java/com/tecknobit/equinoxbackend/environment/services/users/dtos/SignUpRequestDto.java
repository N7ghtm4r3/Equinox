package com.tecknobit.equinoxbackend.environment.services.users.dtos;

import com.tecknobit.equinoxbackend.annotations.dto.DtoRequest;
import com.tecknobit.equinoxbackend.environment.validation.annotations.*;
import com.tecknobit.equinoxcore.annotations.Structure;
import jakarta.validation.constraints.NotBlank;

import static com.tecknobit.equinoxcore.helpers.InputsValidator.DEFAULT_LANGUAGE;

@Structure
@DtoRequest
public class SignUpRequestDto {

    @Name
    protected String name;

    @Surname
    protected String surname;

    @Email
    protected String email;

    @NotBlank
    protected String password;

    @UserLanguage
    protected String language;

    @ServerSecret
    protected String serverSecretKey;

    public SignUpRequestDto(String name, String surname, String email, String password, String serverSecretKey) {
        this(name, surname, email, password, DEFAULT_LANGUAGE, serverSecretKey);
    }

    public SignUpRequestDto(String name, String surname, String email, String password, String language, String serverSecretKey) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.language = language;
        this.serverSecretKey = serverSecretKey;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLanguage() {
        return language;
    }

    public String getServerSecretKey() {
        return serverSecretKey;
    }

}
