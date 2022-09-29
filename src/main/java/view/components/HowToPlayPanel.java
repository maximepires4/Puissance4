package view.components;

import controller.MenuController;
import utils.MenuManager;
import utils.Menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * Panel expliquant comment jouer
 */
public class HowToPlayPanel extends JPanel implements MenuManager, ActionListener {

    private final MenuController menuController;
    private final JButton returnButton;


    public HowToPlayPanel(MenuController menuController) {
        this.menuController = menuController;

        JPanel infoPanel = new JPanel();

        // Background

        infoPanel.setOpaque(true);
        infoPanel.setBackground(Color.blue);

        // Layout

        this.setLayout(new GridBagLayout());
        infoPanel.setLayout(new GridLayout(0, 1));


        // Border

        infoPanel.setBorder(BorderFactory.createTitledBorder("<html><font face='arial' size='8' color='orange'>How to play</font></html>"));


        // Elements

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;

        JLabel title = new JLabel("<html><font face='arial' size='8' color='orange'>How To Play</font></html>");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);

        JTextArea text = new JTextArea("Rules: \n" +
                "\n" +
                "The Puissance 4 rules are very simple. It’s always played with 2 players and a grid. Each turn each player puts a piece of his color, red or yellow, inside a column and it will fall until it reaches the lowest available spot. The one who can put 4 pieces of the same color in a row horizontally, vertically or diagonally wins. If no one manages to do it, then the match ends in a draw.\n" +
                "\n" +
                "Connection: \n" +
                "\n" +
                "Each player must connect himself to play. First, he has to create an account, fill the “New User” part and click on sign up. Then, he can connect with the sign in button.\n" +
                "\n" +
                "Players: \n" +
                "\n" +
                "It’s possible to choose to play against another player or against an IA. \n" +
                "For the IA, click on “Launch IA” and choose the IA level and the style you would like to play with.\n" +
                "The styles available are the following one: the Turtle Style, the Aggressive Style, the Stalker Style and finally the Kamikaze Style.\n" +
                "\n" +
                "Good luck!\n" +
                "\n" +
                "\n",10, 40);
        text.setFont(new Font("Serif", Font.ITALIC, 16));
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setForeground(Color.white);
        text.setOpaque(false);
        text.setEditable(false);
        this.add(text);

        Icon returnToMainMenu= new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/RTMM.png")));

        returnButton = new JButton(returnToMainMenu);
        returnButton.setBorderPainted(false);
        returnButton.setFocusPainted(false);
        returnButton.setContentAreaFilled(false);
        returnButton.addActionListener(this);

        this.add(returnButton, gridBagConstraints);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(returnButton)) {
                menuController.changeMenu(Menus.MAINMENU);
        }
    }


    @Override
    public Menus getMenu() {
        return Menus.INFOS;
    }
}
