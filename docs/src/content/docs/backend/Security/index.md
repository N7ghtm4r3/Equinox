---
title: "Security"
slug: "backend/Security"
---

<span id="security"></span>

The backend provides components for token authentication and user authorization

## Security configuration

[SecurityConfig](/Equinox/backend/Configurations/#securityconfig) supplies a default stateless chain and token
filter through Spring Boot auto-configuration. Provide your own beans to replace individual defaults,
or extend the configuration to customize its protected baseline methods

## User authentication

Use [EquinoxTokenAuthenticationFilter](/Equinox/backend/Security/authentication/EquinoxTokenAuthenticationFilter/) to load
users from the `token` request header. Its customization guide covers authentication state, request
exclusions, and registering a replacement filter bean


Use [EquinoxUserAuthentication](/Equinox/backend/Security/authentication/EquinoxUserAuthentication/) to represent a user
as the authentication principal after your application has verified the user. Both constructors
create authenticated tokens; the constructor accepting authorities also lets you assign permissions

## User authorization

Use [IsAuthorizedUser](/Equinox/backend/Security/authorization/annotations/IsAuthorizedUser/) to restrict access by comparing
the requested user identifier with the authenticated principal's identifier
