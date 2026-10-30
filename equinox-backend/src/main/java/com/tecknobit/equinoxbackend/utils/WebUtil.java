package com.tecknobit.equinoxbackend.utils;

import com.tecknobit.equinoxbackend.environment.exceptions.EquinoxServerException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;
import static org.apache.commons.codec.binary.Hex.encodeHexString;

/**
 * The {@code WebUtil} class is useful to compute digests and request signatures and handle web-related files
 *
 * <p>Equinox backends use the SHA-256 helper to hash user credentials and server secrets. The other
 * operations support hexadecimal or Base64 digests, HMAC signatures, URL downloads, and data URIs
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
public class WebUtil {

    /**
     * {@code HMAC_SHA256_ALGORITHM} the algorithm name used to compute HMAC signatures with SHA-256
     */
    public static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    /**
     * {@code HMAC_SHA512_ALGORITHM} the algorithm name used to compute HMAC signatures with SHA-512
     */
    public static final String HMAC_SHA512_ALGORITHM = "HmacSHA512";

    /**
     * {@code MD5_ALGORITHM} the algorithm name used to compute MD5 digests
     */
    public static final String MD5_ALGORITHM = "MD5";

    /**
     * {@code SHA1_ALGORITHM} the algorithm name used to compute SHA-1 digests
     */
    public static final String SHA1_ALGORITHM = "SHA-1";

    /**
     * {@code SHA256_ALGORITHM} the algorithm name used to compute SHA-256 digests
     */
    public static final String SHA256_ALGORITHM = "SHA-256";

    /**
     * Method used to compute the Base64-encoded digest of the supplied data
     *
     * @param data      The text to hash, encoded using the platform default charset
     * @param algorithm The digest algorithm: {@code MD5}, {@code SHA-1}, or {@code SHA-256}
     *
     * @return the Base64-encoded digest as {@link String}
     * @throws IllegalArgumentException when the algorithm is null or unsupported
     * @throws NoSuchAlgorithmException when the requested digest algorithm is unavailable
     */
    public static String base64Digest(String data, String algorithm) throws NoSuchAlgorithmException {
        return base64Digest(data.getBytes(), algorithm);
    }

    /**
     * Method used to compute the Base64-encoded digest of the supplied data
     *
     * @param data      The bytes to hash
     * @param algorithm The digest algorithm: {@code MD5}, {@code SHA-1}, or {@code SHA-256}
     *
     * @return the Base64-encoded digest as {@link String}
     * @throws IllegalArgumentException when the algorithm is null or unsupported
     * @throws NoSuchAlgorithmException when the requested digest algorithm is unavailable
     */
    public static String base64Digest(byte[] data, String algorithm) throws NoSuchAlgorithmException {
        return new String(getEncoder().encode(digest(data, algorithm)));
    }

    /**
     * Method used to compute the hexadecimal digest of the supplied data
     *
     * @param data      The text to hash, encoded using the platform default charset
     * @param algorithm The digest algorithm: {@code MD5}, {@code SHA-1}, or {@code SHA-256}
     *
     * @return the hexadecimal digest as {@link String}
     * @throws IllegalArgumentException when the algorithm is null or unsupported
     * @throws NoSuchAlgorithmException when the requested digest algorithm is unavailable
     */
    public static String stringDigest(String data, String algorithm) throws NoSuchAlgorithmException {
        return encodeHexString(digest(data, algorithm));
    }

    /**
     * Method used to compute the hexadecimal digest of the supplied data
     *
     * @param data      The bytes to hash
     * @param algorithm The digest algorithm: {@code MD5}, {@code SHA-1}, or {@code SHA-256}
     *
     * @return the hexadecimal digest as {@link String}
     * @throws IllegalArgumentException when the algorithm is null or unsupported
     * @throws NoSuchAlgorithmException when the requested digest algorithm is unavailable
     */
    public static String stringDigest(byte[] data, String algorithm) throws NoSuchAlgorithmException {
        return encodeHexString(digest(data, algorithm));
    }

    /**
     * Method used to compute the raw digest of the supplied data
     *
     * @param data      The text to hash, encoded using the platform default charset
     * @param algorithm The digest algorithm: {@code MD5}, {@code SHA-1}, or {@code SHA-256}
     *
     * @return the raw digest as {@code byte[]}
     * @throws IllegalArgumentException when the algorithm is null or unsupported
     * @throws NoSuchAlgorithmException when the requested digest algorithm is unavailable
     */
    public static byte[] digest(String data, String algorithm) throws NoSuchAlgorithmException {
        return digest(data.getBytes(), algorithm);
    }

    /**
     * Method used to compute the raw digest of the supplied data
     *
     * @param data      The bytes to hash
     * @param algorithm The digest algorithm: {@code MD5}, {@code SHA-1}, or {@code SHA-256}
     *
     * @return the raw digest as {@code byte[]}
     * @throws IllegalArgumentException when the algorithm is null or unsupported
     * @throws NoSuchAlgorithmException when the requested digest algorithm is unavailable
     */
    public static byte[] digest(byte[] data, String algorithm) throws NoSuchAlgorithmException {
        if (algorithm == null || (!algorithm.equals(MD5_ALGORITHM) && !algorithm.equals(SHA1_ALGORITHM)
                && !algorithm.equals(SHA256_ALGORITHM))) {
            throw new IllegalArgumentException("Algorithm must be MD5,SHA-1 or SHA-256");
        }

        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.update(data);
        return messageDigest.digest();
    }

    /**
     * Method used to compute a hexadecimal HMAC signature for request data
     *
     * <p>The key and data are encoded as UTF-8. Every question mark is removed from the data before signing
     *
     * @param signatureKey The clear-text signing key
     * @param data         The request data to sign after removing question marks
     * @param algorithm    The HMAC algorithm: {@code HmacSHA256} or {@code HmacSHA512}
     *
     * @return the hexadecimal HMAC signature as {@link String}
     * @throws IllegalArgumentException when the algorithm is null or unsupported
     * @throws Exception when the signing algorithm or key cannot be used
     */
    public static String getSignature(String signatureKey, String data, String algorithm) throws Exception {
        if (algorithm == null || (!algorithm.equals(HMAC_SHA256_ALGORITHM) && !algorithm.equals(HMAC_SHA512_ALGORITHM)))
            throw new IllegalArgumentException("Algorithm must be HmacSHA256 or HmacSHA512");

        Mac sha = Mac.getInstance(algorithm);
        sha.init(new SecretKeySpec(signatureKey.getBytes(UTF_8), algorithm));

        return encodeHexString(sha.doFinal(data.replace("?", "").getBytes(UTF_8)));
    }

    /**
     * Method used to compute a Base64-encoded HMAC signature using a binary key
     *
     * <p>The supplied data is encoded using the platform default charset and signed without removing characters
     *
     * @param signatureKey The signing-key bytes
     * @param data         The request data to sign
     * @param algorithm    The HMAC algorithm: {@code HmacSHA256} or {@code HmacSHA512}
     *
     * @return the Base64-encoded HMAC signature as {@link String}
     * @throws IllegalArgumentException when the algorithm is null or unsupported
     * @throws Exception when the signing algorithm or key cannot be used
     */
    public static String getBase64Signature(byte[] signatureKey, String data, String algorithm) throws Exception {
        if (algorithm == null || (!algorithm.equals(HMAC_SHA256_ALGORITHM) && !algorithm.equals(HMAC_SHA512_ALGORITHM)))
            throw new IllegalArgumentException("Algorithm must be HmacSHA256 or HmacSHA512");

        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(signatureKey, algorithm));

        return getEncoder().encodeToString(mac.doFinal(data.getBytes()));
    }

    /**
     * Method used to compute a Base64-encoded HMAC signature using a Base64-encoded key
     *
     * <p>The supplied data is encoded using the platform default charset and signed without removing characters
     *
     * @param signatureKey The Base64-encoded signing key to decode before signing
     * @param data         The request data to sign
     * @param algorithm    The HMAC algorithm: {@code HmacSHA256} or {@code HmacSHA512}
     *
     * @return the Base64-encoded HMAC signature as {@link String}
     * @throws IllegalArgumentException when the key is not valid Base64 or the algorithm is null or unsupported
     * @throws Exception when the signing algorithm or decoded key cannot be used
     */
    public static String getBase64Signature(String signatureKey, String data, String algorithm) throws Exception {
        return getBase64Signature(getDecoder().decode(signatureKey), data, algorithm);
    }

    /**
     * Method used to download URL content to a permanent or temporary file
     *
     * <p>Permanent downloads overwrite the supplied destination and create missing parent directories.
     * Temporary downloads use a file in the default temporary directory with the supplied file-name suffix
     * and are scheduled for deletion when the JVM exits. The source stream is closed after the download
     *
     * @param url      The source URL
     * @param pathName The destination path including a file-name suffix; only the suffix is used for temporary downloads
     * @param save     Whether to retain the download at the supplied destination instead of a temporary file
     *
     * @return the file containing the downloaded content as {@link File}
     * @throws IOException when the URL or destination is invalid, or downloading or file creation fails
     */
    public static File downloadFile(String url, String pathName, boolean save) throws IOException {
        if (url == null)
            throw new IOException("The URL source cannot be null");
        if (pathName == null || pathName.isBlank())
            throw new IOException("The path name cannot be null or blank");

        Path requestedPath;
        try {
            requestedPath = Path.of(pathName);
        } catch (InvalidPathException e) {
            throw new IOException("The path name is not valid", e);
        }

        Path fileName = requestedPath.getFileName();
        if (fileName == null)
            throw new IOException("The path must include a file name");

        int extensionIndex = fileName.toString().lastIndexOf('.');
        if (extensionIndex < 0)
            throw new IOException("The file name must include a suffix");

        URL sourceUrl;
        try {
            sourceUrl = new URI(url).toURL();
        } catch (URISyntaxException | MalformedURLException | IllegalArgumentException e) {
            throw new IOException("The URL source is not a valid source", e);
        }

        Path destination;
        if (save) {
            Path parent = requestedPath.toAbsolutePath().getParent();
            if (parent != null)
                Files.createDirectories(parent);
            destination = requestedPath;
        } else {
            String suffix = fileName.toString().substring(extensionIndex);
            destination = Files.createTempFile("equinox-download-", suffix);
            destination.toFile().deleteOnExit();
        }

        try (InputStream source = sourceUrl.openStream()) {
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
        }

        return destination.toFile();
    }

    /**
     * Method used to encode a file as a Base64 data URI
     *
     * <p>The URI contains the detected media type followed by {@code ;base64,} and the encoded file content
     *
     * @param file The file whose media type and content are used to create the URI
     *
     * @return the data URI as {@link String}
     * @throws IOException when the media type cannot be probed or the file cannot be read
     */
    public static String createDataURIScheme(File file) throws IOException {
        Path path = file.toPath();
        return "data:" + Files.probeContentType(path) + ";base64," + getEncoder().encodeToString(Files.readAllBytes(path));
    }

    /**
     * Method used to compute a Base64-encoded SHA-256 digest for sensitive backend data
     *
     * @param secret The text to hash, encoded using the platform default charset
     *
     * @return the Base64-encoded SHA-256 digest as {@link String}
     * @throws EquinoxServerException when the SHA-256 digest algorithm is unavailable
     */
    public static String hashSha256Base64(String secret) {
        try {
            return base64Digest(secret, SHA256_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new EquinoxServerException(e);
        }
    }

}
