package Logic;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Random;

/**
 * This class implements a downloaded/downloading file with path , size , downloaded percent , ...
 *
 * @author Arya Khaligh
 */
public class DownloadingFile implements Serializable {
    public static String selectedFileName;
    private String downloadingFileName;
    private double downloadingFileSize;
    private double downloadedAmount;
    private double downloadedPercent;
    private double downloadSpeed = 5.6; //This is random !
    private String downloadSpeedString;
    private String url = "://";
    private String startTime;
    protected boolean isSelected;
    private boolean isPaused;
    private boolean isCanceled;
    private String fileSaveDirectory; //This is the file folder path
    private boolean isCompleted = false;
    private String filePath = "TMP"; //This is the file path
    private LocalTime timeForCompare;


    public DownloadingFile(String fileName, double fileSize) {
        startTime = getTime();
        isPaused = false;
        isCanceled = false;

        setFileName(fileName);


        //This is tmp . Change it later !
        setAmountsByDownloadedAmount(0);
        timeForCompare = LocalTime.now();

    }

    public LocalTime getTimeForCompare() {
        return timeForCompare;
    }

    /**
     * @return downloadingFileName
     */
    public String getDownloadingFileName() {
        return downloadingFileName;
    }

    /**
     * @return downloadingFileSize
     */
    public double getDownloadingFileSize() {
        return downloadingFileSize;
    }

    /**
     * @return downloadedPercent
     */
    public double getDownloadedPercent() {
        return downloadedPercent;
    }

    /**
     * @return downloaded percent in string and .02 format .
     */
    public String getDownloadedPercentString() {
        String tempString = String.format("%.02f", downloadedPercent);
        return tempString;
    }

//    /**
//     * @return fileInformation
//     */
//    public String getFileInfo() {
//        String fileInformation = "File size : " + getDownloadingFileSize() + " |  Downloaded amount : " + getDownloadedAmount()
//                + " |  Download percent : " + getDownloadedPercentString() + "% | Speed : " + getDownloadSpeedString() + "                 ";
//        return fileInformation;
//    }

    /**
     * @return downloadedAmount
     */
    public double getDownloadedAmount() {
        return downloadedAmount;
    }

    /**
     * @return downloadSpeed
     */
    public double getDownloadSpeed() {
        return downloadSpeed;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @return isPaused
     */
    public boolean getIspaused() {
        return isPaused;
    }

    /**
     * @return isCanceled
     */
    public boolean getIsCanceled() {
        return isCanceled;
    }

    /**
     * This method sets URL .
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
        System.out.println("URL : " + url);
    }


    /**
     * This method returns current time .
     *
     * @return current time as String .
     */
    public static String getTime() {
        String time;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        time = sdf.format(cal.getTime());

        return time;
    }

    /**
     * @return minute in Integer format .
     */
    public static int getMinute() {
        String min = "";
        min += getTime().charAt(3);
        min += getTime().charAt(4);

        return Integer.parseInt(min);
    }

    /**
     * @return hour in Integer format
     */
    public static int getHour() {
        String hour = "";
        hour += getTime().charAt(0);
        hour += getTime().charAt(1);

        return Integer.parseInt(hour);
    }

    /**
     * @return second in Integer format
     */
    public static int getSec() {
        String sec = "";
        sec += getTime().charAt(6);
        sec += getTime().charAt(7);

        return Integer.parseInt(sec);
    }

    /**
     * This method sets fileName .
     *
     * @param fileName
     */
    public void setFileName(String fileName) {
        downloadingFileName = fileName;
    }

    /**
     * This method sets size .
     *
     * @param size
     */
    public void setDownloadingFileSize(double size) {
        downloadingFileSize = size;
    }

    /**
     * This method sets downloadedAmount .
     *
     * @param downloadedAmount
     */
    private void setDownloadedAmount(double downloadedAmount) {
        this.downloadedAmount = downloadedAmount;
    }

    /**
     * This method sets setDownloadedPercent .
     */
    private void setDownloadedPercent() {
        downloadedPercent = (downloadedAmount / downloadingFileSize) * 100;
    }

    /**
     * This method sets amount by downloaded amount
     *
     * @param downloadedAmount
     */
    public void setAmountsByDownloadedAmount(double downloadedAmount) {
        if ((downloadedAmount > getDownloadingFileSize()) || (downloadedAmount < 0)) {
            throw new IllegalArgumentException("Invalid downloaded amount !");
        }

        setDownloadedAmount(downloadedAmount);
        setDownloadedPercent();
    }

    /**
     * This method changes isPaused .
     */
    public void changePauseState() {
        if (isCanceled == false)
            isPaused = true;
    }

    /**
     * This method changes isCanceled to true .
     */
    public void cancelDownload() {
        isCanceled = true;
    }

    /**
     * @return isSelected
     */
    public boolean getIsSelected() {
        return isSelected;
    }

    /**
     * This method sets isSelected .
     *
     * @param isSelected
     */
    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    /**
     * @return downloadSpeedString
     */
    public String getDownloadSpeedString() {
        //Add some actions here !
        downloadSpeedString = downloadSpeed + " MB";

        return downloadSpeedString;
    }

    /**
     * This method sets fileSaveDirectory and also invokes setFilePath() method .
     *
     * @param fileSaveDirectory
     */
    public void setFileSaveDirectory(String fileSaveDirectory) {
        this.fileSaveDirectory = fileSaveDirectory;
        setFilePath();
    }

    /**
     * This method sets setFilePath ;
     */
    private void setFilePath() {
        filePath = fileSaveDirectory + "\\" + downloadingFileName;
    }

    /**
     * This method returns fileSaveDirectory .
     *
     * @return fileSaveDirectory
     */
    public String getFileSaveDirectory() {
        return fileSaveDirectory;
    }

    /**
     * This method returns filePath .
     *
     * @return filePath
     */
    public String getFilePath() {
        return fileSaveDirectory;
    }

    public void changePausePrimary() {
        if (isPaused == false)
            isPaused = true;
        else
            isPaused = false;
    }

    public void changeIsCompletedToCompleted() {
        isCompleted = true;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }




}
