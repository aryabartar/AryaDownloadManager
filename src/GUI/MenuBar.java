package GUI;

import Interface.IClickOnMenu;
import Logic.LanguageAnalyzer;

import javax.sql.rowset.serial.SerialArray;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This method implements a menuBar to program GUI .
 */
public class MenuBar implements ActionListener , Serializable{

    private JMenuBar menuBar;
    private ArrayList<JMenu> menus;
    private static String[] buttonNames = new String[]{
            LanguageAnalyzer.getWord(33), LanguageAnalyzer.getWord(34), LanguageAnalyzer.getWord(35)
            , LanguageAnalyzer.getWord(36) , LanguageAnalyzer.getWord(37), LanguageAnalyzer.getWord(38)
            , LanguageAnalyzer.getWord(39) , LanguageAnalyzer.getWord(40)};
    private static String[] buttonIconNames = new String[]{
            "./GUI icons/download.png", "./GUI icons/pause.png", "./GUI icons/resume.png",
            "./GUI icons/cancel.png", "./GUI icons/remove.png", "./GUI icons/settings.png",
            "./GUI icons/zip.png" , "./GUI icons/exit.png"};
    private IClickOnMenu clickOnMenu ;


    public MenuBar () {
        menuBar = new JMenuBar();
        menus = new ArrayList<>();
        initializeMenuBar();
    }

    /**
     * This method will initialize menu bar items .
     */
    private void initializeMenuBar () {
        JMenu tempDownloadMenu = new JMenu(LanguageAnalyzer.getWord(41));
        JMenu tempHelpMenu = new JMenu(LanguageAnalyzer.getWord(42));

        tempHelpMenu.add(initAndGetAboutMenuItem()) ;

        menus.add(tempDownloadMenu);
        menus.add(tempHelpMenu);

        for (int count = 0; count < buttonNames.length; count++) {
            JMenuItem item = new JMenuItem(buttonNames[count]);
            Icon tempIcon = new ImageIcon(getClass().getResource(buttonIconNames[count]));
            item.setIcon(tempIcon);
            item.addActionListener(this);
            menus.get(0).add(item);
        }

        menus.get(0).getItem(0).setMnemonic('D') ;
        menus.get(0).getItem(0).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.ALT_MASK));

        menus.get(0).getItem(1).setMnemonic('P') ;
        menus.get(0).getItem(1).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));

        menus.get(0).getItem(2).setMnemonic('R') ;
        menus.get(0).getItem(2).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));

        menus.get(0).getItem(3).setMnemonic('C') ;
        menus.get(0).getItem(3).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));

        menus.get(0).getItem(4).setMnemonic('O') ;
        menus.get(0).getItem(4).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));

        menus.get(0).getItem(5).setMnemonic('S') ;
        menus.get(0).getItem(5).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));

        menus.get(0).getItem(6).setMnemonic('Z');
        menus.get(0).getItem(6).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.ALT_MASK));

        menus.get(0).getItem(7).setMnemonic('E');
        menus.get(0).getItem(7).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));

        for (JMenu menu : menus) {
            menuBar.add(menu);
        }
    }

    /**
     * Initializes about JMenuItem .
     * @return aboutMenuItem
     */
    private JMenuItem initAndGetAboutMenuItem () {
        Icon aboutIcon = new ImageIcon(getClass().getResource("./GUI icons/about.png"));
        JMenuItem aboutMenuItem = new JMenuItem(LanguageAnalyzer.getWord(43)) ;

        aboutMenuItem.setIcon(aboutIcon);
        aboutMenuItem.setMnemonic('I');
        aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.ALT_MASK));
        aboutMenuItem.addActionListener(this);

        return aboutMenuItem ;
    }

    /**
     * @return menuBar
     */
    public JMenuBar getMenuBar() {
        return menuBar;
    }

    /**
     * This menu will set clickOnMenu .
     * @param clickOnMenu
     */
    public void addClickOnMenuListener (IClickOnMenu clickOnMenu) {
        this.clickOnMenu = clickOnMenu ;
    }

    /**
     * This actionPerformer invokes methods when user clicks on menu items .
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() ==  menus.get(0).getItem(0)){
            clickOnMenu.clickOnDownload();
        }
        if (e.getSource() == menus.get(0).getItem(1)) {
            clickOnMenu.clickOnPause();
        }
        if (e.getSource() == menus.get(0).getItem(2)) {
            clickOnMenu.clickResume();
        }
        if (e.getSource() == menus.get(0).getItem(3)) {
            clickOnMenu.clickCancel();
        }
        if (e.getSource() == menus.get(0).getItem(4)) {
            clickOnMenu.clickRemove();
        }
        if (e.getSource() == menus.get(0).getItem(5)) {
            clickOnMenu.clickSettings();
        }
        if (e.getSource() == menus.get(0).getItem(6)) {
            clickOnMenu.clickOnExport();
        }
        if (e.getSource() == menus.get(0).getItem(7)) {
            clickOnMenu.clickOnExit();
        }
        if(e.getSource() == menus.get(1).getItem(0)) {
            clickOnMenu.clickOnAbout();
        }
    }


}
