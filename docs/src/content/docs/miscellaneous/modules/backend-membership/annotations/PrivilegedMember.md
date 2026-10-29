---
title: "PrivilegedMember"
slug: "miscellaneous/modules/backend-membership/annotations/PrivilegedMember"
---

The `PrivilegedMember` annotation marks methods intended for privileged members

## Usage

The `owner` label identifies the intended role for the project update operation


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxmisc.backendmembership.annotations.PrivilegedMember;

@PrivilegedMember(role = "owner")
public void updateProject(String projectId) {
    // check membership and the required role before updating the project
}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxmisc.backendmembership.annotations.PrivilegedMember

@PrivilegedMember(role = "owner")
fun updateProject(projectId: String) {
    // check membership and the required role before updating the project
}
```

</div>

</div>

The `role` label defaults to an empty string. The annotation does not define a role model or interpret the label
