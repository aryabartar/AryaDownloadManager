package Logic;

import GUI.BlockedSitePanel;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

/**
 * This class implements file actions .
 */
public class FileProcess {

    /**
     * This method is used in adding blocked sites , ...
     *
     * @param filePath
     * @param appendThis
     */
    public static void appendToThisFile(String filePath, String appendThis) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter out = null;
        try {
            fw = new FileWriter(filePath, true);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);
            out.println(appendThis);
            out.close();
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        } finally {

            if (out != null)
                out.close();

            try {
                if (bw != null)
                    bw.close();
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
            try {
                if (fw != null)
                    fw.close();
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
        }
    }

    /**
     * Used to clear blocked sites list .
     *
     * @param filePath
     */
    public static void clearFile(String filePath) {
        Formatter output = null;

        try {
            output = new Formatter(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        output.format("");
    }

    /**
     * This metod returns an ArrayList containing blocked sites path .
     * @param filePath
     * @return
     */
    public static ArrayList<String> getBlockedSitesAsArray(String filePath) {
        Scanner input = null;
        ArrayList<String> addresses = new ArrayList<>();

        try {
            input = new Scanner(Paths.get(filePath));
        } catch (IOException e) {
            System.err.println("Error opening file . ");
        }

        try {
            while (input.hasNext()) {
                addresses.add(input.nextLine());
            }
        } catch (NullPointerException e) {
            System.err.println("Can't find ./saves/blocked.txt");
        }
        return addresses;
    }

    /**
     * This method is used to change language to given path .
     * @param path
     */
    public static void changeLanguage(String path) {
        Formatter output = null;
        Scanner input = null ;

        try {
            output = new Formatter("./saves/language/default.txt");
        } catch (FileNotFoundException e) {
            System.err.println("Write permission denied. Terminating.");
        }

        try {
            input = new Scanner(Paths.get("./saves/language/" + path)) ;
        } catch (IOException e) {
            System.err.println("Read permission denied. Terminating.");

        }
        while (input.hasNext()) {
            output.format(input.nextLine()) ;
        }

        input.close();
        output.close();
    }

    /**
     * This method is used to write an ArrayList to a give path .
     * @param filePath
     * @param sites
     */
    public static void writeArrayListToFile(String filePath, ArrayList<BlockedSitePanel> sites) {

        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter out = null;
        try {
            fw = new FileWriter(filePath, false);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);
            for (BlockedSitePanel bsp : sites) {
                out.println(bsp.getUrl());
            }
            out.close();
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        } finally {

            if (out != null)
                out.close();

            try {
                if (bw != null)
                    bw.close();
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
            try {
                if (fw != null)
                    fw.close();
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
        }
    }
}
