package entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Game {
    private int id;
    private int idPlayer1;
    private int idPlayer2;
    private int idCpuLevel;
    private int gridSizeX;
    private int gridSizeY;
    private int result;
    private Timestamp dateTimeStart;
    private int duration;

    public Game(int id, int idPlayer1, int idPlayer2, int idCpuLevel, int gridSizeX, int gridSizeY, int result, Timestamp dateTimeStart, int duration) {
        this.id = id;
        this.idPlayer1 = idPlayer1;
        this.idPlayer2 = idPlayer2;
        this.idCpuLevel = idCpuLevel;
        this.gridSizeX = gridSizeX;
        this.gridSizeY = gridSizeY;
        this.result = result;
        this.dateTimeStart = dateTimeStart;
        this.duration = duration;
    }

    public Game(int id, int idPlayer1, int idPlayer2, int idCpuLevel, int gridSizeX, int gridSizeY, Timestamp dateTimeStart) {
        this.id = id;
        this.idPlayer1 = idPlayer1;
        this.idPlayer2 = idPlayer2;
        this.idCpuLevel = idCpuLevel;
        this.gridSizeX = gridSizeX;
        this.gridSizeY = gridSizeY;
        this.dateTimeStart = dateTimeStart;
    }

    public int getId() {
        return id;
    }

    public int getIdPlayer1() {
        return idPlayer1;
    }

    public int getIdPlayer2() {
        return idPlayer2;
    }

    public int getIdCpuLevel() {
        return idCpuLevel;
    }

    public int getGridSizeX() {
        return gridSizeX;
    }

    public int getGridSizeY() {
        return gridSizeY;
    }

    public int getResult() {
        return result;
    }

    public Timestamp getDateTimeStart() {
        return dateTimeStart;
    }

    public int getDuration() {
        return duration;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", idPlayer1=" + idPlayer1 +
                ", idPlayer2=" + idPlayer2 +
                ", idCpuLevel=" + idCpuLevel +
                ", gridSizeX=" + gridSizeX +
                ", gridSizeY=" + gridSizeY +
                ", result='" + result + '\'' +
                ", dateTimeStart='" + dateTimeStart + '\'' +
                ", duration=" + duration +
                '}';
    }
}




