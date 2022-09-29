package view;

import controller.GameController;
import controller.MenuController;
import controller.StatisticsController;
import model.GameModel;
import utils.MenuManager;
import utils.Menus;
import view.components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;


public class View extends JFrame implements WindowListener, Observer {

    private final java.util.List<JPanel> panels;

    private final MenuController menuController;

    public View(MenuController menuController, GameController gameController, StatisticsController statisticsController, int width, int height) {
        this.menuController = menuController;

        this.setTitle("Puissance 4");

        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(true);
        mainPanel.setBackground(Color.BLUE);

        this.setLayout(new BorderLayout());
        mainPanel.setLayout(new CardLayout());

        // Chaque menus du jeu est divisé dans des JPanel customisés différent
        // Au début du programme chaque différents menus sont créés
        // Quand c'est à leur tour d'être affiché, ils seront mis à jour et rendu visible

        MainMenuPanel mainMenuPanel = new MainMenuPanel(menuController);
        SettingsPanel settingsPanel = new SettingsPanel(menuController);
        ConnexionPanel connexionPanel = new ConnexionPanel(menuController);
        HowToPlayPanel howToPlayPanel = new HowToPlayPanel(menuController);
        ProfilePanel profilePanel = new ProfilePanel(menuController);
        LaunchIAPanel launchIAPanel = new LaunchIAPanel(menuController);
        GamePanel gamePanel = new GamePanel(gameController);
        StatisticsPanel statisticsPanel = new StatisticsPanel(statisticsController);

        panels = new ArrayList<>(); // On utilise une List de JPanel pour pouvoir les mettre à jour

        panels.add(mainMenuPanel);
        panels.add(settingsPanel);
        panels.add(connexionPanel);
        panels.add(howToPlayPanel);
        panels.add(profilePanel);
        panels.add(launchIAPanel);
        panels.add(gamePanel);
        panels.add(statisticsPanel);

        panels.forEach((p) -> {
            if (((MenuManager) p).getMenu() != Menus.MAINMENU) p.setVisible(false); // Seul le menu principal est visible dès le début
            p.setOpaque(true);
            p.setBackground(Color.BLUE);
            mainPanel.add(p); // On ajoute tout les panels à notre fenetre
        });

        // Logo

        Icon titleIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/Title.png")));

        JLabel titleLabel = new JLabel(titleIcon);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.blue);

        // Add

        this.add(titleLabel, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);

        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        GameModel model = (GameModel) o;
        Menus currentMenu = model.getCurrentMenu(); // On récupère le menu censé être affiché

        // On teste chaque panel
        panels.forEach((p) -> {
            p.setVisible(currentMenu == ((MenuManager) p).getMenu()); // On affiche le bon panel

            // Certains panels ont besoin d'être mis à jour
            if (((MenuManager) p).getMenu() == currentMenu) {
                switch (currentMenu) {
                    case PROFILE:
                        ((ProfilePanel) p).updatePanel(model.getCurrentPlayer());
                        break;
                    case GAME:
                        ((GamePanel) p).updatePanel(model.getCurrentPlacement().clone(), model.getGrid(), model.getPlayable(), model.getCurrentPlayable(), model.isGameFinished());
                        break;
                    case SETTINGS:
                        ((SettingsPanel) p).updatePanel();
                        break;
                    case STATISTICS:
                        ((StatisticsPanel) p).updatePanel();
                        break;
                }
            }
        });
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        menuController.exitGame();
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }
}

