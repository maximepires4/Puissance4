package dao;


import entity.CPULevel;
import entity.Game;
import entity.Playable;
import entity.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GameDAOImpl implements GameDAO {

    private Connection connection;

    public GameDAOImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void add(Game game) {
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("INSERT INTO Game values (0," + (game.getIdPlayer1() == -1 ? null : game.getIdPlayer1()) + "," + (game.getIdPlayer2() == -1 ? null : game.getIdPlayer2()) + "," + (game.getIdCpuLevel() == -1 ? null : game.getIdCpuLevel()) + "," + game.getGridSizeX() + "," + game.getGridSizeY() + "," + game.getResult() + ",'" + game.getDateTimeStart() +"',"+ game.getDuration() +")");

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getNextId() {
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT max(id) FROM Game");

            resultSet.next();
            return resultSet.getInt("max(id)") + 1;

        } catch (SQLException e){
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public List<Game> search(int idPlayer1, int idPlayer2, String dateTimeStart) {

        List<Game> games = new ArrayList<>();

        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Game WHERE id_player_1 = '" + idPlayer1 + "' AND id_player_2 = '" + idPlayer2 + "' AND date_time_start = '"+ dateTimeStart + "'");

            while (resultSet.next()){
                games.add(new Game(resultSet.getInt("id"), resultSet.getInt("player_1_id"), resultSet.getInt("player_2_id"), resultSet.getInt("cpu_level_id"),resultSet.getInt("grid_size_x"), resultSet.getInt("grid_sixe_y"),resultSet.getInt("result"),resultSet.getTimestamp("datetime_start"),resultSet.getInt("duration")));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }


    public List<Game> searchByIdPlayer(int idPlayer1, int idPlayer2) {

        List<Game> games = new ArrayList<>();

        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Game WHERE id_player_1 = '" + idPlayer1 + "' AND id_player_2 = '" + idPlayer2 + "'");

            while (resultSet.next()){
                games.add(new Game(resultSet.getInt("id"), resultSet.getInt("id_player_1"), resultSet.getInt("id_player_2"), resultSet.getInt("id_cpu_level"),resultSet.getInt("grid_size_x"), resultSet.getInt("grid_sixe_y"),resultSet.getInt("result"),resultSet.getTimestamp("date_time_start"),resultSet.getInt("duration")));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int searchEntityGame(Playable playable, boolean focusVictory) {
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = null;
            if(playable instanceof Player){
                Player p = (Player)playable;
                resultSet = statement.executeQuery("SELECT count(id) FROM Game WHERE (player_1_id = " + p.getId() + " OR player_2_id = " + p.getId() + ")" + (focusVictory ? " AND result = " + p.getId() : ""));
            } else if(playable instanceof CPULevel){
                CPULevel c = (CPULevel) playable;
                resultSet = statement.executeQuery("SELECT count(id) FROM Game WHERE cpu_level_id = " + c.getId() + (focusVictory ? " AND result = " + -c.getId() : ""));
            }

            if(resultSet == null) return 0;

            while (resultSet.next()){
                return resultSet.getInt("count(id)");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return 0;
    }


}
