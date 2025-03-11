package com.tecknobit.equinoxbackend.configuration;

import kotlin.Metadata;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.prefs.Preferences;

/**
 * The {@code ConfigsGenerator} class is useful to create automatically some configuration files
 *
 * @author N7ghtm4r3 - Tecknobit
 * @since 1.0.1
 */
@Deprecated(
        since = "1.0.9",
        forRemoval = true
)
// SEE THE NEW DOCUMENTATION WHICH REPLACE THIS AS https://github.com/N7ghtm4r3/Equinox/blob/main/documd/backend/Configurations.md
public class ConfigsGenerator {

    /**
     * {@code RESOURCES_CONFIG_FILE_NAME} the name for the resources configuration file
     */
    private static final String RESOURCES_CONFIG_FILE_NAME = "ResourcesConfig";

    /**
     * {@code CORS_ADVICE_FILE_NAME} the name for the CORS configuration file
     */
    private static final String CORS_ADVICE_FILE_NAME = "CORSAdvice";

    /**
     * {@code context} the {@link Class} where this method has been invoked
     */
    private final Class<?> context;

    /**
     * {@code preferences} helper to check with the {@link #configFileStillNotCreated(String)} method if a config file
     * has been already created or not
     */
    private final Preferences preferences;

    /**
     * {@code isKotlinClass} whether the caller class ({@link #context}) is a <b>Kotlin</b> class
     */
    private final boolean isKotlinClass;

    /**
     * {@code JAVA_CONFIGURATION_FILE_CONTENT} the content of the {@link WebMvcConfigurer}, as <b>Java class</b>, to
     * allow the serve of the static resources by the backend
     *
     * @apiNote this class will be created in the same package where has been invoked the {@link #createResourcesConfigFile(HashSet)}
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
                  \s
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
     * @apiNote this class will be created in the same package where has been invoked the {@link #createResourcesConfigFile(HashSet)}
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
                       \s
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
                       \s
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
                      \s
            }""";

    /**
     * {@code JAVA_CORS_ADVICE_FILE_CONTENT} the content of the <b>CORSAdvice</b> file, as <b>Java class</b>, to
     * set the CORS origin policy for the backend
     *
     * @apiNote this class will be created in the same package where has been invoked the {@link #crateCorsAdviceFile()}
     * method e.g. :
     * <pre>
     *     {@code
     *         com.your.package
     *          |
     *          |-> Caller.java
     *          |-> CORSAdvice.java
     *     }
     * </pre>
     */
    private static final String JAVA_CORS_ADVICE_FILE_CONTENT = """
                       \s
            import org.springframework.boot.web.servlet.FilterRegistrationBean;
            import org.springframework.context.annotation.Bean;
            import org.springframework.context.annotation.Configuration;
            import org.springframework.web.cors.CorsConfiguration;
            import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
            import org.springframework.web.filter.CorsFilter;
                       \s
            /**
             * The {@code CORSAdvice} class is useful to set the CORS policy
             *
             * @author N7ghtm4r3 - Tecknobit
             */
            @Configuration
            public class CORSAdvice {
                   \s
                /**
                 * Method to set the CORS filter <br>
                 * No any-params required
                 */
                @Bean
                public FilterRegistrationBean corsFilter() {
                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowCredentials(false);
                    config.addAllowedOrigin("*");
                    config.addAllowedHeader("*");
                    config.addAllowedMethod("*");
                    source.registerCorsConfiguration("/**", config);
                    FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
                    bean.setOrder(0);
                    return bean;
                }
                       \s
            }""";

    /**
     * {@code KOTLIN_CORS_ADVICE_FILE_CONTENT} the content of the <b>CORSAdvice</b> file, as <b>Kotlin class</b>, to
     * set the CORS origin policy for the backend
     *
     * @apiNote this class will be created in the same package where has been invoked the {@link #crateCorsAdviceFile()}
     * method e.g. :
     * <pre>
     *     {@code
     *         com.your.package
     *          |
     *          |-> Caller.kt
     *          |-> CORSAdvice.kt
     *     }
     * </pre>
     */
    private static final String KOTLIN_CORS_ADVICE_FILE_CONTENT = """
            \s
             import org.springframework.boot.web.servlet.FilterRegistrationBean
             import org.springframework.context.annotation.Bean
             import org.springframework.context.annotation.Configuration
             import org.springframework.web.cors.CorsConfiguration
             import org.springframework.web.cors.UrlBasedCorsConfigurationSource
             import org.springframework.web.filter.CorsFilter
                        \s
             /**
              * The `CORSAdvice` class is useful to set the CORS policy
              *
              * @author N7ghtm4r3 - Tecknobit
              */
             @Configuration
             class CORSAdvice {
            \s
                 /**
                  * Method to set the CORS filter
                  *
                  * No any-params required
                  */
                 @Bean
                 fun corsFilter(): FilterRegistrationBean<*> {
                     val source = UrlBasedCorsConfigurationSource()
                     val config = CorsConfiguration()
                     config.allowCredentials = false
                     config.addAllowedOrigin("*")
                     config.addAllowedHeader("*")
                     config.addAllowedMethod("*")
                     source.registerCorsConfiguration("/**", config)
                     val bean: FilterRegistrationBean<*> = FilterRegistrationBean(CorsFilter(source))
                     bean.order = 0
                     return bean
                 }
                \s
             }""";

