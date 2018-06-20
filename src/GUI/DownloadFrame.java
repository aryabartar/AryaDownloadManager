/**
 * DownloadFrame class implements a frame to perform download actions .
 */


package GUI;

import Interface.IClickOnDownloadFrame;
import Logic.DownloadingFile;
import Logic.FileProcess;
import Logic.LanguageAnalyzer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.Timer;

public class DownloadFrame implements ActionListener {

    private JFrame frame;
    private JPanel panel;
    private JButton addToQueueButton;
    private JButton okButton;
    private JLabel label;
    private JTextField urlField;
    private JButton cancel;
    private JTextField nameField;
    private IClickOnDownloadFrame clickOnDownloadFrame;
    private JSpinner spinner;
    private JRadioButton defaultRadioButton;
    private JRadioButton startTimeRadioButton;
    private JRadioButton startAfterRadioButton;
    private JSpinner startAfterSpinnerMinute;
    private JSpinner startAfterSpinnerSecond;

    /**
     * This constructor initializes panel and frame .
     */
    public DownloadFrame() {
        initRadioButtons();
        initFrame();
        initializePanel();

        panel.add(initNamePanel());
        panel.add(initUrlPanel());
        panel.add(initDefaultRadioButtonPanel());
        panel.add(iniTimeChooser());
        panel.add(initStartAfterPanel());
        panel.add(initOkAndCancelButtonPanel());
        panel.add(initCancelButton());

        frame.add(panel);
        frame.pack();
    }

    /**
     * This method initializes spinner and adds it to panel .
     *
     * @return added panel
     */
    private JPanel iniTimeChooser() {
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BorderLayout(5, 0));

        SpinnerDateModel model = new SpinnerDateModel();
        model.setCalendarField(Calendar.MINUTE);

        spinner = new JSpinner();
        spinner.setEnabled(false);
        spinner.setModel(model);
        spinner.setEditor(new JSpinner.DateEditor(spinner, "HH:mm"));

        tempPanel.add(startTimeRadioButton, BorderLayout.CENTER);
        tempPanel.add(spinner, BorderLayout.EAST);

