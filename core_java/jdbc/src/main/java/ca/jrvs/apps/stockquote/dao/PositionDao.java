package ca.jrvs.apps.stockquote.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PositionDao implements CrudDao<Position, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PositionDao.class);

    private static final String INSERT = "INSERT into position (symbol, number_of_shares, value_paid) VALUES (?,?,?)";
    private static final String GET_ONE = "SELECT symbol, number_of_shares, value_paid FROM position WHERE symbol = ?";
    private static final String UPDATE = "UPDATE position SET symbol = ?, number_of_shares = ?, value_paid = ? WHERE symbol = ?";
    private static final String DELETE = "DELETE FROM position WHERE symbol = ?";
    private static final String DELETE_ALL = "DELETE FROM position";
    private static final String GET_ALL = "SELECT * FROM position";

    private Connection c;

    public PositionDao(Connection connection) {
        this.c = connection;
    }

    @Override
    public Position save(Position entity) throws IllegalArgumentException {
        if(findById(entity.getTicker()).isEmpty()){
            try(PreparedStatement statement = this.c.prepareStatement(INSERT);){
                statement.setString(1, entity.getTicker());
                statement.setDouble(2, entity.getNumOfShares());
                statement.setDouble(3, entity.getValuePaid());
                statement.execute();
            }catch(SQLException e){
                LOGGER.error("Error saving position entity: {}", entity, e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        else{
            try(PreparedStatement insertStatement = this.c.prepareStatement(UPDATE);){
                insertStatement.setString(1, entity.getTicker());
                insertStatement.setDouble(2, entity.getNumOfShares());
                insertStatement.setDouble(3, entity.getValuePaid());

                insertStatement.execute();
            }catch(SQLException e){
                LOGGER.error("Error updating position entity: {}", entity, e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return entity;
    }

    @Override
    public Optional<Position> findById(String id) throws IllegalArgumentException {
        Position position = new Position();
        try(PreparedStatement statement = this.c.prepareStatement(GET_ONE)){
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                position.setTicker(rs.getString("symbol"));
                position.setNumOfShares(rs.getInt("number_of_shares"));
                position.setValuePaid(rs.getDouble("value_paid"));
            }
        }catch(SQLException e){
            LOGGER.error("Error finding position by id: {}", id, e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return Optional.of(position);
    }

    @Override
    public Iterable<Position> findAll() {
        List<Position> positions = new ArrayList<>();
        try(PreparedStatement statement = this.c.prepareStatement(GET_ALL)){
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Position position = new Position();
                position.setTicker(rs.getString("symbol"));
                position.setNumOfShares(rs.getInt("number_of_shares"));
                position.setValuePaid(rs.getDouble("value_paid"));
                positions.add(position);
            }
        }catch(SQLException e){
            LOGGER.error("Error finding all positions", e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return positions;
    }

    @Override
    public void deleteById(String id) throws IllegalArgumentException {
        try(PreparedStatement statement = this.c.prepareStatement(DELETE);){
            statement.setString(1, id);
            statement.execute();
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        try(PreparedStatement statement = this.c.prepareStatement(DELETE_ALL);){
            statement.execute();
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //implement all inherited methods
    //you are not limited to methods defined in CrudDao

}