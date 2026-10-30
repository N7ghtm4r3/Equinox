`WebUtil` provides digests, HMAC request signatures, URL downloads, and data URI encoding. It is available
in `com.tecknobit.equinoxbackend.utils` and is included in `equinox-backend`.

## Compute a digest

Choose the digest algorithm and the representation required by the consumer:

| Method | Result |
| --- | --- |
| `digest(data, algorithm)` | Raw digest as `byte[]` |
| `stringDigest(data, algorithm)` | Hexadecimal digest as `String` |
| `base64Digest(data, algorithm)` | Base64-encoded digest as `String` |
| `hashSha256Base64(secret)` | Base64-encoded SHA-256 digest with backend exception handling |

The first three methods accept either `String` or `byte[]` and support `MD5_ALGORITHM`,
`SHA1_ALGORITHM`, and `SHA256_ALGORITHM`. Null or unsupported algorithm names cause
`IllegalArgumentException`; an unavailable digest implementation causes `NoSuchAlgorithmException`.

=== "Java"

    ```java
    import com.tecknobit.equinoxbackend.utils.WebUtil;
    import java.nio.charset.StandardCharsets;

    byte[] data = "hello".getBytes(StandardCharsets.UTF_8);
    String hexadecimal = WebUtil.stringDigest(data, WebUtil.SHA256_ALGORITHM);
    String base64 = WebUtil.base64Digest(data, WebUtil.SHA256_ALGORITHM);
    ```

=== "Kotlin"

    ```kotlin
    import com.tecknobit.equinoxbackend.utils.WebUtil

    val data = "hello".toByteArray(Charsets.UTF_8)
    val hexadecimal = WebUtil.stringDigest(data, WebUtil.SHA256_ALGORITHM)
    val base64 = WebUtil.base64Digest(data, WebUtil.SHA256_ALGORITHM)
    ```

Both results represent the same digest using different encodings. String overloads encode their
input using the platform default charset; pass bytes when the charset must be explicit.

`hashSha256Base64` delegates to the SHA-256 Base64 operation and wraps an unavailable algorithm in
`EquinoxServerException`. It is also used by the supplied user service; the
[server protector](../APIs/ServerProtector.md) uses the same digest format.

## Sign request data

HMAC operations support `HMAC_SHA256_ALGORITHM` and `HMAC_SHA512_ALGORITHM`. The hexadecimal and
Base64 methods differ in how they interpret the key and data:

| Method | Key | Data handling | Result |
| --- | --- | --- | --- |
| `getSignature(key, data, algorithm)` | Clear-text string encoded as UTF-8 | Removes every `?`, then encodes as UTF-8 | Hexadecimal |
| `getBase64Signature(keyBytes, data, algorithm)` | Raw bytes | Encodes with the platform default charset; preserves characters | Base64 |
| `getBase64Signature(base64Key, data, algorithm)` | Decodes the Base64 key first | Encodes with the platform default charset; preserves characters | Base64 |

Use the overload matching the request-signing contract of the service you call:

=== "Java"

    ```java
    byte[] key = "signing-key".getBytes(StandardCharsets.UTF_8);
    String data = "amount=10&currency=EUR";

    String signature = WebUtil.getBase64Signature(key, data, WebUtil.HMAC_SHA256_ALGORITHM);
    ```

=== "Kotlin"

    ```kotlin
    val key = "signing-key".toByteArray(Charsets.UTF_8)
    val data = "amount=10&currency=EUR"

    val signature = WebUtil.getBase64Signature(key, data, WebUtil.HMAC_SHA256_ALGORITHM)
    ```

A clear-text string key must not be passed to the string overload of `getBase64Signature` unless it
is already Base64-encoded. Signing methods declare `Exception`; invalid algorithms or invalid Base64
keys can cause `IllegalArgumentException`.

## Download a file

`downloadFile(url, pathName, save)` writes a URL's content to either a permanent destination or a
new temporary file. The supplied path must contain a file name with a suffix, such as `.pdf`.

=== "Java"

    ```java
    import java.io.File;

    File report = WebUtil.downloadFile("https://example.com/report.pdf", "downloads/report.pdf", true);
    File temporaryReport = WebUtil.downloadFile("https://example.com/report.pdf", "report.pdf", false);
    ```

=== "Kotlin"

    ```kotlin
    val report = WebUtil.downloadFile("https://example.com/report.pdf", "downloads/report.pdf", true)
    val temporaryReport = WebUtil.downloadFile("https://example.com/report.pdf", "report.pdf", false)
    ```

With `save = true`, missing parent directories are created and an existing destination is overwritten.
With `save = false`, the returned file contains the download in the system temporary directory and
is scheduled for deletion when the JVM exits; the supplied file name contributes its suffix.

Invalid URLs or paths, failed downloads, and file-creation failures cause `IOException`.

## Create a data URI

`createDataURIScheme(file)` detects the media type, reads the file, and embeds its Base64-encoded
content in a URI. For a detected PNG image, the result starts with `data:image/png;base64,`.

=== "Java"

    ```java
    String imageUri = WebUtil.createDataURIScheme(new File("images/logo.png"));
    ```

=== "Kotlin"

    ```kotlin
    import java.io.File

    val imageUri = WebUtil.createDataURIScheme(File("images/logo.png"))
    ```

The complete file is read into memory. Media-type detection or file-reading failures cause
`IOException`; the method does not supply a fallback media type when detection returns no result.
