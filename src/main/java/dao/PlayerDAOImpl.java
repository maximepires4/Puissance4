package dao;

import entity.Player;
import utils.Permissions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAOImpl implements PlayerDAO {

    private final Connection connection;

    public PlayerDAOImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public Player add(String username, String email, String password, Permissions permission) {
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("INSERT INTO Player values (0,'" + username + "','" + email + "','" + password + "'," + permission.ordinal() + ")");

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Player WHERE username = '" + username + "' AND email = '" + email + "' AND password = '" + password + "'");
            resultSet.next();

            return new Player(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("email"), resultSet.getString("password"), Permissions.values()[resultSet.getInt("permission")]);
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void remove(int id) {
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("DELETE FROM Player WHERE id =" + id);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Player> search(String username, String password) {
        List<Player> players = new ArrayList<>();

        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Player WHERE username = '" + username + "' AND password = '" + password + "'");

            while (resultSet.next()){
                players.add(new Player(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("email"), resultSet.getString("password"), Permissions.values()[resultSet.getInt("permission")]));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return players;
    }

    @Override
    public List<Player> searchByUsernameAndEmail(String username, String email) {
        List<Player> players = new ArrayList<>();

        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Player WHERE username = '" + username + "' OR email = '" + email + "'");

            while (resultSet.next()){
                players.add(new Player(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("email"), resultSet.getString("password"), Permissions.values()[resultSet.getInt("permission")]));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return players;
    }

    @Override
    public List<Player> fullList() {
        List<Player> players = new ArrayList<>();

        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Player");

            while (resultSet.next()){
                players.add(new Player(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("email"), resultSet.getString("password"), Permissions.values()[resultSet.getInt("permission")]));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return players;
    }

    @Override
    public List<Player> searchExceptId(int id, String username, String email){
        List<Player> players = new ArrayList<>();

        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Player WHERE (username = '" + username + "' OR email = '" + email + "') AND id !=" + id);

            while (resultSet.next()){
                players.add(new Player(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("email"), resultSet.getString("password"), Permissions.values()[resultSet.getInt("permission")]));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return players;
    }

    @Override
    public boolean update(int id, String username, String email, String password, Permissions permission) {
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate("UPDATE Player SET username = '" + username + "', email = '" + email + "', password = '" + password + "', permission = " + permission.ordinal() + " WHERE id = " + id);
            return true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
