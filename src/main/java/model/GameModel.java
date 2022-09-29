package model;

import entity.*;
import utils.GameState;
import utils.Menus;
import utils.Permissions;
import utils.Settings;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class GameModel extends Observable {
    private final Player[] players;

    private final int[] currentPlacement = new int[2];
    private int currentPlayer;

    private Menus currentMenu;

    private Game game;
    private boolean gameFinished = true;

    private List<Turn> turnList;

    private Grid grid;
    private final Playable[] playable = new Playable[2];
    private Playable currentPlayable;

    public GameModel() {
        players = new Player[2];
    }

    public void initGame(int idGame) {
        if(playable[0] instanceof CPULevel){
            CPULevel c = (CPULevel)playable[0];
            game = new Game(idGame, -1, ((Player) playable[1]).getId(), c.getId(), Settings.getInstance().getColumns(), Settings.getInstance().getRows(), new Timestamp(System.currentTimeMillis()));
        } else if(playable[1] instanceof CPULevel){
            CPULevel c = (CPULevel)playable[1];
            game = new Game(idGame, ((Player) playable[0]).getId(), -1, c.getId(), Settings.getInstance().getColumns(), Settings.getInstance().getRows(), new Timestamp(System.currentTimeMillis()));
        } else {
            game = new Game(idGame, ((Player) playable[0]).getId(), ((Player) playable[1]).getId(), -1, Settings.getInstance().getColumns(), Settings.getInstance().getRows(), new Timestamp(System.currentTimeMillis()));
        }
        gameFinished = false;
        turnList = new ArrayList<>();
        grid = new Grid(Settings.getInstance().getColumns(), Settings.getInstance().getRows());
        currentPlacement[0] = 0;
        currentPlacement[1] = Settings.getInstance().getRows() - 1;
        playable[0].setTokenValueInGrid(Grid.PLAYER1);
        playable[1].setTokenValueInGrid(Grid.PLAYER2);
        currentPlayable = playable[(int) Math.round(Math.random())];

        setCurrentPanel(Menus.GAME);

        if(currentPlayable instanceof CPULevel){
            computeTurn();
        }
    }

    public void addPlayable(Playable playable) {
        if (this.playable[0] == null) {
            this.playable[0] = playable;
        } else {
            this.playable[1] = playable;
        }
    }

    public Player getPlayer(int playerIndex) {
        return players[playerIndex];
    }

    public void moveCurrentPlacement(int direction) {

        if (grid.isGridFull()) {
            currentPlacement[0] = -1;
            currentPlacement[1] = -1;
        } else {
            do {
                moveCurrentColumnPlacement(direction);
                if(direction == 0) direction = 1;
            } while (!findRowPlacement());
        }

        setChanged();
        notifyObservers(this);
    }

    private void moveCurrentColumnPlacement(int direction) {
        currentPlacement[0] += direction;
        if (currentPlacement[0] >= Settings.getInstance().getColumns()) {
            currentPlacement[0] = 0;
        } else if (currentPlacement[0] < 0) {
            currentPlacement[0] = Settings.getInstance().getColumns() - 1;
        }
    }

    private boolean findRowPlacement() {
        for (int i = Settings.getInstance().getRows() - 1; i >= 0; i--) {
            if (grid.getValues()[currentPlacement[0]][i] == Grid.EMPTY) {
                currentPlacement[1] = i;
                return true;
            }
        }

        return false;
    }

    public GameState computeTurn() {
        // TODO: améliorer
        // bizarre, censé tester l'égalité à chaque tour
        // donc pas censé arriver
        if(!placeToken()) {
            endGame(null);
            return GameState.DRAW;
        }

        int winner = grid.checkForWin();

        if(winner == -1 && grid.isGridFull()){
            endGame(null);
            setChanged();
            notifyObservers(this);
            return GameState.DRAW;
        } else if(winner != -1){
            endGame(currentPlayable);
            setChanged();
            notifyObservers(this);
            return winner == 0 ? GameState.PLAYER1 : GameState.PLAYER2;
        } else {
            changePlayable();
            setChanged();
            notifyObservers(this);
            return GameState.NONE;
        }
    }

    private boolean placeToken() {
        if (currentPlacement[0] != -1 && currentPlacement[1] != -1) {
            if (currentPlayable.placeToken(grid, currentPlacement[0], currentPlacement[1])) {

                if (currentPlayable instanceof Player) {
                    Player p = (Player)currentPlayable;
                    turnList.add(new Turn(game.getId(), (turnList.isEmpty() ? 1 : turnList.get(turnList.size() - 1).getTurn() + 1), p.getId(), -1, currentPlacement[0] + 1, Settings.getInstance().getRows() - currentPlacement[1]));
                } else if(currentPlayable instanceof CPULevel){
                    CPULevel c = (CPULevel) currentPlayable;
                    turnList.add(new Turn(game.getId(), (turnList.isEmpty() ? 1 : turnList.get(turnList.size() - 1).getTurn() + 1), -1, c.getId(), currentPlacement[0] + 1, Settings.getInstance().getRows() - currentPlacement[1]));
                }

                if (!findRowPlacement()) moveCurrentPlacement(0);

                return true;
            }
        }

        return false;
    }

    private void changePlayable() {
        if(currentPlayable.compareTo(playable[0]) == 0){
            currentPlayable = playable[1];
        } else {
            currentPlayable = playable[0];
        }

        setChanged();
        notifyObservers(this);
    }

    private void endGame(Playable winner) {
        if(winner == null){
            game.setResult(0);
        } else if(winner instanceof Player){
            Player p = (Player) winner;
            game.setResult(p.getId());
        } else if(winner instanceof CPULevel){
            CPULevel c = (CPULevel) winner;
            game.setResult(-c.getId());
        }

        game.setDuration((int) (System.currentTimeMillis() - game.getDateTimeStart().getTime()) / 1000);
        gameFinished = true;

        setChanged();
        notifyObservers(this);
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public boolean playersConnected() {
        return players[0] != null && players[1] != null;
    }

    public void setPlayersPlayable() {
        playable[0] = players[0];
        playable[1] = players[1];
    }

    public Grid getGrid() {
        return grid;
    }

    public int[] getCurrentPlacement() {
        return currentPlacement;
    }

    public Playable[] getPlayable() {
        return playable;
    }

    public Playable getCurrentPlayable() {
        return currentPlayable;
    }

    public void setCurrentPanel(Menus currentMenu) {
        this.currentMenu = currentMenu;
        setChanged();
        notifyObservers(this);
    }

    public void setCurrentPanel(Menus currentMenu, int playerIndex) {
        currentPlayer = playerIndex;
        setCurrentPanel(currentMenu);
    }

    public Menus getCurrentMenu() {
        return currentMenu;
    }

    public void setPlayer(int index, Player player) {
        players[index] = player;
        setChanged();
        notifyObservers(this);
    }

    public boolean playersContains(Player player) {
        if (players[0] != null && players[0].compareTo(player) == 0) {
            return true;
        } else return players[1] != null && players[1].compareTo(player) == 0;
    }

    public Player getCurrentPlayer() {
        return players[currentPlayer];
    }

    public void updatePlayer(int id, String username, String email, String password, Permissions permission) {
        for (Player p : players) {
            if (p != null && p.getId() == id) {
                p.setUsername(username);
                p.setEmail(email);
                p.setPassword(password);
                p.setPermission(permission);
            }
        }
    }

    public void playerAdded() {
        setChanged();
        notifyObservers(this);
    }

    public Game getGame() {
        return game;
    }

    public List<Turn> getTurnList() {
        return turnList;
    }
}
