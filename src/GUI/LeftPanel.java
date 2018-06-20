package GUI;

import Interface.IClickOnLeftPanel;
import Logic.LanguageAnalyzer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class implements left panel to main GUI  .
 */
public class LeftPanel implements ActionListener , Serializable {
    private JPanel panel ;
    private JButton queueButton ;
    private JButton processingButton ;
    private JButton completedButton ;
    private IClickOnLeftPanel clickOnLeftPanel ;

    public LeftPanel () {
        initPanel();
    }

    /**
     * This method initializes panel .
     */
    private void initPanel () {
        panel = new JPanel();
        ArrayList<JButton> buttons = new ArrayList<>();

        queueButton = new JButton(LanguageAnalyzer.getWord(30));
        queueButton.addActionListener(this);
        processingButton = new JButton(LanguageAnalyzer.getWord(31)) ;
        completedButton = new JButton(LanguageAnalyzer.getWord(32)) ;

        buttons.add(processingButton);
        buttons.add(completedButton);
        buttons.add(queueButton);

        panel.setLayout(new GridLayout(buttons.size(), 1));
        for (JButton button : buttons) {
            button.setBackground(new Color(63 , 111 , 110  ));
            panel.add(button);
        }
    }

    /**
     *
     * @return panel
     */
    public JPanel getPanel() {
         return panel ;
    }

    /**
     * This method sets clickOnLeftPanel interface variable .
     * @param clickOnLeftPanel
     */
    public void setClickOnLeftPanel(IClickOnLeftPanel clickOnLeftPanel) {
        this.clickOnLeftPanel = clickOnLeftPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(queueButton)) {
            clickOnLeftPanel.clickOnQueue();
        }
    }
}
