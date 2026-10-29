---
title: "EmptyConstructor"
slug: "backend/Annotations/EmptyConstructor"
---

This annotation is used to mark the default constructors required for the **Spring Boot** entities management

When no profile pic is supplied, it remains `null` until JPA applies the configured default before inserting the user.
An explicitly supplied profile pic is preserved. See the [backend configuration](/Equinox/backend/Environment/#fill-the-configuration)
to customize the default

## Usage


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
// this constructor is used by Spring Boot to initialize autonomously the EquinoxUser entity
@EmptyConstructor
public EquinoxUser() {
    this(null, null, null, null, null, null, null);
}

public EquinoxUser(String id, String token, String name, String surname, String email, String password, String language) {
    this(id, token, name, surname, email, password, null, language);
}

public EquinoxUser(String id, String token, String name, String surname, String email, String password, String profilePic,
                    String language) {
    super(id);
    this.token = token;
    this.name = name;
    this.surname = surname;
    this.email = email;
    this.password = password;
    this.profilePic = profilePic;
    this.language = language;
}

public EquinoxUser(JSONObject jUser) {
    super(jUser);
    token = hItem.getString(TOKEN_KEY);
    name = hItem.getString(NAME_KEY);
    surname = hItem.getString(SURNAME_KEY);
    email = hItem.getString(EMAIL_KEY);
    password = hItem.getString(PASSWORD_KEY);
    profilePic = hItem.getString(PROFILE_PIC_KEY);
    language = hItem.getString(LANGUAGE_KEY);
}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
// this constructor is used by Spring Boot to initialize autonomously the EquinoxUser entity
@EmptyConstructor
constructor() : this(null, null, null, null, null, null, null)

constructor(
    id: String?, token: String?, name: String?, surname: String?,
    email: String?, password: String?, language: String?
) : this(id, token, name, surname, email, password, null, language)

constructor(
    id: String?, token: String?, name: String?, surname: String?,
    email: String?, password: String?, profilePic: String?, language: String?
) : super(id) {
    this.token = token
    this.name = name
    this.surname = surname
    this.email = email
    this.password = password
    this.profilePic = profilePic
    this.language = language
}

constructor(jUser: JSONObject) : super(jUser) {
    token = hItem.getString(TOKEN_KEY)
    name = hItem.getString(NAME_KEY)
    surname = hItem.getString(SURNAME_KEY)
    email = hItem.getString(EMAIL_KEY)
    password = hItem.getString(PASSWORD_KEY)
    profilePic = hItem.getString(PROFILE_PIC_KEY)
    language = hItem.getString(LANGUAGE_KEY)
}
```

</div>

</div>
