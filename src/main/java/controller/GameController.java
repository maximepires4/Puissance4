package controller;

import dao.*;
import entity.Turn;
import model.GameModel;
import utils.DialogMessage;
import utils.GameState;
import utils.Menus;

import java.sql.Connection;

public class GameController {
    private final GameModel gameModel;

    private final Connection connection;

    public GameController(GameModel gameModel, Connection connection) {
        this.gameModel = gameModel;
        this.connection = connection;
    }

    public void moveLeft(){
        gameModel.moveCurrentPlacement(-1);
    }

    public void moveRight(){
        gameModel.moveCurrentPlacement(1);
    }

    public DialogMessage endTurn(){
        GameState state = gameModel.computeTurn();

        if(state != GameState.NONE){
            if(connection != null){
                GameDAO gameDAO = new GameDAOImpl(connection);
                gameDAO.add(gameModel.getGame());

                TurnDAO turnDAO = new TurnDAOImpl(connection);
                for(Turn t : gameModel.getTurnList()){
                    turnDAO.add(t);
                }
            }

            switch (state){

                case DRAW :
                    return DialogMessage.DRAW;
                case PLAYER1 :
                case PLAYER2 :
                    return DialogMessage.PLAYER;
            }
        }

        return null;
    }

    public Menus getMenu() {
        return gameModel.getCurrentMenu();
    }

    public void changeMenu(Menus menu) {
        gameModel.setCurrentPanel(menu);
    }
}
