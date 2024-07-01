package com.tecknobit.equinox.resourcesutils;

import com.tecknobit.apimanager.annotations.Wrapper;

import java.io.File;
import java.util.HashSet;
import java.util.List;

/**
 * The {@code ResourcesProvider} class is useful to create the resources directories and manage the main resources files
 * such as the properties for the server
 *
 * @author N7ghtm4r3 - Tecknobit
 */
public class ResourcesProvider {

    /**
     * {@code DEFAULT_CONFIGURATION_FILE_PATH} the default path where find the default server configuration
     */
    public static final String DEFAULT_CONFIGURATION_FILE_PATH = "app.properties";

    /**
     * {@code CUSTOM_CONFIGURATION_FILE_PATH} the path of the custom server configuration file
     *
     * @apiNote to use your custom configuration <b>you must save the file in the same folder where you placed the
     * server file (.jar) and call it "custom.properties"</b>
     * @implSpec take a look <a href="https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html">here</a>
     * to get more information about the custom configuration for properties that you can use
     */
    public static final String CUSTOM_CONFIGURATION_FILE_PATH = "custom.properties";

    /**
     * {@code containers} the set of the containers folder which contains other subfolders to store the resources
     *
     * @apiNote e.g. containers folder:
     * <pre>
     *     {@code
     *          containerFolder_1
     *          |
     *          |-> subfolder_1
     *          |-> subfolder_2
     *          containerFolder_2
     *          |
     *          |-> subfolder_1
     *          |-> subfolder_2
     *     }
     * </pre>
     */
    protected HashSet<String> containers;

    /**
     * {@code containerDirectory} the main container directory to create
     */
    protected final String containerDirectory;

    /**
     * {@code subDirectories} the subdirectories of the {@link #containerDirectory} folder
     */
    protected final List<String> subDirectories;

    /**
     * Constructor to init the {@link ResourcesProvider} class
     *
     * @param containerDirectory: the main container directory to create
     */
    public ResourcesProvider(String containerDirectory) {
        this(containerDirectory, List.of());
    }

    /**
     * Constructor to init the {@link ResourcesProvider} class
     *
     * @param containerDirectory: the main container directory to create
     * @param subDirectories: the subdirectories of the {@link #containerDirectory} folder
     */
    public ResourcesProvider(String containerDirectory, List<String> subDirectories) {
        if(!containerDirectory.endsWith(File.separator))
            containerDirectory += File.separator;
        this.containerDirectory = containerDirectory;
        this.subDirectories = subDirectories;
        containers = new HashSet<>();
    }

    /**
     * Method to create the {@link #containerDirectory} <br>
     *
     * No-any params required
     */
    @Wrapper
    public void createContainerDirectory() {
        createContainerDirectory(containerDirectory);
    }

    /**
     * Method to create a new container directory
     *
     * @param containerDirectory: the new container directory to create
     */
    public void createContainerDirectory(String containerDirectory) {
        createResourceDirectory(containerDirectory);
        containers.add(containerDirectory);
    }

    /**
     * Method to create a new subdirectory for the {@link #containerDirectory}
     *
     * @param subDirectory: the new subdirectory to create
     */
    @Wrapper
    public void createSubDirectory(String subDirectory) {
        createSubDirectory(containerDirectory, subDirectory);
    }

    /**
     * Method to create a new subdirectory
     *
     * @param containerDirectory: the container folder where create the new subdirectory
     * @param subDirectory: the new subdirectory to create
     */
    public void createSubDirectory(String containerDirectory, String subDirectory) {
        containerDirectory = formatDirectory(containerDirectory);
        createResourceDirectory(containerDirectory + subDirectory);
    }

    /**
     * Method to create the list of the {@link #subDirectories} for the {@link #containerDirectory} <br>
     *
     * No-any params required
     */
    @Wrapper
    public void createSubDirectories() {
        createSubDirectories(containerDirectory, subDirectories);
    }

    /**
     * Method to create the list of the subDirectories for a container directory
     *
     * @param containerDirectory: the container directory to create
     * @param subDirectories: the subdirectories of the subDirectories folder
     */
    public void createSubDirectories(String containerDirectory, List<String> subDirectories) {
        containerDirectory = formatDirectory(containerDirectory);
        for (String directory : subDirectories)
            createResourceDirectory(containerDirectory + directory);
    }

    /**
     * Method to create a new resource directory
     *
     * @param resDirectory: the pathname of the new folder to create, if not already exists
     */
    private void createResourceDirectory(String resDirectory) {
        File directory = new File(resDirectory);
        if(!directory.exists())
            if(!directory.mkdir())
                printError(resDirectory.replaceAll("/", ""));
    }

    /**
     * Method to format the pathname of a directory
     *
     * @param directory: the raw pathname to format, sample path: directory
     * @return the pathname formatted as {@link String}, sample path formatted: directory/
     */
    private String formatDirectory(String directory) {
        if(!directory.endsWith(File.separator))
            directory += File.separator;
        return directory;
    }

    /**
     * Method to print the error occurred during the creation of a resources directory
     * @param directory: the directory when, during the creation, occurred an error
     */
    private void printError(String directory) {
        System.err.println("Error during the creation of the \"" + directory + "\" folder check if the container directory " +
                "exists or the path is valid ");
        System.exit(-1);
    }

    /**
     * Method to get {@link #containers} instance <br>
     * No-any params required
     *
     * @return {@link #containers} instance as {@link HashSet} of {@link String}
     */
    public HashSet<String> getContainers() {
        return containers;
    }

}
