package view.components;

import controller.MenuController;
import entity.Player;
import org.apache.commons.codec.digest.DigestUtils;
import utils.DialogMessage;
import utils.MenuManager;
import utils.Menus;
import utils.Permissions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfilePanel extends JPanel implements MenuManager, ActionListener, ItemListener {

    private final MenuController menuController;

    private Player player;

    private List<Player> players;

    private List<JRadioButton> jRadioButtons;

    private JComboBox<Object> usersComboBox;

    private JButton saveButton;
    private JButton returnButton;
    private JButton saveAdminButton;
    private JButton removeAdminButton;
    private JButton addAdminButton;
    private JButton statisticButton;

    private JTextField username;
    private JTextField email;
    private JPasswordField password;

    private JTextField usernameAdmin;
    private JTextField emailAdmin;
    private JPasswordField passwordAdmin;

    JPanel globalPanel = new JPanel();

    public ProfilePanel(MenuController menuController) {
        this.menuController = menuController;

        this.setLayout(new GridBagLayout());

        globalPanel.setOpaque(true);
        globalPanel.setBackground(Color.blue);

        this.add(globalPanel);
    }

    @Override
    public Menus getMenu() {
        return Menus.PROFILE;
    }

    public void updatePanel(Player player){
        globalPanel.removeAll();

        this.player = player;

        // JPanels

        JPanel managementPanel = new JPanel();
        JPanel profilePanel = new JPanel();
        JPanel profileInfoPanel = new JPanel();

        // Background

        managementPanel.setOpaque(true);
        managementPanel.setBackground(Color.blue);
        profilePanel.setOpaque(true);
        profilePanel.setBackground(Color.blue);
        profileInfoPanel.setOpaque(true);
        profileInfoPanel.setBackground(Color.blue);

        // Layout

        globalPanel.setLayout(new BorderLayout());
        profilePanel.setLayout(new BorderLayout());
        profileInfoPanel.setLayout(new GridLayout(0,2));

        // Border

        profilePanel.setBorder(BorderFactory.createTitledBorder("<html><font face='arial' size='7' color='orange'>Profile</font></html>"));

        // Elements

        username = new JTextField();
        email = new JTextField();
        password = new JPasswordField();

        password.setEchoChar((char)0);

        username.setText(player.getUsername());
        email.setText(player.getEmail());

        Icon save= new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/save.png")));
        Icon retour= new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/retour.png")));
        Icon SSC= new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/SSC.png")));


        saveButton = new JButton(save);
        saveButton.setBorderPainted(false);
        saveButton.setFocusPainted(false);
        saveButton.setContentAreaFilled(false);
        saveButton.addActionListener(this);
        returnButton = new JButton(retour);
        returnButton.setBorderPainted(false);
        returnButton.setFocusPainted(false);
        returnButton.setContentAreaFilled(false);
        returnButton.addActionListener(this);
        statisticButton = new JButton(SSC);
        statisticButton.setBorderPainted(false);
        statisticButton.setFocusPainted(false);
        statisticButton.setContentAreaFilled(false);
        statisticButton.addActionListener(this);

        Font police = new Font("Arial",Font.BOLD,13);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(police);
        usernameLabel.setForeground(Color.ORANGE);
        usernameLabel.setHorizontalAlignment(JLabel.CENTER);
        usernameLabel.setVerticalAlignment(JLabel.CENTER);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(police);
        passwordLabel.setForeground(Color.ORANGE);
        passwordLabel.setHorizontalAlignment(JLabel.CENTER);
        passwordLabel.setVerticalAlignment(JLabel.CENTER);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(police);
        emailLabel.setForeground(Color.ORANGE);
        emailLabel.setHorizontalAlignment(JLabel.CENTER);
        emailLabel.setVerticalAlignment(JLabel.CENTER);

        // Add

        profileInfoPanel.add(usernameLabel);
        profileInfoPanel.add(username);
        profileInfoPanel.add(passwordLabel);
        profileInfoPanel.add(password);
        profileInfoPanel.add(emailLabel);
        profileInfoPanel.add(email);


        profilePanel.add(profileInfoPanel, BorderLayout.NORTH);
        profilePanel.add(saveButton, BorderLayout.SOUTH);
        if(player.getPermission() != Permissions.BASIC) {
            profilePanel.add(statisticButton, BorderLayout.CENTER);
        }

        globalPanel.add(profilePanel, BorderLayout.NORTH);

        if(player.getPermission() == Permissions.ADMIN){

            // Panel

            JPanel adminPanel = new JPanel();
            JPanel adminButtonsPanel = new JPanel();
            JPanel adminInfoPanel = new JPanel();
            JPanel adminInfoFieldsPanel = new JPanel();
            JPanel adminInfoRadioPanel = new JPanel();

            // Background

            adminPanel.setOpaque(true);
            adminPanel.setBackground(Color.BLUE);
            adminButtonsPanel.setOpaque(true);
            adminButtonsPanel.setBackground(Color.BLUE);
            adminInfoPanel.setOpaque(true);
            adminInfoPanel.setBackground(Color.BLUE);
            adminInfoFieldsPanel.setOpaque(true);
            adminInfoFieldsPanel.setBackground(Color.BLUE);
            adminInfoRadioPanel.setOpaque(true);
            adminInfoRadioPanel.setBackground(Color.BLUE);

            // Layout

            adminPanel.setLayout(new BorderLayout());

            adminInfoPanel.setLayout(new GridLayout(2,0));
            adminInfoFieldsPanel.setLayout(new GridLayout(0,2));

            // Background

            adminPanel.setOpaque(true);
            adminPanel.setBackground(Color.blue);
            managementPanel.setOpaque(true);
            managementPanel.setBackground(Color.blue);
            adminPanel.setOpaque(true);
            adminPanel.setBackground(Color.blue);
            adminInfoPanel.setOpaque(true);
            adminInfoPanel.setBackground(Color.blue);

            adminInfoFieldsPanel.setOpaque(true);
            adminInfoFieldsPanel.setBackground(Color.blue);
            adminInfoRadioPanel.setOpaque(true);
            adminInfoRadioPanel.setBackground(Color.blue);
            adminButtonsPanel.setOpaque(true);
            adminButtonsPanel.setBackground(Color.blue);

            // Border

            adminPanel.setBorder(BorderFactory.createTitledBorder("<html><font face='arial' size='5' color='orange'>User Management</font></html>"));

            // Element

            players = menuController.getDatabasePlayers();

            // Trouvé sur :
            // https://www.geeksforgeeks.org/arraylist-array-conversion-java-toarray-methods/
            // J'ai réadapté la ligne : int[] arr = list.stream().mapToInt(i -> i).toArray();
            usersComboBox = new JComboBox<>(players.stream().map(Player::getUsername).toArray());

            usersComboBox.addItemListener(this);

            usernameAdmin = new JTextField();
            emailAdmin = new JTextField();
            passwordAdmin = new JPasswordField();

            passwordAdmin.setEchoChar((char)0);

            saveAdminButton = new JButton("Save");
            addAdminButton = new JButton("Add");
            removeAdminButton = new JButton("Remove");

            saveAdminButton.addActionListener(this);
            removeAdminButton.addActionListener(this);
            addAdminButton.addActionListener(this);

            jRadioButtons = new ArrayList<>();

            for(Permissions p : Permissions.values()){
                JRadioButton jRadioButton = new JRadioButton(p.name(), false);
                jRadioButton.setBorderPainted(false);
                jRadioButton.setFocusPainted(false);
                jRadioButton.setContentAreaFilled(false);
                jRadioButton.setForeground(Color.ORANGE);
                jRadioButton.addActionListener(this);
                jRadioButtons.add(jRadioButton);
            }

            fillAdminFields();

            JLabel usernameAdminLabel = new JLabel("Username");
            usernameAdminLabel.setFont(police);
            usernameAdminLabel.setForeground(Color.ORANGE);
            usernameAdminLabel.setHorizontalAlignment(JLabel.CENTER);
            usernameAdminLabel.setVerticalAlignment(JLabel.CENTER);

            JLabel passwordAdminLabel = new JLabel("Password");
            passwordAdminLabel.setFont(police);
            passwordAdminLabel.setForeground(Color.ORANGE);
            passwordAdminLabel.setHorizontalAlignment(JLabel.CENTER);
            passwordAdminLabel.setVerticalAlignment(JLabel.CENTER);

            JLabel emailAdminLabel = new JLabel("Email");
            emailAdminLabel.setFont(police);
            emailAdminLabel.setForeground(Color.ORANGE);
            emailAdminLabel.setHorizontalAlignment(JLabel.CENTER);
            emailAdminLabel.setVerticalAlignment(JLabel.CENTER);

            // Add

            adminInfoFieldsPanel.add(usernameAdminLabel);
            adminInfoFieldsPanel.add(usernameAdmin);
            adminInfoFieldsPanel.add(emailAdminLabel);
            adminInfoFieldsPanel.add(emailAdmin);
            adminInfoFieldsPanel.add(passwordAdminLabel);
            adminInfoFieldsPanel.add(passwordAdmin);

            jRadioButtons.forEach(adminInfoRadioPanel::add);

            adminInfoPanel.add(adminInfoFieldsPanel);
            adminInfoPanel.add(adminInfoRadioPanel);

            adminButtonsPanel.add(saveAdminButton);
            adminButtonsPanel.add(addAdminButton);
            adminButtonsPanel.add(removeAdminButton);

            adminPanel.add(usersComboBox, BorderLayout.NORTH);
            adminPanel.add(adminInfoPanel, BorderLayout.CENTER);
            adminPanel.add(adminButtonsPanel, BorderLayout.SOUTH);

            globalPanel.add(adminPanel, BorderLayout.CENTER);
        }

        globalPanel.add(returnButton, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        DialogMessage dialogMessage = null;

        if(actionEvent.getSource().equals(saveButton)){
            String usernameStr = username.getText().equals("") ? player.getUsername() : username.getText();
            String emailStr = email.getText().equals("") ? player.getEmail() : email.getText();
            String passwordStr = String.valueOf(password.getPassword()).equals("") ? player.getPassword() : DigestUtils.sha1Hex(String.valueOf(password.getPassword()));
            dialogMessage = menuController.updatePlayer(player.getId(), usernameStr, emailStr, passwordStr, player.getPermission());

        } else if(actionEvent.getSource() instanceof JRadioButton){
            jRadioButtons.forEach(rb -> rb.setSelected(actionEvent.getSource().equals(rb)));

        } else if(actionEvent.getSource().equals(returnButton)){
            menuController.changeMenu(Menus.CONNEXION);

        } else if(actionEvent.getSource().equals(saveAdminButton)){
            Player playerSelected = getPlayerFromCombobox();
            dialogMessage = menuController.updatePlayer(playerSelected.getId(), usernameAdmin.getText(), emailAdmin.getText(), playerSelected.getPassword(), Permissions.valueOf(jRadioButtons.stream().filter(AbstractButton::isSelected).findFirst().get().getText()));

        } else if(actionEvent.getSource().equals(removeAdminButton)){
            Player playerSelected = getPlayerFromCombobox();
            if(player.compareTo(playerSelected) == 0){
                dialogMessage = DialogMessage.REMOVE_YOURSELF;
            } else {
                dialogMessage = menuController.removePlayer(playerSelected);
                usersComboBox.removeItem(usersComboBox.getSelectedItem());
            }

        } else if(actionEvent.getSource().equals(addAdminButton)){
            String passwordStr = passwordAdmin.getPassword().length == 0 ? "" : DigestUtils.sha1Hex(String.valueOf(passwordAdmin.getPassword()));
            dialogMessage = menuController.createNewPlayer(usernameAdmin.getText(), passwordStr, emailAdmin.getText(), Permissions.valueOf(jRadioButtons.stream().filter(AbstractButton::isSelected).findFirst().get().getText()));

        } else if(actionEvent.getSource().equals(statisticButton)){
            menuController.changeMenu(Menus.STATISTICS);
        }

        if(dialogMessage != null){
            JOptionPane.showMessageDialog(this, dialogMessage.getMessage(), dialogMessage.getTitle(), dialogMessage.getType());
        }
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        if(itemEvent.getStateChange() == ItemEvent.SELECTED){
            fillAdminFields();
        }
    }

    private void fillAdminFields(){
        // Trouvé en m'aidant du lien suivant :
        // https://stackoverflow.com/questions/54818723/how-get-elements-from-an-arraylist-based-by-multiple-conditions
        Player player = getPlayerFromCombobox();
        usernameAdmin.setText(player.getUsername());
        emailAdmin.setText(player.getEmail());
        jRadioButtons.forEach(rb -> rb.setSelected(rb.getText().equals(player.getPermission().name())));
        removeAdminButton.setText("Remove " + usernameAdmin.getText());
    }

    private Player getPlayerFromCombobox(){
        return players.stream().filter(p -> p.getUsername().equals(usersComboBox.getSelectedItem().toString())).findFirst().get();
    }


}
