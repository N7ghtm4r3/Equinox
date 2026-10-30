package com.tecknobit.equinoxbackend.configuration;

import com.tecknobit.equinoxbackend.exceptions.SaveData;
import com.tecknobit.equinoxcore.annotations.Wrapper;

import java.security.NoSuchAlgorithmException;
import java.util.prefs.Preferences;

import static com.tecknobit.equinoxbackend.utils.WebUtil.SHA256_ALGORITHM;
import static com.tecknobit.equinoxbackend.utils.WebUtil.base64Digest;
import static java.util.UUID.randomUUID;

/**
 * The {@code ServerProtector} class is useful to manage the shared secret used to protect an Equinox backend
 *
 * <p>The backend stores a Base64-encoded SHA-256 digest in the configured preferences node. Newly
 * generated secrets are exposed through {@link SaveData} so the administrator can save and distribute them
 *
 * <p>The startup commands are {@code rss} to regenerate the secret, {@code dss} to delete it without
 * interrupting execution, and {@code dssi} to delete it and interrupt execution
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 2.0.0
 */
public class ServerProtector {

    /**
     * {@code SERVER_SECRET_KEY} the preferences key used to store the server-secret digest
     */
    public static final String SERVER_SECRET_KEY = "server_secret";

    /**
     * {@code RECREATE_SERVER_SECRET_COMMAND} the startup command used to replace the current server secret
     */
    public static final String RECREATE_SERVER_SECRET_COMMAND = "rss";

    /**
     * {@code DELETE_SERVER_SECRET_COMMAND} the startup command used to delete the secret without interrupting execution
     */
    public static final String DELETE_SERVER_SECRET_COMMAND = "dss";

    /**
     * {@code DELETE_SERVER_SECRET_AND_INTERRUPT_COMMAND} the startup command used to delete the secret and interrupt execution
     */
    public static final String DELETE_SERVER_SECRET_AND_INTERRUPT_COMMAND = "dssi";

    /**
     * {@code preferences} the configured preferences node containing the server-secret digest
     */
    private final Preferences preferences;

    /**
     * {@code saveMessage} the administrator message appended to a newly generated server secret
     */
    private final String saveMessage;

    /**
     * Constructor to init the {@link ServerProtector} class
     *
     * @param storagePath The node path under the current user's preferences root
     * @param saveMessage The administrator message appended when a newly generated secret must be saved
     */
    public ServerProtector(String storagePath, String saveMessage) {
        this.preferences = Preferences.userRoot().node(storagePath);
        if (saveMessage.startsWith(" "))
            saveMessage = saveMessage.trim();
        this.saveMessage = " " + saveMessage;
    }

    /**
     * Method used to initialize server protection and process startup commands
     *
     * <p>When no secret is stored, a new one is generated and exposed through {@link SaveData} before
     * commands are processed. Otherwise, arguments are processed in order until a command interrupts execution
     *
     * @param args The startup arguments, including any {@code rss}, {@code dss}, or {@code dssi} command
     * @throws NoSuchAlgorithmException when the SHA-256 digest algorithm is unavailable
     * @throws SaveData when a new secret is generated and must be saved
     * @throws RuntimeException when the {@code dssi} command deletes the secret and interrupts execution
     */
    public void launch(String[] args) throws NoSuchAlgorithmException, SaveData {
        if (getCurrentServerSecret() == null)
            generateServerSecret();

        for (String arg : args) {
            if (arg.equals(RECREATE_SERVER_SECRET_COMMAND))
                recreateServerSecret();
            if (arg.equals(DELETE_SERVER_SECRET_COMMAND))
                deleteServerSecret();
            if (arg.equals(DELETE_SERVER_SECRET_AND_INTERRUPT_COMMAND))
                deleteServerSecret(true);
        }
    }

    /**
     * Method used to generate a server secret and store its digest
     *
     * <p>The new secret replaces any stored digest. The clear-text secret and administrator message
     * are then exposed through {@link SaveData}, interrupting the current initialization flow
     *
     * @throws NoSuchAlgorithmException when the SHA-256 digest algorithm is unavailable
     * @throws SaveData after the new secret has been stored, so the administrator can save its clear-text value
     */
    public void generateServerSecret() throws NoSuchAlgorithmException, SaveData {
        String serverSecret = randomUUID().toString().replace("-", "");
        storeServerSecret(serverSecret);
        throw new SaveData(serverSecret + saveMessage);
    }

    /**
     * Method used to replace the current server secret with a newly generated value
     *
     * @throws NoSuchAlgorithmException when the SHA-256 digest algorithm is unavailable
     * @throws SaveData after the new secret has been stored, so the administrator can save its clear-text value
     */
    public void recreateServerSecret() throws NoSuchAlgorithmException, SaveData {
        generateServerSecret();
    }

    /**
     * Method used to store the Base64-encoded SHA-256 digest of a server secret
     *
     * @param serverSecret The clear-text secret whose digest replaces the stored value
     * @throws NoSuchAlgorithmException when the SHA-256 digest algorithm is unavailable
     */
    private void storeServerSecret(String serverSecret) throws NoSuchAlgorithmException {
        preferences.put(SERVER_SECRET_KEY, hash(serverSecret));
    }

    /**
     * Method used to check whether a supplied secret matches the stored server-secret digest
     *
     * @param serverSecretToCompare The clear-text secret to verify, or {@code null} to reject the value
     *
     * @return whether the secret matches; {@code false} for null input or an unavailable digest algorithm,
     *         as {@code boolean}
     * @throws NullPointerException when a non-null secret is checked without a stored digest
     */
    public boolean serverSecretMatches(String serverSecretToCompare) {
        String currentServerSecret = getCurrentServerSecret();
        if (serverSecretToCompare == null)
            return false;

        try {
            return currentServerSecret.equals(hash(serverSecretToCompare));
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }

    /**
     * Method used to delete the stored server-secret digest without interrupting execution
     */
    @Wrapper
    public void deleteServerSecret() {
        deleteServerSecret(false);
    }

    /**
     * Method used to delete the stored server-secret digest and optionally interrupt execution
     *
     * @param interruptRuntime Whether execution must be interrupted after deleting the digest
     * @throws RuntimeException when {@code interruptRuntime} is {@code true}, after the digest has been deleted
     */
    public void deleteServerSecret(boolean interruptRuntime) {
        preferences.remove(SERVER_SECRET_KEY);
        if (interruptRuntime)
            throw new RuntimeException("The server secret has been deleted");
    }

    /**
     * Method used to get the stored server-secret digest
     *
     * @return the stored Base64-encoded SHA-256 digest, or {@code null} when absent, as {@link String}
     */
    private String getCurrentServerSecret() {
        return preferences.get(SERVER_SECRET_KEY, null);
    }

    /**
     * Method used to compute the Base64-encoded SHA-256 digest of a server secret
     *
     * @param serverSecret The clear-text secret to hash
     *
     * @return the Base64-encoded digest as {@link String}
     * @throws NoSuchAlgorithmException when the SHA-256 digest algorithm is unavailable
     */
    private String hash(String serverSecret) throws NoSuchAlgorithmException {
        return base64Digest(serverSecret, SHA256_ALGORITHM);
    }

}