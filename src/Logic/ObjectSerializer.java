package Logic;

import GUI.DownloadingFileGui;
import GUI.SettingsFrame;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

/**
 * This class is used to serialize and deserialize different object .
 */
public class ObjectSerializer  {

    /**
     * This method is used to serialize multiple objects in an ArrayList .
     * @param arrayList
     * @param tempPath
     */
    public static void serializeObject(Object arrayList, String tempPath) {

        ObjectOutputStream objectOutputStream = null;
        checkAndMakeSavesDir();

        try {
            objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(tempPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            objectOutputStream.writeObject(arrayList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method deserialize object .
     * @param tempPath
     * @return
     */
    public static Object getObject (String tempPath) {
        ObjectInputStream ois = null;
        Object object = null ;

        try {
            ois = new ObjectInputStream(Files.newInputStream(Paths.get(tempPath)));

            try {
                object = ois.readObject();
            } catch (IOException e) {

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
        }

        return object;
    }

    public static ArrayList<DownloadingFileGui> getSerializedArrayList(String tempPath) {
        ObjectInputStream ois = null;
        ArrayList<DownloadingFileGui> arrayList = null;


        try {
            ois = new ObjectInputStream(Files.newInputStream(Paths.get(tempPath)));

            try {
                arrayList = (ArrayList<DownloadingFileGui>) ois.readObject();
            } catch (IOException e) {

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
        }

        return arrayList;
    }

    /**
     * This method saves to removed.adm
     * @param dfg
     */
    public static void saveRemovedFile(DownloadingFileGui dfg) {

        FileWriter fileWriter = null;
        Formatter output = null;

        checkAndMakeSavesDir();
        try {
            fileWriter = new FileWriter("./saves/removed.adm", true);
        } catch (IOException e) {
            System.out.println("Error while opening removed.adm !");
        }

        output = new Formatter(fileWriter);


        output.format("File name : %s      File URL : %s%n", dfg.getName(), dfg.getUrl());

        if (output != null)
            output.close();

    }

    /**
     * This method checks save direction and if it doesn't exist makes it .
     */
    private static void checkAndMakeSavesDir () {
        File savesDirectory = new File("./saves");
        if (savesDirectory.exists() == false) {
            new File("./saves").mkdirs();
        }

    }
}
