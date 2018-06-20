package GUI;

import Logic.LanguageAnalyzer;

import javax.swing.*;
import java.awt.*;

/**
 * This class implements a frame with panel in it that shows help information .
 */
public class HelpFrame {
    private JFrame frame;
    private JPanel panel;

    /**
     * This constructor invokes methods to initialize panel and frame .
     *
     * @param relationToThisFrame
     */
    public HelpFrame(JFrame relationToThisFrame) {
        initFrame(relationToThisFrame);
        initPanel();
        initInfoLabels();

        frame.add(panel);
        frame.setVisible(true);
        frame.pack();
    }

    /**
     * This method initializes frame .
     *
     * @param relationToThisFrame
     */
    private void initFrame(JFrame relationToThisFrame) {
        frame = new JFrame("Help");
        frame.setLocationRelativeTo(relationToThisFrame);
    }

    /**
     * This method initializes panel .
     */
    private void initPanel() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 14, 14));
    }

    /**
     * This method initializes labels and adds them to panel .
     */
    private void initInfoLabels() {
        Icon smileIcon = new ImageIcon(getClass().getResource("./GUI icons/small-smile.png"));


        JLabel label1 = new JLabel(LanguageAnalyzer.getWord(23));
        label1.setIcon(smileIcon);
        JLabel label2 = new JLabel(LanguageAnalyzer.getWord(24));
        JLabel label3 = new JLabel(LanguageAnalyzer.getWord(25));
        JLabel label4 = new JLabel(LanguageAnalyzer.getWord(26));
        JLabel label5 = new JLabel(LanguageAnalyzer.getWord(27));
        JLabel label6 = new JLabel(LanguageAnalyzer.getWord(28));
        JLabel label7 = new JLabel(LanguageAnalyzer.getWord(29));

        panel.add(label1);
        panel.add(label2);
        panel.add(label3);
        panel.add(label4);
        panel.add(label5);
        panel.add(label6);
        panel.add(label7);
    }
}
