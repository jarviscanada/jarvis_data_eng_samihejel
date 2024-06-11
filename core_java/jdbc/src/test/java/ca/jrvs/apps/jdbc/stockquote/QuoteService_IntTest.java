package ca.jrvs.apps.jdbc.stockquote;

import ca.jrvs.apps.stockquote.DatabaseConnectionManager;
import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.dao.QuoteService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class QuoteService_IntTest {

    private QuoteDao quoteDao;
    private QuoteHttpHelper quoteHttpHelper;
    private QuoteService quoteService;
    private Connection connection;

    @Before
    public void setUp() throws SQLException {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "stock_quote", "postgres", "password");
        connection = dcm.getConnection();
        quoteDao = new QuoteDao(connection);
        QuoteHttpHelper httpHelper = new QuoteHttpHelper("api-key");
        quoteService = new QuoteService(quoteDao, httpHelper);
    }


    @Test
    public void test_fetchQuoteDataFromAPI() {
        Optional<Quote> result = quoteService.fetchQuoteDataFromAPI("AAPL");

        assertEquals(true, result.isPresent());
    }

}
