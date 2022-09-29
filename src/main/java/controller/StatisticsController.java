package controller;

import dao.*;
import entity.CPULevel;
import entity.Player;
import model.GameModel;
import utils.Menus;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsController {
    private final GameModel gameModel;

    private final Connection connection;

    public StatisticsController(GameModel gameModel, Connection connection){
        this.gameModel = gameModel;
        this.connection = connection;
    }

    public Map<String, Double> getIAsVictories() {
        CPULevelDAO cpuLevelDAO = new CPULevelDAOImpl(connection);

        List<CPULevel> cpuLevelList = cpuLevelDAO.searchAll();

        GameDAO gameDAO = new GameDAOImpl(connection);

        Map<String, Double> victoryCountPerIAs = new HashMap<>();

        int iAGameCount = 0;

        for(CPULevel cpuLevel : cpuLevelList){
            double victoryCount = gameDAO.searchEntityGame(cpuLevel, true);
            iAGameCount += gameDAO.searchEntityGame(cpuLevel, false);
            victoryCountPerIAs.put(cpuLevel.getUsername(), victoryCount);
        }

        for(Double victoryCount : victoryCountPerIAs.values()){
            victoryCount /= iAGameCount;
        }

        return victoryCountPerIAs;
    }

    public List<Player> getPlayers() {
        PlayerDAO playerDAO = new PlayerDAOImpl(connection);

        return playerDAO.fullList();
    }

    public int getPlayerGameCount(Player player) {
        GameDAO gameDAO = new GameDAOImpl(connection);

        return gameDAO.searchEntityGame(player, false);
    }

    public double getPlayerVictoryCount(Player player){
        GameDAO gameDAO = new GameDAOImpl(connection);

        return gameDAO.searchEntityGame(player, true);
    }

    public void changeMenu(){
        gameModel.setCurrentPanel(Menus.PROFILE);
    }
}
