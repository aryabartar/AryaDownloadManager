package Interface;

import java.io.Serializable;

public interface IClickOnMenu extends Serializable {
    public void clickOnDownload() ;
    public void clickOnPause () ;
    public void clickResume () ;
    public void clickCancel () ;
    public void clickRemove () ;
    public void clickSettings () ;
    public void clickOnAbout () ;
    public void clickOnExport () ;
    public void clickOnExit () ;
    public void clickOnSearch (String searchText) ;
    public void clickOnSort () ;


}
