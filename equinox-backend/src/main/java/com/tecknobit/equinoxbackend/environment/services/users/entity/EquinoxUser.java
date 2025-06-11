package com.tecknobit.equinoxbackend.environment.services.users.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.equinoxbackend.annotations.EmptyConstructor;
import com.tecknobit.equinoxbackend.environment.services.builtin.entity.EquinoxItem;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.json.JSONObject;

import static com.tecknobit.equinoxcore.helpers.CommonKeysKt.*;

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
     * Constructor to init the {@link EquinoxUser} class <br>
     *
     * @apiNote empty constructor required
     */
    @EmptyConstructor
    public EquinoxUser() {
        this(null, null, null, null, null, null, null);
    }

    /**
     * Constructor to init the {@link EquinoxUser} class
     *
     * @param id Identifier of the user
     * @param token The token which the user is allowed to operate on server
     * @param name The name of the user
     * @param surname The surname of the user
     * @param email The email of the user
     * @param password The password of the user
     * @param language The language of the user
     */
    public EquinoxUser(String id, String token, String name, String surname, String email, String password, String language) {
        this(id, token, name, surname, email, password, DEFAULT_PROFILE_PIC, language);
    }

    /**
     * Constructor to init the {@link EquinoxUser} class
     *
     * @param id Identifier of the user
     * @param token The token which the user is allowed to operate on server
     * @param name The password of the user
     * @param surname The surname of the user
     * @param email The password of the user
     * @param password The password of the user
     * @param profilePic The profile pic of the user
     * @param language The password of the user
     */
    public EquinoxUser(String id, String token, String name, String surname, String email, String password, String profilePic,
                       String language) {
        super(id);
        this.token = token;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.profilePic = profilePic;
        this.language = language;
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
    }

    /**
     * Method used to get {@link #token} instance 
     *
     * @return {@link #token} instance as {@link String}
     */
    public String getToken() {
        return token;
    }

    /**
     * Method used to get {@link #name} instance 
     *
     * @return {@link #name} instance as {@link String}
     */
    public String getName() {
        return name;
    }

    /**
     * Method used to get {@link #surname} instance 
     *
     * @return {@link #surname} instance as {@link String}
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Method used to get the complete name of the user 
     *
     * @return the complete name of the user as {@link String}
     */
    @JsonIgnore
    public String getCompleteName() {
        return name + " " + surname;
    }

    /**
     * Method used to get {@link #email} instance 
     *
     * @return {@link #email} instance as {@link String}
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method used to get {@link #password} instance 
     *
     * @return {@link #password} instance as {@link String}
     */
    public String getPassword() {
        return password;
    }

    /**
     * Method used to get {@link #profilePic} instance 
     *
     * @return {@link #profilePic} instance as {@link String}
     */
    @JsonProperty(PROFILE_PIC_KEY)
    public String getProfilePic() {
        return profilePic;
    }

    /**
     * Method used to get {@link #language} instance 
     *
     * @return {@link #language} instance as {@link String}
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Method used to assemble and return a {@link EquinoxUser} instance
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

}
