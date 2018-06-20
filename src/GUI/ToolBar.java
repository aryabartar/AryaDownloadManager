package GUI;

import Interface.IClickOnMenu;
import Logic.LanguageAnalyzer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * This class implements a toolBar for Queue .
 */
public class ToolBar implements ActionListener {
    private static String[] buttonIconNames = new String[]{
            "./GUI icons/download.png", "./GUI icons/pause.png", "./GUI icons/resume.png",
            "./GUI icons/cancel.png", "./GUI icons/remove.png", "./GUI icons/settings.png"};
    private static String[] buttonNames = new String[]{
            LanguageAnalyzer.getWord(66), LanguageAnalyzer.getWord(67), LanguageAnalyzer.getWord(68),
            LanguageAnalyzer.getWord(69), LanguageAnalyzer.getWord(70), LanguageAnalyzer.getWord(71)};
    private JButton[] buttons;
    private JToolBar toolbar;
    private IClickOnMenu clickOnMenu;
    private JTextField searchTextField;
    private JButton searchButton;
    private JButton sortButton;

    /**
     * This method initializes toolbar and invokes initializeButtons .
     */
    public ToolBar() {
        toolbar = new JToolBar();
        initializeButtons();
        initSort();
        initSearchTextField();
        initSearchIcon();
    }


    /**
     * This method initializes buttons .
     */
    public void initializeButtons() {
        buttons = new JButton[buttonIconNames.length];

        for (int count = 0; count < buttonIconNames.length; count++) {
            Icon tempIcon = new ImageIcon(getClass().getResource(buttonIconNames[count]));

            buttons[count] = new JButton();
            buttons[count].setIcon(tempIcon);
            buttons[count].setPreferredSize(new Dimension(buttons[count].getPreferredSize().width + 5
                    , buttons[count].getPreferredSize().height + 5));
            buttons[count].setBorder(BorderFactory.createEmptyBorder());
            buttons[count].setToolTipText(buttonNames[count]);
            buttons[count].addActionListener(this);
            JLabel tempLabel = new JLabel("    ");

            toolbar.add(tempLabel);
            toolbar.add(buttons[count]);
        }
    }

    /**
     * This method is used to initialize search elements .
     */
    private void initSort() {
        toolbar.add(new JLabel("     "));

        sortButton = new JButton();
        sortButton.setPreferredSize(new Dimension(sortButton.getPreferredSize().width + 5
                , sortButton.getPreferredSize().height + 5));
        sortButton.setBorder(BorderFactory.createEmptyBorder());
        sortButton.setToolTipText("Sort by");

        Icon tempIcon = new ImageIcon(getClass().getResource("./GUI icons/sort.png"));
        sortButton.setIcon(tempIcon);

        sortButton.addActionListener(this);
        toolbar.add(sortButton);
        toolbar.add(new JLabel(" "));
    }

    /**
     * This method is used to initialize searchTextField .
     */
    private void initSearchTextField() {
        toolbar.add(new JLabel("       "));
        searchTextField = new JTextField("Search ...", 16);
        toolbar.add(searchTextField);
    }

    /**
     * This method is used to initialize search button .
     */
    private void initSearchIcon() {
        toolbar.add(new JLabel(" "));

        searchButton = new JButton();

        Icon tempIcon = new ImageIcon(getClass().getResource("./GUI icons/search.png"));
        searchButton.setIcon(tempIcon);

        searchButton.addActionListener(this);
        toolbar.add(searchButton);
        toolbar.add(new JLabel(" "));
    }

    /**
     *
     * @return toolbar
     */
    public JToolBar getToolbar() {
        return toolbar;
    }

    /**
     * This method is used to set clickOnMenu interface variable .
     * @param clickOnMenu
     */
    public void addClickOnMenu(IClickOnMenu clickOnMenu) {
        this.clickOnMenu = clickOnMenu;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttons[0]) {
            clickOnMenu.clickOnDownload();
        }
        if (e.getSource() == buttons[1]) {
            clickOnMenu.clickOnPause();
        }
        if (e.getSource() == buttons[2]) {
            clickOnMenu.clickResume();
        }
        if (e.getSource() == buttons[3]) {
            clickOnMenu.clickCancel();
        }
        if (e.getSource() == buttons[4]) {
            clickOnMenu.clickRemove();
        }
        if (e.getSource() == buttons[5]) {
            clickOnMenu.clickSettings();
        }
        if (e.getSource().equals(sortButton)) {
            clickOnMenu.clickOnSort();
        }
        if (e.getSource().equals(searchButton)) {
            clickOnMenu.clickOnSearch(searchTextField.getText());
        }
    }
}
