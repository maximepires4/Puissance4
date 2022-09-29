package view.components;

import controller.MenuController;
import utils.DialogMessage;
import utils.MenuManager;
import utils.Menus;
import utils.Permissions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * Menu de connexion et de création de joueurs
 * Le joueur pourra aussi depuis ce menu lancer un jeu contre l'autre joueur ou l'ia, ou encore accéder à son profil
 */
public class ConnexionPanel extends JPanel implements MenuManager, ActionListener {

    private final MenuController menuController;

    private final JPanel player1Panel;
    private final JPanel player2Panel;
    private final JPanel player1ProfilePanel;
    private final JPanel player1SignInPanel;
    private final JPanel player2ProfilePanel;
    private final JPanel player2SignInPanel;

    private final JButton signIn1Button;
    private final JButton signIn2Button;
    private final JButton profilePlayer1Button;
    private final JButton profilePlayer2Button;
    private final JButton iaPlayer1Button;
    private final JButton iaPlayer2Button;
    private final JButton disconnectPlayer1Button;
    private final JButton disconnectPlayer2Button;
    private final JButton signUpButton;
    private final JButton returnButton;
    private final JButton playButton;


    private final JTextField usernamePlayer1;
    private final JTextField usernamePlayer2;
    private final JPasswordField passwordPlayer1;
    private final JPasswordField passwordPlayer2;
    private final JTextField usernameNewUser;
    private final JPasswordField passwordNewUser;
    private final JTextField emailNewUser;

    public ConnexionPanel(MenuController menuController) {
        this.menuController = menuController;

        // JPanel

        JPanel globalPanel = new JPanel();
        JPanel playersPanel = new JPanel();
        player1Panel = new JPanel();
        player1ProfilePanel = new JPanel();
        player1SignInPanel = new JPanel();
        JPanel player1SignInInfoPanel = new JPanel();
        player2Panel = new JPanel();
        player2ProfilePanel = new JPanel();
        player2SignInPanel = new JPanel();
        JPanel player2SignInInfoPanel = new JPanel();
        JPanel signUpPanel = new JPanel();
        JPanel signUpInfoPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        // Background

        globalPanel.setOpaque(true);
        globalPanel.setBackground(Color.BLUE);
        playersPanel.setOpaque(true);
        playersPanel.setBackground(Color.BLUE);
        player1Panel.setOpaque(true);
        player1Panel.setBackground(Color.BLUE);
        player1ProfilePanel.setOpaque(true);
        player1ProfilePanel.setBackground(Color.BLUE);
        player1SignInPanel.setOpaque(true);
        player1SignInPanel.setBackground(Color.BLUE);
        player1SignInInfoPanel.setOpaque(true);
        player1SignInInfoPanel.setBackground(Color.BLUE);
        player2Panel.setOpaque(true);
        player2Panel.setBackground(Color.BLUE);
        player2ProfilePanel.setOpaque(true);
        player2ProfilePanel.setBackground(Color.BLUE);
        player2SignInPanel.setOpaque(true);
        player2SignInPanel.setBackground(Color.BLUE);
        player2SignInInfoPanel.setOpaque(true);
        player2SignInInfoPanel.setBackground(Color.BLUE);
        signUpInfoPanel.setOpaque(true);
        signUpInfoPanel.setBackground(Color.BLUE);
        signUpPanel.setOpaque(true);
        signUpPanel.setBackground(Color.BLUE);
        buttonsPanel.setOpaque(true);
        buttonsPanel.setBackground(Color.BLUE);

        // Layout

        this.setLayout(new GridBagLayout());
        globalPanel.setLayout(new GridLayout(0, 1));
        player1ProfilePanel.setLayout(new GridLayout(0, 1));
        player1SignInPanel.setLayout(new BorderLayout());
        player1SignInInfoPanel.setLayout(new GridLayout(3, 2));
        player2ProfilePanel.setLayout(new GridLayout(0, 1));
        player2SignInPanel.setLayout(new BorderLayout());
        player2SignInInfoPanel.setLayout(new GridLayout(3, 2));
        signUpPanel.setLayout(new BorderLayout());
        signUpInfoPanel.setLayout(new GridLayout(4, 2));

        // Border

        signUpPanel.setBorder(BorderFactory.createTitledBorder("<html><font face='arial' size='7' color='orange'>New User</font></html>"));

        // Elements

        Icon signIn = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/SignIn.png")));
        Icon profile= new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/profile.png")));
        Icon playAgainstIA = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/PlayAgainstIA.png")));
        Icon disconnect = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/disconnect.png")));
        Icon signUp = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/SignUp.png")));
        Icon returnToMainMenu= new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/RTMM.png")));
        Icon launchGame = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/launchgame.png")));

        Font policeTitre = new Font("Arial",Font.BOLD,30);
        JLabel title = new JLabel("Connection");
        title.setFont(policeTitre);
        title.setForeground(Color.ORANGE);
        title.setBounds(10,10,150,30);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);

