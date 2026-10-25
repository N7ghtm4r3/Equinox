package com.tecknobit.equinoxbackend.environment.services.users.dtos.responses;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.tecknobit.equinoxbackend.annotations.EmptyConstructor;
import com.tecknobit.equinoxbackend.annotations.dto.DtoResponse;
import com.tecknobit.equinoxcore.annotations.Structure;

import static com.tecknobit.equinoxcore.helpers.CommonKeysKt.PROFILE_PIC_KEY;

/**
 * The {@code ChangeProfilePicResponseDto} class is useful to carry the updated profile pic path
 *
 * <p>The path is serialized as {@code profile_pic} in the response data
 *
 * @since 2.0.0
 */
@Structure
@DtoResponse
public class ChangeProfilePicResponseDto {

    /**
     * {@code profilePic} the profile pic path of the user
     */
    protected String profilePic;

    /**
     * Constructor to init the {@link ChangeProfilePicResponseDto} class without response data
     */
    @EmptyConstructor
    public ChangeProfilePicResponseDto() {
    }

    /**
     * Constructor to init the {@link ChangeProfilePicResponseDto} class
     *
     * @param profilePic The new profile pic path of the user
     */
    public ChangeProfilePicResponseDto(String profilePic) {
        this.profilePic = profilePic;
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
