Provides the logic of an **Equinox's controller** and some utilities to build responses and to handle the `i18n`

## Constants

These following messages are `i18n` ready

| **Constant**                              | **Description**                                                                             |
|-------------------------------------------|---------------------------------------------------------------------------------------------|
| `WRONG_PROCEDURE_MESSAGE`                 | Message to use when the procedure is wrong                                                  |
| `NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE` | Message to use when the request is by a not authorized user or tried to fetch wrong details |
| `RESPONSE_SUCCESSFUL_MESSAGE`             | Message to use when the request has been successful                                         |
| `WRONG_NAME_MESSAGE`                      | Error message used when the name inserted is not valid                                      |
| `WRONG_SURNAME_MESSAGE`                   | Error message used when the surname inserted is not valid                                   |
| `WRONG_EMAIL_MESSAGE`                     | Error message used when the email inserted is not valid                                     |
| `WRONG_PASSWORD_MESSAGE`                  | Error message used when the password inserted is not valid                                  |
| `WRONG_LANGUAGE_MESSAGE`                  | Error message used when the language inserted is not valid                                  |

## JSON utilities

This API provide an instance to work with the `JSON` payloads sent with the request

### jsonHelper

This API allows to retrieve values from the `JSON` payload without to specify the complete path to the value to get

#### Usage

```java

@PostMapping(path = SIGN_UP_ENDPOINT)
@RequestPath(path = "/api/v1/users/signUp", method = POST)
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
@RequestPath(path = "/api/v1/users/hello?language=it", method = POST)
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
@RequestPath(path = "/api/v1/users/hello", method = POST)
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
@RequestPath(path = "/api/v1/users/hello?language=it", method = POST)
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

The following methods allows to build or to use preconfigured response messages

### successResponse

Gets the payload for a successful response

#### Usage

###### No args

```java

@PostMapping(path = SIGN_UP_ENDPOINT)
@RequestPath(path = "/api/v1/users/hello", method = POST)
public String signUp(
        @RequestBody Map<String, Object> payload
) {
    ...
    return successResponse();
}
```

###### Custom message

```java

@PostMapping(path = SIGN_UP_ENDPOINT)
@RequestPath(path = "/api/v1/users/hello", method = POST)
public String signUp(
        @RequestBody Map<String, Object> payload
) {
    ...
    return successResponse("My success message");
}
```

###### Custom message with args

```java

@PostMapping(path = SIGN_UP_ENDPOINT)
@RequestPath(path = "/api/v1/users/hello", method = POST)
public String signUp(
        @RequestBody Map<String, Object> payload
) {
    ...
    return successResponse("My success message", args1, args2, etc...)
}
```

### failedResponse

Gets the payload for a failed response

#### Usage

###### No args

```java

@PostMapping(path = SIGN_UP_ENDPOINT)
@RequestPath(path = "/api/v1/users/hello", method = POST)
public String signUp(
        @RequestBody Map<String, Object> payload
) {
    ...
    return failedResponse("error_key");
}
```

###### With args

```java

@PostMapping(path = SIGN_UP_ENDPOINT)
@RequestPath(path = "/api/v1/users/hello", method = POST)
public String signUp(
        @RequestBody Map<String, Object> payload
) {
    ...
    return failedResponse("error_key", args1, args2, etc...)
}
```

### notAuthorizedOrWrongDetailsResponse

Preconfigured [failed response](#failedresponse) with the `NOT_AUTHORIZED_OR_WRONG_DETAILS_MESSAGE` message

#### Usage

```java

@PostMapping(path = SIGN_UP_ENDPOINT)
@RequestPath(path = "/api/v1/users/hello", method = POST)
public String signUp(
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
@RequestPath(path = "/api/v1/users/hello", method = POST)
public String signUp(
        @RequestBody Map<String, Object> payload
) {
    ...
    return wrongProcedureResponse();
}
```