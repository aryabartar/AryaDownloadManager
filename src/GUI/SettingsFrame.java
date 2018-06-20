package GUI;

import Interface.IChangeLanguage;
import Interface.IChangeLookAndFeel;
import Interface.IClickOnMenu;
import Interface.IClickOnSettingButtons;
import Logic.FileProcess;
import Logic.LanguageAnalyzer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;

/**
 * This class implements a settings frame for the program .
 * User can change save direction and look and feel .
 */
public class SettingsFrame implements ActionListener, Serializable {

    private JFrame settingsFrame = null;
    private JPanel panel = null;
    private JLabel maximumLabel;
    private JButton saveLocationButton;
    private static String savingLocation = javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory().getPath(); //default saving address
    private JRadioButton nimbus;
    private JRadioButton defaultTheme;
    private IChangeLookAndFeel changeLookAndFeel;
    private JTextField savingTextField;
    private JButton okButton;
    private JSpinner numberSpinner;
    private static int maximumDownloadNumbers = 100;
    private IClickOnSettingButtons clickOnSettingButtons;
    private static boolean isDefaultLookAndFeel = true;
    private JButton openBlockedSitesPanel;
    private JTextField blockedSiteTextField;
    private JButton addToBlockedSitesButton;
    private JButton clearBlockedListButton;
    private JRadioButton englishRadioButton;
    private JRadioButton persianRadioButton;
    private IChangeLanguage changeLanguage ;


    public SettingsFrame(JFrame firstWindowFrame) {
        initializeSettingsFrame(firstWindowFrame);
        initializePanel();

        adjustFrame();
    }

    private JPanel initLanguageRadioButtons() {
        englishRadioButton = new JRadioButton(LanguageAnalyzer.getWord(44));
        persianRadioButton = new JRadioButton(LanguageAnalyzer.getWord(45));

        englishRadioButton.addActionListener(this) ;
        persianRadioButton.addActionListener(this);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(englishRadioButton);
        buttonGroup.add(persianRadioButton);

        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BorderLayout());

        JPanel tempInnerPanel = new JPanel();

        tempInnerPanel.add(englishRadioButton);
        tempInnerPanel.add(persianRadioButton);


        Icon languageIcon = getIcon("./GUI icons/language.png");
        JLabel languageLabel = new JLabel(LanguageAnalyzer.getWord(46));
        languageLabel.setIcon(languageIcon) ;

        tempPanel.add(tempInnerPanel, BorderLayout.EAST);
        tempPanel.add(languageLabel, BorderLayout.WEST);

