package com.tecknobit.equinoxbackend.environment.records;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tecknobit.apimanager.annotations.Returner;
import jakarta.persistence.*;
import org.json.JSONObject;

import java.util.Locale;

import static com.tecknobit.equinoxbackend.environment.records.EquinoxUser.ApplicationTheme.Auto;
import static com.tecknobit.equinoxbackend.environment.records.EquinoxUser.USERS_KEY;
import static com.tecknobit.equinoxbackend.inputs.InputValidator.DEFAULT_LANGUAGE;
import static com.tecknobit.equinoxbackend.inputs.InputValidator.LANGUAGES_SUPPORTED;

/**
 * The {@code EquinoxUser} class is useful to represent a base Equinox's system user
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see EquinoxItem
 *
 * @since 1.0.1
 */
@Entity
@Table(name = USERS_KEY)
@DiscriminatorColumn
public class EquinoxUser extends EquinoxItem {

    /**
     * {@code USERS_KEY} the key for the <b>"users"</b> field
     */
    public static final String USERS_KEY = "users";

    /**
     * {@code TOKEN_KEY} the key for the <b>"token"</b> field
     */
    public static final String TOKEN_KEY = "token";

    /**
     * {@code NAME_KEY} the key for the <b>"name"</b> field
     */
    public static final String NAME_KEY = "name";

    /**
     * {@code SURNAME_KEY} the key for the <b>"surname"</b> field
     */
    public static final String SURNAME_KEY = "surname";

    /**
     * {@code EMAIL_KEY} the key for the <b>"email"</b> field
     */
    public static final String EMAIL_KEY = "email";

    /**
     * {@code PASSWORD_KEY} the key for the <b>"password"</b> field
     */
    public static final String PASSWORD_KEY = "password";

    /**
     * {@code PROFILE_PIC_KEY} the key for the <b>"profile_pic"</b> field
     */
    public static final String PROFILE_PIC_KEY = "profile_pic";

    /**
     * {@code DEFAULT_PROFILE_PIC} the default profile pic path when the user has not set own image
     */
    public static final String DEFAULT_PROFILE_PIC = "profiles/defProfilePic.png";

    /**
     * {@code LANGUAGE_KEY} the key for the <b>"language"</b> field
     */
    public static final String LANGUAGE_KEY = "language";

    /**
     * {@code THEME_KEY} the key for the <b>"theme"</b> field
     */
    public static final String THEME_KEY = "theme";

    /**
     * {@code ApplicationTheme} list of the available theming for the client applications
     */
    public enum ApplicationTheme {

        /**
         * {@code Dark} the dark theme to use as theme
         */
        Dark,

        /**
         * {@code Light} the light theme to use as theme
         */
        Light,

        /**
         * {@code Auto} the theme to use based on the user current theme set
         */
        Auto;

        /**
         * Method to get an instance of the {@link ApplicationTheme}
         *
         * @param theme: the name of the theme to get
         * @return the theme instance as {@link ApplicationTheme}
         */
        public static ApplicationTheme getInstance(String theme) {
            if (theme == null)
                return Auto;
            return switch (theme) {
                case "Dark" -> Dark;
                case "Light" -> Light;
                default -> Auto;
            };
        }

    }

    /**
     * {@code token} the token which the user is allowed to operate on server
     */
    @Column(
            name = TOKEN_KEY,
            columnDefinition = "VARCHAR(32) NOT NULL",
            unique = true
    )
    protected final String token;

    /**
     * {@code name} the name of the user
     */
    @Column(
            name = NAME_KEY,
            columnDefinition = "VARCHAR(20) NOT NULL"
    )
    protected final String name;

    /**
     * {@code surname} the surname of the user
     */
    @Column(
            name = SURNAME_KEY,
            columnDefinition = "VARCHAR(30) NOT NULL"
    )
    protected final String surname;

    /**
     * {@code email} the email of the user
     */
    @Column(
            name = EMAIL_KEY,
            columnDefinition = "VARCHAR(75) NOT NULL",
            unique = true
    )
    protected final String email;

    /**
     * {@code password} the password of the user
     */
    @Column(
            name = PASSWORD_KEY,
            nullable = false
    )
    @JsonIgnore
    protected final String password;

    /**
     * {@code profilePic} the profile pic of the user
     */
    @Column(
            name = PROFILE_PIC_KEY,
            columnDefinition = "TEXT DEFAULT '" + DEFAULT_PROFILE_PIC + "'",
            insertable = false
    )
    protected final String profilePic;

    /**
     * {@code language} the language of the user
     */
    @Column(
            name = LANGUAGE_KEY,
            columnDefinition = "VARCHAR(2) NOT NULL"
    )
    protected final String language;

