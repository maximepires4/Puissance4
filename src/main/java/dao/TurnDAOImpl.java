package dao;

import entity.Turn;

import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class TurnDAOImpl implements TurnDAO{

    private final Connection connection;

    public TurnDAOImpl(Connection connection){
        this.connection = connection;
    }

    public void add(Turn turn) {
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("INSERT INTO Turn values (" + turn.getIdGame() + "," + turn.getTurn() + "," + (turn.getPlayerId() == -1 ? null : turn.getPlayerId()) + "," + (turn.getCpuId() == -1 ? null : turn.getCpuId()) + "," + turn.getPositionX() + "," + turn.getPositionY() + ")");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Turn> searchByID(int id_game, int player_id) {
        List<Turn> turns = new ArrayList<>();

        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Turn WHERE game_id = '" + id_game + "' AND player_id = '" + player_id + "'");

            while (resultSet.next()){
                turns.add(new Turn(resultSet.getInt("game_id"), resultSet.getInt("turn"), resultSet.getInt("player_id"), resultSet.getInt("cpu_id"), resultSet.getInt("turn_X"),resultSet.getInt("turn_Y") ));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return turns;
    }

    @Override
    public List<Turn> search(int id_game, int player_id, int turn) {
        List<Turn> turns = new ArrayList<>();

        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Turn WHERE game_id = '" + id_game + "' AND player_id = '" + player_id +  "' AND turn = '" + turn + "'");

            while (resultSet.next()){
                turns.add(new Turn(resultSet.getInt("game_id"), resultSet.getInt("turn"), resultSet.getInt("player_id"), resultSet.getInt("cpu_id"), resultSet.getInt("turn_X"),resultSet.getInt("turn_Y") ));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return turns;
    }


    @Override
    public List<Turn> fullList() {
        List<Turn> turns = new ArrayList<>();

        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Turn");

            while (resultSet.next()){
                turns.add(new Turn(resultSet.getInt("game_id"), resultSet.getInt("turn"), resultSet.getInt("player_id"), resultSet.getInt("cpu_id"), resultSet.getInt("turn_X"),resultSet.getInt("turn_Y")));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return turns;
    }
}
