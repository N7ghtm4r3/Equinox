---
title: "Overview"
slug: "miscellaneous/modules/backend-membership"
---

<span id="overview"></span>

![Maven Central](https://img.shields.io/maven-central/v/io.github.n7ghtm4r3/equinoxmisc-backend-membership.svg?label=Maven%20Central)

![Static Badge](https://img.shields.io/badge/Spring%20Boot-backend-6DB33F)
![Static Badge](https://img.shields.io/badge/JVM-Java%2021-007396)

**v1.0.0**

An optional extension of [Equinox Backend](/Equinox/backend/) for applications where users belong to a project,
group, organization, or another resource. Each membership associates a user with a resource that defines its scope

The module provides the base membership entity, member DTO, repository and service contracts, and a reusable
membership check. Your application defines the scoped resource mapping and the query used to retrieve its members

## Implementation

The module requires Java 21 and builds on the [Equinox Backend environment](/Equinox/backend/Environment/).
The installation examples include the backend and core dependencies, whose types appear in the membership APIs

### Version catalog

- `libs.versions.toml`

```toml
[versions]
equinoxBackendMembership = "1.0.0"
equinoxBackend = "2.0.0"
equinoxCore = "1.2.1"

[libraries]
equinox-backend-membership = { module = "io.github.n7ghtm4r3:equinoxmisc-backend-membership", version.ref = "equinoxBackendMembership" }
equinox-backend = { module = "io.github.n7ghtm4r3:equinox-backend", version.ref = "equinoxBackend" }
equinox-core = { module = "io.github.n7ghtm4r3:equinox-core", version.ref = "equinoxCore" }
```

- `build.gradle.kts`

```kotlin
dependencies {
    implementation(libs.equinox.backend.membership)
    implementation(libs.equinox.backend)
    implementation(libs.equinox.core)
}
```

### Gradle (Kotlin)

```kotlin
dependencies {
    implementation("io.github.n7ghtm4r3:equinoxmisc-backend-membership:1.0.0")
    implementation("io.github.n7ghtm4r3:equinox-backend:2.0.0")
    implementation("io.github.n7ghtm4r3:equinox-core:1.2.1")
}
```

### Gradle Groovy

```groovy
dependencies {
    implementation 'io.github.n7ghtm4r3:equinoxmisc-backend-membership:1.0.0'
    implementation 'io.github.n7ghtm4r3:equinox-backend:2.0.0'
    implementation 'io.github.n7ghtm4r3:equinox-core:1.2.1'
}
```

Repository configuration is covered by the [backend installation guide](/Equinox/backend/#implementation)

## Usage

The examples model users belonging to projects. A user can belong to several projects, with a separate membership
record for each association. Membership in one project does not establish membership in another

`ProjectMember` represents the stored association. Its response, `ProjectMemberDto`, includes the user's profile
and the identifier of the project to which the membership belongs

Each record has its own identifier. A membership lookup instead uses the project identifier and the associated
user's identifier to determine whether that user belongs to the requested project

## APIs available


<details class="equinox-callout api-miscellaneous">

<summary>Entities</summary>


- [EntityMember](/Equinox/miscellaneous/modules/backend-membership/entities/EntityMember/)
  Base entity associating a membership record with a user

</details>


<details class="equinox-callout api-miscellaneous">

<summary>DTOs</summary>


- [Member](/Equinox/miscellaneous/modules/backend-membership/dtos/Member/)
  DTO containing a member identifier and profile details

</details>


<details class="equinox-callout database">

<summary>Repositories</summary>


- [MembersRepository](/Equinox/miscellaneous/modules/backend-membership/repositories/MembersRepository/)
  Persistence contract with a scope-specific membership lookup

</details>


<details class="equinox-callout utils">

<summary>Services</summary>


- [MembersService](/Equinox/miscellaneous/modules/backend-membership/services/MembersService/)
  Membership retrieval and conversion of entity lists to DTOs

</details>


<details class="equinox-callout api-miscellaneous">

<summary>Interfaces</summary>


- [Scoped](/Equinox/miscellaneous/modules/backend-membership/interfaces/Scoped/)
  Membership checks that return the record or deny access

</details>

## Annotations available


<details class="equinox-callout api-miscellaneous">

<summary>Annotations</summary>


- [ScopeProvider](/Equinox/miscellaneous/modules/backend-membership/annotations/ScopeProvider/)
  Marks types and record components that provide a membership scope

- [StandardMember](/Equinox/miscellaneous/modules/backend-membership/annotations/StandardMember/)
  Marks methods intended for standard members

- [PrivilegedMember](/Equinox/miscellaneous/modules/backend-membership/annotations/PrivilegedMember/)
  Marks methods intended for privileged members and carries an optional role label

</details>
