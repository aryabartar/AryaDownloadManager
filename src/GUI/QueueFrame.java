package GUI;

import Interface.IClickOnQueueToolBar;
import Logic.DownloadingFile;

import javax.swing.*;
import java.awt.*;

/**
 * This class implements a QueueFrame and is similar to queue .
 */
public class QueueFrame implements IClickOnQueueToolBar {
    private JFrame frame;
    private JPanel panel;
    private QueueToolBar queueToolBar;
    private DownloadingFilePanel downloadingFilePanel;

    /**
     * This is QueueFrame constructor and invokes methods to instantiate classes .
     *
     * @param downloadingFilePanel
     * @param relationTo
     */
    public QueueFrame(DownloadingFilePanel downloadingFilePanel, JFrame relationTo) {
        initFrame(relationTo);
        initPanel();
        panel.add(downloadingFilePanel.getPanel(), BorderLayout.CENTER);

        this.downloadingFilePanel = downloadingFilePanel;
        queueToolBar = new QueueToolBar();
        queueToolBar.setClickOnQueueToolBar(this);
        panel.add(queueToolBar.getToolbar(), BorderLayout.NORTH);

        downloadingFilePanel.resumeFirstDownloadAndPauseOthers();
        frame.pack();
    }


    /**
     * This method initializes frame .
     *
     * @param relationTo
     */
    private void initFrame(JFrame relationTo) {
        frame = new JFrame("Queue");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(relationTo);
    }

    /**
     * This method initializes panel .
     */
    private void initPanel() {
        panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new BorderLayout(5, 5));
    }

    public void handleFinishedDownloadInQueue () {

        refreshPanel(6);
    }
    /**
     * This method refreshes panel with given flag number .
     * Each flag number performs special work .
     *
     * @param flag
     */
    private void refreshPanel(int flag) {
        frame.remove(panel);
        panel.remove(downloadingFilePanel.getPanel());

        if (flag == 0)
            downloadingFilePanel.upperFile();
        else if (flag == 1)
            downloadingFilePanel.downFile();
        else if (flag == 2)
            downloadingFilePanel.pauseSelectedFiles();
        else if (flag == 3)
            downloadingFilePanel.removeSelectedFiles();
        else if (flag == 4)
            downloadingFilePanel.resumeSelectedFiles();
        else if (flag == 5)
            downloadingFilePanel.cancelSelectedFiles();
        else if(flag == 6)
            downloadingFilePanel.handleFinishedDownloadInQueue();

        downloadingFilePanel.resumeFirstDownloadAndPauseOthers();
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(queueToolBar.getToolbar(), BorderLayout.NORTH);
        panel.add(downloadingFilePanel.getPanel(), BorderLayout.CENTER);

        frame.add(panel);
        frame.pack();
    }

    @Override
    public void clickOnUp() {
        refreshPanel(0);

    }

    @Override
    public void clickOnDown() {
        refreshPanel(1);
    }

    @Override
    public void clickOnPause() {
        refreshPanel(2);
    }

    @Override
    public void clickOnRemove() {
        refreshPanel(3);
    }

    @Override
    public void clickOnResume() {
        refreshPanel(4);
    }

    @Override
    public void clickOnCancel() {
        refreshPanel(5);
    }


}
