package com.tecknobit.equinoxbackend.configuration;

import com.tecknobit.apimanager.apis.ResourcesUtils;
import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.equinoxbackend.environment.services.builtin.controller.EquinoxController;
import com.tecknobit.equinoxbackend.resourcesutils.ResourcesManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.tecknobit.equinoxbackend.resourcesutils.ResourcesManager.RESOURCES_KEY;

/**
 * The {@code EquinoxBackendConfiguration} contains the current configuration used for an Equinox's
 * backend instance. It is based on a singleton design pattern to avoid useless instantiation
 *
 * @author N7ghtm4r3 - Tecknobit
 * @since 1.0.6
 */
public class EquinoxBackendConfiguration {

    /**
     * {@code configuration} the current configuration of the Equinox's backend instance
     */
    private static final EquinoxBackendConfiguration configuration = new EquinoxBackendConfiguration();

    /**
     * {@code EQUINOX_CONFIGURATION_PATHNAME} the pathname of the configuration file
     */
    private static final String EQUINOX_CONFIGURATION_PATHNAME = "equinox.config";

    /**
     * {@code resourcesConfig} the configuration of the resources
     */
    private ResourcesConfig resourcesConfig;

    /**
     * {@code serverProtectorConfig} the configuration of the server protector
     */
    private ServerProtectorConfig serverProtectorConfig;

    /**
     * Private constructor to instantiate the configuration instance
     */
    private EquinoxBackendConfiguration() {
        String rawConfiguration = getConfigurationContent();
        if (rawConfiguration != null) {
            JsonHelper hConfig = new JsonHelper(rawConfiguration);
            loadResourcesConfig(hConfig);
            loadServerProtectorConfig(hConfig);
        } else
            resourcesConfig = new ResourcesConfig();
    }

