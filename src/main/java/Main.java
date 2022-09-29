import controller.GameController;
import controller.MenuController;
import controller.StatisticsController;
import model.GameModel;
import utils.Settings;
import view.View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        String databaseUrl = args[0] + "aaaa";
        String username = args[1];
        String password = args[2];

        Connection connection = null;

        // Connection à la base de donnée
        try {
            connection = DriverManager.getConnection(databaseUrl, username, password);
            System.out.println("Connected");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Pattern MVC

        GameModel gameModel = new GameModel();

        MenuController menuController = new MenuController(gameModel, connection);
        GameController gameController = new GameController(gameModel, connection);
        StatisticsController statisticsController = new StatisticsController(gameModel, connection);

        View view = new View(menuController, gameController, statisticsController, Settings.getInstance().getWidth(), Settings.getInstance().getHeight());

        gameModel.addObserver(view);
    }
}

