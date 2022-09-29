package view.components;

import controller.MenuController;
import utils.MenuManager;
import utils.Menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class MainMenuPanel extends JPanel implements MenuManager, ActionListener {

    private final MenuController menuController;

    private final JButton connexionButton;
    private final JButton settingsButton;
    private final JButton infoButton;
    private final JButton exitButton;

    public MainMenuPanel(MenuController menuController){

        this.menuController = menuController;

        // Panel
        JPanel buttonPanel = new JPanel();

        // Layout
        this.setLayout(new BorderLayout());
        buttonPanel.setLayout(new GridBagLayout());

        // Background
        buttonPanel.setOpaque(true);
        buttonPanel.setBackground(Color.blue);

        // Elements

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;

        Icon play = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/Play.png")));
        Icon settings = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/Settings.png")));
        Icon howToPlay = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/How.png")));
        Icon exit = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/Exit.png")));

        connexionButton = new JButton(play);
        connexionButton.setBorderPainted(false);
        connexionButton.setFocusPainted(false);
        connexionButton.setContentAreaFilled(false);
        connexionButton.addActionListener(this);

        settingsButton = new JButton(settings);
        settingsButton.setBorderPainted(false);
        settingsButton.setFocusPainted(false);
        settingsButton.setContentAreaFilled(false);
        settingsButton.addActionListener(this);

        infoButton = new JButton(howToPlay);
        infoButton.setBorderPainted(false);
        infoButton.setFocusPainted(false);
        infoButton.setContentAreaFilled(false);
        infoButton.addActionListener(this);

        exitButton = new JButton(exit);
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.addActionListener(this);

        // Add
        buttonPanel.add(connexionButton, gridBagConstraints);
        buttonPanel.add(settingsButton, gridBagConstraints);
        buttonPanel.add(infoButton, gridBagConstraints);
        buttonPanel.add(exitButton, gridBagConstraints);

        this.add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(connexionButton)){
            menuController.changeMenu(Menus.CONNEXION);

        } else if(actionEvent.getSource().equals(settingsButton)){
            menuController.changeMenu(Menus.SETTINGS);

        } else if(actionEvent.getSource().equals(infoButton)){
            menuController.changeMenu(Menus.INFOS);

        } else if(actionEvent.getSource().equals(exitButton)){
            menuController.exitGame();
        }
    }

    @Override
    public Menus getMenu() {
        return Menus.MAINMENU;
    }

}