package ca.jrvs.apps.stockquote.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuoteHttpHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuoteHttpHelper.class);
    private String apiKey;
    private OkHttpClient client;
    private ObjectMapper objectMapper;
    public QuoteHttpHelper(String apiKey) {
        this.apiKey = apiKey;
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }
    /**
     * Fetch latest quote data from Alpha Vantage endpoint
     * @param symbol
     * @return Quote with latest data
     * @throws IllegalArgumentException - if no data was found for the given symbol
     */
    public Quote fetchQuoteInfo(String symbol) throws IllegalArgumentException {
        Request request = new Request.Builder()
                .url("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&datatype=json")
                .addHeader("X-RapidAPI-Key", apiKey)
                .addHeader("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                .build();
        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                LOGGER.error("Unexpected code {}", response);
                throw new IOException("Unexpected code " + response);
            }
            String responseBody = Objects.requireNonNull(response.body()).string();
            Quote quote = objectMapper.readValue(responseBody, Quote.class);
            LOGGER.info("Successfully fetched quote for symbol: {}", symbol);
            return quote;
        } catch (NullPointerException | IOException e) {
            LOGGER.error("Error fetching quote for symbol: {}", symbol, e);
            e.printStackTrace();
            throw new IllegalArgumentException("The Quote could not be fetched", e);
        }
    }
}