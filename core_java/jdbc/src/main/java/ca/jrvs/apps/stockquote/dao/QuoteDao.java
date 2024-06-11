package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.jdbc.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuoteDao implements CrudDao<Quote, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PositionDao.class);


    private Connection c;

    private static final String INSERT = "INSERT into quote (symbol, open, high, low, price, volume, latest_trading_day, previous_close, change, change_percent, timestamp) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private static final String GET_ONE = "SELECT * FROM quote WHERE symbol = ?";
    private static final String GET_ALL = "SELECT * FROM quote";
    private static final String UPDATE = "UPDATE quote SET symbol = ?, open = ?, high = ?, low = ?, price = ?, volume= ?, latest_trading_day= ?, previous_close= ?, change= ?, change_percent= ?, timestamp= ? WHERE symbol = ?";
    private static final String DELETE = "DELETE FROM quote WHERE symbol = ?";
    private static final String DELETE_ALL = "DELETE FROM quote";

    public QuoteDao(Connection connection) {
        this.c = connection;
    }
    @Override
    public Quote save(Quote entity) throws IllegalArgumentException {
        if(findById(entity.getTicker()).isEmpty()){
            try(PreparedStatement statement = this.c.prepareStatement(INSERT);){
                statement.setString(1, entity.getTicker());
                statement.setDouble(2, entity.getOpen());
                statement.setDouble(3, entity.getHigh());
                statement.setDouble(4, entity.getLow());
                statement.setDouble(5, entity.getPrice());
                statement.setInt(6, entity.getVolume());
                statement.setDate(7, entity.getLatestTradingDay());
                statement.setDouble(8, entity.getPreviousClose());
                statement.setDouble(9, entity.getChange());
                statement.setString(10, entity.getChangePercent());
                statement.setTimestamp(11, entity.getTimestamp());

                statement.execute();
            }catch(SQLException e){
                LOGGER.error("Error occurred while updating quote with symbol: {}", entity.getTicker(), e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        else{
            try(PreparedStatement insertStatement = this.c.prepareStatement(UPDATE);){
                insertStatement.setString(1, entity.getTicker());
                insertStatement.setDouble(2, entity.getOpen());
                insertStatement.setDouble(3, entity.getHigh());
                insertStatement.setDouble(4, entity.getLow());
                insertStatement.setDouble(5, entity.getPrice());
                insertStatement.setInt(6, entity.getVolume());
                insertStatement.setDate(7, entity.getLatestTradingDay());
                insertStatement.setDouble(8, entity.getPreviousClose());
                insertStatement.setDouble(9, entity.getChange());
                insertStatement.setString(10, entity.getChangePercent());
                insertStatement.setTimestamp(11, entity.getTimestamp());

                insertStatement.execute();
            }catch(SQLException e){
                LOGGER.error("Error occurred while inserting quote with symbol: {}", entity.getTicker(), e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return entity;

    }

    @Override
    public Optional<Quote> findById(String id) throws IllegalArgumentException {
        Quote quote = new Quote();
        try(PreparedStatement statement = this.c.prepareStatement(GET_ONE)){
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                quote.setTicker(rs.getString("symbol"));
                quote.setOpen(rs.getDouble("open"));
                quote.setHigh(rs.getDouble("high"));
                quote.setLow(rs.getDouble("low"));
                quote.setPrice(rs.getDouble("price"));
                quote.setVolume(rs.getInt("volume"));
                quote.setLatestTradingDay(rs.getDate("latest_trading_day"));
                quote.setPreviousClose(rs.getDouble("previous_close"));
                quote.setChange(rs.getInt("change"));
                quote.setChangePercent(rs.getString("change_percent"));
                quote.setTimestamp(rs.getTimestamp("timestamp"));
            }
        }catch(SQLException e){
            LOGGER.error("Error occurred while finding quote with symbol: {}", id, e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return Optional.of(quote);
    }

    @Override
    public List<Quote> findAll() {
        List<Quote> quotes = new ArrayList<>();
        try (PreparedStatement statement = this.c.prepareStatement(GET_ALL)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Quote quote = new Quote();
                quote.setTicker(rs.getString("symbol"));
                quote.setOpen(rs.getDouble("open"));
                quote.setHigh(rs.getDouble("high"));
                quote.setLow(rs.getDouble("low"));
                quote.setPrice(rs.getDouble("price"));
                quote.setVolume(rs.getInt("volume"));
                quote.setLatestTradingDay(rs.getDate("latest_trading_day"));
                quote.setPreviousClose(rs.getDouble("previous_close"));
                quote.setChange(rs.getInt("change"));
                quote.setChangePercent(rs.getString("change_percent"));
                quote.setTimestamp(rs.getTimestamp("timestamp"));
                quotes.add(quote);
            }
        } catch (SQLException e) {
            LOGGER.error("Error occurred while finding all quotes", e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return quotes;
    }

    @Override
    public void deleteById(String id) throws IllegalArgumentException {
        try(PreparedStatement statement = this.c.prepareStatement(DELETE);){
            statement.setString(1, id);
            statement.execute();
        }catch(SQLException e){
            LOGGER.error("Error occurred while deleting quote with symbol: {}", id, e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        try(PreparedStatement statement = this.c.prepareStatement(DELETE_ALL);){
            statement.execute();
        }catch(SQLException e){
            LOGGER.error("Error occurred while deleting all quotes", e);

            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
