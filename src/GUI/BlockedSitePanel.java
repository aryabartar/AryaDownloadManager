package GUI;

import Interface.IClickOnRemoveFromBlockedList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

/**
 * This class implements a panel for a blocked site .
 */
public class BlockedSitePanel implements ActionListener {

    private String url;
    private JPanel panel;
    private JButton removeButton;
    private IClickOnRemoveFromBlockedList clickOnRemoveFromBlockedList;
    private boolean isRemoved = false;

    /**
     * This constructor initialize panel and components .
     *
     * @param ulr
     */
    public BlockedSitePanel(String ulr) {
        this.url = ulr;
        initPanel();
        initUrlIcon();
        initRemoveButton();
        initUrl();
    }

    /**
     * This method initialize panel .
     */
    private void initPanel() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    }

    /**
     *
     * @return panel
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * This method initializes Url label .
     */
    private void initUrl() {
        JLabel urlLabel = new JLabel(url);
        panel.add(urlLabel, BorderLayout.CENTER);
    }

    /**
     * This method initializes icon label .
     */
    private void initUrlIcon() {
        Icon tempIcon = getIconByPath("./GUI icons/url.png");

        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(tempIcon);

        panel.add(iconLabel, BorderLayout.WEST);
    }

    /**
     * This method initializes remove button and adds action listener .
     */
    private void initRemoveButton() {
        Icon tempIcon = getIconByPath("./GUI icons/small-remove.png");
        removeButton = new JButton();
        removeButton.setIcon(tempIcon);
        removeButton.addActionListener(this);

        panel.add(removeButton, BorderLayout.EAST);
    }

    /**
     * This method is used to get Icon object by given path .
     * @param path
     * @return
     */
    private Icon getIconByPath(String path) {
        Icon tempIcon = new ImageIcon(getClass().getResource(path));
        return tempIcon;
    }

    /**
     * This method sets clickOnRemoveFromBlockedList interface .
     * @param clickOnRemoveFromBlockedList
     */
    public void setClickOnRemoveFromBlockedList(IClickOnRemoveFromBlockedList clickOnRemoveFromBlockedList) {
        this.clickOnRemoveFromBlockedList = clickOnRemoveFromBlockedList;
    }

    /**
     *
     * @return url
     */
    public String getUrl() {
        return url;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(removeButton)) {
            isRemoved = true;
            clickOnRemoveFromBlockedList.clickOnRemove(url);
        }
    }
}
