package GUI;

import Logic.DownloadThread;
import Logic.DownloadingFile;
import Logic.LanguageAnalyzer;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class implements a GUI for DownloadingFile class .
 * Other classes make an instance of this class rather than making class of DownloadingFile directly .
 */
public class DownloadingFileGui implements MouseListener, ActionListener, Serializable {

    private int backgroundCounter;
    private JPanel panel;
    private JPanel mainPanel;
    private JProgressBar progressBar;
    private Color backgroundColor;
    private DownloadingFile downloadingFile;
    private JLabel fileInformationLabel;
    private final Color firstPanelColor;
    private JButton folderButton; //this button is in left .
    private DownloadThread thisThread;
    private boolean isRemoved = false ;


    /**
     * This constructor instantiates downloadingFile and other fields .
     *
     * @param fileName
     * @param fileSize
     */
    public DownloadingFileGui(String fileName, double fileSize) {

        downloadingFile = new DownloadingFile(fileName, fileSize);
        downloadingFile.setFileSaveDirectory(SettingsFrame.getSavingLocation());

        initializePanel();
        initMainPanel();
        initFolderButton();

        backgroundCounter = 0;

        initFileNameLabel();
        setProgressBar();
        initFileInformationLabel();

        firstPanelColor = mainPanel.getBackground();

        panel.addMouseListener(this);

    }

    /**
     * This method initializes fileNameLabel and adds it to panel .
     */
    private void initFileNameLabel() {
        JLabel fileNameLabel = new JLabel(LanguageAnalyzer.getWord(11) + downloadingFile.getDownloadingFileName());
        fileNameLabel.setBorder(BorderFactory.createEmptyBorder(6, 0, 4, 0));

        panel.add(fileNameLabel);
    }