        manageConnectionComponents(0,false);
        manageConnectionComponents(1,false);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;

        usernamePlayer1 = new JTextField();
        usernamePlayer2 = new JTextField();

        passwordPlayer1 = new JPasswordField();
        passwordPlayer2 = new JPasswordField();

        passwordPlayer1.setEchoChar('*');
        passwordPlayer2.setEchoChar('*');

        usernameNewUser = new JTextField();
        passwordNewUser = new JPasswordField();
        emailNewUser = new JTextField();

        passwordNewUser.setEchoChar('*');

        Font police = new Font("Arial",Font.BOLD,17);
        JLabel username1 = new JLabel("Username");
        username1.setFont(police);
        username1.setForeground(Color.ORANGE);
        username1.setHorizontalAlignment(JLabel.CENTER);
        username1.setVerticalAlignment(JLabel.CENTER);

        JLabel username2 = new JLabel("Username");
        username2.setFont(police);
        username2.setForeground(Color.ORANGE);
        username2.setHorizontalAlignment(JLabel.CENTER);
        username2.setVerticalAlignment(JLabel.CENTER);

        JLabel newUsername = new JLabel("Username");
        newUsername.setFont(police);
        newUsername.setForeground(Color.ORANGE);
        newUsername.setHorizontalAlignment(JLabel.CENTER);
        newUsername.setVerticalAlignment(JLabel.CENTER);

        JLabel pass1 = new JLabel("Password");
        pass1.setFont(police);
        pass1.setForeground(Color.ORANGE);
        pass1.setHorizontalAlignment(JLabel.CENTER);
        pass1.setVerticalAlignment(JLabel.CENTER);

        JLabel pass2 = new JLabel("Password");
        pass2.setFont(police);
        pass2.setForeground(Color.ORANGE);
        pass2.setHorizontalAlignment(JLabel.CENTER);
        pass2.setVerticalAlignment(JLabel.CENTER);

        JLabel pass = new JLabel("Password");
        pass.setFont(police);
        pass.setForeground(Color.ORANGE);
        pass.setHorizontalAlignment(JLabel.CENTER);
        pass.setVerticalAlignment(JLabel.CENTER);

        JLabel email = new JLabel("Email");
        email.setFont(police);
        email.setForeground(Color.ORANGE);
        email.setHorizontalAlignment(JLabel.CENTER);
        email.setVerticalAlignment(JLabel.CENTER);

