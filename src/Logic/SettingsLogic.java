package Logic;

import java.io.Serializable;

/**
 * This method implements logic for settings frame .
 */
public class SettingsLogic implements Serializable {

    private  int maximumDownload = 100 ;
    private  String path ;
    private boolean isDefaultLookAndFeel = true ;

    public SettingsLogic (int maximumDownload , String path , Boolean isDefaultLookAndFeel) {
        this.isDefaultLookAndFeel = isDefaultLookAndFeel ;
        this.maximumDownload = maximumDownload ;
        this.path = path ;
    }

    public void setDefaultLookAndFeel(boolean defaultLookAndFeel) {
        isDefaultLookAndFeel = defaultLookAndFeel;
    }

    /**
     *
     * @return maximum download .
     */
    public int getMaximumDownload() {
        return maximumDownload;
    }

    /**
     *
     * @return saving path
     */
    public String getPath() {
        return path;
    }

    /**
     * @return isDefaultLookAndFeel .
     */
    public boolean getIsDefaultLookAndFeel() {
        return isDefaultLookAndFeel;
    }
}
