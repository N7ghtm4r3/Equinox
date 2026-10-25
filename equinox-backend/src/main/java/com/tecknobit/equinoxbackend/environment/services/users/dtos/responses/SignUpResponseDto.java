package com.tecknobit.equinoxbackend.environment.services.users.dtos.responses;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.tecknobit.equinoxbackend.annotations.EmptyConstructor;
import com.tecknobit.equinoxbackend.annotations.dto.DtoResponse;
import com.tecknobit.equinoxcore.annotations.Structure;

import static com.tecknobit.equinoxcore.helpers.CommonKeysKt.*;

/**
 * The {@code SignUpResponseDto} class is useful to carry the sign-up response data
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
public class SignUpResponseDto {

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
     * Constructor to init the {@link SignUpResponseDto} class without response data
     *
     * <p>This constructor is available to consumers that deserialize the response. Serialization
     * of an instance created with the parameterized constructor does not require it
     */
    @EmptyConstructor
    public SignUpResponseDto() {
    }

    /**
     * Constructor to init the {@link SignUpResponseDto} class
     *
     * @param userId     The identifier of the user
     * @param userToken  The token of the user
     * @param profilePic The profile pic path of the user
     */
    public SignUpResponseDto(String userId, String userToken, String profilePic) {
        this.userId = userId;
        this.userToken = userToken;
        this.profilePic = profilePic;
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

}