    /**
     * This method initializes mainPanel.
     */
    private void initMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
    }

    /**
     * This method initializes folder button and adds it to mainPanel .
     */
    private void initFolderButton() {

        JPanel tempPanel = new JPanel();

        folderButton = new JButton();

        Icon fileIcon = new ImageIcon(getClass().getResource("./GUI icons/small-folder.png"));
        folderButton.setIcon(fileIcon);
        folderButton.addActionListener(this);

        tempPanel.add(folderButton);
        tempPanel.setBorder(BorderFactory.createEmptyBorder(6, 0, 8, 0));

        mainPanel.add(tempPanel, BorderLayout.EAST);
    }

    /**
     * This method initializes file information label and adds it to mainPanel .
     */
    private void initFileInformationLabel() {
        fileInformationLabel = new JLabel(getFileInformationForPanel());
        panel.add(fileInformationLabel);
    }

    private String getFileInformationForPanel() {
        return String.format("File name : " + downloadingFile.getDownloadingFileName() + " | Downloaded amount : " + downloadingFile.getDownloadedAmount()
                + " | Download percent : " + downloadingFile.getDownloadedPercent() + " | Speed : " + "0");
    }

    /**
     * @return fileInformationLabel text
     */
    public String getFileInformation() {
        return fileInformationLabel.getText();
    }

    /**
     * This method initializes panel .
     */
    private void initializePanel() {
        panel = new JPanel(new GridLayout(4, 1));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.GRAY));
    }

    /**
     * This method returns mainPanel .
     *
     * @return mainPanel
     */
    public JPanel getPanel() {
        mainPanel.add(panel, BorderLayout.CENTER);
        return mainPanel;
    }

    /**
     * @return progress bar
     */
    public JProgressBar getProgressBar() {
        return progressBar;
    }

    /**
     * This method initializes JProgress bar .
     *
     * @return
     */
    private void setProgressBar() {
        final int MAX = 100;
        progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(MAX);
        progressBar.setStringPainted(true);
        setProgressBarProgress();

        panel.add(getProgressBar());
    }

    /**
     * This method will set progress bar progression .
     */
    private void setProgressBarProgress() {
        int percent = (int) downloadingFile.getDownloadedPercent();
        if ((percent < 0) || (percent > 100))
            throw new IllegalArgumentException(LanguageAnalyzer.getWord(12));
        else
            getProgressBar().setValue(percent);
    }

    /**
     * This panel has labels and information about downloading file and is used in popOped frame .
     *
     * @return panel
     */
    private JPanel popOpWindowInformation() {

        ArrayList<JLabel> labels = new ArrayList<>();
        JProgressBar temp = new JProgressBar();
        JPanel popOpPanel = new JPanel(new GridLayout(9, 1, 4, 4));
        popOpPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 15));

        JLabel fileName = new JLabel(LanguageAnalyzer.getWord(13) + downloadingFile.getDownloadingFileName());
        JLabel fileSize = new JLabel(LanguageAnalyzer.getWord(14) + downloadingFile.getDownloadingFileSize());
        JLabel downloadedAmount = new JLabel(LanguageAnalyzer.getWord(15) + downloadingFile.getDownloadedAmount());
        JLabel downloadedPercent = new JLabel(LanguageAnalyzer.getWord(16) + downloadingFile.getDownloadedPercentString() + " %");
        JLabel downloadSpeed = new JLabel(LanguageAnalyzer.getWord(17) + downloadingFile.getDownloadSpeedString());
        JLabel URL = new JLabel(LanguageAnalyzer.getWord(18) + downloadingFile.getUrl());
        JLabel savingTo = new JLabel(LanguageAnalyzer.getWord(19) + downloadingFile.getFileSaveDirectory());
        JLabel startTime = new JLabel(LanguageAnalyzer.getWord(20) + downloadingFile.getStartTime());


        labels.add(fileName);
        labels.add(fileSize);
        labels.add(downloadedAmount);
        labels.add(downloadedPercent);
        labels.add(downloadSpeed);
        labels.add(URL);
        labels.add(savingTo);
        labels.add(startTime);

        for (JLabel label : labels) {
            Icon tempIcon = new ImageIcon(getClass().getResource("./GUI icons/small-ninja.png"));
            label.setIcon(tempIcon);
            popOpPanel.add(label);
        }

        temp.setMaximum(100);
        temp.setMinimum(0);
        temp.setValue((int) downloadingFile.getDownloadedPercent());
        temp.setStringPainted(true);
        popOpPanel.add(temp);

        return popOpPanel;
    }

    /**
     * @return isSelected (if it is returns true)
     */
    public boolean isSelected() {
        if (backgroundCounter % 2 == 0)
            downloadingFile.setIsSelected(false);
        else
            downloadingFile.setIsSelected(true);

        return downloadingFile.getIsSelected();
    }

    /**
     * This method changes pause state .
     */
    public void changePauseState() {
        if (downloadingFile.getIsCanceled() == false) {

            Icon tempIcon = new ImageIcon(getClass().getResource("./GUI icons/pause-small.png"));
            panel.remove(2);
            JLabel pausedInfoLabel = new JLabel(LanguageAnalyzer.getWord(21));
            pausedInfoLabel.setIcon(tempIcon);
            panel.add(pausedInfoLabel);
            downloadingFile.changePauseState();
        }
    }

    /**
     * This method changes resume state .
     */
    public void changeResumeState() {
        if (downloadingFile.getIsCanceled() == false) {

            panel.remove(2);
//            JLabel resumeInfoLabel = new JLabel(getFileInformationForPanel());
            panel.add(getFileInformationLabel());
            downloadingFile.changePauseState();
        }
    }

    /**
     * @return true if paused ind vise versa .
     */
    public boolean getIsPaused() {
        return downloadingFile.getIspaused();
    }

    /**
     * This method cancels download .
     */
    public void cancelDownload() {
        downloadingFile.cancelDownload();
        Icon tempIcon = new ImageIcon(getClass().getResource("./GUI icons/cancel-small.png"));
        panel.remove(2);
        JLabel cancelInfoLabel = new JLabel(LanguageAnalyzer.getWord(22));
        cancelInfoLabel.setIcon(tempIcon);
        panel.add(cancelInfoLabel);
    }


    /**
     * This method handles double click on panel .
     *
     * @param event
     */
    public void mouseClicked(MouseEvent event) {
        //handles double click on panel
        if (event.getClickCount() == 2) {
            System.out.println(downloadingFile.getFilePath());
            openFileOrFolderByPath(downloadingFile.getFilePath());
        }
    }

    /**
     * If mouse enters a panel .
     *
     * @param event
     */
    public void mouseEntered(MouseEvent event) {
        if (isSelected() == false)
            panel.setBackground(new Color(223, 223, 223));
    }

    /**
     * If mouse exits from panel .
     *
     * @param event
     */
    public void mouseExited(MouseEvent event) {
        if (isSelected() == false)
            panel.setBackground(firstPanelColor);
    }

    /**
     * if mouse is pressed in panel this method changes the background .
     *
     * @param event
     */
    public void mousePressed(MouseEvent event) {
        if (SwingUtilities.isRightMouseButton(event)) {

            JFrame popOpFrame = new JFrame("Downloading file information ");
            popOpFrame.setLocationRelativeTo(panel);
            popOpFrame.setVisible(true);
            popOpFrame.add(popOpWindowInformation());
            popOpFrame.pack();

        } else if (SwingUtilities.isLeftMouseButton(event)) {
            backgroundCounter++;
            if (backgroundCounter == 0) {
                backgroundColor = panel.getBackground();
            }
            if (backgroundCounter % 2 == 1) {
                panel.setBackground(Color.GRAY);
            } else {
                panel.setBackground(backgroundColor);
            }

        }
    }

    public void mouseReleased(MouseEvent event) {
    }

    /**
     * This method is use to return an icon with a given path .
     *
     * @param filePath
     * @return
     */
    private Icon getIcon(String filePath) {
        Icon tempIcon = new ImageIcon(getClass().getResource(filePath));
        return tempIcon;
    }

    /**
     * This method opens file or folder by path .
     *
     * @param path
     */
    private void openFileOrFolderByPath(String path) {
        if (downloadingFile.getIsCompleted() == true) {
            try {

                File file = new File(path + "/" + downloadingFile.getDownloadingFileName());
                Desktop desktop = Desktop.getDesktop();
                desktop.open(file);

            } catch (IOException | NullPointerException | IllegalArgumentException e1) {
                JOptionPane.showMessageDialog(panel, "Can't open file/path !");
            }
        }

        else {
            JOptionPane.showMessageDialog(null , "Wait until download finishes !");
        }
    }

    /**
     * This method opens file or folder .
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(folderButton)) {
            openFileOrFolderByPath(downloadingFile.getFileSaveDirectory());
        }
    }

    /**
     * This method sets URL .
     *
     * @param url
     */
    public void setUrl(String url) {
        downloadingFile.setUrl(url);
    }

    /**
     * @return name
     */
    public String getName() {
        return downloadingFile.getDownloadingFileName();
    }

    /**
     * @return URL
     */
    public String getUrl() {
        return downloadingFile.getUrl();
    }

    /**
     * @return Size
     */
    public double getSize() {
        return downloadingFile.getDownloadingFileSize();
    }

    /**
     * @return local time
     */
    public LocalTime getTimeToCompare() {
        return downloadingFile.getTimeForCompare();
    }

    public String getDirectory() {
        return downloadingFile.getFilePath();
    }

    public JLabel getFileInformationLabel() {
        return fileInformationLabel;
    }

    public void setDownloadingFileSize(Double size) {
        downloadingFile.setDownloadingFileSize(size);
    }

    public void setFileName(String name) {
        downloadingFile.setFileName(name);
    }

    public String getFileName() {
        return downloadingFile.getDownloadingFileName();
    }

    public void setProgress(int percent) {
        progressBar.setValue(percent);
    }

    public void changePausePrimary() {
        downloadingFile.changePausePrimary();
    }

    public void addDownloadThred(DownloadThread dt) {
        thisThread = dt;
    }

    public DownloadThread getDownloadThread() {
        return thisThread;
    }

    public void changeIsCompletedToCompleted() {
        downloadingFile.changeIsCompletedToCompleted();
    }


    public boolean getIsCompleted () {
        return downloadingFile.getIsCompleted() ;
    }

    public boolean getIsCanceled() {
        return downloadingFile.getIsCanceled() ;
    }

    public void setIsRemovedToTrue () {
        isRemoved = true ;
    }

    public boolean getIsRemoved () {
        return isRemoved ;
    }

}