        return tempPanel;
    }

    /**
     * This method initializes a panel that has label and two time choosers
     * for minute and second .
     *
     * @return initialized panel .
     */
    private JPanel initStartAfterPanel() {
        JPanel tempPanel = new JPanel();
        JPanel tempLeftPanel = new JPanel();

        tempLeftPanel.setLayout(new FlowLayout(5, 5, 5));
        tempPanel.setLayout(new BorderLayout());


        startAfterSpinnerSecond = new JSpinner();
        startAfterSpinnerSecond.setValue(10);
        startAfterSpinnerSecond.setEnabled(false);

        startAfterSpinnerMinute = new JSpinner();
        startAfterSpinnerMinute.setValue(0);
        startAfterSpinnerMinute.setEnabled(false);

        tempPanel.add(startAfterRadioButton, BorderLayout.WEST);
        tempLeftPanel.add(startAfterSpinnerMinute);
        tempLeftPanel.add(new JLabel(":"));
        tempLeftPanel.add(startAfterSpinnerSecond);
        tempPanel.add(tempLeftPanel, BorderLayout.EAST);

        return tempPanel;
    }

    /**
     * This method initializes defaultRadioButton .
     *
     * @return
     */
    private JPanel initDefaultRadioButtonPanel() {
        JPanel tempPanel = new JPanel();

        tempPanel.setLayout(new BorderLayout());
        tempPanel.add(defaultRadioButton, BorderLayout.WEST);

        return tempPanel;
    }


    /**
     * This method initializes 3 JButtons and add them to buttonGroup .
     */
    private void initRadioButtons() {
        startTimeRadioButton = new JRadioButton(LanguageAnalyzer.getWord(2));
        defaultRadioButton = new JRadioButton(LanguageAnalyzer.getWord(3));
        startAfterRadioButton = new JRadioButton(LanguageAnalyzer.getWord(4));

        defaultRadioButton.addActionListener(this);
        startTimeRadioButton.addActionListener(this);
        startAfterRadioButton.addActionListener(this);

        defaultRadioButton.setSelected(true);

        ButtonGroup buttonGroup = new ButtonGroup();

        buttonGroup.add(defaultRadioButton);
        buttonGroup.add(startTimeRadioButton);
        buttonGroup.add(startAfterRadioButton);
    }

    /**
     * This method initializes ok and queue buttons .
     *
     * @return panel with two buttons .
     */
    private JPanel initOkAndCancelButtonPanel() {
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new GridLayout(1, 2, 5, 5));

        okButton = new JButton(LanguageAnalyzer.getWord(5));
        okButton.addActionListener(this::actionPerformed);
        tempPanel.add(okButton);

        addToQueueButton = new JButton(LanguageAnalyzer.getWord(6));
        addToQueueButton.addActionListener(this::actionPerformed);
        tempPanel.add(addToQueueButton);

        return tempPanel;
    }

    /**
     * This method initializes cancel button .
     *
     * @return button
     */
    private JButton initCancelButton() {
        cancel = new JButton(LanguageAnalyzer.getWord(7));
        cancel.addActionListener(this);
        return cancel;
    }

    /**
     * This method initializes name label and text filed .
     *
     * @return panel
     */
    private JPanel initNamePanel() {
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BorderLayout());

        Icon tempIcon = new ImageIcon(getClass().getResource("./GUI icons/small-tag.png"));

        label = new JLabel(LanguageAnalyzer.getWord(8));
        label.setIcon(tempIcon);
        tempPanel.add(label, BorderLayout.WEST);

        nameField = new JTextField(13);
        tempPanel.add(nameField, BorderLayout.CENTER);
        return tempPanel;
    }

    /**
     * This method initializes URL label and text filed .
     *
     * @return
     */
    private JPanel initUrlPanel() {
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BorderLayout());

        Icon tempIcon = new ImageIcon(getClass().getResource("./GUI icons/small-download.png"));

        label = new JLabel(LanguageAnalyzer.getWord(9));
        label.setIcon(tempIcon);
        tempPanel.add(label, BorderLayout.WEST);

        urlField = new JTextField(13);
        tempPanel.add(urlField, BorderLayout.CENTER);
        return tempPanel;
    }

    /**
     * This method will set frame to visible .
     */
    private void initFrame() {
        frame = new JFrame("Download");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * This method will initialize JPanel .
     */
    private void initializePanel() {
        panel = new JPanel(new GridLayout(7, 1, 10, 10));
        Border border = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        panel.setBorder(border);
    }

    /**
     * This method sets frame's location .
     *
     * @param toThisFrame
     */
    public void setRelationTo(JFrame toThisFrame) {
        frame.setLocationRelativeTo(toThisFrame);
    }

    /**
     * This method is interface setter .
     *
     * @param clickOnDownloadFrame
     */
    public void addClickOnDownloadFrame(IClickOnDownloadFrame clickOnDownloadFrame) {
        this.clickOnDownloadFrame = clickOnDownloadFrame;
    }

    /**
     * This method is event handler .
     *
     * @param e as and ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //click on queue
        if (e.getSource() == addToQueueButton) {
            System.out.println(LanguageAnalyzer.getWord(11));

            //set time
            if (startTimeRadioButton.isSelected()) {
                Date date = (Date) spinner.getValue();

                int startMinute = DateAnalyser.getMinute(date);
                int startHour = DateAnalyser.getHour(date);

                int nowHour = DownloadingFile.getHour();
                int nowMinute = DownloadingFile.getMinute();

                int minDifference = startMinute - nowMinute;
                int hourDifference = startHour - nowHour;

                System.out.println("h dif : " + hourDifference + "   m dif : " + minDifference);

                if ((hourDifference < 0) || ((hourDifference == 0) && (minDifference < 0))) {
                    JOptionPane.showMessageDialog(panel, "You should download today !");
                } else if ((hourDifference == 0) && (minDifference == 0)) {
                    addToQueue();
                } else {

                    Timer timer = new Timer();

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            addToQueue();
                        }
                    }, (hourDifference * 60 * 60 * 1000) + (minDifference * 60 * 1000) - (DownloadingFile.getSec() * 1000));

                }
            }

            if (startAfterRadioButton.isSelected()) {
                Timer timer = new Timer();

                int min = Integer.parseInt(startAfterSpinnerMinute.getValue().toString());
                int sec = Integer.parseInt(startAfterSpinnerSecond.getValue().toString());


                System.out.println("Min : " + min + "    Sec : " + sec);
                if ((min < 0) || (sec < 0)) {
                    JOptionPane.showMessageDialog(panel, "Negative numbers!");
                } else {
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            addToQueue();
                        }
                    }, (min * 60 * 1000) + (sec * 1000));
                }
            } else if (defaultRadioButton.isSelected() == true) {

                addToQueue();
            }

        }
        //click on Ok
        if (e.getSource() == okButton) {
            System.out.println("Ok button2");

            //set time
            if (startTimeRadioButton.isSelected()) {
                Date date = (Date) spinner.getValue();
                int startMinute = DateAnalyser.getMinute(date);
                int startHour = DateAnalyser.getHour(date);

                int nowHour = DownloadingFile.getHour();
                int nowMinute = DownloadingFile.getMinute();

                int minDifference = startMinute - nowMinute;
                int hourDifference = startHour - nowHour;

                System.out.println("h dif : " + hourDifference + "   m dif : " + minDifference);

                if ((hourDifference < 0) || ((hourDifference == 0) && (minDifference < 0))) {
                    JOptionPane.showMessageDialog(panel, "You should download today !");
                } else if ((hourDifference == 0) && (minDifference == 0)) {
                    addToDownloads();
                } else {

                    Timer timer = new Timer();

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            addToDownloads();
                        }
                    }, (hourDifference * 60 * 60 * 1000) + (minDifference * 60 * 1000) - (DownloadingFile.getSec() * 1000));

                }
            }

            if (startAfterRadioButton.isSelected()) {
                Timer timer = new Timer();

                int min = Integer.parseInt(startAfterSpinnerMinute.getValue().toString());
                int sec = Integer.parseInt(startAfterSpinnerSecond.getValue().toString());


                System.out.println("Min : " + min + "    Sec : " + sec);
                if ((min < 0) || (sec < 0)) {
                    JOptionPane.showMessageDialog(panel, "Negative numbers!");
                } else {
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            addToDownloads();
                        }
                    }, (min * 60 * 1000) + (sec * 1000));
                }
            } else if (defaultRadioButton.isSelected() == true) {

                System.out.println("Ok button3");
                addToDownloads();
            }


        }
        //click on cancel
        if (e.getSource().equals(cancel)) {
            frame.setVisible(false);
        }

        if (e.getSource().equals(defaultRadioButton)) {
            spinner.setEnabled(false);
            startAfterSpinnerMinute.setEnabled(false);
            startAfterSpinnerSecond.setEnabled(false);
        }

        if (e.getSource().equals(startTimeRadioButton)) {
            spinner.setEnabled(true);
            startAfterSpinnerMinute.setEnabled(false);
            startAfterSpinnerSecond.setEnabled(false);

        }

        if (e.getSource().equals(startAfterRadioButton)) {
            spinner.setEnabled(false);
            startAfterSpinnerMinute.setEnabled(true);
            startAfterSpinnerSecond.setEnabled(true);

        }

    }

    /**
     * This method is used to add to queue .
     */
    private void addToQueue() {
        if ((checkUrlIsBlocked(urlField.getText()) == true) || (urlField.getText().equals("")) || (nameField.getText().equals(""))) {
            JOptionPane.showMessageDialog(null, "You can't download from blocked sites !");
        } else {
            clickOnDownloadFrame.clickOnAddToQueue(nameField.getText(), urlField.getText());
        }
    }

    /**
     * This method is used to add to downloads .
     */
    private void addToDownloads() {

        if ((checkUrlIsBlocked(urlField.getText()) == true) || (urlField.getText().equals("")) || (nameField.getText().equals(""))) {
            JOptionPane.showMessageDialog(null, "You can't download from blocked sites !");
        } else {
            clickOnDownloadFrame.clickOnOk(nameField.getText(), urlField.getText());

        }
    }

    /**
     * @return true if the domain is blocked and false if it's not
     */
    private Boolean checkUrlIsBlocked(String thisSite) {
        ArrayList<String> blockedFilePaths;
        blockedFilePaths = FileProcess.getBlockedSitesAsArray("./saves/blocked.txt");
        boolean isFound = false;

        for (String str : blockedFilePaths) {
            if (thisSite.matches(str + "\\S*")) {
                isFound = true;
            }
        }
        return isFound;
    }
}