        return tempPanel;
    }

    /**
     * This method sets the frame relation and enables its visibility .
     *
     * @param firstWindowFrame Sets location to this frame .
     */
    private void initializeSettingsFrame(JFrame firstWindowFrame) {
        settingsFrame = new JFrame();
        settingsFrame.setLocationRelativeTo(firstWindowFrame);
    }

    public static void setIsDefaultLookAndFeel(boolean isDefaultLookAndFeel) {
        SettingsFrame.isDefaultLookAndFeel = isDefaultLookAndFeel;
    }

    private JPanel initAddToBlockedSites() {

        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BorderLayout(10, 10));

        blockedSiteTextField = new JTextField();
        tempPanel.add(blockedSiteTextField, BorderLayout.CENTER);

        addToBlockedSitesButton = new JButton(LanguageAnalyzer.getWord(47));
        addToBlockedSitesButton.addActionListener(this);

        tempPanel.add(addToBlockedSitesButton, BorderLayout.EAST);

        return tempPanel;
    }

    private JButton initClearBlockedListButton() {
        clearBlockedListButton = new JButton(LanguageAnalyzer.getWord(48));
        clearBlockedListButton.addActionListener(this);

        return clearBlockedListButton;
    }

    /**
     * This method initializes panel components and adds them to the panel .
     */
    private void initializePanel() {
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        panel.setLayout(new GridLayout(7, 1, 5, 5));

        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BorderLayout(4, 4));

        Icon directoryIcon = getIcon("./GUI icons/small-download.png");
        JLabel locationLabel = new JLabel(LanguageAnalyzer.getWord(49));
        locationLabel.setIcon(directoryIcon);
        tempPanel.add(locationLabel, BorderLayout.WEST);

        savingTextField = new JTextField(15);
        savingTextField.setEditable(false);
        savingTextField.setText(savingLocation);
        tempPanel.add(savingTextField, BorderLayout.CENTER);

        saveLocationButton = new JButton("...");
        saveLocationButton.addActionListener(this);
        tempPanel.add(saveLocationButton, BorderLayout.EAST);

        panel.add(tempPanel);


        JPanel tempPanel2 = new JPanel();
        tempPanel2.setLayout(new BorderLayout(20, 5));

        Icon maxIcon = getIcon("./GUI icons/small-max.png");
        maximumLabel = new JLabel(LanguageAnalyzer.getWord(50));
        maximumLabel.setIcon(maxIcon);
        tempPanel2.add(maximumLabel, BorderLayout.WEST);

        numberSpinner = new JSpinner();
        numberSpinner.setValue(new Integer(SettingsFrame.getMaximumDownloadNumbers()));
        tempPanel2.add(numberSpinner, BorderLayout.CENTER);

        okButton = new JButton();
        okButton.setText(LanguageAnalyzer.getWord(51));
        okButton.addActionListener(this);
        tempPanel2.add(okButton, BorderLayout.EAST);
        panel.add(tempPanel2);

        JPanel tempPanel3 = new JPanel();
        tempPanel3.setLayout(new BorderLayout());

        Icon lookAndFeelIcon = getIcon("./GUI icons/small-eye.png");
        JLabel chooseLookAndFeel = new JLabel(LanguageAnalyzer.getWord(52));
        chooseLookAndFeel.setIcon(lookAndFeelIcon);
        tempPanel3.add(chooseLookAndFeel, BorderLayout.WEST);


        tempPanel3.add(setRadioButtons(), BorderLayout.CENTER);
        tempPanel3.add(new JLabel("         "), BorderLayout.EAST);

        panel.add(tempPanel3);
        panel.add(initLanguageRadioButtons());
        panel.add(initAddToBlockedSites());
        panel.add(initBlockedSites());
        panel.add(initClearBlockedListButton());
    }

    private JPanel initBlockedSites() {
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BorderLayout(4, 4));

        openBlockedSitesPanel = new JButton(LanguageAnalyzer.getWord(53));
        openBlockedSitesPanel.addActionListener(this);

        tempPanel.add(openBlockedSitesPanel, BorderLayout.CENTER);

        return tempPanel;

    }

    /**
     * This method adjusts settingsFrame .
     */
    private void adjustFrame() {
        settingsFrame.add(panel);
        settingsFrame.pack();
        settingsFrame.setVisible(true);
    }

    /**
     * This method makes RadioButtons and add them action listener .
     */
    private JPanel setRadioButtons() {
        JPanel radioButtonPanel = new JPanel();
        radioButtonPanel.setLayout(new FlowLayout(2, 2, 2));

        nimbus = new JRadioButton(LanguageAnalyzer.getWord(54), false);
        nimbus.addActionListener(this);

        defaultTheme = new JRadioButton(LanguageAnalyzer.getWord(55), false);
        defaultTheme.addActionListener(this);

        ButtonGroup radioGroup = new ButtonGroup();

        radioGroup.add(nimbus);
        radioGroup.add(defaultTheme);
        radioButtonPanel.add(nimbus);
        radioButtonPanel.add(defaultTheme);

        return radioButtonPanel;
    }

    /**
     * This method sets changeLookAndFeel .
     *
     * @param changeLookAndFeel sets to this .
     */
    public void addChangeLookAndFeel(IChangeLookAndFeel changeLookAndFeel) {
        this.changeLookAndFeel = changeLookAndFeel;
    }

    /**
     * This method is used to select a folder location .
     */
    private void chooseFileLocation() {

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(LanguageAnalyzer.getWord(56));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(settingsFrame) == JFileChooser.APPROVE_OPTION) {
            savingLocation = chooser.getSelectedFile().getPath();

            System.out.println("getCurrentDirectory(): "
                    + chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : "
                    + chooser.getSelectedFile());
            savingTextField.setText("" + chooser.getSelectedFile());
        } else {
            System.out.println("No Selection ");
        }
    }

    /**
     * This
     *
     * @param filePath
     * @return
     */
    private Icon getIcon(String filePath) {
        Icon tempIcon = new ImageIcon(getClass().getResource(filePath));
        return tempIcon;
    }

    public void addIClickOnSettingButtons(IClickOnSettingButtons clickOnSettingButtons) {
        this.clickOnSettingButtons = clickOnSettingButtons;
    }

    public void changeLookAndFeeltoNimbus() {
        changeLookAndFeel.changeToNimbus();
        isDefaultLookAndFeel = false;
        GUI.changeLookAndFeelToNimbus(settingsFrame);
    }

    /**
     * @return maximumDownloadNumbers
     */
    public static int getMaximumDownloadNumbers() {
        return maximumDownloadNumbers;
    }

    /**
     * @return savingLocation
     */
    public static String getSavingLocation() {
        return savingLocation;
    }

    public static void setMaximumDownloadNumbers(int maximumDownloadNumbers) {
        SettingsFrame.maximumDownloadNumbers = maximumDownloadNumbers;
    }

    public static void setSavingLocation(String savingLocation) {
        SettingsFrame.savingLocation = savingLocation;
    }

    public static boolean getIsDefaultLookAndFeel() {
        return isDefaultLookAndFeel;
    }

    public void setChangeLanguage(IChangeLanguage changeLanguage) {
        this.changeLanguage = changeLanguage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //save file actionListener
        if (e.getSource().equals(saveLocationButton)) {
            chooseFileLocation();
        }

        //nimbus action listener
        if (e.getSource().equals(nimbus)) {
            isDefaultLookAndFeel = false;
            changeLookAndFeeltoNimbus();
        }

        //default theme actionListener
        if (e.getSource().equals(defaultTheme)) {
            changeLookAndFeel.changeToDefault();
            isDefaultLookAndFeel = true;
            GUI.changeLookAndFeelToDefault(settingsFrame);
        }

        if (e.getSource().equals(okButton)) {
            System.out.println(numberSpinner.getValue());
            String tempNumberString = "" + numberSpinner.getValue();
            maximumDownloadNumbers = Integer.parseInt(tempNumberString);
            clickOnSettingButtons.clickOnOk();
            System.out.println("Ok");
        }

        //add button (for blocked sites )
        if (e.getSource().equals(openBlockedSitesPanel)) {
            new BlockedSitesFrame(settingsFrame);
        }

        //addToBlockedSitesButton
        if (e.getSource().equals(addToBlockedSitesButton)) {
            if (blockedSiteTextField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Do you want to block all the sites in the net?\n" +
                        "I don't think this is a good idea :D\nWrite sth ;)");
            } else {
                FileProcess.appendToThisFile("./saves/blocked.txt", blockedSiteTextField.getText());
            }
        }

        //clearBlockedListButton
        if (e.getSource().equals(clearBlockedListButton)) {
            FileProcess.clearFile("./saves/blocked.txt");
        }

        if(e.getSource().equals(englishRadioButton)) {
            changeLanguage.clickOnEnglish();
        }

        if (e.getSource().equals(persianRadioButton)) {
            changeLanguage.clickOnPersian();
        }

    }
}
