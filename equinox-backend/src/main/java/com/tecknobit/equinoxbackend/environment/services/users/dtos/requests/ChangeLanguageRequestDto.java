package com.tecknobit.equinoxbackend.environment.services.users.dtos.requests;

import com.tecknobit.equinoxbackend.annotations.EmptyConstructor;
import com.tecknobit.equinoxbackend.annotations.dto.DtoRequest;
import com.tecknobit.equinoxbackend.environment.validation.annotations.UserLanguage;
import com.tecknobit.equinoxcore.annotations.Structure;

/**
 * The {@code ChangeLanguageRequestDto} class is useful to carry the language change request data
 *
 * <p>The language must match a supported key or value when validated with {@link UserLanguage}.
 * Null and empty values are rejected; no default language is assigned
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
@Structure
@DtoRequest
public class ChangeLanguageRequestDto {

    /**
     * {@code language} the new language to assign to the user
     */
    @UserLanguage
    protected String language;

    /**
     * Constructor to init the {@link ChangeLanguageRequestDto} class for JSON deserialization
     */
    @EmptyConstructor
    public ChangeLanguageRequestDto() {
    }

    /**
     * Constructor to init the {@link ChangeLanguageRequestDto} class
     *
     * @param language The new language to assign to the user
     */
    public ChangeLanguageRequestDto(String language) {
        this.language = language;
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
