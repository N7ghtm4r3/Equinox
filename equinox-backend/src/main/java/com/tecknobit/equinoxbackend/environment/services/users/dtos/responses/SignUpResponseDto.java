package com.tecknobit.equinoxbackend.environment.services.users.dtos.responses;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.tecknobit.equinoxbackend.annotations.dto.DtoResponse;
import com.tecknobit.equinoxcore.annotations.Structure;

import static com.tecknobit.equinoxcore.helpers.CommonKeysKt.*;

@Structure
@DtoResponse
public class SignUpResponseDto {

    private final String userId;

    private final String userToken;

    private final String profilePic;

    public SignUpResponseDto(String userId, String userToken, String profilePic) {
        this.userId = userId;
        this.userToken = userToken;
        this.profilePic = profilePic;
    }

    @JsonGetter(USER_IDENTIFIER_KEY)
    public String getUserId() {
        return userId;
    }

    @JsonGetter(TOKEN_KEY)
    public String getUserToken() {
        return userToken;
    }

    @JsonGetter(PROFILE_PIC_KEY)
    public String getProfilePic() {
        return profilePic;
    }

}
