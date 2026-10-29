package com.tecknobit.equinoxbackend.apis.resources;

import com.tecknobit.equinoxcore.annotations.Wrapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * The {@code ResourcesUtils} class is useful to manage the resources folder to get its resources, both with methods
 * working in the JAR scope and both with method working a runtime outside a JAR
 * <pre>
 * {@code
 *
 *      // Invoking a ResourcesUtils method one time
 *      String resourceContent = ResourcesUtils.getResourceContent("my_path_name.resource", MyCallingClass.class);
 *
 *      // Invoking a ResourcesUtils methods multiple time is more efficient instantiating a dedicated object
 *      ResourcesUtils<Class<MyCallingClass>> resourcesUtils = new ResourcesUtils<>(MyCallingClass.class);
 *
 *      String firstResourceContent = resourcesUtils.getResourceContent("my_first_path_name.resource");
 *
 *      String secondResourceContent = resourcesUtils.getResourceContent("my_second_path_name.resource");
 *
 * }
 * </pre>
 *
 * @param <T> the class from get its classloader to reach the resources folder
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the usage at <a href="https://github.com/N7ghtm4r3/APIManager/blob/main/documd/ResourcesUtils.md">ResourcesUtils.md</a>
 * @since 2.2.2
 */
public class ResourcesUtils<T extends Class<?>> {

    /**
     * {@code context} the context class from get its classloader to reach the resources folder
     */
    private final Class<T> context;

    /**
     * Constructor to init the {@link ResourcesUtils} class
     *
     * @param context: the context class from get its classloader to reach the resources folder
     *
     */
    public ResourcesUtils(Class<T> context) {
        this.context = context;
    }

    /**
     * Method to get a copy of a resource file during the runtime. <br>
     * That copy will be deleted when the program terminates, this invoking
     * the {@link File#deleteOnExit()} method
     *
     * @param pathname: the pathname of the resource to get
     *
     * @return a copy of a resource file that will be deleted when the program terminates as {@link File}
     *
     * @throws IOException when an error occurred during the fetching of the resource
     */
    @Wrapper
    public File getResourceFileRuntimeCopy(String pathname) throws IOException {
        return getResourceFileRuntimeCopy(pathname, context);
    }

    /**
     * Method to get a copy of a resource file
     *
     * @param pathname: the pathname of the resource to get
     *
     * @return a copy of a resource file as {@link File}
     *
     * @throws IOException when an error occurred during the fetching of the resource
     */
    @Wrapper
    public File getResourceFileCopy(String pathname) throws IOException {
        return getResourceFileCopy(pathname, context);
    }

    /**
     * Method to a resource file
     *
     * @param pathname: the pathname of the resource to get
     *
     * @return a resource file as {@link File}
     *
     * @apiNote this method doesn't work when is invoked in a JAR environment
     */
    @Wrapper
    public File getResourceFile(String pathname) {
        return getResourceFile(pathname, context);
    }

    /**
     * Method to get the content of a resource file
     *
     * @param pathname: the pathname of the resource to get its content
     *
     * @return the content of a resource file as {@link String}
     *
     * @throws IOException when an error occurred during the fetching of the resource
     */
    @Wrapper
    public String getResourceContent(String pathname) throws IOException {
        return getResourceContent(pathname, context);
    }

    /**
     * Method to get the stream of a resource file
     *
     * @param pathname: the pathname of the resource to get its stream
     * @return the stream of a resource file as {@link InputStream}
     * @throws IOException when an error occurred during the fetching of the resource
     */
    @Wrapper
    public InputStream getResourceStream(String pathname) throws IOException {
        return getResourceStream(pathname, context);
    }

    /**
     * Method to get a copy of a resource file during the runtime. <br>
     * That copy will be deleted when the program terminates, this invoking
     * the {@link File#deleteOnExit()} method
     *
     * @param pathname: the pathname of the resource to get
     * @param context: the context class from get its classloader to reach the resources folder
     * @param <T> the class type of the context e.g. MyCallingClass.class
     *
     * @return a copy of a resource file that will be deleted when the program terminates as {@link File}
     *
     * @throws IOException when an error occurred during the fetching of the resource
     */
    public static <T> File getResourceFileRuntimeCopy(String pathname, Class<T> context) throws IOException {
        File resourceCopy = getResourceFileCopy(pathname, context);
        resourceCopy.deleteOnExit();
        return resourceCopy;
    }

    /**
     * Method to get a copy of a resource file
     *
     * @param pathname: the pathname of the resource to get
     * @param context:  the context class from get its classloader to reach the resources folder
     * @param <T>       the class type of the context e.g. MyCallingClass.class
     * @return a copy of a resource file as {@link File}
     * @throws IOException when an error occurred during the fetching of the resource
     */
    public static <T> File getResourceFileCopy(String pathname, Class<T> context) throws IOException {
        String content = getResourceContent(pathname, context);
        File resourceCopy = new File(pathname);
        try (FileWriter writer = new FileWriter(resourceCopy, false)) {
            writer.write(content);
            writer.flush();
        }
        return resourceCopy;
    }

    /**
     * Method to a resource file
     *
     * @param pathname: the pathname of the resource to get
     * @param context:  the context class from get its classloader to reach the resources folder
     * @param <T>       the class type of the context e.g. MyCallingClass.class
     * @return a resource file as {@link File}
     * @apiNote this method doesn't work when is invoked in a JAR environment
     */
    public static <T> File getResourceFile(String pathname, Class<T> context) {
        return new File(context.getClassLoader().getResource(pathname).getFile());
    }

    /**
     * Method to get the content of a resource file
     *
     * @param pathname: the pathname of the resource to get its content
     * @param context:  the context class from get its classloader to reach the resources folder
     * @param <T>       the class type of the context e.g. MyCallingClass.class
     * @return the content of a resource file as {@link String}
     * @throws IOException when an error occurred during the fetching of the resource
     */
    public static <T> String getResourceContent(String pathname, Class<T> context) throws IOException {
        return new String(getResourceStream(pathname, context).readAllBytes(), UTF_8);
    }

    /**
     * Method to get the stream of a resource file
     *
     * @param pathname: the pathname of the resource to get its stream
     * @param context:  the context class from get its classloader to reach the resources folder
     * @param <T>       the class type of the context e.g. MyCallingClass.class
     * @return the stream of a resource file as {@link InputStream}
     * @throws IOException when an error occurred during the fetching of the resource
     */
    public static <T> InputStream getResourceStream(String pathname, Class<T> context) throws IOException {
        InputStream resource = context.getClassLoader().getResourceAsStream(pathname);
        if (resource == null)
            throw new IOException(pathname + " not found!");
        return resource;
    }

}