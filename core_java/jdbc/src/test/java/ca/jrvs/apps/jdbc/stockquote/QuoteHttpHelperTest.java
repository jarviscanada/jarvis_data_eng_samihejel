package ca.jrvs.apps.jdbc.stockquote;

import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class QuoteHttpHelperTest {

    @Mock
    private OkHttpClient client;

    @Mock
    private Call call;

    @Mock
    private Response response;

    @Mock
    private ResponseBody responseBody;

    private QuoteHttpHelper quoteHttpHelper;

    private ObjectMapper objectMapper;

    private final String testKey = "fedb3c0e2amsh0563c24761e8cd0p1f58a3jsn96f21dea4a6c";


    @Before
    public void setUp() {
        quoteHttpHelper = new QuoteHttpHelper(testKey);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void fetchQuoteInfoTest() throws IOException {
        String symbol = "MSFT";
        Request request = new Request.Builder()
                .url("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&datatype=json")
                .addHeader("X-RapidAPI-Key", testKey)
                .addHeader("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                .build();
        String quoteJson = "{\"Global Quote\": {\"01. symbol\": \"" + symbol + "\", \"05. price\": \"280.00\"}}";

        when(client.newCall(request)).thenReturn(call);
        when(call.execute()).thenReturn(response);
        when(response.isSuccessful()).thenReturn(true);
        when(response.body()).thenReturn(responseBody);
        when(responseBody.string()).thenReturn(quoteJson);

        Quote actualQuote = quoteHttpHelper.fetchQuoteInfo(symbol);

        assertEquals(200.0, actualQuote.getPrice());
    }

}