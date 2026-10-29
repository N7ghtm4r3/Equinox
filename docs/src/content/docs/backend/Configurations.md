---
title: "Configurations"
slug: "backend/Configurations"
---

In this page you can find different configuration components ready to be implemented in your backend

## SecurityConfig

`SecurityConfig` provides the default stateless security filter chain and token filter. It belongs to the
`com.tecknobit.equinoxbackend.environment.security.configuration` package and is shipped as a Spring Boot
auto-configuration, so you do not need to copy or import it into your application

Initialize the [Equinox environment](/Equinox/backend/Environment/#out-of-the-box-launch) before Spring Boot startup
and provide an `EquinoxUsersRepository` bean for your user type


<aside class="equinox-callout note">

<p class="equinox-callout-title">Let Spring Boot load the security defaults</p>


Component scanning searches selected packages for components such as controllers, services, and
configuration classes. Auto-configuration is a separate loading mechanism: Spring Boot discovers
`SecurityConfig` through the `.imports` file already included in the Equinox JAR and processes it
after your application's bean definitions

If your application explicitly scans Equinox packages with `@ComponentScan`, exclude registered
auto-configuration classes from that scan using `AutoConfigurationExcludeFilter`, as shown in the
[Java and Kotlin launch examples](/Equinox/backend/Environment/#out-of-the-box-launch)

This exclusion does not disable `SecurityConfig`. It lets Spring Boot load it as an auto-configuration,
so `@ConditionalOnMissingBean` can see your custom beans before registering defaults.

You do not need to create the `.imports` file in your application or import `SecurityConfig` manually

</aside>

### Default behavior

| Component | Behavior |
| --- | --- |
| Filter chain | Stateless sessions, CSRF and form login disabled |
| Public paths | `/api/v2/users/signUp`, `/api/v2/users/signIn`, and configured resource directories through `/directory/**` |
| Other paths | Require an authenticated security context |
| Token filter | Runs before `AnonymousAuthenticationFilter` |
| Access denied handler | Delegates `EquinoxForbiddenException` to MVC for a localized HTTP `403` failure response |

The access denied handler uses the `localeResolver` bean supplied by
[EquinoxLocaleConfig](#equinoxlocaleconfig) and delegates the exception to the MVC
`handlerExceptionResolver`. Register [EquinoxGlobalExceptionHandler](/Equinox/backend/Exceptions/EquinoxGlobalExceptionHandler/#registration)
to produce the localized Equinox failure response

With the default locale resolver, authenticated users use their stored language; absent, unauthenticated,
or anonymous authentication uses the request locale. The handler resolves this locale explicitly because
Security can reject a request before MVC establishes its locale context


<aside class="equinox-callout warning">

<p class="equinox-callout-title">Documentation endpoints in production</p>


Swagger UI and OpenAPI paths are permitted only when both `springdoc.api-docs.enabled` and
`springdoc.swagger-ui.enabled` are `true`. Both flags default to `false` in this configuration
Keep documentation endpoints restricted in production

</aside>


<aside class="equinox-callout note">

<p class="equinox-callout-title">Authentication does not grant roles</p>


The default [token filter](/Equinox/backend/Security/authentication/EquinoxTokenAuthenticationFilter/) creates an
authenticated `EquinoxUserAuthentication` after finding a user by token. No authorities are assigned,
so the user satisfies `.authenticated()` without automatically receiving a role

</aside>

### Replace a default bean

Spring Boot processes application bean definitions before Equinox's auto-configuration
`@ConditionalOnMissingBean` then provides only the components that your application has not declared

- A `SecurityFilterChain` bean replaces the default chain
- An `EquinoxTokenAuthenticationFilter` bean, including a subclass, replaces the default filter


<aside class="equinox-callout warning">

<p class="equinox-callout-title">A custom chain owns its security rules</p>


The conditions are independent. Replacing the chain does not remove the default filter bean or
automatically add it to your own chain. Include the filter explicitly when your chain uses Equinox
token authentication

Your chain does not inherit Equinox's sign-in, sign-up, resource, or documentation rules
Declare all public paths and other security settings that your application needs

</aside>

This example adds a public `/health` path and uses the application-provided filter or the default one


<div class="equinox-tabs" id="equinox-tabs-0">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-0-tab-0" aria-controls="equinox-tabs-0-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-0-tab-1" aria-controls="equinox-tabs-0-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-0-panel-0" aria-labelledby="equinox-tabs-0-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.environment.security.filters.EquinoxTokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration(proxyBeanMethods = false)
public class ApplicationSecurityConfig {

    @Bean
    public SecurityFilterChain applicationSecurityFilterChain(
            HttpSecurity http,
            EquinoxTokenAuthenticationFilter<?, ?> tokenFilter) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/health").permitAll()
                        .anyRequest().authenticated())
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilterBefore(tokenFilter, AnonymousAuthenticationFilter.class);
        return http.build();
    }

}
```

</div>

<div role="tabpanel" id="equinox-tabs-0-panel-1" aria-labelledby="equinox-tabs-0-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.environment.security.filters.EquinoxTokenAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter

@Configuration(proxyBeanMethods = false)
class ApplicationSecurityConfig {

    @Bean
    fun applicationSecurityFilterChain(
        http: HttpSecurity,
        tokenFilter: EquinoxTokenAuthenticationFilter<*, *>
    ): SecurityFilterChain {
        http.csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.requestMatchers("/health").permitAll()
                    .anyRequest().authenticated()
            }
            .formLogin { it.disable() }
            .addFilterBefore(tokenFilter, AnonymousAuthenticationFilter::class.java)
        return http.build()
    }

}
```

</div>

</div>

For a custom filter bean, see [Register the filter](/Equinox/backend/Security/authentication/EquinoxTokenAuthenticationFilter/#register-the-filter)

### Customize through inheritance

Extend `SecurityConfig` when you want to retain its bean factories and customize selected parts of the
baseline. Register the subclass as an application configuration

The inherited `@Bean` methods supply the beans through the subclass, so its protected method overrides
apply. With the current `@ConditionalOnMissingBean` conditions, the original auto-configuration skips
its default beans when the subclass has already supplied their definitions


<aside class="equinox-callout note">

<p class="equinox-callout-title">Optional auto-configuration exclusion</p>


Excluding the original auto-configuration is not required for this customization. If you want to
disable its automatic loading entirely, add the following entry to your application's `application.properties`:

```properties
spring.autoconfigure.exclude=com.tecknobit.equinoxbackend.environment.security.configuration.SecurityConfig
```

This setting controls auto-configuration loading separately from component scanning. Your
registered application configuration subclass still supplies the inherited beans

</aside>

The following subclass adds `/health` to the existing public patterns. Resource paths are still added by
`getPermittedPatterns()`, and the remaining baseline settings are retained


<div class="equinox-tabs" id="equinox-tabs-1">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-1-tab-0" aria-controls="equinox-tabs-1-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-1-tab-1" aria-controls="equinox-tabs-1-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-1-panel-0" aria-labelledby="equinox-tabs-1-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.environment.security.configuration.SecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.ArrayList;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableMethodSecurity
public class CustomSecurityConfig extends SecurityConfig {

    @Override
    protected ArrayList<String> resolvePermittedPatterns() {
        ArrayList<String> patterns = super.resolvePermittedPatterns();
        patterns.add("/health");
        return patterns;
    }

}
```

</div>

<div role="tabpanel" id="equinox-tabs-1-panel-1" aria-labelledby="equinox-tabs-1-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.environment.security.configuration.SecurityConfig
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableMethodSecurity
class CustomSecurityConfig : SecurityConfig() {

    override fun resolvePermittedPatterns(): ArrayList<String> {
        val patterns = super.resolvePermittedPatterns()
        patterns.add("/health")
        return patterns
    }

}
```

</div>

</div>

| Protected method | Customization |
| --- | --- |
| `resolvePermittedPatterns()` | Change base public paths while retaining the automatic resource paths |
| `getPermittedPatterns()` | Replace the complete public path list |
| `assembleFilterChainBaseline(http, tokenFilter)` | Customize the security builder before the inherited factory calls `build()` |
| `useAccessDeniedHandler()` | Replace the access denied response handler |

Call the corresponding superclass method when you want to retain its defaults. When extending the assembled
baseline, add settings to the returned `HttpSecurity` builder; the inherited factory builds the chain

## ResourcesConfig

This configuration class allows the backend to serve static resources


<div class="equinox-tabs" id="equinox-tabs-2">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-2-tab-0" aria-controls="equinox-tabs-2-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-2-tab-1" aria-controls="equinox-tabs-2-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-2-panel-0" aria-labelledby="equinox-tabs-2-tab-0" tabindex="0">


```java
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

/**
 * The {@code ResourceConfigs} class is useful to set the configuration of the resources to correctly serve the
 * images by the server
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see WebMvcConfigurer
 */
@Configuration
public class ResourcesConfig implements WebMvcConfigurer {

    /**
     * Add handlers to serve static resources such as images, js, and, css
     * files from specific locations under web application root, the classpath,
     * and others.
     *
     * @see ResourceHandlerRegistry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("file:<list>") // replace <list> with the folders the backend will use
                .setCachePeriod(0)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }

}
```

</div>

<div role="tabpanel" id="equinox-tabs-2-panel-1" aria-labelledby="equinox-tabs-2-tab-1" tabindex="0">


```kotlin
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.resource.PathResourceResolver

/**
 * The `ResourceConfigs` class is useful to set the configuration of the resources to correctly serve the
 * images by the server
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see WebMvcConfigurer
 */
@Configuration
class ResourcesConfig : WebMvcConfigurer {

    /**
     * Add handlers to serve static resources such as images, js, and, css
     * files from specific locations under web application root, the classpath,
     * and others.
     *
     * @see ResourceHandlerRegistry
     */
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/**")
            .addResourceLocations("file:<list>") // replace <list> with the folders the backend will use
            .setCachePeriod(0)
            .resourceChain(true)
            .addResolver(PathResourceResolver())
    }

}
```

</div>

</div>

## CORS

This configuration class allows to set the `CORS` origin policy for the backend


<div class="equinox-tabs" id="equinox-tabs-3">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-3-tab-0" aria-controls="equinox-tabs-3-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-3-tab-1" aria-controls="equinox-tabs-3-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-3-panel-0" aria-labelledby="equinox-tabs-3-tab-0" tabindex="0">


```java
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * The {@code CORSAdvice} class is useful to set the CORS policy
 *
 * @author N7ghtm4r3 - Tecknobit
 */
@Configuration
public class CORSAdvice {

    /**
     * Method used to set the CORS filter 
     */
    @Bean
    @SuppressWarnings({"rawtypes"})
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false); // set as you need
        config.addAllowedOrigin("*"); // set as you need
        config.addAllowedHeader("*"); // set as you need
        config.addAllowedMethod("*"); // set as you need
        source.registerCorsConfiguration("/**", config); // set as you need
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

}
```

</div>

<div role="tabpanel" id="equinox-tabs-3-panel-1" aria-labelledby="equinox-tabs-3-tab-1" tabindex="0">


```kotlin
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

/**
 * The `CORSAdvice` class is useful to set the CORS policy
 *
 * @author N7ghtm4r3 - Tecknobit
 */
@Configuration
class CORSAdvice {

    /**
     * Method used to set the CORS filter
     */
    @Bean
    @SuppressWarnings(["rawtypes"])
    fun corsFilter(): FilterRegistrationBean<*> {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = false // set as you need
        config.addAllowedOrigin("*") // set as you need
        config.addAllowedHeader("*") // set as you need
        config.addAllowedMethod("*") // set as you need
        source.registerCorsConfiguration("/**", config) // set as you need
        val bean: FilterRegistrationBean<*> = FilterRegistrationBean(CorsFilter(source))
        bean.order = 0
        return bean
    }

}
```

</div>

</div>

## MessageSource

This configuration provides the message resource bundles used to internationalize your backend application.
It loads the configured bundles with UTF-8 encoding and uses English as the default message source locale.

Request locale selection is configured separately by [EquinoxLocaleConfig](#equinoxlocaleconfig).
Use both configurations together: `MessageSourceConfig` provides the `messageSource` bean, while
`EquinoxLocaleConfig` provides the `localeResolver` bean.


<div class="equinox-tabs" id="equinox-tabs-4">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-4-tab-0" aria-controls="equinox-tabs-4-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-4-tab-1" aria-controls="equinox-tabs-4-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-4-panel-0" aria-labelledby="equinox-tabs-4-tab-0" tabindex="0">


```java
package com.tecknobit.equinoxbackend.environment.configuration;

import com.tecknobit.equinoxcore.annotations.Assembler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;

import static java.util.Locale.ENGLISH;

/**
 * The {@code MessageSourceConfig} class configures the message resources bundle
 * used by the backend application.
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
@Configuration
public class MessageSourceConfig {

    /**
     * {@code MESSAGES_KEY} default path where are placed the resources bundle
     */
    private static final String MESSAGES_KEY = "lang/messages"; // customize as needed

    /**
     * {@code CUSTOM_MESSAGES_KEY} path where the user can place the custom resources bundle
     */
    private static final String CUSTOM_MESSAGES_KEY = "lang/custom_messages"; // customize as needed

    /**
     * Method used to load and assemble the bundle resources of the international messages resources
     *
     * @return the bundle as {@link ResourceBundleMessageSource}
     */
    @Bean
    @Assembler
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(MESSAGES_KEY, CUSTOM_MESSAGES_KEY); // customize as needed
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setDefaultLocale(ENGLISH); // customize as needed
        messageSource.setUseCodeAsDefaultMessage(true); // Use the message code when no translation is available
        return messageSource;
    }

}
```

</div>

<div role="tabpanel" id="equinox-tabs-4-panel-1" aria-labelledby="equinox-tabs-4-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxcore.annotations.Assembler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import java.nio.charset.StandardCharsets
import java.util.Locale

/**
 * The `MessageSourceConfig` class configures the message resources bundle
 * used by the backend application.
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
@Configuration
open class MessageSourceConfig {

    companion object {

        /**
         * `MESSAGES_KEY` default path where are placed the resources bundle
         */
        private const val MESSAGES_KEY = "lang/messages"

        /**
         * `CUSTOM_MESSAGES_KEY` path where the user can place the custom resources bundle
         */
        private const val CUSTOM_MESSAGES_KEY = "lang/custom_messages"

    }

    /**
     * Method used to load and assemble the bundle resources of the international messages resources
     *
     * @return the bundle as [ResourceBundleMessageSource]
     */
    @Bean
    @Assembler
    open fun messageSource(): ResourceBundleMessageSource {
        val messageSource = ResourceBundleMessageSource()
        messageSource.setBasenames(MESSAGES_KEY, CUSTOM_MESSAGES_KEY) // customize as needed
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name())
        messageSource.setDefaultLocale(Locale.ENGLISH) // customize as needed
        messageSource.setUseCodeAsDefaultMessage(true) // Use the message code when no translation is available
        return messageSource
    }

}
```

</div>

</div>

## EquinoxLocaleConfig

This configuration exposes the `localeResolver` bean used to resolve request locales from the authenticated
user's language. It retrieves the resolver from the shared
[environment service](/Equinox/backend/Environment/#environment-service), so a custom service can supply its own
[EquinoxUserLocaleResolver](/Equinox/backend/APIs/EquinoxUserLocaleResolver/).

The default environment service creates its resolver once and returns the same instance on subsequent
calls. Register a custom environment service before the first call to `getInstance()` and before Spring
creates the `localeResolver` bean.

Use this configuration alongside [MessageSource](#messagesource): the resolver selects the request locale,
and the message source uses that locale to look up translations in the configured bundles.


<div class="equinox-tabs" id="equinox-tabs-5">

<div role="tablist" aria-label="Examples by platform or language">
<button type="button" role="tab" id="equinox-tabs-5-tab-0" aria-controls="equinox-tabs-5-panel-0" aria-selected="true" tabindex="0">Java</button>
<button type="button" role="tab" id="equinox-tabs-5-tab-1" aria-controls="equinox-tabs-5-panel-1" aria-selected="false" tabindex="-1">Kotlin</button>
</div>

<div role="tabpanel" id="equinox-tabs-5-panel-0" aria-labelledby="equinox-tabs-5-tab-0" tabindex="0">


```java
import com.tecknobit.equinoxbackend.environment.configuration.EquinoxEnvironmentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

@Configuration(proxyBeanMethods = false)
public final class EquinoxLocaleConfig {

    @Bean
    public LocaleResolver localeResolver() {
        EquinoxEnvironmentService environmentService = EquinoxEnvironmentService.getInstance();
        return environmentService.getUserLocaleResolver();
    }

}
```

</div>

<div role="tabpanel" id="equinox-tabs-5-panel-1" aria-labelledby="equinox-tabs-5-tab-1" tabindex="0">


```kotlin
import com.tecknobit.equinoxbackend.environment.configuration.EquinoxEnvironmentService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver

@Configuration(proxyBeanMethods = false)
class EquinoxLocaleConfig {

    @Bean
    fun localeResolver(): LocaleResolver {
        val environmentService = EquinoxEnvironmentService.getInstance()
        return environmentService.getUserLocaleResolver()
    }

}
```

</div>

</div>

For the default resolution rules and a custom resolver example, see
[EquinoxUserLocaleResolver](/Equinox/backend/APIs/EquinoxUserLocaleResolver/).
