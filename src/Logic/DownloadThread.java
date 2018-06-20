package Logic;

import GUI.DownloadingFileGui;
import Interface.IFinishNotifier;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DownloadThread extends SwingWorker<Object, Long> {
    private static final int BUFFER_SIZE = 4096;
    private String fileURL;
    private String saveDir;
    private DownloadingFileGui downloadingFileGui;
    private IFinishNotifier iFinishNotifier;

    public DownloadThread(String fileURL, String saveDir, DownloadingFileGui downloadingFileGui) {
        this.fileURL = fileURL;
        this.saveDir = saveDir;
        this.downloadingFileGui = downloadingFileGui;
        downloadingFileGui.getFileInformationLabel().setText("Starting ... ");

    }

    @Override
    protected Object doInBackground() throws Exception {

        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();

            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                    if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {
                // extracts file name from URL
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                        fileURL.length());
            }

            System.out.println("Content-Type = " + contentType);
            System.out.println("Content-Disposition = " + disposition);
            System.out.println("Content-Length = " + contentLength);
            System.out.println("fileName = " + fileName);

            downloadingFileGui.setDownloadingFileSize((double) contentLength);
            downloadingFileGui.setFileName(fileName);


            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = saveDir + File.separator + fileName;

            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);


            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                File file = new File(saveDir + "\\" + fileName);
                publish(file.length());

                while (downloadingFileGui.getIsPaused() == true) {
                    System.out.print("");
                }

                if ((downloadingFileGui.getIsCanceled() == true) || (downloadingFileGui.getIsRemoved() == true)) {
                    wait();
                    downloadingFileGui.setProgress(0);
                }
                System.out.print("");
            }

            outputStream.close();
            inputStream.close();

            System.out.println("File downloaded");
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();

        return null;
    }

    @Override
    protected void process(List<Long> chunks) {
        if (chunks.size() != 0) {
            double downloadPercent;
            downloadPercent = (chunks.get(0) / downloadingFileGui.getSize()) * 100;

            double downloadedAmountAfterAdjustment;
            double downloadingFileSizeAfterAdjustment;
            String MbOrKb;

            if (downloadingFileGui.getSize() > 1000002) {
                downloadedAmountAfterAdjustment = ByteProcessorClass.MB(chunks.get(0));
                downloadingFileSizeAfterAdjustment = ByteProcessorClass.MB((long) downloadingFileGui.getSize());
                MbOrKb = "MB";
            } else {
                downloadedAmountAfterAdjustment = ByteProcessorClass.KB(chunks.get(0));
                downloadingFileSizeAfterAdjustment = ByteProcessorClass.KB((long) downloadingFileGui.getSize());
                MbOrKb = "KB";
            }
            downloadingFileGui.getFileInformationLabel().setText("File name : " + downloadingFileGui.getFileName() + " | Downloaded amount : " + String.format("%.2f", downloadedAmountAfterAdjustment)
                    + " / " + String.format("%.2f", downloadingFileSizeAfterAdjustment) + MbOrKb + " | Downloaded percent : " + String.format("%.2f", downloadPercent) + "%" + " | Speed :" + " -- MB ");

            downloadingFileGui.setProgress((int) downloadPercent);

        }
        super.process(chunks);
    }

    @Override
    protected void done() {
        double downloadingFileSizeAfterAdjustment;
        String MbOrKb;

        if (downloadingFileGui.getSize() > 1000002) {
            downloadingFileSizeAfterAdjustment = ByteProcessorClass.MB((long) downloadingFileGui.getSize());
            MbOrKb = "MB";
        } else {
            downloadingFileSizeAfterAdjustment = ByteProcessorClass.KB((long) downloadingFileGui.getSize());
            MbOrKb = "KB";
        }
        downloadingFileGui.getFileInformationLabel().setText("Download completed ! " + " | File size : " + String.format("%.2f", downloadingFileSizeAfterAdjustment) + MbOrKb);
        downloadingFileGui.setProgress(100);

        iFinishNotifier.finished();
        downloadingFileGui.changeIsCompletedToCompleted();
        super.done();
    }


    public void resume() {
        downloadingFileGui.changePausePrimary();
    }

    public void setIfinishNotifier(IFinishNotifier iFinishNotifier) {
        this.iFinishNotifier = iFinishNotifier;
    }
}