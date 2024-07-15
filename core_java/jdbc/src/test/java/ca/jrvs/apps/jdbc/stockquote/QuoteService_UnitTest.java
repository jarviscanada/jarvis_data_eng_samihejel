package ca.jrvs.apps.jdbc.stockquote;

import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.dao.QuoteService;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuoteService_UnitTest {

    private QuoteService quoteService;
    private QuoteDao mockDao;
    private QuoteHttpHelper mockHttpHelper;

    @Before
    public void setUp() {
        mockDao = mock(QuoteDao.class);
        mockHttpHelper = mock(QuoteHttpHelper.class);
        quoteService = new QuoteService(mockDao, mockHttpHelper);
    }

    @Test
    public void fetchQuoteDataFromAPI_Success() {
        Quote mockQuote = new Quote();
        mockQuote.setTicker("AAPL");
        when(mockHttpHelper.fetchQuoteInfo(anyString())).thenReturn(mockQuote);
        when(mockDao.save(mockQuote)).thenReturn(mockQuote);

        Optional<Quote> result = quoteService.fetchQuoteDataFromAPI("AAPL");

        assertEquals(mockQuote, result.get());
    }

    @Test
    public void fetchQuoteDataFromAPI_QuoteNotFound() {
        when(mockHttpHelper.fetchQuoteInfo(anyString())).thenReturn(null);

        Optional<Quote> result = quoteService.fetchQuoteDataFromAPI("AAPL");

        assertEquals(Optional.empty(), result);

    }
}
