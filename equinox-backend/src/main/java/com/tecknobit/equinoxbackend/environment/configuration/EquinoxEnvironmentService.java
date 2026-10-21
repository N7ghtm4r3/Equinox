package com.tecknobit.equinoxbackend.environment.configuration;

import com.tecknobit.apimanager.apis.ServerProtector;
import com.tecknobit.equinoxbackend.apis.resources.ResourcesProvider;
import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;

import java.util.UUID;

import static com.tecknobit.equinoxbackend.apis.resources.ResourcesManager.RESOURCES_KEY;
import static com.tecknobit.equinoxbackend.environment.configuration.EquinoxBackendConfiguration.ResourcesConfig;
import static com.tecknobit.equinoxbackend.environment.configuration.EquinoxBackendConfiguration.ServerProtectorConfig;

/**
 * The {@code EquinoxEnvironmentService} class is useful to initialize the backend environment
 * and provide access to its configuration, resources, and server protector
 *
 * <p>Register a custom service through {@link #initialize(EquinoxEnvironmentService)} before
 * the first call to {@link #getInstance()}. Otherwise, the shared service is created on first access
 *
 * <p>Initialize the environment before starting the Spring Boot application
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
public class EquinoxEnvironmentService {

    /**
     * {@code equinoxEnvironmentService} the shared environment service, or {@code null} before initialization
     */
    private static EquinoxEnvironmentService equinoxEnvironmentService;

    /**
     * {@code resourcesProvider} the resources provider and manager
     */
    protected ResourcesProvider resourcesProvider;

    /**
     * {@code serverProtector} the server protector used to manage access to the backend
     *
     * @apiNote the commands scheme:
     * <ul>
     *     <li>
     *         <b>rss</b> -> launch your java application with "rss" to recreate the server secret <br>
     *                       e.g java -jar your_backend.jar rss
     *     </li>
     *     <li>
     *         <b>dss</b> -> launch your java application with "dss" to delete the current server secret <br>
     *                       e.g java -jar your_backend.jar dss
     *     </li>
     *     <li>
     *         <b>dssi</b> -> launch your java application with "dssi" to delete the current server secret and interrupt
     *                        the current workflow of the server <br>
     *                        e.g java -jar your_backend.jar dssi
     *     </li>
     * </ul>
     */
    protected ServerProtector serverProtector;

    /**
     * {@code backendConfiguration} the current configuration of the backend environment
     */
    protected final EquinoxBackendConfiguration backendConfiguration;

    /**
     * {@code equinoxUserLocaleResolver} the user locale resolver retained by this environment service
     */
    protected final EquinoxUserLocaleResolver<?> equinoxUserLocaleResolver;

    /**
     * Constructor to init the {@link EquinoxEnvironmentService} class with the backend configuration
     * and the default user locale resolver
     */
    protected EquinoxEnvironmentService() {
        backendConfiguration = EquinoxBackendConfiguration.getInstance();
        equinoxUserLocaleResolver = new EquinoxUserLocaleResolver<>();
    }

    /**
     * Method used to generate a random UUID identifier without hyphens
     *
     * @return the identifier as {@link String}
     */
    public String generateIdentifier() {
        UUID randomUUID = UUID.randomUUID();
        String identifier = randomUUID.toString();

        return identifier.replace("-", "");
    }

    /**
     * Method used to initialize the configured resource directories and server protector
     *
     * <p>Resource directories are created only when enabled in the backend configuration.
     * The server protector is launched only when its configuration is present. This method
     * does not start the Spring Boot application
     *
     * @param args             The command-line arguments passed to the server protector
     * @param saveMessageExtra The values used to format the server protector's save message
     * @throws RuntimeException if initialization fails or the enabled server protector has already
     *                          been instantiated
     * @apiNote the server protector accepts the following commands:
     * <ul>
     *     <li>{@code rss} to recreate the server secret</li>
     *     <li>{@code dss} to delete the current server secret</li>
     *     <li>{@code dssi} to delete the current server secret and interrupt the server workflow</li>
     * </ul>
     */
    public void initEquinoxEnvironment(String[] args, Object... saveMessageExtra) {
        try {
            ResourcesConfig resourcesConfig = backendConfiguration.getResourcesConfig();

            if (resourcesConfig.createResourcesFolder()) {
                resourcesProvider = new ResourcesProvider(RESOURCES_KEY, resourcesConfig.getSubdirectories());
                resourcesProvider.createContainerDirectory();
                resourcesProvider.createSubDirectories();
            }

            if (backendConfiguration.serverProtectorEnabled()) {
                if (serverProtector != null)
                    throw new IllegalAccessException("The protector has been already instantiated");

                ServerProtectorConfig serverProtectorConfig = backendConfiguration.getServerProtectorConfig();
                serverProtector = new ServerProtector(
                        serverProtectorConfig.getStoragePath(),
                        serverProtectorConfig.getSaveMessage(saveMessageExtra)
                );

                serverProtector.launch(args);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method used to get the current backend configuration
     *
     * @return the {@link #backendConfiguration} instance as {@link EquinoxBackendConfiguration}
     */
    public EquinoxBackendConfiguration getBackendConfiguration() {
        return backendConfiguration;
    }

    /**
     * Method used to get the server protector of the backend environment
     *
     * @return the {@link #serverProtector} instance, or {@code null} if it has not been instantiated,
     * as {@link ServerProtector}
     */
    public ServerProtector getServerProtector() {
        return serverProtector;
    }

    /**
     * Method used to get the resources provider of the backend environment
     *
     * @return the {@link #resourcesProvider} instance, or {@code null} if resource directory creation
     * has not been initialized or is disabled, as {@link ResourcesProvider}
     */
    public ResourcesProvider getResourcesProvider() {
        return resourcesProvider;
    }

    /**
     * Method used to get the locale resolver retained by this environment service
     *
     * <p>The default implementation returns the same resolver on each call. Subclasses can override
     * this method to provide a resolver for their own user type
     *
     * @return the {@link #equinoxUserLocaleResolver} instance as {@link EquinoxUserLocaleResolver}
     */
    public EquinoxUserLocaleResolver<? extends EquinoxUser> getUserLocaleResolver() {
        return equinoxUserLocaleResolver;
    }

    /**
     * Method used to get the shared environment service
     *
     * <p>A default service is registered on first access if no custom service has been registered.
     * Concurrent first access must be coordinated by the caller
     *
     * @return the shared service as {@link EquinoxEnvironmentService}
     * @throws IllegalStateException if another call registers a service between the initialization
     *     check and the attempt to register the default service
     */
    public static EquinoxEnvironmentService getInstance() {
        if(equinoxEnvironmentService == null)
            initialize(new EquinoxEnvironmentService());

        return equinoxEnvironmentService;
    }

    /**
     * Method used to register the shared environment service
     *
     * <p>Register a custom service before the first call to {@link #getInstance()}. An existing
     * non-null service cannot be replaced
     *
     * @param environmentService The non-null environment service to share with backend components
     * @throws IllegalArgumentException if {@code environmentService} is {@code null}
     * @throws IllegalStateException if a non-null service has already been registered
     */
    public static synchronized void initialize(EquinoxEnvironmentService environmentService) {
        if(environmentService == null)
            throw new IllegalArgumentException("Environment service instance must be not null");

        if(equinoxEnvironmentService != null)
            throw new IllegalStateException("Environment service instance already initialized");

        equinoxEnvironmentService = environmentService;
    }

}
