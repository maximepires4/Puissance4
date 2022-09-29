package entity;

public class Turn {
    private final int idGame;
    private final int turn;
    private final int playerId;
    private final int cpuId;
    private final int positionX;
    private final int positionY;

    public Turn(int idGame, int turn, int playerId, int cpuId, int positionX, int positionY) {
        this.idGame = idGame;
        this.turn = turn;
        this.playerId = playerId;
        this.cpuId = cpuId;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getIdGame() {
        return idGame;
    }

    public int getTurn() {
        return turn;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getCpuId() {
        return cpuId;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    @Override
    public String toString() {
        return "Turn{" +
                "idGame=" + idGame +
                ", turn=" + turn +
                ", playerId=" + playerId +
                ", cpuId=" + cpuId +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                '}';
    }
}

