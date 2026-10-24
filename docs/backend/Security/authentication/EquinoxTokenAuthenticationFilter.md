# EquinoxTokenAuthenticationFilter

Loads an `EquinoxUser` from the `token` request header and exposes it in the current Spring Security
context. `EquinoxTokenAuthenticationFilter<U, R>` accepts a custom user type and a matching
`EquinoxUsersRepository<U>` implementation

It belongs to the `com.tecknobit.equinoxbackend.environment.security.filters` package

## Behavior

Initialize the [Equinox environment](../../Environment.md#out-of-the-box-launch) before the filter is
created. The constructor copies the configured resource subdirectories into the filter's exclusions

| Request | Result |
| --- | --- |
| URI starts with an excluded resource directory prefix | Skips token processing |
| Missing or blank `token` header | Continues without changing the security context |
| No user matches `findUserByToken(token)` | Continues without changing the security context |
| User matches the token | Replaces the context authentication with `getUserAuthentication(user)` and continues |

The filter does not directly reject missing or unknown tokens. The security chain decides whether the
request can access the endpoint

The default `getUserAuthentication(user)` uses the single-argument
[EquinoxUserAuthentication](EquinoxUserAuthentication.md) constructor, which creates an authenticated
token without authorities. A matching user therefore satisfies the default chain's `.authenticated()`
requirement without automatically receiving a role

## Customization

### Custom authentication and exclusions

Extend the filter and override `getUserAuthentication()` to choose the authentication representation
The following example assigns `ROLE_USER` to every user found by token and also skips token processing
for `/health`. Choose the authorities according to your application's permission policy

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.environment.security.authentication.EquinoxUserAuthentication;
    import com.tecknobit.equinoxbackend.environment.security.filters.EquinoxTokenAuthenticationFilter;
    import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
    import com.tecknobit.equinoxbackend.environment.services.users.repository.EquinoxUsersRepository;
    import jakarta.servlet.http.HttpServletRequest;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;

    import java.util.List;

    public class CustomTokenFilter extends EquinoxTokenAuthenticationFilter<
            EquinoxUser, EquinoxUsersRepository<EquinoxUser>> {

        public CustomTokenFilter(EquinoxUsersRepository<EquinoxUser> repository) {
            super(repository);
        }

        @Override
        protected EquinoxUserAuthentication<EquinoxUser> getUserAuthentication(EquinoxUser user) {
            return new EquinoxUserAuthentication<>(user, List.of(new SimpleGrantedAuthority("ROLE_USER")));
        }

        @Override
        protected boolean shouldNotFilter(HttpServletRequest request) {
            return "/health".equals(request.getRequestURI()) || super.shouldNotFilter(request);
        }

    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.environment.security.authentication.EquinoxUserAuthentication
    import com.tecknobit.equinoxbackend.environment.security.filters.EquinoxTokenAuthenticationFilter
    import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser
    import com.tecknobit.equinoxbackend.environment.services.users.repository.EquinoxUsersRepository
    import jakarta.servlet.http.HttpServletRequest
    import org.springframework.security.core.authority.SimpleGrantedAuthority

    class CustomTokenFilter(repository: EquinoxUsersRepository<EquinoxUser>) :
        EquinoxTokenAuthenticationFilter<EquinoxUser, EquinoxUsersRepository<EquinoxUser>>(repository) {

        override fun getUserAuthentication(user: EquinoxUser): EquinoxUserAuthentication<EquinoxUser> {
            return EquinoxUserAuthentication(user, listOf(SimpleGrantedAuthority("ROLE_USER")))
        }

        override fun shouldNotFilter(request: HttpServletRequest): Boolean {
            return request.requestURI == "/health" || super.shouldNotFilter(request)
        }

    }
    ```

These examples use `EquinoxUser`; substitute your user subclass and its repository type when needed
Perform any additional account or token checks required by your application before returning an
authenticated token. Retain the default `getUserAuthentication()` implementation when no authorities
need to be granted

Calling `super.shouldNotFilter(request)` retains the resource exclusions. The default implementation
matches string prefixes: a configured `images` directory skips both `/images/file.png` and
`/images-other`. Override the method without the superclass call if you need a different matching policy

Skipping the filter does not make an endpoint public. Permit `/health` in the
[security configuration](../../Configurations.md#securityconfig) as well

### Register the filter

Declare your subclass as a Spring bean. Merely extending the class does not replace the default filter
This example assumes your application provides an `EquinoxUsersRepository<EquinoxUser>` bean

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
    import com.tecknobit.equinoxbackend.environment.services.users.repository.EquinoxUsersRepository;
    import org.springframework.boot.web.servlet.FilterRegistrationBean;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;

    @Configuration(proxyBeanMethods = false)
    public class TokenFilterConfig {

        @Bean
        public CustomTokenFilter customTokenFilter(EquinoxUsersRepository<EquinoxUser> repository) {
            return new CustomTokenFilter(repository);
        }

        @Bean
        public FilterRegistrationBean<CustomTokenFilter> tokenFilterRegistration(CustomTokenFilter filter) {
            FilterRegistrationBean<CustomTokenFilter> registration = new FilterRegistrationBean<>(filter);
            registration.setEnabled(false);
            return registration;
        }

    }
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser
    import com.tecknobit.equinoxbackend.environment.services.users.repository.EquinoxUsersRepository
    import org.springframework.boot.web.servlet.FilterRegistrationBean
    import org.springframework.context.annotation.Bean
    import org.springframework.context.annotation.Configuration

    @Configuration(proxyBeanMethods = false)
    class TokenFilterConfig {

        @Bean
        fun customTokenFilter(repository: EquinoxUsersRepository<EquinoxUser>): CustomTokenFilter {
            return CustomTokenFilter(repository)
        }

        @Bean
        fun tokenFilterRegistration(filter: CustomTokenFilter): FilterRegistrationBean<CustomTokenFilter> {
            val registration = FilterRegistrationBean(filter)
            registration.isEnabled = false
            return registration
        }

    }
    ```

`SecurityConfig` detects the custom filter bean, skips its default filter, and injects the custom one
into the default chain. This does not require extending `SecurityConfig`

The disabled `org.springframework.boot.web.servlet.FilterRegistrationBean` prevents the embedded
servlet container from registering the filter separately. Spring Security then controls its position
in the chain, as described in the [Spring Security filter documentation](https://docs.spring.io/spring-security/reference/servlet/architecture.html#_declaring_your_filter_as_a_bean)

If you also provide your own `SecurityFilterChain`, add the filter to that chain explicitly