    /**
     * Constructor to init the {@link ConfigsGenerator} class
     *
     * @param context: the {@link Class} where this method has been invoked
     */
    public ConfigsGenerator(Class<?> context) {
        this.context = context;
        preferences = Preferences.userNodeForPackage(context);
        isKotlinClass = isKotlinClass();
    }

    /**
     * Method to get whether the caller class is a <b>Kotlin</b> class <br>
     *
     * @return whether the caller class is a <b>Kotlin</b> class as boolean
     */
    private boolean isKotlinClass() {
        try {
            return context.getAnnotation(Metadata.class).k() == 1;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Method to create the {@link WebMvcConfigurer} to correctly serve the static resources by backend
     *
     * @param containers: the set of the containers folder which contains other subfolders to store the resources
     * @throws IOException when an error during the creation or fetching the path is occurred
     * @apiNote this class will be created in the same package where this method has been invoked
     * <pre>
     *     {@code
     *         com.your.package
     *          |
     *          |-> Caller.java/kt
     *          |-> ResourcesConfig.java/kt
     *     }
     * </pre>
     * Please consider to invoke this method just one time, but there is the check if the file already exists and the creation
     * will be skipped automatically
     */
    public void createResourcesConfigFile(HashSet<String> containers) throws IOException {
        String path = getConfigsPath(RESOURCES_CONFIG_FILE_NAME);
        String content = getConfigFileContent(KOTLIN_CONFIGURATION_FILE_CONTENT, JAVA_CONFIGURATION_FILE_CONTENT);
        createConfigFile(path, content.replace("<list>", containers.toString()));
        preferences.putBoolean(RESOURCES_CONFIG_FILE_NAME, true);
    }

    /**
     * Method to create the <b>CORSAdvice</b> to correctly set the CORS origin policy for the backend <br>
     *
     * @throws IOException when an error during the creation or fetching the path is occurred
     * @apiNote this class will be created in the same package where this method has been invoked
     * <pre>
     *     {@code
     *         com.your.package
     *          |
     *          |-> Caller.java/kt
     *          |-> CORSAdvice.java/kt
     *     }
     * </pre>
     * Please consider to invoke this method just one time, but there is the check if the file already exists and the creation
     * will be skipped automatically
     */
    public void crateCorsAdviceFile() throws IOException {
        String path = getConfigsPath(CORS_ADVICE_FILE_NAME);
        String content = getConfigFileContent(KOTLIN_CORS_ADVICE_FILE_CONTENT, JAVA_CORS_ADVICE_FILE_CONTENT);
        createConfigFile(path, content);
        preferences.putBoolean(CORS_ADVICE_FILE_NAME, true);
    }

    /**
     * Method to get the content of the configuration file to create
     *
     * @param kotlinContent: the kotlin version of the content to create the relate config file
     * @param javaContent:   the java version of the content to create the relate config file
     * @return the content correctly formatted for Java or Kotlin language as {@link String}
     * @apiNote will be added also the package if it is needed
     */
    private String getConfigFileContent(String kotlinContent, String javaContent) {
        String packageName = context.getPackageName();
        String content;
        if (isKotlinClass) {
            if (!packageName.isEmpty())
                content = "package " + packageName + "\n" + kotlinContent;
            else
                content = kotlinContent;
        } else {
            if (!packageName.isEmpty())
                content = "package " + packageName + ";\n" + javaContent;
            else
                content = javaContent;
        }
        return content;
    }

    /**
     * Method to create the config file and then save it
     *
     * @param configsPath: the path where save the configuration file
     * @param content:     the content of the configuration file to create
     * @throws IOException when an error during the creation of the file occurred
     * @apiNote will be checked if at the given <b>configsPath</b> the file already exists so to skip the creation of
     * that file
     */
    private void createConfigFile(String configsPath, String content) throws IOException {
        if (!new File(configsPath).exists() && configFileStillNotCreated(configsPath)) {
            FileWriter writer = new FileWriter(configsPath);
            writer.write(content.replaceAll("\\\\", "/")
                    .replaceAll("\\[", "")
                    .replaceAll("]", "")
            );
            writer.flush();
            writer.close();
        }
    }

    /**
     * Method to check if a config file has been already created for the current project,
     * so to skip its creation
     *
     * @param configsPath: the path where the configuration file is stored
     */
    private boolean configFileStillNotCreated(String configsPath) {
        String configFileName;
        if (configsPath.contains(CORS_ADVICE_FILE_NAME))
            configFileName = CORS_ADVICE_FILE_NAME;
        else
            configFileName = RESOURCES_CONFIG_FILE_NAME;
        return !preferences.getBoolean(configFileName, false);
    }

    /**
     * Method to get the path where place the configuration file class
     *
     * @param configFileName: the name of the configuration file to create
     * @return the path where place the configuration file class as {@link String}
     */
    private String getConfigsPath(String configFileName) {
        String subPath;
        String extension;
        if (isKotlinClass) {
            subPath = "kotlin";
            extension = ".kt";
        } else {
            subPath = "java";
            extension = ".java";
        }
        return System.getProperty("user.dir") + "\\src\\main\\" + subPath + "\\" + context.getPackageName()
                .replaceAll("\\.", "\\\\") + "\\" + configFileName + extension;
    }

}
