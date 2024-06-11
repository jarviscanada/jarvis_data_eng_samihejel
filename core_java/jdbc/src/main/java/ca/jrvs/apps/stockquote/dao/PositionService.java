package ca.jrvs.apps.stockquote.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class PositionService {

    private PositionDao dao;
    private static final Logger LOGGER = LoggerFactory.getLogger(PositionService.class);

    public PositionService(PositionDao dao) {
        this.dao = dao;
    }

    /**
     * Processes a buy order and updates the database accordingly
     * @param ticker
     * @param numberOfShares
     * @param price
     * @return The position in our database after processing the buy
     */
    public Optional<Position> buy(String ticker, int numberOfShares, double price) {
        // Retrieve existing position for the given ticker
        Optional<Position> existingPosition = dao.findById(ticker);
        Position position = new Position();
        if (existingPosition.isPresent()) {
            position = existingPosition.get();
            position.setNumOfShares(position.getNumOfShares() + numberOfShares);
            position.setValuePaid(position.getValuePaid() + (numberOfShares * price));
        } else {
            position.setTicker(ticker);
            position.setNumOfShares(numberOfShares);
            position.setValuePaid(numberOfShares * price);
        }
        dao.save(position);
        LOGGER.info("Successfully processed buy order for ticker: {}, shares: {}, price: {}", ticker, numberOfShares, price);
        return Optional.of(position);

    }

    /**
     * Sells all shares of the given ticker symbol
     * @param ticker
     */
    public void sell(String ticker) {
        Optional<Position> position = dao.findById(ticker);
        if(position.isPresent()){
            dao.deleteById(ticker);
            LOGGER.info("Successfully sold all shares for ticker: {}", ticker);
        }else {
            LOGGER.warn("Attempted to sell shares for ticker: {}, but no position found", ticker);
        }

    }

}
