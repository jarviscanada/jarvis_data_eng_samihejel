package ca.jrvs.apps.jdbc.stockquote;

import ca.jrvs.apps.stockquote.DatabaseConnectionManager;
import ca.jrvs.apps.stockquote.dao.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class PositionService_IntTest {

    private PositionDao positionDao;
    private PositionService positionService;
    private Connection connection;


    @Before
    public void setUp() throws SQLException {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "stock_quote", "postgres", "password");
        connection = dcm.getConnection();
        positionDao = new PositionDao(connection);
        positionService = new PositionService(positionDao);
    }

    @Test
    public void test_buy() {
        // Call the service method to buy shares
        Optional<Position> result = positionService.buy("AAPL", 100, 150.0);

        // Verify that the purchase was successful
        assertEquals(true, result.isPresent());
        // Additional assertions can be added based on expected data
    }

    @Test
    public void test_sell() {
        positionService.buy("AAPL", 100, 150.0);

        positionService.sell("AAPL");

        Optional<Position> result = positionDao.findById("AAPL");
        assertEquals(false, result.isPresent());
    }
}
