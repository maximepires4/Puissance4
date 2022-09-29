package dao;

import entity.Player;
import utils.Permissions;

import java.util.List;

public interface PlayerDAO {
    Player add(String username, String email, String password, Permissions permission);

    void remove(int id);

    List<Player> search(String username, String password);

    List<Player> searchByUsernameAndEmail(String username, String email);

    List<Player> fullList();

    List<Player> searchExceptId(int id, String username, String email);

    boolean update(int id, String username, String email, String password, Permissions permission);
}
