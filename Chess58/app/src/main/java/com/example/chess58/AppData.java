package com.example.chess58;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.DomainCombiner;
import java.util.ArrayList;

public class AppData implements Serializable {
    private static String RECORDING_FILE = "Recording.ser";

    public static ArrayList<Recording> recordingsData;

    /**
     * Read data from the given file
     * @return Object from file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ArrayList<Recording> ReadData() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = MainActivity.instance.openFileInput(RECORDING_FILE);
            ois = new ObjectInputStream(fis);
            recordingsData = (ArrayList<Recording>) ois.readObject();
            ois.close();
            return recordingsData;
        }catch(IOException | ClassNotFoundException e){
            recordingsData = new ArrayList<Recording>();
            return recordingsData;
        }
    }

    /**
     * Write data to file
     * @throws IOException
     */
    public static void WriteData() {
        if(recordingsData == null) return;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try{
            fos = MainActivity.instance.openFileOutput(RECORDING_FILE, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(recordingsData);
            oos.close();
        } catch (IOException e) { }
    }

}
