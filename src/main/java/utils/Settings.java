package utils;

import java.awt.*;
import java.io.*;

public class Settings implements Serializable {

    private static Settings instance = null;
    public static final String FILE_NAME = "settings.bin";

    private int width = 1920;
    private int height = 1080;

    private int columns = 7;
    private int rows = 6;

    private int victoryCount = 4;

    private Color player1 = Color.YELLOW;
    private Color player2 = Color.RED;

    public static Settings getInstance() {
        if(instance == null){
            Settings.readSettings();
        }

        return instance;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public Color[] getColors() {
        return new Color[]{player1, player2};
    }

    public void setPlayer1Color(Color player1) {
        this.player1 = player1;
    }

    public void setPlayer2Color(Color player2) {
        this.player2 = player2;
    }

    public int getVictoryCount() {
        return victoryCount;
    }

    public void setVictoryCount(int victoryCount) {
        this.victoryCount = victoryCount;
    }

    private static void readSettings(){
        try {
            FileInputStream fileIn = new FileInputStream(Settings.FILE_NAME);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            instance = (Settings) objectIn.readObject();
            System.out.println("Settings has been read from the file " + Settings.FILE_NAME);
            objectIn.close();
        } catch (Exception e) {
            System.out.println("An error occurred while trying to read the settings");
            System.out.println("Creating a new settings file ...");

            instance = new Settings();
            instance.saveSettings();
        }
    }

    public void saveSettings(){
        try {
            FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);
            objectOut.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
