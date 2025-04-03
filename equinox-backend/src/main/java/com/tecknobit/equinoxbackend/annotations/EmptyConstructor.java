package com.tecknobit.equinoxbackend.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@code EmptyConstructor} annotation is used to mark the default constructors required for the Spring Boot
 * entities management. For example:
 *
 * <pre>
 *     {@code
 *
 *         // this constructor is used by Spring Boot to initialize autonomously the EquinoxUser entity
 *         @EmptyConstructor
 *         public EquinoxUser() {
 *             this(null, null, null, null, null, null, null);
 *         }
 *
 *         public EquinoxUser(String id, String token, String name, String surname, String email, String password, String language) {
 *             this(id, token, name, surname, email, password, DEFAULT_PROFILE_PIC, language);
 *         }
 *
 *         public EquinoxUser(String id, String token, String name, String surname, String email, String password, String profilePic,
 *                            String language) {
 *             super(id);
 *             this.token = token;
 *             this.name = name;
 *             this.surname = surname;
 *             this.email = email;
 *             this.password = password;
 *             this.profilePic = profilePic;
 *             this.language = language;
 *         }
 *
 *         public EquinoxUser(JSONObject jUser) {
 *             super(jUser);
 *             token = hItem.getString(TOKEN_KEY);
 *             name = hItem.getString(NAME_KEY);
 *             surname = hItem.getString(SURNAME_KEY);
 *             email = hItem.getString(EMAIL_KEY);
 *             password = hItem.getString(PASSWORD_KEY);
 *             profilePic = hItem.getString(PROFILE_PIC_KEY);
 *             language = hItem.getString(LANGUAGE_KEY);
 *         }
 *     }
 * </pre>
 *
 * @author N7ghtm4r3 - Tecknobit
 * @since 1.1.0
 */
@Target(ElementType.CONSTRUCTOR)
@Retention(RetentionPolicy.SOURCE)
public @interface EmptyConstructor {
}
