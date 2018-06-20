package GUI;

import Logic.DownloadThread;
import Logic.DownloadingFile;
import Logic.ObjectSerializer;
import javafx.scene.shape.Path;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;


public class DownloadingFilePanel implements Serializable {

    private ArrayList<DownloadingFileGui> files;
    private JPanel panel;
    private int maximumDownloadSize;


    /**
     * This constructor invokes methods to initialize fields .
     */
    public DownloadingFilePanel() {
        files = new ArrayList<>();
        initializePanel();
    }

    /**
     * This method initializes panel .
     */
    private void initializePanel() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(4, 5, 5, 2));
        panel.setLayout(new GridLayout(files.size(), 1));
    }

    /**
     * This method adds a DownloadingFileGui to files ArrayList .
     *
     * @param file
     */
    public void addDownloadingFile(DownloadingFileGui file) {


        files.add(file);

        panel.setLayout(new GridLayout(files.size(), 1));
        panel.add(file.getPanel());

    }


    /**
     * This method returns a panel containing downloading files with this string
     * in name or url .
     */
    public JScrollPane getPanelWithStringInUrlOrName(String searchThisText) {
        JPanel tempPanel = new JPanel();
        ArrayList<DownloadingFileGui> tempArray = new ArrayList<>();

        for (DownloadingFileGui dfg : files)
            if ((dfg.getName().contains(searchThisText) || (dfg.getUrl().contains(searchThisText))))
                tempArray.add(dfg);

        tempPanel.setLayout(new GridLayout(tempArray.size(), 1, 5, 5));

        for (DownloadingFileGui dfg : tempArray)
            tempPanel.add(dfg.getPanel());

        return getScrolledPanel(tempPanel);
    }

    public void returnAllPanelsToMainPanel() {
        initializePanel();

        for (DownloadingFileGui dfg : files) {
            panel.add(dfg.getPanel());
        }
    }

    /**
     * This method returns panel .
     *
     * @return
     */
    public JScrollPane getPanel() {
        JScrollPane tempScrollPane = new JScrollPane(panel);
        tempScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        return tempScrollPane;
    }

    private JScrollPane getScrolledPanel(JPanel thisPanel) {
        JScrollPane tmp = new JScrollPane(thisPanel);
        tmp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return tmp;
    }

    /**
     * This method pauses selected files .
     */
    public void pauseSelectedFiles() {
        for (DownloadingFileGui dfg : files) {
            if (dfg.isSelected() == true) {
                dfg.changePauseState();
            }
        }
    }

    /**
     * This method resumes selected files .
     */
    public void resumeSelectedFiles() {
        for (DownloadingFileGui dfg : files) {
            if (dfg.isSelected() == true) {
                dfg.changeResumeState();
                dfg.getDownloadThread().resume();
            }
        }
    }

    /**
     * This method cancels selected files ,
     */
    public void cancelSelectedFiles() {
        for (DownloadingFileGui dfg : files) {
            if (dfg.isSelected() == true) {
                dfg.cancelDownload();
            }
        }
    }

    /**
     * This method removes selected files .
     */
    public void removeSelectedFiles() {
        for (int count = 0; count < files.size(); count++) {
            if (files.get(count).isSelected() == true) {
                ObjectSerializer.saveRemovedFile(files.get(count));
                files.get(count).setIsRemovedToTrue();
                files.remove(count);
                count--;
            }
        }

        initializePanel();
        panel.setLayout(new GridLayout(files.size(), 1));
        for (DownloadingFileGui dfg : files) {
            panel.add(dfg.getPanel());
        }
    }

    /**
     * This method uppers selected files .
     */
    public void upperFile() {
        for (int count = 0; count < files.size(); count++) {
            if (files.get(count).isSelected() == true) {
                if (count == 0)
                    continue;
                DownloadingFileGui tempDownloadingFileGui = files.get(count - 1);
                files.set(count - 1, files.get(count));
                files.set(count, tempDownloadingFileGui);
            }
        }
        if (files.get(0).getIsCompleted() == false) {
            files.get(0).changePausePrimary();
        }

        initializePanel();
        updatePanel();

    }

    public void handleFinishedDownloadInQueue() {
        if (files.size() > 0) {
            DownloadingFileGui dfg = files.get(0);
            files.remove(0);
            files.add(dfg);


            initializePanel();

            int filesSize = files.size();
            files.get(0).changePauseState();
            for (int count = 0; count < filesSize; count++) {
                panel.setLayout(new GridLayout(files.size(), 1));
                panel.add(files.get(count).getPanel());
            }

            System.out.println("LKJLOJLOIJOOLJNOOLIOJNOLJOLJOIOJ");
        }

    }

    /**
     * This method is used to update panel .
     */
    private void updatePanel() {
        int filesSize = files.size();

        for (int count = 0; count < filesSize; count++) {
            panel.setLayout(new GridLayout(files.size(), 1));
            panel.add(files.get(count).getPanel());
        }
    }


    /**
     * This method lowers the selected file.
     */
    public void downFile() {
        for (int count = files.size() - 1; count >= 0; count--) {
            if (files.get(count).isSelected() == true) {
                if (count == files.size() - 1)
                    continue;

                DownloadingFileGui tempDownloadingFileGui = files.get(count + 1);

                files.set(count + 1, files.get(count));
                files.set(count, tempDownloadingFileGui);

                System.out.println(files.get(count + 1).getFileInformation());
                System.out.println("count = " + count);
            }
        }

        initializePanel();
        int filesSize = files.size();

        for (int count = 0; count < filesSize; count++) {
            panel.setLayout(new GridLayout(files.size(), 1));
            panel.add(files.get(count).getPanel());
        }
    }

    /**
     * This method sets maximum downloads to the given integer value .
     *
     * @param maximumDownloadSize
     */
    public void setMaximumDownloadSize(int maximumDownloadSize) {
        this.maximumDownloadSize = maximumDownloadSize;
    }

    /**
     * This method returns number of resumed files .
     *
     * @return
     */
    public int getNumberOfResumedFilesInPanel() {
        int resumedCounter = 0;

        for (DownloadingFileGui dfg : files) {
            if (dfg.getIsPaused() == false)
                resumedCounter++;
        }
        return resumedCounter;
    }

    /**
     * This method is used in queue and resumes first file and pauses others .
     */
    public void resumeFirstDownloadAndPauseOthers() {
        for (int count = 0; count < files.size(); count++) {
            if (count == 0) {
                files.get(0).changeResumeState();
                files.get(0).changePausePrimary();
            } else {
                files.get(count).changePauseState();
            }
        }
    }

    /**
     * @return files
     */
    public ArrayList<DownloadingFileGui> getArrayList() {
        return files;
    }

    /**
     * This method is used to sort files by name .
     *
     * @param isUp
     */
    public void sortByName(Boolean isUp) {
        DownloadingFileGui[] tempArray = new DownloadingFileGui[files.size()];

        int count = 0;
        for (DownloadingFileGui dfg : files) {
            tempArray[count] = dfg;
            count++;
        }

        DownloadingFileGui tempDfg;
        for (int i = 0; i < tempArray.length; i++) {
            for (int j = i + 1; j < tempArray.length; j++) {
                if (tempArray[i].getName().compareTo(tempArray[j].getName()) > 0) {
                    tempDfg = tempArray[i];
                    tempArray[i] = tempArray[j];
                    tempArray[j] = tempDfg;
                }
            }
        }

        putArrayElementsToArrayList(tempArray, isUp);

        updatePanel();
    }

    /**
     * This method is used to sort files by size .
     *
     * @param isUp
     */
    public void sortBySize(Boolean isUp) {
        DownloadingFileGui[] tempArray = new DownloadingFileGui[files.size()];

        int count = 0;
        for (DownloadingFileGui dfg : files) {
            tempArray[count] = dfg;
            count++;
        }

        DownloadingFileGui tempDfg;
        for (int i = 0; i < tempArray.length; i++) {
            for (int j = i + 1; j < tempArray.length; j++) {
                if (tempArray[i].getSize() > tempArray[j].getSize()) {
                    tempDfg = tempArray[i];
                    tempArray[i] = tempArray[j];
                    tempArray[j] = tempDfg;
                }
            }
        }

        putArrayElementsToArrayList(tempArray, isUp);
        updatePanel();
    }

    /**
     * This method sorts files by date .
     *
     * @param isUp
     */
    public void sortByDate(boolean isUp) {
        DownloadingFileGui[] tempArray = new DownloadingFileGui[files.size()];

        int count = 0;
        for (DownloadingFileGui dfg : files) {
            tempArray[count] = dfg;
            count++;
        }

        DownloadingFileGui tempDfg;
        for (int i = 0; i < tempArray.length; i++) {
            for (int j = i + 1; j < tempArray.length; j++) {
                if (tempArray[i].getTimeToCompare().compareTo(tempArray[j].getTimeToCompare()) > 0) {
                    tempDfg = tempArray[i];
                    tempArray[i] = tempArray[j];
                    tempArray[j] = tempDfg;
                }
            }
        }

        putArrayElementsToArrayList(tempArray, isUp);
        updatePanel();
    }

    /**
     * This method sorts files by name and date .
     *
     * @param isUp
     */
    public void sortByNameAndDate(boolean isUp) {
        sortByName(isUp);

        DownloadingFileGui[] tempArray = new DownloadingFileGui[files.size()];

        int count = 0;
        for (DownloadingFileGui dfg : files) {
            tempArray[count] = dfg;
            count++;
        }

        DownloadingFileGui tempDfg;
        for (int i = 0; i < tempArray.length; i++) {
            for (int j = i + 1; j < tempArray.length; j++) {
                if ((tempArray[i].getTimeToCompare().compareTo(tempArray[j].getTimeToCompare()) > 0) &&
                        (tempArray[i].getName().equals(tempArray[j].getName()))) {
                    tempDfg = tempArray[i];
                    tempArray[i] = tempArray[j];
                    tempArray[j] = tempDfg;
                }
            }
        }

        putArrayElementsToArrayList(tempArray, isUp);
        updatePanel();
    }

    /**
     * This method sorts files by name and size .
     *
     * @param isUp
     */
    public void sortByNameAndSize(boolean isUp) {
        sortByName(isUp);

        DownloadingFileGui[] tempArray = new DownloadingFileGui[files.size()];

        int count = 0;
        for (DownloadingFileGui dfg : files) {
            tempArray[count] = dfg;
            count++;
        }

        DownloadingFileGui tempDfg;
        for (int i = 0; i < tempArray.length; i++) {
            for (int j = i + 1; j < tempArray.length; j++) {
                if ((tempArray[i].getSize() > tempArray[j].getSize()) &&
                        (tempArray[i].getName().equals(tempArray[j].getName()))) {
                    tempDfg = tempArray[i];
                    tempArray[i] = tempArray[j];
                    tempArray[j] = tempDfg;
                }
            }
        }

        putArrayElementsToArrayList(tempArray, isUp);
        updatePanel();
    }

    public void sortByDateAndSize(boolean isUp) {
        sortByName(isUp);

        DownloadingFileGui[] tempArray = new DownloadingFileGui[files.size()];

        int count = 0;
        for (DownloadingFileGui dfg : files) {
            tempArray[count] = dfg;
            count++;
        }

        DownloadingFileGui tempDfg;
        for (int i = 0; i < tempArray.length; i++) {
            for (int j = i + 1; j < tempArray.length; j++) {
                if ((tempArray[i].getSize() > tempArray[j].getSize()) &&
                        (tempArray[i].getTimeToCompare().compareTo(tempArray[j].getTimeToCompare()) > 0)) {
                    tempDfg = tempArray[i];
                    tempArray[i] = tempArray[j];
                    tempArray[j] = tempDfg;
                }
            }
        }

        putArrayElementsToArrayList(tempArray, isUp);
        updatePanel();
    }

    /**
     * This method is used in sort methods and puts array elements into ArrayList .
     *
     * @param tempArray
     * @param isUp
     */
    private void putArrayElementsToArrayList(DownloadingFileGui[] tempArray, boolean isUp) {
        if (isUp == true) {
            files = new ArrayList<>();
            for (int i = 0; i < tempArray.length; i++) {
                files.add(tempArray[i]);
            }
        } else if (isUp == false) {
            files = new ArrayList<>();
            for (int i = tempArray.length - 1; i > -1; i--) {
                files.add(tempArray[i]);
            }
        }
    }


}