        signIn1Button = new JButton(signIn);
        signIn1Button.setBorderPainted(false);
        signIn1Button.setFocusPainted(false);
        signIn1Button.setContentAreaFilled(false);
        signIn2Button = new JButton(signIn);
        signIn2Button.setBorderPainted(false);
        signIn2Button.setFocusPainted(false);
        signIn2Button.setContentAreaFilled(false);
        profilePlayer1Button = new JButton(profile);
        profilePlayer1Button.setBorderPainted(false);
        profilePlayer1Button.setFocusPainted(false);
        profilePlayer1Button.setContentAreaFilled(false);
        profilePlayer2Button = new JButton(profile);
        profilePlayer2Button.setBorderPainted(false);
        profilePlayer2Button.setFocusPainted(false);
        profilePlayer2Button.setContentAreaFilled(false);
        iaPlayer1Button = new JButton(playAgainstIA);
        iaPlayer1Button.setBorderPainted(false);
        iaPlayer1Button.setFocusPainted(false);
        iaPlayer1Button.setContentAreaFilled(false);
        iaPlayer2Button = new JButton(playAgainstIA);
        iaPlayer2Button.setBorderPainted(false);
        iaPlayer2Button.setFocusPainted(false);
        iaPlayer2Button.setContentAreaFilled(false);
        disconnectPlayer1Button = new JButton(disconnect);
        disconnectPlayer1Button.setBorderPainted(false);
        disconnectPlayer1Button.setFocusPainted(false);
        disconnectPlayer1Button.setContentAreaFilled(false);
        disconnectPlayer2Button = new JButton(disconnect);
        disconnectPlayer2Button.setBorderPainted(false);
        disconnectPlayer2Button.setFocusPainted(false);
        disconnectPlayer2Button.setContentAreaFilled(false);
        signUpButton = new JButton(signUp);
        signUpButton.setBorderPainted(false);
        signUpButton.setFocusPainted(false);
        signUpButton.setContentAreaFilled(false);
        returnButton = new JButton(returnToMainMenu);
        returnButton.setBorderPainted(false);
        returnButton.setFocusPainted(false);
        returnButton.setContentAreaFilled(false);
        playButton = new JButton(launchGame);
        playButton.setBorderPainted(false);
        playButton.setFocusPainted(false);
        playButton.setContentAreaFilled(false);

        signIn1Button.addActionListener(this);
        signIn2Button.addActionListener(this);
        profilePlayer1Button.addActionListener(this);
        profilePlayer2Button.addActionListener(this);
        iaPlayer1Button.addActionListener(this);
        iaPlayer2Button.addActionListener(this);
        disconnectPlayer1Button.addActionListener(this);
        disconnectPlayer2Button.addActionListener(this);
        signUpButton.addActionListener(this);
        returnButton.addActionListener(this);
        playButton.addActionListener(this);

        // Adding elements

        player1SignInInfoPanel.add(username1);
        player1SignInInfoPanel.add(usernamePlayer1);
        player1SignInInfoPanel.add(pass1);
        player1SignInInfoPanel.add(passwordPlayer1);

        player2SignInInfoPanel.add(username2);
        player2SignInInfoPanel.add(usernamePlayer2);
        player2SignInInfoPanel.add(pass2);
        player2SignInInfoPanel.add(passwordPlayer2);

        signUpInfoPanel.add(newUsername);
        signUpInfoPanel.add(usernameNewUser);
        signUpInfoPanel.add(pass);
        signUpInfoPanel.add(passwordNewUser);
        signUpInfoPanel.add(email);
        signUpInfoPanel.add(emailNewUser);

        player1SignInPanel.add(player1SignInInfoPanel, BorderLayout.CENTER);
        player1SignInPanel.add(signIn1Button, BorderLayout.SOUTH);

        player2SignInPanel.add(player2SignInInfoPanel, BorderLayout.CENTER);
        player2SignInPanel.add(signIn2Button, BorderLayout.SOUTH);

        player1ProfilePanel.add(profilePlayer1Button);
        player1ProfilePanel.add(iaPlayer1Button);
        player1ProfilePanel.add(disconnectPlayer1Button);

        player2ProfilePanel.add(profilePlayer2Button);
        player2ProfilePanel.add(iaPlayer2Button);
        player2ProfilePanel.add(disconnectPlayer2Button);

        player1Panel.add(player1SignInPanel);
        player1Panel.add(player1ProfilePanel);

        player2Panel.add(player2SignInPanel);
        player2Panel.add(player2ProfilePanel);

        playersPanel.add(player1Panel);
        playersPanel.add(player2Panel);

        signUpPanel.add(signUpInfoPanel, BorderLayout.CENTER);
        signUpPanel.add(signUpButton, BorderLayout.SOUTH);

        globalPanel.add(playersPanel);
        globalPanel.add(signUpPanel);

        buttonsPanel.add(playButton);
        buttonsPanel.add(returnButton);


