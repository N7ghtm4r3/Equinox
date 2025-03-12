package com.tecknobit.equinoxbackend.configuration;

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
     * {@code serverProtectorEnabled} whether the protection by the {@link com.tecknobit.apimanager.apis.ServerProtector}
     * class is requested to protect the server from not authorized accesses
     */
    private boolean serverProtectorEnabled;

    /**
     * Private constructor to instantiate the configuration to use
     */
    private EquinoxBackendConfiguration() {
    }

    /**
     * Method used to get the {@link #serverProtectorEnabled} instance
     *
     * @return the {@link #serverProtectorEnabled} instance as boolean
     */
    public boolean isServerProtectorEnabled() {
        return serverProtectorEnabled;
    }

    /**
     * Method to set the {@link #serverProtectorEnabled} instance
     *
     * @param serverProtectorEnabled Whether the protection by the {@link com.tecknobit.apimanager.apis.ServerProtector}
     *                               class is requested to protect the server from not authorized accesses
     */
    public void setServerProtectorEnabled(boolean serverProtectorEnabled) {
        this.serverProtectorEnabled = serverProtectorEnabled;
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
