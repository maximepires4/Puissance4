package dao;


import entity.Turn;

import java.util.List;

public interface TurnDAO{
    void add(Turn turn);
    List<Turn> search(int id_game, int player_id, int turn);
    List<Turn> searchByID(int id_game, int player_id);
    List<Turn> fullList();

}


