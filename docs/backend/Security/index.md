# Security

The backend provides components for token authentication and user authorization

## User authentication

Use [EquinoxUserAuthentication](authentication/EquinoxUserAuthentication.md) to represent a user
as the authentication principal. Its constructors distinguish an unauthenticated token from a token
created after your application has verified the user

## User authorization

Use [IsAuthorizedUser](authorization/annotations/IsAuthorizedUser.md) to restrict access by comparing
the requested user identifier with the authenticated principal's identifier
