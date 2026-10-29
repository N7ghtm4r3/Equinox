---
title: "EntityMember"
slug: "miscellaneous/modules/backend-membership/entities/EntityMember"
---

The `EntityMember` class associates a membership record with an `EquinoxUser`. It extends `EquinoxItem` and implements
the [DTOConvertible](/Equinox/backend/APIs/DtoConvertible/) contract

## Usage

The base entity contains the membership record identifier and the association with the user. The scoped resource
is represented by a field or relationship in the concrete entity. Its [member DTO](/Equinox/miscellaneous/modules/backend-membership/dtos/Member/) represents
the data returned by the conversion

In this example, `projectId` identifies the scope. `membershipId` identifies the membership record itself, while
`getMemberId()` returns the associated user's identifier

```java
import com.tecknobit.equinoxbackend.annotations.EmptyConstructor;
import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import com.tecknobit.equinoxcore.annotations.Returner;
import com.tecknobit.equinoxmisc.backendmembership.entities.EntityMember;
import jakarta.persistence.AssociationOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity(name = "ProjectMember")
@Table(
        name = "project_members",
        uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "member_id"})
)
@AssociationOverride(name = "member", joinColumns = @JoinColumn(name = "member_id", nullable = false))
public class ProjectMember extends EntityMember<ProjectMemberDto, EquinoxUser> {

    @Column(name = "project_id", nullable = false)
    private String projectId;

    @EmptyConstructor
    protected ProjectMember() {
        super();
    }

    public ProjectMember(String membershipId, EquinoxUser member, String projectId) {
        super(membershipId, member);
        this.projectId = projectId;
    }

    @Override
    @Returner
    public ProjectMemberDto convertToRelatedDTO() {
        ProjectMemberDto member = new ProjectMemberDto(
            getMemberId(),
            getProfilePic(),
            getName(),
            getSurname(),
            getEmail(),
            projectId
        );

        return member;
    }
}
```

The unique constraint expresses the one-membership-per-project-and-user relationship. Profile getters read the
associated user, so that association must be initialized before profile details or the DTO can be retrieved

The concrete entity is used by [MembersRepository](/Equinox/miscellaneous/modules/backend-membership/repositories/MembersRepository/) to resolve membership
within its scope