    /**
     * Method used to get the configuration of the backend
     *
     * @return the content of the configuration for the backend as {@link String}, {@code null} if not specified
     */
    private String getConfigurationContent() {
        ResourcesUtils<Class<EquinoxBackendConfiguration>> resourcesUtils = new ResourcesUtils<>(
                EquinoxBackendConfiguration.class);
        try {
            return resourcesUtils.getResourceContent(EQUINOX_CONFIGURATION_PATHNAME);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Method used to load the {@link #resourcesConfig} instance
     *
     * @param hConfig The configuration retrieved from the {@link #getConfigurationContent()} method
     */
    private void loadResourcesConfig(JsonHelper hConfig) {
        JsonHelper hResources = hConfig.getJsonHelper(RESOURCES_KEY);
        resourcesConfig = new ResourcesConfig(hResources);
    }

    /**
     * Method used to load the {@link #serverProtectorConfig} instance
     *
     * @param hConfig The configuration retrieved from the {@link #getConfigurationContent()} method
     */
    private void loadServerProtectorConfig(JsonHelper hConfig) {
        JsonHelper hServerProtector = hConfig.getJsonHelper(ServerProtectorConfig.SERVER_PROTECTOR_KEY);
        if (hServerProtector != null)
            serverProtectorConfig = new ServerProtectorConfig(hServerProtector);
    }

    /**
     * Method used to get the {@link #resourcesConfig} instance
     *
     * @return the {@link #resourcesConfig} instance as {@link ResourcesConfig}
     */
    public ResourcesConfig getResourcesConfig() {
        return resourcesConfig;
    }

    /**
     * Method used to get the {@link #serverProtectorConfig} instance
     *
     * @return the {@link #serverProtectorConfig} instance as {@link ServerProtectorConfig}
     */
    public ServerProtectorConfig getServerProtectorConfig() {
        return serverProtectorConfig;
    }

    /**
     * Method used to check whether the protection by the {@link com.tecknobit.apimanager.apis.ServerProtector} is
     * currently enabled
     *
     * @return whether the protection by the {@link com.tecknobit.apimanager.apis.ServerProtector} is
     * currently enabled as {@code boolean}
     */
    public boolean serverProtectorEnabled() {
        return serverProtectorConfig != null;
    }

    /**
     * The {@code ResourcesConfig} represents the configuration used for the static folders of the resources provided by
     * the server
     *
     * @author N7ghtm4r3 - Tecknobit
     * @since 1.1.0
     */
    public static final class ResourcesConfig {

        /**
         * {@code RESOURCES_FOLDER_KEY} the constant value for the {@code resources_folder} property
         */
        public static final String RESOURCES_FOLDER_KEY = "resources_folder";

        /**
         * {@code SUBDIRECTORIES_KEY} the constant value for the {@code subdirectories} property
         */
        public static final String SUBDIRECTORIES_KEY = "subdirectories";

        /**
         * {@code createResourcesFolder} whether the {@link ResourcesManager#RESOURCES_KEY} must be created,
         * if not specified will be created as default
         */
        private final boolean createResourcesFolder;

        /**
         * {@code subdirectories} the subdirectories to create and place under the {@link ResourcesManager#RESOURCES_KEY}
         * folder
         */
        private final List<String> subdirectories;

        /**
         * Default constructor used to instantiate the configuration
         */
        public ResourcesConfig() {
            createResourcesFolder = true;
            subdirectories = Collections.EMPTY_LIST;
        }

        /**
         * Constructor used to instantiate the configuration
         *
         * @param hConfig The configuration retrieved from the {@link #EQUINOX_CONFIGURATION_PATHNAME}
         */
        public ResourcesConfig(JsonHelper hConfig) {
            if (hConfig == null) {
                createResourcesFolder = true;
                subdirectories = Collections.EMPTY_LIST;
            } else {
                createResourcesFolder = hConfig.getBoolean(RESOURCES_FOLDER_KEY, true);
                subdirectories = hConfig.fetchList(SUBDIRECTORIES_KEY, new ArrayList<>());
            }
        }

        /**
         * Method used to get the {@link #createResourcesFolder} instance
         *
         * @return the {@link #createResourcesFolder} instance as {@code boolean}
         */
        public boolean createResourcesFolder() {
            return createResourcesFolder;
        }

        /**
         * Method used to get the {@link #subdirectories} instance
         *
         * @return the {@link #subdirectories} instance as {@link List} of {@link String}
         */
        public List<String> getSubdirectories() {
            return subdirectories;
        }

    }

    /**
     * The {@code ServerProtectorConfig} represents the configuration used to configure the
     * {@link EquinoxController#serverProtector} instance
     *
     * @author N7ghtm4r3 - Tecknobit
     * @since 1.1.0
     */
    public static final class ServerProtectorConfig {

        /**
         * {@code SERVER_PROTECTOR_KEY} the constant value for the {@code server_protector} property
         */
        public static final String SERVER_PROTECTOR_KEY = "server_protector";

        /**
         * {@code STORAGE_PATH_KEY} the constant value for the {@code storage_path} property
         */
        public static final String STORAGE_PATH_KEY = "storage_path";

        /**
         * {@code SAVE_MESSAGE_KEY} the constant value for the {@code save_message} property
         */
        public static final String SAVE_MESSAGE_KEY = "save_message";

        /**
         * {@code storagePath} the constant value for the {@code save_message} property
         */
        private final String storagePath;

        /**
         * {@code saveMessage} the constant value for the {@code save_message} property
         */
        private final String saveMessage;

        /**
         * Constructor used to instantiate the configuration
         *
         * @param hConfig The configuration retrieved from the {@link #EQUINOX_CONFIGURATION_PATHNAME}
         */
        public ServerProtectorConfig(JsonHelper hConfig) {
            storagePath = hConfig.getString(STORAGE_PATH_KEY);
            saveMessage = hConfig.getString(SAVE_MESSAGE_KEY);
        }

        /**
         * Method used to get the {@link #storagePath} instance
         *
         * @return the {@link #storagePath} instance as {@link String}
         */
        public String getStoragePath() {
            return storagePath;
        }

        /**
         * Method used to get the {@link #saveMessage} instance
         *
         * @param extra Extra arguments used to format the save message
         *
         * @return the {@link #saveMessage} instance as {@link String}
         */
        public String getSaveMessage(Object... extra) {
            return String.format(saveMessage, extra);
        }

    }

    /**
     * Method used to get the current {@link #configuration} instance
     *
     * @return the current {@link #configuration} instance as {@link EquinoxBackendConfiguration}
     */
    public static EquinoxBackendConfiguration getInstance() {
        return configuration;
    }

}
