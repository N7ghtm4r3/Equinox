Provides the logic of an **Equinox's controller** and some utilities to build responses and to handle the `i18n`

## Constants

The predefined message keys are available in
[EquinoxMessages](EquinoxMessages.md) and support internationalization

## JSON utilities

The controller retains a [JsonHelper](JsonHelper.md) for the JSON payload loaded from a request.
Use its typed accessors to read values and select fallbacks for missing fields

### jsonHelper

This API allows to retrieve values from the `JSON` payload without to specify the complete path to the value to get

#### Usage

```java

@PostMapping(path = SIGN_UP_ENDPOINT)
@RequestPath(path = "/api/v2/users/signUp", method = POST)
public String signUp(
        @RequestBody Map<String, Object> payload
) {
    loadJsonHelper(payload); // load the instance
    String name = jsonHelper.getString(NAME_KEY); // use the instance
    ...
}
```

## I18n Utilities

The following methods allows to handle the internationalization of the messages used into responses

### setSessionLocale

Changes the current locale of the session

#### Usage

###### From path

If the request path append in the search params the `language` param will be set the locale specified by that param

```java

@PostMapping(path = SIGN_UP_ENDPOINT)
@RequestPath(path = "/api/v2/users/hello?language=it", method = POST)
public String signUp(
        @RequestBody Map<String, Object> payload
) {
    String i18n = // logic to retrieve the message
    return i18n; // "Ciao Mondo!"
}
```

###### From payload

If the request payload specifies the `language` param will be set the locale specified by that param

```java

@PostMapping(path = SIGN_UP_ENDPOINT)
@RequestPath(path = "/api/v2/users/hello", method = POST)
public String signUp(
        @RequestBody Map<String, Object> payload
) {
    loadJsonHelper(payload);
    String language = jsonHelper.getString(LANGUAGE_KEY); // it
    setSessionLocale(language);
    String i18n = // logic to retrieve the message
    return i18n; // "Ciao Mondo!"
}
```

### getInternationalizedMessage

Gets the international message from the default resource set and the custom resource set specified as follows:

``` bash 
resources
  └── lang
       └── custom_messages.properties
       └── custom_messages_any_locale.properties
       └── ...
```

#### Usage

```java

@PostMapping(path = SIGN_UP_ENDPOINT)
@RequestPath(path = "/api/v2/users/hello?language=it", method = POST)
public String signUp(
        @RequestBody Map<String, Object> payload
) {
    // without args
    String i18n = getInternationalizedMessage("key");

    // with args
    String i18nArgs = getInternationalizedMessage("key", args1, args2, etc...)
    
    ...
}
```

## Response Utilities

The following methods build responses with a status and response data.
The message and `JSONObject` overloads return `ResponseEntity<String>` with the complete JSON body already
serialized as text. The generic `successResponse(V value)` overload returns a map body for Spring to serialize

### successResponse

Builds a response with HTTP status `200`. The no-argument overload uses the default success message;
string arguments are message keys resolved using the current locale

#### Usage

###### No args

```java

@PostMapping(path = SIGN_UP_ENDPOINT)
@RequestPath(path = "/api/v2/users/hello", method = POST)
public ResponseEntity<String> signUp(
        @RequestBody Map<String, Object> payload
) {
    ...
    return successResponse();
}
```

###### Custom message

```java

@PostMapping(path = SIGN_UP_ENDPOINT)
@RequestPath(path = "/api/v2/users/hello", method = POST)
public ResponseEntity<String> signUp(
        @RequestBody Map<String, Object> payload
) {
    ...
    return successResponse("success_key");
}
```

###### Custom message with args

```java

@PostMapping(path = SIGN_UP_ENDPOINT)
@RequestPath(path = "/api/v2/users/hello", method = POST)
public ResponseEntity<String> signUp(
        @RequestBody Map<String, Object> payload
) {
    ...
    return successResponse("success_key", args1, args2);
}
```

### failedResponse

Builds a response with the HTTP status defined by `ResponseStatus.FAILED` (`500`).
The error key is resolved using the current locale

#### Usage

###### No args

```java

@PostMapping(path = SIGN_UP_ENDPOINT)
@RequestPath(path = "/api/v2/users/hello", method = POST)
public ResponseEntity<String> signUp(
        @RequestBody Map<String, Object> payload
) {
    ...
    return failedResponse("error_key");
}
```

###### With args

```java

@PostMapping(path = SIGN_UP_ENDPOINT)
@RequestPath(path = "/api/v2/users/hello", method = POST)
public ResponseEntity<String> signUp(
        @RequestBody Map<String, Object> payload
) {
    ...
    return failedResponse("error_key", new Object[] { args1, args2 });
}
```

### notAuthorizedOrWrongDetailsResponse

Preconfigured [failed response](#failedresponse) with the `NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE` message

#### Usage

```java

@PostMapping(path = SIGN_UP_ENDPOINT)
@RequestPath(path = "/api/v2/users/hello", method = POST)
public ResponseEntity<String> signUp(
        @RequestBody Map<String, Object> payload
) {
    ...
    return notAuthorizedOrWrongDetailsResponse();
}
```

### wrongProcedureResponse

Preconfigured [failed response](#failedresponse) with the `WRONG_PROCEDURE_MESSAGE` message

#### Usage

```java

@PostMapping(path = SIGN_UP_ENDPOINT)
@RequestPath(path = "/api/v2/users/hello", method = POST)
public ResponseEntity<String> signUp(
        @RequestBody Map<String, Object> payload
) {
    ...
    return wrongProcedureResponse();
}
```
