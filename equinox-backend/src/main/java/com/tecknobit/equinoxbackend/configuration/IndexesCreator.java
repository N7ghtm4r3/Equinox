package com.tecknobit.equinoxbackend.configuration;

import com.tecknobit.equinoxcore.annotations.Assembler;
import com.tecknobit.equinoxcore.annotations.Wrapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Session;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.tecknobit.equinoxbackend.apis.batch.EquinoxItemsHelper.*;

/**
 * The {@code IndexesCreator} class is useful to create custom indexes in the tables made up the database of the system.
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @since 1.0.8
 */
@Transactional
public abstract class IndexesCreator {

    /**
     * {@code ALTER_TABLE_} query command
     */
    public static final String ALTER_TABLE_ = "ALTER TABLE ";

    /**
     * {@code SHOW_INDEX_FROM_} query command
     */
    public static final String SHOW_INDEX_FROM_ = "SHOW INDEX FROM ";

    /**
     * {@code _ADD_FULLTEXT_INDEX_} query command
     */
    public static final String _ADD_FULLTEXT_INDEX_ = " ADD FULLTEXT INDEX %s (";

    /**
     * {@code _IN_NATURAL_LANGUAGE_MODE} the mode applied to the FTS (full text search)
     */
    public static final String _IN_NATURAL_LANGUAGE_MODE = " IN NATURAL LANGUAGE MODE";

    /**
     * {@code _IN_BOOLEAN_MODE} the mode applied to the FTS (full text search)
     */
    public static final String _IN_BOOLEAN_MODE = " IN BOOLEAN MODE";

    /**
     * {@code _KEY_NAME} query part
     */
    protected static final String _KEY_NAME = "Key_name='";

    /**
     * {@code entityManager} entity manager helper
     */
    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * Method used to create the custom indexes
     *
     * @apiNote this method required the {@link jakarta.annotation.PostConstruct} annotation to correctly work
     */
    public abstract void createIndexes();

    /**
     * Method used to create the full text index used in the Full Text Search (FTS)
     *
     * @param table     The table where create the index
     * @param indexName The name of the index
     * @param fields    The field used to create the index
     */
    @Wrapper
    protected void createFullTextIndex(String table, String indexName, List<String> fields) {
        createIndex(table, indexName, _ADD_FULLTEXT_INDEX_, fields);
    }

    /**
     * Method used to create an index in the specified table
     *
     * @param table     The table where create the index
     * @param indexName The name of the index
     * @param indexType The type of the index to create
     * @param fields    The field used to create the index
     */
    protected void createIndex(String table, String indexName, String indexType, List<String> fields) {
        String showQuery = SHOW_INDEX_FROM_ + table + _WHERE_ + _KEY_NAME + indexName + SINGLE_QUOTE;
        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet set = statement.executeQuery(showQuery);
                if (!set.next()) {
                    String createIndexQuery = assembleCreateIndexQuery(table, indexType, indexName, fields);
                    statement.execute(createIndexQuery);
                }
            }
        });
    }

    /**
     * Method used to assemble the query to create an index
     *
     * @param table     The table where create the index
     * @param indexName The name of the index
     * @param indexType The type of the index to create
     * @param fields    The field used to create the index
     * @return the query used to create an index as {@link String}
     */
    @Assembler
    protected String assembleCreateIndexQuery(String table, String indexType, String indexName, List<String> fields) {
        StringBuilder query = new StringBuilder(ALTER_TABLE_ + table);
        query.append(String.format(indexType, indexName));
        int fieldsNumber = fields.size();
        int lastIndex = fieldsNumber - 1;
        for (int j = 0; j < fieldsNumber; j++) {
            query.append(fields.get(j));
            if (j < lastIndex)
                query.append(COMMA);
        }
        query.append(CLOSED_ROUND_BRACKET);
        return query.toString();
    }

    /**
     * Method used to format the keywords to use in the query for a full text search
     *
     * @param keywords           The raw keywords to format
     * @param escapeDoubleQuotes Whether escape the {@code "} character
     * @return the keywords to use in the full text search as {@link String}
     */
    @Wrapper
    public static String formatFullTextKeywords(Collection<String> keywords, boolean escapeDoubleQuotes) {
        return formatFullTextKeywords(keywords, "", escapeDoubleQuotes);
    }

    /**
     * Method used to format the keywords to use in the query for a full text search
     *
     * @param keywords           The raw keywords to format
     * @param trailingCharacter  The trailing character to add to the keywords string such *
     * @param escapeDoubleQuotes Whether escape the {@code "} character
     * @return the keywords to use in the full text search as {@link String}
     */
    @Wrapper
    public static String formatFullTextKeywords(Collection<String> keywords, String trailingCharacter,
                                                boolean escapeDoubleQuotes) {
        return formatFullTextKeywords(keywords, "", trailingCharacter, escapeDoubleQuotes);
    }

    /**
     * Method used to format the keywords to use in the query for a full text search
     *
     * @param keywords           The raw keywords to format
     * @param leadingCharacter   The leading character to add to the keywords string such -, +, etc...
     * @param trailingCharacter  The trailing character to add to the keywords string such *
     * @param escapeDoubleQuotes Whether escape the {@code "} character
     * @return the keywords to use in the full text search as {@link String}
     */
    public static String formatFullTextKeywords(Collection<String> keywords, String leadingCharacter,
                                                String trailingCharacter, boolean escapeDoubleQuotes) {
        if (keywords.isEmpty())
            return "";
        keywords = appendEscapeCharacters(keywords, leadingCharacter, trailingCharacter);
        String formattedKeywords = String.join(" ", keywords);
        if (escapeDoubleQuotes)
            formattedKeywords = formattedKeywords.replaceAll("\"", "");
        return formattedKeywords;
    }

    /**
     * Method used to append the escape characters like leading and trailing characters to each keyword for the
     * {@link #formatFullTextKeywords(Collection, String, String, boolean)} method for the {@link #_IN_BOOLEAN_MODE}
     * full text search
     *
     * @param keywords          The keywords used in the full text search
     * @param leadingCharacter  The leading character to add to the keywords string such -, +, etc...
     * @param trailingCharacter The trailing character to add to the keywords string such *
     * @return the keywords with the escape characters as {@link Collection} of {@link String}
     */
    private static Collection<String> appendEscapeCharacters(Collection<String> keywords, String leadingCharacter,
                                                             String trailingCharacter) {
        if (keywords.isEmpty() || (leadingCharacter.isEmpty() && trailingCharacter.isEmpty()))
            return keywords;
        String[] tmpKeywords = keywords.toArray(new String[0]);
        for (int j = 0; j < tmpKeywords.length; j++)
            tmpKeywords[j] = leadingCharacter + tmpKeywords[j] + trailingCharacter;
        return Arrays.stream(tmpKeywords).toList();
    }

}
