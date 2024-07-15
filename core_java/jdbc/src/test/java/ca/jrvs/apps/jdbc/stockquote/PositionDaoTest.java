package ca.jrvs.apps.jdbc.stockquote;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import ca.jrvs.apps.jdbc.DatabaseConnectionManager;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.jrvs.apps.stockquote.dao.Position;
import ca.jrvs.apps.stockquote.dao.PositionDao;

public class PositionDaoTest {

    private DatabaseConnectionManager dcm;
    private Connection connection;
    private PositionDao positionDao;

    @Before
    public void setUp() throws SQLException {
        dcm = new DatabaseConnectionManager("localhost", "hplussport", "postgres", "password");
        connection = dcm.getConnection();
        positionDao = new PositionDao(connection);
    }

    @After
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void saveTest() {
        Position position = new Position();
        position.setTicker("AAPL");
        position.setNumOfShares(100);
        position.setValuePaid(15000.0);

        Position savedPosition = positionDao.save(position);

        assertEquals(position, savedPosition);
    }

    @Test
    public void findByIdTest() {
        String ticker = "AAPL";

        Optional<Position> optionalPosition = positionDao.findById(ticker);

        assertTrue(optionalPosition.isPresent());
        assertEquals(ticker, optionalPosition.get().getTicker());
    }

    @Test
    public void findAllTest() {
        List<Position> positions = (List<Position>) positionDao.findAll();

        assertNotNull(positions);
        assertFalse(positions.isEmpty());
    }

    @Test
    public void deleteByIdTest() {
        String ticker = "AAPL";

        positionDao.deleteById(ticker);

        assertFalse(positionDao.findById(ticker).isPresent());
    }

    @Test
    public void deleteAllTest() {
        positionDao.deleteAll();

        assertTrue(((List<Position>) positionDao.findAll()).isEmpty());
    }
}