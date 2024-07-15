package ca.jrvs.apps.jdbc.stockquote;

import ca.jrvs.apps.stockquote.dao.Position;
import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.PositionService;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PositionService_UnitTest {

    private PositionService positionService;
    private PositionDao mockDao;

    @Before
    public void setUp() {
        mockDao = mock(PositionDao.class);
        positionService = new PositionService(mockDao);
    }

    @Test
    public void buy_NewPosition_Success() {
        when(mockDao.findById(any())).thenReturn(Optional.empty());
        when(mockDao.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Position> result = positionService.buy("AAPL", 100, 150.0);

        assertEquals("AAPL", result.get().getTicker());
        assertEquals(100, result.get().getNumOfShares());
        assertEquals(150.0 * 100, result.get().getValuePaid(), 0.001);
    }

    @Test
    public void buy_ExistingPosition_Success() {
        Position existingPosition = new Position();
        existingPosition.setTicker("AAPL");
        existingPosition.setNumOfShares(50);
        existingPosition.setValuePaid(7500.0);
        when(mockDao.findById("AAPL")).thenReturn(Optional.of(existingPosition));
        when(mockDao.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Position> result = positionService.buy("AAPL", 100, 150.0);

        assertEquals("AAPL", result.get().getTicker());
        assertEquals(150, result.get().getNumOfShares());
        assertEquals(22500.0, result.get().getValuePaid(), 0.001);

    }

    @Test
    public void sell_PositionExists_Success() {
        Position existingPosition = new Position();
        existingPosition.setTicker("AAPL");
        existingPosition.setNumOfShares(100);
        existingPosition.setValuePaid(15000.0);
        when(mockDao.findById("AAPL")).thenReturn(Optional.of(existingPosition));

        positionService.sell("AAPL");

    }


}