package controller;

import dao.*;
import entity.CPULevel;
import entity.Player;
import model.GameModel;
import utils.DialogMessage;
import utils.Menus;
import utils.Permissions;
import utils.Settings;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MenuController {
    private final GameModel gameModel;
    private final Connection connection;

    public MenuController(GameModel gameModel, Connection connection) {
        this.gameModel = gameModel;
        this.connection = connection;
    }

    public DialogMessage launchGame(boolean cpu){
        int id = 0;
        if(connection != null){
            GameDAO gameDAO = new GameDAOImpl(connection);
            id = gameDAO.getNextId();

            if(id == -1){
                return DialogMessage.ERROR;
            }
        }

        if((!cpu && !gameModel.playersConnected())){
            return DialogMessage.LAUNCH_ERROR;
        }

        if(!cpu) gameModel.setPlayersPlayable();

        gameModel.initGame(id);

        return null;
    }

    public DialogMessage launchGame(CPULevel cpuLevel) {
        gameModel.addPlayable(cpuLevel);
        return launchGame(true);
    }

    public void changeMenu(Menus menus) {
        gameModel.setCurrentPanel(menus);
    }

    public void changeMenu(int playerIndex) {
        gameModel.setCurrentPanel(Menus.PROFILE, playerIndex);
    }

    public void exitGame() {
        Settings.getInstance().saveSettings();
        System.exit(0);
    }

    public DialogMessage createNewPlayer(String username, String password, String email, Permissions permission) {
        if(connection == null){
            return DialogMessage.NO_CONNECTION;
        }

        if(username.equals("") || password.equals("") || email.equals("")){
            return DialogMessage.FIELDS_BLANK;
        }

        PlayerDAO playerDAO = new PlayerDAOImpl(connection);

        List<Player> players = playerDAO.searchByUsernameAndEmail(username, email);

        if(players.isEmpty()){
            Player player = playerDAO.add(username, email, password, permission);
            if(player != null){
                gameModel.playerAdded();
                return DialogMessage.NEW_USER_SUCCESS;
            } else {
                return DialogMessage.NEW_USER_FAIL;
            }
        } else {
            for(Player p : players){
                if(p.getUsername().equals(username)){
                    return DialogMessage.USERNAME_TAKEN;
                } else if(p.getEmail().equals(email)){
                    return DialogMessage.EMAIL_TAKEN;
                }
            }

            return DialogMessage.ERROR;
        }
    }

    public DialogMessage connectPlayer(int playerIndex, String username, String password) {
        Player player;

        if(connection == null){
            player = new Player(playerIndex, "Anonymous", "", "", Permissions.BASIC);
        } else {
            PlayerDAO playerDAO = new PlayerDAOImpl(connection);
            List<Player> players = playerDAO.search(username, password);

            if(players.isEmpty()){
                return DialogMessage.SIGN_IN_FAIL;
            }

            player = players.get(0); // Normalement il n'y a pas de doublons
        }

        if(player == null){
            return DialogMessage.SIGN_IN_FAIL;
        } else if(gameModel.playersContains(player)){
            return DialogMessage.SIGN_IN_ALREADY;
        }

        gameModel.setPlayer(playerIndex, player);

        return connection == null ? DialogMessage.NO_CONNECTION : DialogMessage.SIGN_IN_SUCCESS;
    }

    public void disconnectPlayer(int playerIndex) {
        gameModel.setPlayer(playerIndex, null);
    }

    public DialogMessage updatePlayer(int id, String username, String email, String password, Permissions permissions) {
        if(connection == null){
            return DialogMessage.NO_CONNECTION;
        }

        PlayerDAO playerDAO = new PlayerDAOImpl(connection);

        List<Player> players = playerDAO.searchExceptId(id,username,email);

        if(players.isEmpty()){
            if(playerDAO.update(id, username, email, password, permissions)) {
                gameModel.updatePlayer(id, username, email, password, permissions);
                return DialogMessage.UPDATE_SUCCESS;
            } else {
                return DialogMessage.ERROR;
            }
        } else {
            for(Player p : players){
                if(p.getUsername().equals(username)){
                    return DialogMessage.USERNAME_TAKEN;
                } else if(p.getEmail().equals(email)){
                    return DialogMessage.EMAIL_TAKEN;
                }
            }

            return DialogMessage.ERROR;
        }
    }

    public List<Player> getDatabasePlayers() {
        PlayerDAO playerDAO = new PlayerDAOImpl(connection);

        return playerDAO.fullList();
    }

    public DialogMessage removePlayer(Player player) {
        PlayerDAO playerDAO = new PlayerDAOImpl(connection);

        if(gameModel.playersContains(player)){
            return DialogMessage.REMOVE_CONNECTED;
        }

        playerDAO.remove(player.getId());

        return DialogMessage.REMOVE_SUCCESS;
    }

    public void addPlayable(int playerIndex) {
        gameModel.addPlayable(gameModel.getPlayer(playerIndex));
    }

    public List<CPULevel> getCpuLevels() {
        if(connection == null){
            return CPULevel.getDefaultCpuLevels();
        }

        CPULevelDAO cpuLevelDAO = new CPULevelDAOImpl(connection);

        return cpuLevelDAO.searchAll();
    }
}
