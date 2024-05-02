package com.tecknobit.equinox;

import com.tecknobit.apimanager.annotations.Wrapper;
import kotlin.Metadata;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
     * {@code JAVA_CONFIGURATION_FILE_CONTENT} the content of the {@link WebMvcConfigurer}, as <b>Java class</b>, to
     * allow the serve of the static resources by the backend
     *
     * @apiNote this class will be created in the same package where has been invoked the {@link #createResourcesConfigFile(Class)}
     * method e.g. :
     * <pre>
     *     {@code
     *         com.your.package
     *          |
     *          |-> Caller.java
     *          |-> ResourcesConfig.java
     *     }
     * </pre>
     */
    private static final String JAVA_CONFIGURATION_FILE_CONTENT = """
       
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
                            .addResourceLocations("file:<list>")
                            .setCachePeriod(0)
                            .resourceChain(true)
                            .addResolver(new PathResourceResolver());
                }

            }""";

    /**
     * {@code KOTLIN_CONFIGURATION_FILE_CONTENT} the content of the {@link WebMvcConfigurer}, as <b>Kotlin class</b>, to
     * allow the serve of the static resources by the backend
     *
     * @apiNote this class will be created in the same package where has been invoked the {@link #createResourcesConfigFile(Class)}
     * method e.g. :
     * <pre>
     *     {@code
     *         com.your.package
     *          |
     *          |-> Caller.kt
     *          |-> ResourcesConfig.kt
     *     }
     * </pre>
     */
    private static final String KOTLIN_CONFIGURATION_FILE_CONTENT = """
            
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
                        .addResourceLocations("file:<list>")
                        .setCachePeriod(0)
                        .resourceChain(true)
                        .addResolver(PathResourceResolver())
                }
           
            }"""
       ;

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
     * Method to create the {@link WebMvcConfigurer} to correctly serve the static resources by backend
     *
     * @param context: the {@link Class} where this method has been invoked
     * @throws IOException when an error during the creation or fetching the path is occurred
     *
     * @apiNote this class will be created in the same package where this method has been invoked
     * <pre>
     *     {@code
     *         com.your.package
     *          |
     *          |-> Caller.java/kt
     *          |-> ResourcesConfig.java/kt
     *     }
     * </pre>
     */
    public void createResourcesConfigFile(Class<?> context) throws IOException {
        String packageName = context.getPackageName();
        boolean isKotlinClass = isKotlinClass(context);
        String content;
        if(isKotlinClass) {
            if(!packageName.isEmpty())
                content = "package " + packageName + "\n" + KOTLIN_CONFIGURATION_FILE_CONTENT;
            else
                content = KOTLIN_CONFIGURATION_FILE_CONTENT;
        } else {
            if(!packageName.isEmpty())
                content = "package " + packageName + ";\n" + JAVA_CONFIGURATION_FILE_CONTENT;
            else
                content = JAVA_CONFIGURATION_FILE_CONTENT;
        }
        FileWriter writer = new FileWriter(getConfigsPath(context, isKotlinClass));
        writer.write(content.replace("<list>", containers.toString()
                .replaceAll("\\\\", "/")
                .replaceAll("\\[", "")
                .replaceAll("]", ""))
        );
        writer.flush();
        writer.close();
    }

    /**
     * Method to get whether the caller class is a <b>Kotlin</b> class
     *
     * @param context: the {@link Class} where the {@link #createResourcesConfigFile(Class)} method has been invoked
     * @return whether the caller class is a <b>Kotlin</b> class as boolean
     */
    private boolean isKotlinClass(Class<?> context) {
        try {
            return context.getAnnotation(Metadata.class).k() == 1;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Method to get the path where place the "ResourcesConfig.java/kt" class
     *
     * @param context: the {@link Class} where the {@link #createResourcesConfigFile(Class)} method has been invoked
     * @param isKotlinClass: whether the caller class is a <b>Kotlin</b> class
     * @return the path where place the "ResourcesConfig.java/kt" class as {@link String}
     */
    private String getConfigsPath(Class<?> context, boolean isKotlinClass) {
        String subPath;
        String extension;
        if(isKotlinClass) {
            subPath = "kotlin";
            extension = ".kt";
        } else {
            subPath = "java";
            extension = ".java";
        }
        return System.getProperty("user.dir") + "\\src\\main\\" + subPath + "\\" + context.getPackageName()
                .replaceAll("\\.", "\\\\") + "\\ResourcesConfig" + extension;
    }

}
