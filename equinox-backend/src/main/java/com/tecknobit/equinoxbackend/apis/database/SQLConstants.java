package com.tecknobit.equinoxbackend.apis.database;

/**
 * The {@code SQLConstants} class is a container for the constants used in the {@code SQL} queries. <br>
 * The naming convention for those constants which are part of query such where, values, etc... is:
 * The underscore character ({@code _}) stands for {@code whitespace}, so the {@link #_WHERE_} constant value is: " WHERE "
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.1.5
 */
public class SQLConstants {

    /**
     * {@code SINGLE_QUOTE} single quote character
     */
    public static final String SINGLE_QUOTE = "'";

    /**
     * {@code OPENED_ROUND_BRACKET} opened round bracket character
     */
    public static final String OPENED_ROUND_BRACKET = "(";

    /**
     * {@code CLOSED_ROUND_BRACKET} closed round bracket character
     */
    public static final String CLOSED_ROUND_BRACKET = ")";

    /**
     * {@code COMMA} comma character
     */
    public static final String COMMA = ",";

    /**
     * {@code QUESTION_MARK} question mark character
     */
    public static final String QUESTION_MARK = "?";

    /**
     * {@code VALUES} values query part
     */
    public static final String _VALUES_ = " VALUES ";

    /**
     * {@code DELETE_FROM_} delete from query command
     */
    public static final String DELETE_FROM_ = "DELETE FROM ";

    /**
     * {@code WHERE} where query part
     */
    public static final String _WHERE_ = " WHERE ";

    /**
     * {@code IN_CLAUSE} in clause query part
     */
    public static final String _IN_CLAUSE_ = " IN ";

    /**
     * Private constructor to avoid instantiation
     */
    private SQLConstants() {}
    
}
