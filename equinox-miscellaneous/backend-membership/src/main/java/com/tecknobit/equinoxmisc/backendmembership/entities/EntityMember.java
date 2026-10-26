package com.tecknobit.equinoxmisc.backendmembership.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tecknobit.equinoxbackend.environment.services.builtin.entities.EquinoxItem;
import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import com.tecknobit.equinoxcore.annotations.FutureEquinoxApi;
import com.tecknobit.equinoxcore.annotations.Structure;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import static com.tecknobit.equinoxcore.helpers.CommonKeysKt.*;

@Structure
@MappedSuperclass
@JsonIgnoreProperties(value = {IDENTIFIER_KEY})
@FutureEquinoxApi(
        releaseVersion = "2.0.0",
        additionalNotes = """
                - Make generic for EquinoxUser customization
                - Document it in related docu page
                """
)
public abstract class EntityMember<U extends EquinoxUser> extends EquinoxItem {

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected final U member;

    protected EntityMember(String id, U member) {
        super(id);
        this.member = member;
    }

    @JsonGetter(MEMBER_IDENTIFIER_KEY)
    public String getMemberId() {
        return member.getId();
    }

    @JsonGetter(PROFILE_PIC_KEY)
    public String getProfilePic() {
        return member.getProfilePic();
    }

    public String getName() {
        return member.getName();
    }

    public String getSurname() {
        return member.getSurname();
    }

    public String getEmail() {
        return member.getEmail();
    }

    @JsonIgnore
    public abstract boolean isPrivileged();

}