        this.add(title, gridBagConstraints);
        this.add(globalPanel, gridBagConstraints);
        this.add(buttonsPanel, gridBagConstraints);
    }

    @Override
    public Menus getMenu() {
        return Menus.CONNEXION;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        DialogMessage dialogMessage = null;

        if (actionEvent.getSource().equals(signIn1Button)) {
            dialogMessage = menuController.connectPlayer(0, usernamePlayer1.getText(), DigestUtils.sha1Hex(String.valueOf(passwordPlayer1.getPassword())));
            if (dialogMessage == DialogMessage.SIGN_IN_SUCCESS || dialogMessage == DialogMessage.NO_CONNECTION) {
                manageConnectionComponents(0, true);
            }

        } else if (actionEvent.getSource().equals(signIn2Button)) {
            dialogMessage = menuController.connectPlayer(1, usernamePlayer2.getText(), DigestUtils.sha1Hex(String.valueOf(passwordPlayer2.getPassword())));
            if (dialogMessage == DialogMessage.SIGN_IN_SUCCESS || dialogMessage == DialogMessage.NO_CONNECTION) {
                manageConnectionComponents(1, true);
            }

        } else if (actionEvent.getSource().equals(signUpButton)) {
            dialogMessage = menuController.createNewPlayer(usernameNewUser.getText(), DigestUtils.sha1Hex(String.valueOf(passwordNewUser.getPassword())), emailNewUser.getText(), Permissions.BASIC);

        } else if (actionEvent.getSource().equals(returnButton)) {
            menuController.changeMenu(Menus.MAINMENU);

        } else if (actionEvent.getSource().equals(disconnectPlayer1Button)) {
            manageConnectionComponents(0, false);
            menuController.disconnectPlayer(0);

        } else if (actionEvent.getSource().equals(disconnectPlayer2Button)) {
            manageConnectionComponents(1, false);
            menuController.disconnectPlayer(1);

        } else if(actionEvent.getSource().equals(profilePlayer1Button)){
            menuController.changeMenu(0);

        } else if(actionEvent.getSource().equals(profilePlayer2Button)){
            menuController.changeMenu(1);

        } else if(actionEvent.getSource().equals(playButton)){
            dialogMessage = menuController.launchGame(false);

        } else if(actionEvent.getSource().equals(iaPlayer1Button)){
            menuController.addPlayable(0);
            menuController.changeMenu(Menus.GAME_IA);

        } else if(actionEvent.getSource().equals(iaPlayer2Button)) {
            menuController.addPlayable(1);
            menuController.changeMenu(Menus.GAME_IA);
        }

        if(dialogMessage != null){
            JOptionPane.showMessageDialog(this, dialogMessage.getMessage(), dialogMessage.getTitle(), dialogMessage.getType());
        }
    }

    /**
     * Gère les éléments relatifs aux joueurs
     * Suivant le joueur qui se connecte, on peut afficher les bons boutons
     * @param player
     * @param connected
     */
    private void manageConnectionComponents(int player, boolean connected) {
        if (player == 0) {
            if(connected){
                player1ProfilePanel.setVisible(true);
                player1SignInPanel.setVisible(false);
                player1Panel.setBorder(BorderFactory.createTitledBorder("<html><font face='arial' size='7' color='orange'>" + usernamePlayer1.getText() + "</font></html>"));
            } else {
                player1Panel.setBorder(BorderFactory.createTitledBorder("<html><font face='arial' size='7' color='orange'>Player 1</font></html>"));
                player1ProfilePanel.setVisible(false);
                player1SignInPanel.setVisible(true);
            }
        } else if (player == 1) {
            if(connected){
                player2ProfilePanel.setVisible(true);
                player2SignInPanel.setVisible(false);
                player2Panel.setBorder(BorderFactory.createTitledBorder("<html><font face='arial' size='7' color='orange'>" + usernamePlayer2.getText() + "</font></html>"));
            } else {
                player2Panel.setBorder(BorderFactory.createTitledBorder("<html><font face='arial' size='7' color='orange'> Player 2</font></html>"));
                player2ProfilePanel.setVisible(false);
                player2SignInPanel.setVisible(true);
            }
        }
    }
}
