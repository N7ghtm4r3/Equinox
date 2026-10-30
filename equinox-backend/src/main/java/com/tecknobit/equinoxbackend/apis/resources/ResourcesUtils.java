package com.tecknobit.equinoxbackend.apis.resources;

import com.tecknobit.equinoxcore.annotations.Wrapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * The {@code ResourcesUtils} class is useful to access classpath resources used by Equinox backends
 *
 * <p>Content and stream access also support resources packaged in a JAR. Direct file access requires
 * an unpacked resource. Instances reuse the supplied context class for repeated lookups
 *
 * <pre>{@code
 * ResourcesUtils<ResourcesUtils> resourcesUtils = new ResourcesUtils<>(ResourcesUtils.class);
 * String configuration = resourcesUtils.getResourceContent("equinox.config");
 * }</pre>
 *
 * @param <T> The type whose class loader is used to locate resources
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
public class ResourcesUtils<T> {

    /**
     * {@code context} the class whose class loader is used to locate backend resources
     */
    private final Class<T> context;

    /**
     * Constructor to init the {@link ResourcesUtils} class
     *
     * @param context The class whose class loader is used to locate resources
     */
    public ResourcesUtils(Class<T> context) {
        this.context = context;
    }

    /**
     * Method used to copy a classpath text resource to a file scheduled for deletion when the JVM exits
     *
     * @param pathname The classpath resource name, also used as the destination file path
     *
     * @return the resource copy scheduled for deletion as {@link File}
     * @throws IOException when the resource cannot be found, read, or written
     */
    @Wrapper
    public File getResourceFileRuntimeCopy(String pathname) throws IOException {
        return getResourceFileRuntimeCopy(pathname, context);
    }

    /**
     * Method used to copy a classpath text resource to a file
     *
     * <p>The resource is decoded as UTF-8 and written using the default file-writer charset. An existing
     * file at the destination is overwritten; this operation does not preserve arbitrary binary content
     *
     * @param pathname The classpath resource name, also used as the destination file path
     *
     * @return the resource copy as {@link File}
     * @throws IOException when the resource cannot be found, read, or written
     */
    @Wrapper
    public File getResourceFileCopy(String pathname) throws IOException {
        return getResourceFileCopy(pathname, context);
    }

    /**
     * Method used to resolve an unpacked classpath resource as a file
     *
     * <p>This operation does not extract resources packaged in a JAR
     *
     * @param pathname The classpath resource name
     *
     * @return the file corresponding to the resource URL as {@link File}
     */
    @Wrapper
    public File getResourceFile(String pathname) {
        return getResourceFile(pathname, context);
    }

    /**
     * Method used to read the UTF-8 content of a classpath resource
     *
     * @param pathname The classpath resource name
     *
     * @return the decoded resource content as {@link String}
     * @throws IOException when the resource cannot be found or read
     */
    @Wrapper
    public String getResourceContent(String pathname) throws IOException {
        return getResourceContent(pathname, context);
    }

    /**
     * Method used to open a classpath resource for reading
     *
     * @param pathname The classpath resource name
     *
     * @return the resource stream, which the caller must close, as {@link InputStream}
     * @throws IOException when the resource cannot be found
     */
    @Wrapper
    public InputStream getResourceStream(String pathname) throws IOException {
        return getResourceStream(pathname, context);
    }

    /**
     * Method used to copy a classpath text resource to a file scheduled for deletion when the JVM exits
     *
     * @param pathname The classpath resource name, also used as the destination file path
     * @param context  The class whose class loader is used to locate the resource
     * @param <T>      The type of the context class
     *
     * @return the resource copy scheduled for deletion as {@link File}
     * @throws IOException when the resource cannot be found, read, or written
     */
    public static <T> File getResourceFileRuntimeCopy(String pathname, Class<T> context) throws IOException {
        File resourceCopy = getResourceFileCopy(pathname, context);
        resourceCopy.deleteOnExit();
        return resourceCopy;
    }

    /**
     * Method used to copy a classpath text resource to a file
     *
     * <p>The resource is decoded as UTF-8 and written using the default file-writer charset. An existing
     * file at the destination is overwritten; this operation does not preserve arbitrary binary content
     *
     * @param pathname The classpath resource name, also used as the destination file path
     * @param context  The class whose class loader is used to locate the resource
     * @param <T>      The type of the context class
     *
     * @return the resource copy as {@link File}
     * @throws IOException when the resource cannot be found, read, or written
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
     * Method used to resolve an unpacked classpath resource as a file
     *
     * <p>This operation does not extract resources packaged in a JAR
     *
     * @param pathname The classpath resource name
     * @param context  The class whose class loader is used to locate the resource
     * @param <T>      The type of the context class
     *
     * @return the file corresponding to the resource URL as {@link File}
     */
    public static <T> File getResourceFile(String pathname, Class<T> context) {
        return new File(context.getClassLoader().getResource(pathname).getFile());
    }

    /**
     * Method used to read the UTF-8 content of a classpath resource
     *
     * @param pathname The classpath resource name
     * @param context  The class whose class loader is used to locate the resource
     * @param <T>      The type of the context class
     *
     * @return the decoded resource content as {@link String}
     * @throws IOException when the resource cannot be found or read
     */
    public static <T> String getResourceContent(String pathname, Class<T> context) throws IOException {
        return new String(getResourceStream(pathname, context).readAllBytes(), UTF_8);
    }

    /**
     * Method used to open a classpath resource for reading
     *
     * @param pathname The classpath resource name
     * @param context  The class whose class loader is used to locate the resource
     * @param <T>      The type of the context class
     *
     * @return the resource stream, which the caller must close, as {@link InputStream}
     * @throws IOException when the resource cannot be found
     */
    public static <T> InputStream getResourceStream(String pathname, Class<T> context) throws IOException {
        InputStream resource = context.getClassLoader().getResourceAsStream(pathname);
        if (resource == null)
            throw new IOException(pathname + " not found!");
        return resource;
    }

}