package com.tecknobit.equinoxbackend.environment.models;

import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser;
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall;
import com.tecknobit.equinoxcore.annotations.Structure;
import kotlin.Deprecated;

import static com.tecknobit.equinoxbackend.environment.services.builtin.entity.EquinoxItem.IDENTIFIER_KEY;
import static com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser.*;
import static com.tecknobit.equinoxbackend.environment.services.users.entity.EquinoxUser.ApplicationTheme.Auto;
import static com.tecknobit.equinoxcore.helpers.InputsValidator.HOST_ADDRESS_KEY;

/**
 * The {@code EquinoxLocalUser} class is useful to represent a user in the client application
 *
 * @author N7ghtm4r3 - Tecknobit
 * @since 1.0.1
 */
@Deprecated(
        message = "This class will be moved in the Equinox-Compose library in the next version"
)
@Structure
public abstract class EquinoxLocalUser {

    /**
     * {@code hostAddress} the host address which the user communicate
     */
    protected String hostAddress;

    /**
     * {@code userId} the identifier of the user
     */
    protected String userId;

    /**
     * {@code userToken} the token of the user
     */
    protected String userToken;

    /**
     * {@code profilePic} the profile pick of the user
     */
    protected String profilePic;

    /**
     * {@code name} the name of the user
     */
    protected String name;

    /**
     * {@code surname} the surname of the user
     */
    protected String surname;

    /**
     * {@code email} the email of the user
     */
    protected String email;

    /**
     * {@code password} the password of the user
     */
    protected String password;

    /**
     * {@code language} the language of the user
     */
    protected String language;

    /**
     * {@code theme} the theme of the user
     */
    protected ApplicationTheme theme;

    /**
     * Method to init the local user session 
     */
    @RequiresSuperCall
    protected void initLocalUser() {
        hostAddress = getHostAddress();
        userId = getPreference(IDENTIFIER_KEY);
        userToken = getPreference(TOKEN_KEY);
        profilePic = getPreference(PROFILE_PIC_KEY);
        name = getPreference(NAME_KEY);
        surname = getPreference(SURNAME_KEY);
        email = getPreference(EMAIL_KEY);
        password = getPreference(PASSWORD_KEY);
        language = getPreference(LANGUAGE_KEY);
        theme = ApplicationTheme.getInstance(getPreference(THEME_KEY));
    }

    /**
     * Method to insert and init a new local user
     *
     * @param hostAddress: the host address which the user communicate
     * @param name:        the name of the user
     * @param surname:     the surname of the user
     * @param email:       the email of the user
     * @param password:    the password of the user
     * @param language:    the language of the user
     * @param hResponse:   the payload response received from an authentication request
     * @param custom: the custom parameters added in a customization of the {@link EquinoxUser}
     * @apiNote workflow example:
     * <pre>
     *     {@code
     *          public class CustomLocalUser extends EquinoxLocalUser {
     *
     *              private String currency;
     *
     *              @Override
     *              @RequiresSuperCall
     *              @CustomParametersOrder(order = {"currency"})
     *              public void insertNewUser(String hostAddress, String name, String surname, String email, String password,
     *                                      String language, JsonHelper hResponse, Object... custom) {
     *                  // set first your custom parameters
     *                  setCurrency(custom[0].toString());
     *                  // then invoke the super method
     *                  super.insertNewUser(hostAddress, name, surname, email, password, language, hResponse);
     *              }
     *
     *              public void setCurrency(String currency) {
     *                  this.setPreference("currency", currency);
     *                  this.currency = currency;
     *              }
     *
     *               public String getCurrency() {
     *                  return this.currency;
     *              }
     *
     *          }
     *     }
     * </pre>
     */
    @RequiresSuperCall
    public void insertNewUser(String hostAddress, String name, String surname, String email, String password,
                              String language, JsonHelper hResponse, Object... custom) {
        setHostAddress(hostAddress);
        setUserId(hResponse.getString(IDENTIFIER_KEY));
        setUserToken(hResponse.getString(TOKEN_KEY));
        setProfilePic(hResponse.getString(PROFILE_PIC_KEY));
        setName(name);
        setSurname(surname);
        setEmail(email);
        setPassword(password);
        setLanguage(language);
        setTheme(Auto);
        initLocalUser();
    }

