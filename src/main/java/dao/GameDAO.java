package dao;

import entity.CPULevel;
import entity.Game;
import entity.Playable;

import java.util.List;

public interface GameDAO {

    void add(Game game);

    int getNextId();

    List<Game> search(int idPlayer1, int idPlayer2, String dateTimeStart);

    List<Game> searchByIdPlayer(int idPlayer1, int idPlayer2);

    int searchEntityGame(Playable playable, boolean focusVictory);
}

