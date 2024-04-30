package com.tecknobit.equinox;

import com.tecknobit.apimanager.annotations.Wrapper;
import kotlin.Metadata;

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
     * server file (.jar) and call it "nova.properties"</b>
     * @implSpec take a look <a href="https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html">here</a>
     * to get more information about the custom configuration for properties that you can use
     */
    public static final String CUSTOM_CONFIGURATION_FILE_PATH = "custom.properties";

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

    protected HashSet<String> containers;

    protected final String containerDirectory;

    protected final List<String> subDirectories;

    public ResourcesProvider(String containerDirectory) {
        this(containerDirectory, List.of());
    }

    public ResourcesProvider(String containerDirectory, List<String> subDirectories) {
        if(!containerDirectory.endsWith(File.separator))
            containerDirectory += File.separator;
        this.containerDirectory = containerDirectory;
        this.subDirectories = subDirectories;
        containers = new HashSet<>();
    }

    @Wrapper
    public void createContainerDirectory() {
        createContainerDirectory(containerDirectory);
    }

    public void createContainerDirectory(String containerDirectory) {
        createResourceDirectory(containerDirectory);
        containers.add(containerDirectory);
    }

    @Wrapper
    public void createSubDirectory(String subDirectory) {
        createSubDirectory(containerDirectory, subDirectory);
    }

    public void createSubDirectory(String containerDirectory, String subDirectory) {
        containerDirectory = formatDirectory(containerDirectory);
        createResourceDirectory(containerDirectory + subDirectory);
    }

    @Wrapper
    public void createSubDirectories() {
        createSubDirectories(containerDirectory, subDirectories);
    }

    public void createSubDirectories(String containerDirectory, List<String> subDirectories) {
        containerDirectory = formatDirectory(containerDirectory);
        for (String directory : subDirectories)
            createResourceDirectory(containerDirectory + directory);
    }

    private void createResourceDirectory(String resDirectory) {
        File directory = new File(resDirectory);
        if(!directory.exists())
            if(!directory.mkdir())
                printError(resDirectory.replaceAll("/", ""));
    }

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

    private boolean isKotlinClass(Class<?> context) {
        try {
            return context.getAnnotation(Metadata.class).k() == 1;
        } catch (NullPointerException e) {
            return false;
        }
    }

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
