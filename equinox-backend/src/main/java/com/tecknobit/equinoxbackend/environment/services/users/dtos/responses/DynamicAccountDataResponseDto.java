package com.tecknobit.equinoxbackend.environment.services.users.dtos.responses;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.tecknobit.equinoxbackend.annotations.EmptyConstructor;
import com.tecknobit.equinoxbackend.annotations.dto.DtoResponse;
import com.tecknobit.equinoxcore.annotations.Structure;

import static com.tecknobit.equinoxcore.helpers.CommonKeysKt.PROFILE_PIC_KEY;

/**
 * The {@code DynamicAccountDataResponseDto} class is useful to carry account data for synchronization across devices
 *
 * <p>The response contains the user's email, profile pic, and saved language. The profile pic is serialized
 * as {@code profile_pic}. Extend this class to expose additional response properties through getters
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
@Structure
@DtoResponse
public class DynamicAccountDataResponseDto {

    /**
     * {@code email} the current email address of the user
     */
    protected String email;

    /**
     * {@code profilePic} the profile pic path of the user
     */
    protected String profilePic;

    /**
     * {@code language} the saved language of the user
     */
    protected String language;

    /**
     * Constructor to init the {@link DynamicAccountDataResponseDto} class without response data
     *
     * <p>This constructor is available to consumers that deserialize the response. Serialization
     * of an instance created with the parameterized constructor does not require it
     */
    @EmptyConstructor
    public DynamicAccountDataResponseDto() {
    }

    /**
     * Constructor to init the {@link DynamicAccountDataResponseDto} class
     *
     * @param email      The current email address of the user
     * @param profilePic The profile pic path of the user
     * @param language   The saved language of the user
     */
    public DynamicAccountDataResponseDto(String email, String profilePic, String language) {
        this.email = email;
        this.profilePic = profilePic;
        this.language = language;
    }

    /**
     * Method used to get the {@link #email} instance
     *
     * @return the {@link #email} instance as {@link String}
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method used to get the {@link #profilePic} instance
     *
     * @return the {@link #profilePic} instance as {@link String}
     */
    @JsonGetter(PROFILE_PIC_KEY)
    public String getProfilePic() {
        return profilePic;
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
