package GUI;

import Interface.IClickOnRemoveFromBlockedList;
import Logic.FileProcess;
import Logic.LanguageAnalyzer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class implements a frame and a panel in it for all blocked sites .
 */
public class BlockedSitesFrame implements IClickOnRemoveFromBlockedList {

    private JFrame frame;
    private JPanel panel;
    private ArrayList<BlockedSitePanel> blockedSites  ;
    private JFrame relationToThisFrame ;

    /**
     * This constructor invokes methods to initialize panel and components .
     * @param relationToThisFrame
     */
    public BlockedSitesFrame(JFrame relationToThisFrame) {
        initFrame(relationToThisFrame);
        initPanel();
        initBlockedList();

        this.relationToThisFrame = relationToThisFrame ;

        frame.add(panel);
        frame.pack();
    }

    /**
     * This method initializes addresses ArrayList  .
     */
    private void initBlockedList() {
        ArrayList<String> addresses;
        blockedSites = new ArrayList<>() ;
        addresses = FileProcess.getBlockedSitesAsArray("./saves/blocked.txt");

        panel.setLayout(new GridLayout(addresses.size(), 1, 4, 4));

        for (String address : addresses) {
            BlockedSitePanel tempBlockedSitePanel = new BlockedSitePanel(address);
            tempBlockedSitePanel.setClickOnRemoveFromBlockedList(this);
            blockedSites.add(tempBlockedSitePanel) ;
        }

        addBlockedSitesToPanel();
    }

    /**
     * This method adds blocked sites to panel .
     */
    private void addBlockedSitesToPanel () {
        JPanel previousPanel = panel ;

        initPanel();
        panel.setLayout(new GridLayout(blockedSites.size(), 1, 4, 4));
        for (BlockedSitePanel bsp : blockedSites) {
            panel.add(bsp.getPanel()) ;
        }

        frame.remove(previousPanel) ;
        frame.add(panel) ;
        frame.pack();
    }

    /**
     * This method initializes panel and makes it visible .
     * @param relationToThisFrame
     */
    private void initFrame(JFrame relationToThisFrame) {
        frame = new JFrame(LanguageAnalyzer.getWord(0));
        frame.setVisible(true);
        frame.setLocationRelativeTo(relationToThisFrame);
    }

    /**
     * This method initializes panel .
     */
    private void initPanel() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
    }


    /**
     * This method removes site from black list .
     * @param name
     */
    private void removeBlockedSiteFromBlockedSites (String name) {
        for (int count = 0 ; count < blockedSites.size() ; count++) {
            if (blockedSites.get(count).getUrl().equals(name)) {
                blockedSites.remove(count) ;
                count-- ;
            }
        }

        addBlockedSitesToPanel();

        if (blockedSites.size() == 0) {
            panel.add(new JLabel(LanguageAnalyzer.getWord(1))) ;
            frame.pack();
        }

        FileProcess.writeArrayListToFile("./saves/blocked.txt" , blockedSites) ;
    }

    @Override
    public void clickOnRemove(String name) {
        removeBlockedSiteFromBlockedSites(name);
    }

}