    /**
     * {@code theme} the theme of the user
     */
    @Transient
    protected final ApplicationTheme theme;

    /**
     * Constructor to init the {@link EquinoxUser} class <br>
     * <p>
     * No-any params required
     *
     * @apiNote empty constructor required
     */
    public EquinoxUser() {
        this(null, null, null, null, null, null, null);
    }

    /**
     * Constructor to init the {@link EquinoxUser} class
     *
     * @param id:       identifier of the user
     * @param token:    the token which the user is allowed to operate on server
     * @param name:     the name of the user
     * @param surname:  the surname of the user
     * @param email:    the email of the user
     * @param password: the password of the user
     * @param language: the language of the user
     */
    public EquinoxUser(String id, String token, String name, String surname, String email, String password, String language) {
        this(id, token, name, surname, email, password, DEFAULT_PROFILE_PIC, language, Auto);
    }

    /**
     * Constructor to init the {@link EquinoxUser} class
     *
     * @param id:         identifier of the user
     * @param token:      the token which the user is allowed to operate on server
     * @param name:       the password of the user
     * @param surname:    the surname of the user
     * @param email:      the password of the user
     * @param password:   the password of the user
     * @param profilePic: the profile pic of the user
     * @param language:   the password of the user
     * @param theme:      the theme of the user
     */
    public EquinoxUser(String id, String token, String name, String surname, String email, String password, String profilePic,
                       String language, ApplicationTheme theme) {
        super(id);
        this.token = token;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.profilePic = profilePic;
        this.language = language;
        this.theme = theme;
    }

    /**
     * Constructor to init the {@link EquinoxUser} class
     *
     * @param jUser: user details formatted as JSON
     */
    public EquinoxUser(JSONObject jUser) {
        super(jUser);
        token = hItem.getString(TOKEN_KEY);
        name = hItem.getString(NAME_KEY);
        surname = hItem.getString(SURNAME_KEY);
        email = hItem.getString(EMAIL_KEY);
        password = hItem.getString(PASSWORD_KEY);
        profilePic = hItem.getString(PROFILE_PIC_KEY);
        language = hItem.getString(LANGUAGE_KEY);
        theme = Auto;
    }

    /**
     * Method to get {@link #token} instance <br>
     * No-any params required
     *
     * @return {@link #token} instance as {@link String}
     */
    public String getToken() {
        return token;
    }

    /**
     * Method to get {@link #name} instance <br>
     * No-any params required
     *
     * @return {@link #name} instance as {@link String}
     */
    public String getName() {
        return name;
    }

    /**
     * Method to get {@link #surname} instance <br>
     * No-any params required
     *
     * @return {@link #surname} instance as {@link String}
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Method to get the complete name of the user <br>
     * No-any params required
     *
     * @return the complete name of the user as {@link String}
     */
    @JsonIgnore
    public String getCompleteName() {
        return name + " " + surname;
    }

    /**
     * Method to get {@link #email} instance <br>
     * No-any params required
     *
     * @return {@link #email} instance as {@link String}
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method to get {@link #password} instance <br>
     * No-any params required
     *
     * @return {@link #password} instance as {@link String}
     */
    public String getPassword() {
        return password;
    }

    /**
     * Method to get {@link #profilePic} instance <br>
     * No-any params required
     *
     * @return {@link #profilePic} instance as {@link String}
     */
    @JsonProperty(PROFILE_PIC_KEY)
    public String getProfilePic() {
        return profilePic;
    }

    /**
     * Method to get {@link #language} instance <br>
     * No-any params required
     *
     * @return {@link #language} instance as {@link String}
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Method to get {@link #name} instance <br>
     * No-any params required
     *
     * @return {@link #name} instance as {@link ApplicationTheme}
     */
    @JsonIgnore
    public ApplicationTheme getTheme() {
        return theme;
    }

    /**
     * Method to assemble and return a {@link EquinoxUser} instance
     *
     * @param jUser: user details formatted as JSON
     * @return the user instance as {@link EquinoxUser}
     */
    @Returner
    public static EquinoxUser getInstance(JSONObject jUser) {
        if (jUser != null)
            return new EquinoxUser(jUser);
        return null;
    }

    /**
     * Method to get a supported language for the user <br>
     * <p>
     * No-any params required
     *
     * @return a supported language for the user as {@link String}
     */
    public static String getValidUserLanguage() {
        String currentLanguageTag = Locale.getDefault().toLanguageTag().substring(0, 2);
        if (LANGUAGES_SUPPORTED.get(currentLanguageTag) == null)
            return DEFAULT_LANGUAGE;
        return currentLanguageTag;
    }

}
