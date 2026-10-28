package com.tecknobit.equinoxmisc.backendmembership.dtos;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tecknobit.equinoxbackend.annotations.EmptyConstructor;
import com.tecknobit.equinoxbackend.environment.validation.annotations.Email;
import com.tecknobit.equinoxbackend.environment.validation.annotations.Name;
import com.tecknobit.equinoxbackend.environment.validation.annotations.Surname;
import com.tecknobit.equinoxcore.annotations.DTO;
import com.tecknobit.equinoxcore.annotations.Structure;
import jdk.jfr.Experimental;

import static com.tecknobit.equinoxcore.helpers.CommonKeysKt.PROFILE_PIC_KEY;

/**
 * The {@code Member} class is useful to transfer a member identifier and profile details
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 */
@DTO
@Structure
@Experimental
public class Member {

    /**
     * {@code id} the identifier exposed for the member
     */
    private String id;

    /**
     * {@code profilePic} the member's profile picture
     */
    @JsonProperty(PROFILE_PIC_KEY)
    private String profilePic;

    /**
     * {@code name} the member's name
     */
    @Name
    private String name;

    /**
     * {@code surname} the member's surname
     */
    @Surname
    private String surname;

    /**
     * {@code email} the member's email address
     */
    @Email
    private String email;

    /**
     * Constructor to init the {@link Member} class with all profile fields and the identifier set to {@code null}
     */
    @EmptyConstructor
    public Member() {
    }

    /**
     * Constructor to init the {@link Member} class
     *
     * @param id         The identifier exposed for the member
     * @param profilePic The member's profile picture
     * @param name       The member's name
     * @param surname    The member's surname
     * @param email      The member's email address
     */
    public Member(String id, String profilePic, String name, String surname, String email) {
        this.id = id;
        this.profilePic = profilePic;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    /**
     * Method used to get the {@link #id} instance
     *
     * @return the {@link #id} instance as {@link String}
     */
    public String getId() {
        return id;
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
     * Method used to get the {@link #email} instance
     *
     * @return the {@link #email} instance as {@link String}
     */
    public String getEmail() {
        return email;
    }

}
