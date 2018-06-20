package GUI;

import Interface.IClickOnSortFrame;
import Logic.LanguageAnalyzer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SortFrame implements ActionListener {
    private JFrame frame;
    private JPanel panel;
    private JButton okButton;
    private IClickOnSortFrame clickOnSortFrame;
    private JCheckBox nameButton = new JCheckBox(LanguageAnalyzer.getWord(57), true);
    private JCheckBox dateButton = new JCheckBox(LanguageAnalyzer.getWord(58));
    private JCheckBox sizeButton = new JCheckBox(LanguageAnalyzer.getWord(59));
    private JRadioButton upRadioButton;

    public SortFrame(JFrame relationToThis) {
        initFrame(relationToThis);
        initPanel();

        panel.add(initChooseSortMode());
        panel.add(initUpAndDownRadioButtons());
        panel.add(initOkButton());

        frame.add(panel);
        frame.pack();
    }

    private void initFrame(JFrame relationToThis) {
        frame = new JFrame("Sort by ...");
        frame.setVisible(true);
        frame.setLocationRelativeTo(relationToThis);
    }

    private void initPanel() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 5, 5));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    private JPanel initUpAndDownRadioButtons() {
        JPanel tempPanel = new JPanel();
        JPanel tempInnerPanel = new JPanel();

        tempPanel.setLayout(new BorderLayout());

        upRadioButton = new JRadioButton("Up");
        JRadioButton downRadioButton = new JRadioButton("Down");

        ButtonGroup bg = new ButtonGroup();

        upRadioButton.setSelected(true);
        bg.add(upRadioButton);
        bg.add(downRadioButton);

        tempInnerPanel.add(upRadioButton);
        tempInnerPanel.add(downRadioButton);

        tempPanel.add(tempInnerPanel, BorderLayout.CENTER);
        tempPanel.add(new JLabel(LanguageAnalyzer.getWord(65)), BorderLayout.WEST);

        return tempPanel;
    }

    private JPanel initChooseSortMode() {
        JPanel tempPanel = new JPanel();
        JPanel tempInnerPanel = new JPanel();

        tempPanel.setLayout(new BorderLayout());

        nameButton = new JCheckBox(LanguageAnalyzer.getWord(60), true);
        dateButton = new JCheckBox(LanguageAnalyzer.getWord(61));
        sizeButton = new JCheckBox(LanguageAnalyzer.getWord(62));

        tempInnerPanel.add(nameButton);
        tempInnerPanel.add(dateButton);
        tempInnerPanel.add(sizeButton);

        tempPanel.add(tempInnerPanel, BorderLayout.CENTER);
        tempPanel.add(new JLabel(LanguageAnalyzer.getWord(63)), BorderLayout.WEST);

        return tempPanel;
    }

    private JButton initOkButton() {
        okButton = new JButton(LanguageAnalyzer.getWord(64));
        okButton.addActionListener(this);
        return okButton;
    }

    public void setClickOnSortFrame(IClickOnSortFrame clickOnSortFrame) {
        this.clickOnSortFrame = clickOnSortFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //0 => sort by name / 1 => date / 2 => size
        //3 => name and date / 4 => name and size
        // second parameter is for up and down
        if (e.getSource().equals(okButton)) {
            if ((nameButton.isSelected() == true) && (sizeButton.isSelected() == false) && (dateButton.isSelected() == false))
                clickOnSortFrame.clickOnOkInSortFrame(0, upRadioButton.isSelected());

            else if ((nameButton.isSelected() == false) && (sizeButton.isSelected() == false) && (dateButton.isSelected() == true))
                clickOnSortFrame.clickOnOkInSortFrame(1, upRadioButton.isSelected());

            else if ((nameButton.isSelected() == false) && (sizeButton.isSelected() == true) && (dateButton.isSelected() == false))
                clickOnSortFrame.clickOnOkInSortFrame(2, upRadioButton.isSelected());

            else if ((nameButton.isSelected() == true) && (sizeButton.isSelected() == false) && (dateButton.isSelected() == true))
                clickOnSortFrame.clickOnOkInSortFrame(3, upRadioButton.isSelected());

            else if ((nameButton.isSelected() == true) && (sizeButton.isSelected() == true) && (dateButton.isSelected() == false))
                clickOnSortFrame.clickOnOkInSortFrame(4, upRadioButton.isSelected());

            else if ((nameButton.isSelected() == false) && (sizeButton.isSelected() == true) && (dateButton.isSelected() == true))
                clickOnSortFrame.clickOnOkInSortFrame(5, upRadioButton.isSelected());

            else if ((nameButton.isSelected() == true) && (sizeButton.isSelected() == true) && (dateButton.isSelected() == true))
                clickOnSortFrame.clickOnOkInSortFrame(6, upRadioButton.isSelected());

            else
                JOptionPane.showMessageDialog(frame, "You should check from this list : " +
                        "\n1) name\n2) date\n3) size\n4) name and date\n5) name and size 6) size and date");
        }
    }
}
