---
title: "ConstraintValidatorDefinition"
slug: "backend/Annotations/ConstraintValidatorDefinition"
---

The `ConstraintValidatorDefinition` annotation identifies constraint validator
classes. Its required `validates` parameter specifies the constraint annotation
that invokes the annotated validator

This association is documented at source level. The annotation does not register
the validator or trigger validation

## Usage

Apply the annotation to your validator class and specify the corresponding
constraint annotation


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.annotations.ConstraintValidatorDefinition;

@ConstraintValidatorDefinition(validates = Version.class)
public class VersionValidator implements ConstraintValidator<Version, String> {
    // validator implementation
}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.annotations.ConstraintValidatorDefinition

@ConstraintValidatorDefinition(validates = Version::class)
class VersionValidator : ConstraintValidator<Version, String> {
    // validator implementation
}
```

</div>

</div>

The examples assume an existing `Version` constraint annotation and the
corresponding `ConstraintValidator` interface. Implement its required methods
in your validator class
