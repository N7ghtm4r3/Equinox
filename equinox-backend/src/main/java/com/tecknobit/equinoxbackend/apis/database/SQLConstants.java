package com.tecknobit.equinoxbackend.apis.database;

// TODO: 01/09/2025 TO DOCU 1.1.5 AND AWARE ABOUT THE CONVETION OF USING _ 
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
    
    private SQLConstants() {}
    
}
