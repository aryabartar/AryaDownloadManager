package GUI;

import Interface.IClickOnQueueToolBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class implements a toolBar for Queue .
 */
public class QueueToolBar implements ActionListener {

    private static String[] buttonIconNames = new String[]{
            "./GUI icons/up.png", "./GUI icons/down.png", "./GUI icons/pause.png", "./GUI icons/resume.png",
            "./GUI icons/cancel.png", "./GUI icons/remove.png"};
    private static String[] buttonNames = new String[]{
            "up", "pause", "Pause", "Resume", "Cancel",
            "Remove", "Settings"};
    private JButton[] buttons;
    private JToolBar toolbar;
    private IClickOnQueueToolBar clickOnQueueToolBar;
    private JTextField searchTextField ;

    /**
     * The constructor initializes toolbar and then invokes a method to initialize buttons .
     */
    public QueueToolBar() {
        toolbar = new JToolBar();
        initializeButtons();
    }


    /**
     * This method initializes toolbar buttons .
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
            JLabel tempLabel = new JLabel("   ");

            toolbar.add(tempLabel);
            toolbar.add(buttons[count]);
        }

    }

    /**
     * @return toolbar
     */
    public JToolBar getToolbar() {
        return toolbar;
    }

    /**
     * This class sets clickOnQueueToolBar .
     *
     * @param clickOnQueueToolBar
     */
    public void setClickOnQueueToolBar(IClickOnQueueToolBar clickOnQueueToolBar) {
        this.clickOnQueueToolBar = clickOnQueueToolBar;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //up
        if (e.getSource() == buttons[0]) {
            clickOnQueueToolBar.clickOnUp();
        }
        //down
        if (e.getSource() == buttons[1]) {
            clickOnQueueToolBar.clickOnDown();
        }
        //pause
        if (e.getSource() == buttons[2]) {
            clickOnQueueToolBar.clickOnPause();
        }
        //resume
        if (e.getSource() == buttons[3]) {
            clickOnQueueToolBar.clickOnResume();
        }
        //cancel
        if (e.getSource() == buttons[4]) {
            clickOnQueueToolBar.clickOnCancel();
        }
        //remove
        if (e.getSource() == buttons[5]) {
            clickOnQueueToolBar.clickOnRemove();
        }

    }
}
