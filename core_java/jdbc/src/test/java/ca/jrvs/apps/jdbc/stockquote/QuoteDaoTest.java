package ca.jrvs.apps.jdbc.stockquote;

import ca.jrvs.apps.jdbc.DatabaseConnectionManager;
import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class QuoteDaoTest {


    private DatabaseConnectionManager dcm;
    private Connection connection;
    private QuoteDao quoteDao;

    @Before
    public void setUp() throws SQLException {
        dcm = new DatabaseConnectionManager("localhost", "hplussport", "postgres", "password");
        connection = dcm.getConnection();
        quoteDao = new QuoteDao(connection);
    }

    @After
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void saveTest() {
        Quote quote = new Quote();
        quote.setTicker("AAPL");
        quote.setOpen(150.0);
        quote.setHigh(155.0);
        quote.setLow(148.0);
        quote.setPrice(152.0);
        quote.setVolume(1000000);
        quote.setLatestTradingDay(new java.sql.Date(System.currentTimeMillis()));
        quote.setPreviousClose(149.0);
        quote.setChange(3.0);
        quote.setChangePercent("2%");
        quote.setTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));

        Quote savedQuote = quoteDao.save(quote);

        assertEquals(quote, savedQuote);
    }

    @Test
    public void findByIdTest() {
        String ticker = "AAPL";

        Optional<Quote> optionalQuote = quoteDao.findById(ticker);

        assertTrue(optionalQuote.isPresent());
        assertEquals(ticker, optionalQuote.get().getTicker());
    }

    @Test
    public void findAllTest() {
        List<Quote> quotes = quoteDao.findAll();

        assertNotNull(quotes);
        assertFalse(quotes.isEmpty());
    }

    @Test
    public void deleteByIdTest() {
        String ticker = "AAPL";

        quoteDao.deleteById(ticker);

        assertFalse(quoteDao.findById(ticker).isPresent());
    }

    @Test
    public void deleteAllTest() {
        quoteDao.deleteAll();

        assertTrue(quoteDao.findAll().isEmpty());
    }
}