    /**
     * Method to set the {@link #hostAddress} instance <br>
     *
     * @param hostAddress: the host address which the user communicate
     */
    public void setHostAddress(String hostAddress) {
        setPreference(HOST_ADDRESS_KEY, hostAddress);
        this.hostAddress = hostAddress;
    }

    /**
     * Method to get {@link #hostAddress} instance <br>
     * No-any params required
     *
     * @return {@link #hostAddress} instance as {@link String}
     */
    public String getHostAddress() {
        String hostAddress = getPreference(HOST_ADDRESS_KEY);
        if (hostAddress == null)
            return "";
        return hostAddress;
    }

    /**
     * Method to set the {@link #userId} instance <br>
     *
     * @param userId: the identifier of the user
     */
    public void setUserId(String userId) {
        setPreference(IDENTIFIER_KEY, userId);
        this.userId = userId;
    }

    /**
     * Method to get {@link #userId} instance <br>
     * No-any params required
     *
     * @return {@link #userId} instance as {@link String}
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Method to set the {@link #userToken} instance <br>
     *
     * @param userToken: the token of the user
     */
    public void setUserToken(String userToken) {
        setPreference(TOKEN_KEY, userToken);
        this.userToken = userToken;
    }

    /**
     * Method to get {@link #userToken} instance <br>
     * No-any params required
     *
     * @return {@link #userToken} instance as {@link String}
     */
    public String getUserToken() {
        return userToken;
    }

    /**
     * Method to set the {@link #profilePic} instance <br>
     *
     * @param profilePic: the profile pic of the user
     */
    public void setProfilePic(String profilePic) {
        if (this.profilePic == null || !this.profilePic.equals(profilePic)) {
            profilePic = hostAddress + "/" + profilePic;
            setPreference(PROFILE_PIC_KEY, profilePic);
            this.profilePic = profilePic;
        }
    }

    /**
     * Method to get {@link #profilePic} instance <br>
     * No-any params required
     *
     * @return {@link #profilePic} instance as {@link String}
     */
    public String getProfilePic() {
        return profilePic;
    }

    /**
     * Method to set the {@link #name} instance <br>
     *
     * @param name: the name of the user
     */
    public void setName(String name) {
        setPreference(NAME_KEY, name);
        this.name = name;
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
     * Method to set the {@link #surname} instance <br>
     *
     * @param surname: the surname of the user
     */
    public void setSurname(String surname) {
        setPreference(SURNAME_KEY, surname);
        this.surname = surname;
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
    public String getCompleteName() {
        return name + " " + surname;
    }

    /**
     * Method to set the {@link #email} instance <br>
     *
     * @param email: the email of the user
     */
    public void setEmail(String email) {
        setPreference(EMAIL_KEY, email);
        this.email = email;
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
     * Method to set the {@link #password} instance <br>
     *
     * @param password: the password of the user
     */
    public void setPassword(String password) {
        setPreference(PASSWORD_KEY, password);
        this.password = password;
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
     * Method to set the {@link #language} instance <br>
     *
     * @param language: the language of the user
     */
    public void setLanguage(String language) {
        setPreference(LANGUAGE_KEY, language);
        this.language = language;
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
     * Method to set the {@link #theme} instance <br>
     *
     * @param theme: the theme of the user
     */
    public void setTheme(ApplicationTheme theme) {
        setPreference(THEME_KEY, theme.name());
        this.theme = theme;
    }

    /**
     * Method to get {@link #theme} instance <br>
     * No-any params required
     *
     * @return {@link #theme} instance as {@link ApplicationTheme}
     */
    public ApplicationTheme getTheme() {
        return theme;
    }

    /**
     * Method to store and set a preference
     *
     * @param key:   the key of the preference
     * @param value: the value of the preference
     */
    protected abstract void setPreference(String key, String value);

    /**
     * Method to get a stored preference
     *
     * @param key: the key of the preference to get
     * @return the preference stored as {@link String}
     */
    protected abstract String getPreference(String key);

    /**
     * Method to get whether the user is already authenticated in a session <br>
     * No-any params required
     *
     * @return whether the user is already authenticated in a session as boolean
     */
    public boolean isAuthenticated() {
        return userId != null;
    }

    /**
     * Method to clear the current local user session <br>
     * No-any params required
     */
    public abstract void clear();

}