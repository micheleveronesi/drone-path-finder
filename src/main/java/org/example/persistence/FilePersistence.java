package org.example.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class FilePersistence implements Persistence{
    private final String filepath;
    private final Gson gson;

    public FilePersistence(String filepath) {
        this.filepath = filepath;
        this.gson = new GsonBuilder().create();
    }

    @Override
    public Instance getLastInstance() {
        Instance tmp;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            tmp = gson.fromJson(br, Instance.class);
        } catch(Exception e) {
            tmp =  null;
        }
        return tmp;
    }

    @Override
    public boolean saveInstance(Instance instance) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filepath));
            bw.write(gson.toJson(instance));
        } catch(Exception e) {
            return false;
        }
        return true;
    }
}
