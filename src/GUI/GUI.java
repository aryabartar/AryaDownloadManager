package GUI;

import Interface.*;

import Logic.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * This class is main GUI class and instantiates all other classes .
 */
public class GUI implements IClickOnMenu, IClickOnDownloadFrame, IChangeLookAndFeel
        , IClickOnLeftPanel, IClickOnSettingButtons, IClickOnSortFrame, IChangeLanguage, IFinishNotifier {

    private JFrame firstWindowFrame;
    private DownloadingFilePanel downloadingFilePanel;
    private DownloadingFilePanel downloadingFileQueuePanel;
    private JPanel panel;
    private ToolBar toolBar;
    private LeftPanel leftPanel;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private QueueFrame queueFrame;


    /**
     * This constructor initializes all other classes .
     */
    public GUI() {

        LanguageAnalyzer.getLanguageAsString();

        firstWindowFrame = new JFrame("Arya Download Manager");
        downloadingFilePanel = new DownloadingFilePanel();
        initializePanel();
        initLeftPanel();
        initializeToolBar();
        initSettings();


        MenuBar menuBar = new MenuBar();
        menuBar.addClickOnMenuListener(this);
        firstWindowFrame.setJMenuBar(menuBar.getMenuBar());

        initFilesFromLastSave();

        downloadingFileQueuePanel = new DownloadingFilePanel();

        initQueueFilesFromLastSave();

        showFirstFrame();

        systemTray();
        downloadingFilePanel.sortByDate(true);
        updateFirstFrame(downloadingFilePanel.getPanel());
    }

    /**
     * This method is used to initialize saved settings from last run .
     */
    private void initSettings() {
        SettingsLogic tempSettingsLogic = (SettingsLogic) ObjectSerializer.getObject("./saves/settings.adm");
        File settingsFile = new File("./saves/settings.adm");

        if ((settingsFile.exists() == true) && (ObjectSerializer.getObject("./saves/settings.adm") instanceof SettingsLogic)) {
            SettingsFrame.setMaximumDownloadNumbers(tempSettingsLogic.getMaximumDownload());
            SettingsFrame.setSavingLocation(tempSettingsLogic.getPath());

            if (tempSettingsLogic.getIsDefaultLookAndFeel() == false) {
                System.out.println("false");
                changeLookAndFeelToNimbus(firstWindowFrame);
                SettingsFrame.setIsDefaultLookAndFeel(false);
            } else {
                System.out.println("true");
                changeLookAndFeelToDefault(firstWindowFrame);
                SettingsFrame.setIsDefaultLookAndFeel(true);

            }
        } else {
            System.err.println("Settings file not found !");
            changeLookAndFeelToDefault(firstWindowFrame);
        }
    }

    /**
     * This method is used to initialize files from last save .
     */
    private void initFilesFromLastSave() {
        ArrayList<DownloadingFileGui> downloadingFileGui = null;
        downloadingFileGui = ObjectSerializer.getSerializedArrayList("./saves/list.adm");

        if (downloadingFileGui != null)
            for (DownloadingFileGui dfg : downloadingFileGui)
                addRealFile(dfg);
    }

    /**
     * This method is used to initialize queue from the last save .
     */
    private void initQueueFilesFromLastSave() {
        ArrayList<DownloadingFileGui> downloadingFileGui = null;

        downloadingFileGui = ObjectSerializer.getSerializedArrayList("./saves/queueList.adm");

        if (downloadingFileGui != null)
            for (DownloadingFileGui dfg : downloadingFileGui)
                addRealFileToQueue(dfg);
    }


    /**
     * This method initializes toolbar .
     */
    public void initializeToolBar() {
        toolBar = new ToolBar();
        panel.add(toolBar.getToolbar(), BorderLayout.NORTH);
        toolBar.addClickOnMenu(this);
    }

    /**
     * This method will set firstWindowFrame to visible .
     */
    protected void showFirstFrame() {
        java.net.URL imgUrl = getClass().getResource("./GUI icons/main-icon.png");
        ImageIcon icon = new ImageIcon(imgUrl);
        firstWindowFrame.setIconImage(icon.getImage());

        Dimension screenSize1 = Toolkit.getDefaultToolkit().getScreenSize();
        int height1 = screenSize1.height;
        int width1 = screenSize1.width;
        firstWindowFrame.setSize(width1 / 2, height1 / 2);

        firstWindowFrame.setVisible(true);
        firstWindowFrame.setLocationRelativeTo(null);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height * 2 / 3;
        int width = screenSize.width * 2 / 3;
        firstWindowFrame.setPreferredSize(new Dimension(width, height));

        firstWindowFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                systemTray();
                e.getWindow().dispose();
            }
        });
    }

    /**
     * This method initializes panel .
     */
    private void initializePanel() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout(3, 3));
        panel.add(downloadingFilePanel.getPanel(), BorderLayout.CENTER);
        firstWindowFrame.add(panel);
    }

    /**
     * This method initializes left panel .
     */
    public void initLeftPanel() {
        leftPanel = new LeftPanel();
        leftPanel.setClickOnLeftPanel(this);
        panel.add(leftPanel.getPanel(), BorderLayout.WEST);
    }

    /**
     * This static method is used to change frame look and feel to nimbus .
     *
     * @param thisFrame changes this frame look and feel .
     */
    public static void changeLookAndFeelToNimbus(JFrame thisFrame) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e1) {
            System.out.println("Nimbus exception !");
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                System.out.println("Nimbus exception !");
            }
        }
        SwingUtilities.updateComponentTreeUI(thisFrame);
    }

    /**
     * This static method is used to change frame look and feel to default .
     *
     * @param thisFrame changes this frame look and feel .
     */
    public static void changeLookAndFeelToDefault(JFrame thisFrame) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(thisFrame);
        } catch (Exception e1) {
            System.out.println("Look and feel exception !");
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                System.out.println("Look and feel exception !");
            }
        }
    }

    /**
     * This method adds a file to the queue .
     *
     * @param fileName
     * @param url
     * @param size
     */
    public void addFileToQueue(String fileName, String url, double size) {
        DownloadingFileGui file = new DownloadingFileGui(fileName, size);
        file.setUrl(url);
        file.changePauseState();

        DownloadThread tempDownloadThread = new DownloadThread(file.getUrl(), file.getDirectory(), file);
        tempDownloadThread.setIfinishNotifier(this);
        file.addDownloadThred(tempDownloadThread);
        executorService.execute(tempDownloadThread);

        downloadingFileQueuePanel.addDownloadingFile(file);

    }

    public void addRealFileToQueue(DownloadingFileGui dfg) {
        downloadingFileQueuePanel.addDownloadingFile(dfg);
    }

    /**
     * This method adds a file to main panel .
     *
     * @param fileName
     * @param url
     * @param size
     */
    public void addFile(String fileName, String url, double size) {
        if (downloadingFilePanel.getNumberOfResumedFilesInPanel() < SettingsFrame.getMaximumDownloadNumbers()) {
            DownloadingFileGui file = new DownloadingFileGui(fileName, size);
            file.setUrl(url);
            downloadingFilePanel.addDownloadingFile(file);

            DownloadThread tempDownloadThread = new DownloadThread(file.getUrl(), file.getDirectory(), file);
            file.addDownloadThred(tempDownloadThread);

            tempDownloadThread.setIfinishNotifier(this);
            executorService.execute(tempDownloadThread);


            updateFirstFrame(downloadingFilePanel.getPanel());
        } else {
            JOptionPane.showMessageDialog(firstWindowFrame, "Your download is reached to maximum size !", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is used to add file to panel .
     *
     * @param dfg
     */
    public void addRealFile(DownloadingFileGui dfg) {
        if (downloadingFilePanel.getNumberOfResumedFilesInPanel() < SettingsFrame.getMaximumDownloadNumbers()) {
            downloadingFilePanel.addDownloadingFile(dfg);
            panel.add(downloadingFilePanel.getPanel(), BorderLayout.CENTER);

            firstWindowFrame.add(panel);
            updateFirstFrame(downloadingFilePanel.getPanel());
        } else {
            JOptionPane.showMessageDialog(firstWindowFrame, "Your download is reached to maximum size !", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * This method is used to search in list.
     *
     * @param searchText
     */
    private void searchInList(String searchText) {

        updateFirstFrame(downloadingFilePanel.getPanelWithStringInUrlOrName(searchText));

        System.out.println("SEARCHING");
    }

    /**
     * This method is used to update first panel .
     */
    private void updateFirstPanel() {
        JPanel previousPanel = panel;

        initializePanel();
        panel.add(toolBar.getToolbar(), BorderLayout.NORTH);
        panel.add(downloadingFilePanel.getPanel(), BorderLayout.CENTER);
        panel.add(leftPanel.getPanel(), BorderLayout.WEST);

        firstWindowFrame.remove(previousPanel);
        firstWindowFrame.add(panel);

        firstWindowFrame.pack();
    }

    /**
     * This method is used to open settings frame .
     */
    private void openSettings() {

        SettingsFrame settingsFrame = new SettingsFrame(firstWindowFrame);

        settingsFrame.addChangeLookAndFeel(this);
        settingsFrame.addIClickOnSettingButtons(this);
        settingsFrame.setChangeLanguage(this);
    }

    /**
     * This method is used in close sign of the frame .
     */
    private void systemTray() {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            PopupMenu ADM = new PopupMenu();
            MenuItem item = new MenuItem("");
            ADM.add(item);

            Image icon = null;
            try {
                icon = ImageIO.read(getClass().getResource("./GUI icons/main-icon.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            TrayIcon trayIcon = new TrayIcon(icon, "ADM", ADM);
            trayIcon.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    showFirstFrame();
                    tray.remove(trayIcon);
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }

    private void serializeDownloadingFiles() {
        ObjectSerializer.serializeObject(downloadingFilePanel.getArrayList(), "./saves/list.adm");

    }

    private void serializeDownloadingFilesQueue() {
        ObjectSerializer.serializeObject(downloadingFileQueuePanel.getArrayList(), "./saves/queueList.adm");
    }


    @Override
    public void clickOnDownload() {
        System.out.println("Download");
        DownloadFrame downloadFrame = new DownloadFrame();
        downloadFrame.addClickOnDownloadFrame(this);
        downloadFrame.setRelationTo(firstWindowFrame);
    }

    @Override
    public void clickOnPause() {
        System.out.println("Pause");
        downloadingFilePanel.pauseSelectedFiles();
        updateFirstFrame(downloadingFilePanel.getPanel());
    }

    @Override
    public void clickResume() {
        System.out.println("Resume");
        downloadingFilePanel.resumeSelectedFiles();
        updateFirstFrame(downloadingFilePanel.getPanel());
    }

    @Override
    public void clickCancel() {
        System.out.println("Cancel");
        downloadingFilePanel.cancelSelectedFiles();
        updateFirstFrame(downloadingFilePanel.getPanel());
    }

    @Override
    public void clickRemove() {
        System.out.println("Remove");
        downloadingFilePanel.removeSelectedFiles();

        JPanel previousPanel = panel;

        initializePanel();
        panel.add(toolBar.getToolbar(), BorderLayout.NORTH);
        panel.add(downloadingFilePanel.getPanel(), BorderLayout.CENTER);
        panel.add(leftPanel.getPanel(), BorderLayout.WEST);

        firstWindowFrame.remove(previousPanel);
        firstWindowFrame.add(panel);
        SwingUtilities.updateComponentTreeUI(firstWindowFrame);

    }


    @Override
    public void clickSettings() {
        System.out.println("Settings");
        openSettings();

    }

    @Override
    public void clickOnAbout() {
        System.out.println("About");
        new HelpFrame(firstWindowFrame);
    }

    @Override
    public void clickOnExport() {
        new ZipUtils();
    }

    @Override
    public void clickOnExit() {
        System.out.println("Exit");
        serializeDownloadingFiles();
        serializeDownloadingFilesQueue();

        SettingsLogic tempSettingsLogic = new SettingsLogic(SettingsFrame.getMaximumDownloadNumbers(),
                SettingsFrame.getSavingLocation(), SettingsFrame.getIsDefaultLookAndFeel());
        ObjectSerializer.serializeObject(tempSettingsLogic, "./saves/settings.adm");

        System.exit(0);
    }


    /**
     * This method will show all the downloads in panel .
     */
    public void returnToMainPanel() {

        updateFirstFrame(downloadingFilePanel.getPanel());
    }

    @Override
    public void clickOnSearch(String searchText) {
        if (searchText.equals("")) {
            downloadingFilePanel.returnAllPanelsToMainPanel();
            returnToMainPanel();
        } else {
            searchInList(searchText);
        }
    }

    @Override
    public void clickOnSort() {
        SortFrame sortFrame = new SortFrame(firstWindowFrame);
        sortFrame.setClickOnSortFrame(this);
    }

    @Override
    public void clickOnOk(String text, String url) {
        addFile(text, url, 4);
    }

    @Override
    public void clickOnAddToQueue(String text, String url) {
        addFileToQueue(text, url, 4);
    }

    @Override
    public void changeToNimbus() {
        changeLookAndFeelToNimbus(firstWindowFrame);
    }

    @Override
    public void changeToDefault() {
        changeLookAndFeelToDefault(firstWindowFrame);
    }

    @Override
    public void clickOnOk() {
        int maximumDownloadNumber = SettingsFrame.getMaximumDownloadNumbers();
        downloadingFilePanel.setMaximumDownloadSize(maximumDownloadNumber);
    }

    @Override
    public void clickOnQueue() {
        queueFrame = new QueueFrame(downloadingFileQueuePanel, firstWindowFrame);
    }


    @Override
    public void clickOnOkInSortFrame(int key, boolean isUp) {
        //0 => sort by name / 1 => date / 2 => size
        //3 => name and date / 4 => name and size
        // second parameter is for up and down

        //Sort by name
        if (key == 0) {
            downloadingFilePanel.sortByName(isUp);
            updateFirstFrame(downloadingFilePanel.getPanel());
        }

        //sort by date
        if (key == 1) {
            downloadingFilePanel.sortByDate(isUp);
            updateFirstFrame(downloadingFilePanel.getPanel());
        }

        //Sort by size
        if (key == 2) {
            downloadingFilePanel.sortBySize(isUp);
            updateFirstFrame(downloadingFilePanel.getPanel());
        }

        if (key == 3) {
            downloadingFilePanel.sortByNameAndDate(isUp);
            updateFirstFrame(downloadingFilePanel.getPanel());
        }

        if (key == 4) {
            downloadingFilePanel.sortByNameAndSize(isUp);
            updateFirstFrame(downloadingFilePanel.getPanel());
        }

        if (key == 5) {
            downloadingFilePanel.sortByDateAndSize(isUp);
            updateFirstFrame(downloadingFilePanel.getPanel());
        }
        if (key == 6) {
            downloadingFilePanel.sortByNameAndSize(isUp);
            updateFirstFrame(downloadingFilePanel.getPanel());
        }
    }

    @Override
    public void clickOnEnglish() {
        FileProcess.changeLanguage("English.txt");
    }

    @Override
    public void clickOnPersian() {
        FileProcess.changeLanguage("Persian.txt");
    }


    private void updateFirstFrame(JScrollPane thisPanel) {
        JPanel previousPanel = panel;

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(thisPanel, BorderLayout.CENTER);
        panel.add(toolBar.getToolbar(), BorderLayout.NORTH);
        panel.add(leftPanel.getPanel(), BorderLayout.WEST);

        firstWindowFrame.remove(previousPanel);
        firstWindowFrame.add(panel);
        SwingUtilities.updateComponentTreeUI(firstWindowFrame);
    }

    @Override
    public void finished() {
        if (queueFrame != null)
            queueFrame.handleFinishedDownloadInQueue();
    }
}
