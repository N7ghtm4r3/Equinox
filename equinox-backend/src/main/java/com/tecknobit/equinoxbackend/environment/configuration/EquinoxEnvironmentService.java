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
 * <p>The shared instance is available through {@link #getInstance()}. Initialize the environment
 * before starting the Spring Boot application
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
public class EquinoxEnvironmentService {

    /**
     * {@code equinoxEnvironmentService} the shared instance of the environment service
     */
    private static final EquinoxEnvironmentService equinoxEnvironmentService = new EquinoxEnvironmentService();

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
     * Constructor to init the {@link EquinoxEnvironmentService} class with the backend configuration
     */
    protected EquinoxEnvironmentService() {
        backendConfiguration = EquinoxBackendConfiguration.getInstance();
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

    public <U extends EquinoxUser> EquinoxUserLocaleResolver<U> getUserLocaleResolver() {
        return new EquinoxUserLocaleResolver<>();
    }

    /**
     * Method used to get the shared environment service
     *
     * @return the {@link #equinoxEnvironmentService} instance as {@link EquinoxEnvironmentService}
     */
    public static EquinoxEnvironmentService getInstance() {
        return equinoxEnvironmentService;
    }

}
