package com.tecknobit.equinoxbackend.environment.services.users.dtos.responses;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.tecknobit.equinoxbackend.annotations.EmptyConstructor;
import com.tecknobit.equinoxbackend.annotations.dto.DtoResponse;
import com.tecknobit.equinoxcore.annotations.Structure;

import static com.tecknobit.equinoxcore.helpers.CommonKeysKt.*;

/**
 * The {@code SignInResponseDto} class is useful to carry the sign-in response data
 *
 * <p>The identifier, token, and profile pic are serialized as {@code user_id}, {@code token},
 * and {@code profile_pic}. Extend this class to expose additional response properties through getters
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
@Structure
@DtoResponse
public class SignInResponseDto {

    /**
     * {@code userId} the identifier of the user
     */
    protected String userId;

    /**
     * {@code userToken} the token of the user
     */
    protected String userToken;

    /**
     * {@code profilePic} the profile pic path of the user
     */
    protected String profilePic;

    /**
     * {@code name} the name of the user
     */
    protected String name;

    /**
     * {@code surname} the surname of the user
     */
    protected String surname;

    /**
     * {@code language} the saved language of the user
     */
    protected String language;

    /**
     * Constructor to init the {@link SignInResponseDto} class without response data
     *
     * <p>This constructor is available to consumers that deserialize the response. Serialization
     * of an instance created with the parameterized constructor does not require it
     */
    @EmptyConstructor
    public SignInResponseDto() {
    }

    /**
     * Constructor to init the {@link SignInResponseDto} class
     *
     * @param userId     The identifier of the user
     * @param userToken  The token of the user
     * @param profilePic The profile pic path of the user
     * @param name       The name of the user
     * @param surname    The surname of the user
     * @param language   The saved language of the user
     */
    public SignInResponseDto(String userId, String userToken, String profilePic, String name, String surname,
                             String language) {
        this.userId = userId;
        this.userToken = userToken;
        this.profilePic = profilePic;
        this.name = name;
        this.surname = surname;
        this.language = language;
    }

    /**
     * Method used to get the {@link #userId} instance
     *
     * @return the {@link #userId} instance as {@link String}
     */
    @JsonGetter(USER_IDENTIFIER_KEY)
    public String getUserId() {
        return userId;
    }

    /**
     * Method used to get the {@link #userToken} instance
     *
     * @return the {@link #userToken} instance as {@link String}
     */
    @JsonGetter(TOKEN_KEY)
    public String getUserToken() {
        return userToken;
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
     * Method used to get the {@link #language} instance
     *
     * @return the {@link #language} instance as {@link String}
     */
    public String getLanguage() {
        return language;
    }

}
