package ca.jrvs.apps.stockquote.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class QuoteService {

    private QuoteDao dao;
    private QuoteHttpHelper httpHelper;

    private static final Logger LOGGER = LoggerFactory.getLogger(QuoteService.class);

    public QuoteService(QuoteDao dao, QuoteHttpHelper httpHelper) {
        this.dao = dao;
        this.httpHelper = httpHelper;
    }

    /**
     * Fetches latest quote data from endpoint
     * @param ticker
     * @return Latest quote information or empty optional if ticker symbol not found
     */
    public Optional<Quote> fetchQuoteDataFromAPI(String ticker) {
        Quote quote = httpHelper.fetchQuoteInfo(ticker);

            // Save quote data to the database
            if(quote != null){
                dao.save(quote);
                LOGGER.info("Fetched and saved quote data for ticker: {}", ticker);
                return Optional.of(quote);
            }else{
                LOGGER.warn("Quote data not found for ticker: {}", ticker);
            }
            return Optional.of(null);

    }

}