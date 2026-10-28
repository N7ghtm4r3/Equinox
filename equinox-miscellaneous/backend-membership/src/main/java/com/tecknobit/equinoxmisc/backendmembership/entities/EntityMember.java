package com.tecknobit.equinoxmisc.backendmembership.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tecknobit.equinoxbackend.annotations.EmptyConstructor;
import com.tecknobit.equinoxbackend.environment.services.builtin.entities.EquinoxItem;
import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import com.tecknobit.equinoxcore.annotations.Structure;
import com.tecknobit.equinoxcore.utils.dto.DTOConvertible;
import com.tecknobit.equinoxmisc.backendmembership.dtos.Member;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jdk.jfr.Experimental;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import static com.tecknobit.equinoxcore.helpers.CommonKeysKt.*;

/**
 * The {@code EntityMember} class is useful to associate a membership record with an {@link EquinoxUser}
 *
 * <p>Concrete entities define the association with the scoped resource and implement the conversion to their
 * member DTO. Profile getters expose the associated user's details and require that association to be initialized
 *
 * @param <MD> The DTO type produced by the membership entity
 * @param <U>  The type of the associated user
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @see EquinoxItem
 * @see EquinoxUser
 * @see Member
 * @see DTOConvertible
 *
 */
@Experimental
@Structure
@MappedSuperclass
@JsonIgnoreProperties(value = {IDENTIFIER_KEY})
public abstract class EntityMember<MD extends Member, U extends EquinoxUser> extends EquinoxItem
        implements DTOConvertible<MD> {

    /**
     * {@code member} the user associated with this membership record
     */
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected final U member;

    /**
     * Constructor to init the {@link EntityMember} class with no identifier or associated user
     *
     * @apiNote empty constructor required for persistence initialization
     */
    @EmptyConstructor
    protected EntityMember() {
        this(null, null);
    }

    /**
     * Constructor to init the {@link EntityMember} class
     *
     * @param id     The membership record identifier
     * @param member The user associated with the membership record
     */
    protected EntityMember(String id, U member) {
        super(id);
        this.member = member;
    }

    /**
     * Method used to get the associated user's identifier
     *
     * @return the associated user's identifier as {@link String}
     */
    @JsonGetter(MEMBER_IDENTIFIER_KEY)
    public String getMemberId() {
        return member.getId();
    }

    /**
     * Method used to get the associated user's profile picture
     *
     * @return the associated user's profile picture as {@link String}
     */
    @JsonGetter(PROFILE_PIC_KEY)
    public String getProfilePic() {
        return member.getProfilePic();
    }

    /**
     * Method used to get the associated user's name
     *
     * @return the associated user's name as {@link String}
     */
    public String getName() {
        return member.getName();
    }

    /**
     * Method used to get the associated user's surname
     *
     * @return the associated user's surname as {@link String}
     */
    public String getSurname() {
        return member.getSurname();
    }

    /**
     * Method used to get the associated user's email address
     *
     * @return the associated user's email address as {@link String}
     */
    public String getEmail() {
        return member.getEmail();
    }

}