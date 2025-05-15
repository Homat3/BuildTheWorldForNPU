package com.tf.npu.util;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FolderDataGetter<T> {
    private final String dataPath;
    private final Class<T> tClass;
    private final ArrayList<T> data;


    public FolderDataGetter(String dataPathFolder, Class<T> tClass) {
        this.dataPath = "../src/main/resources/data/npu/" + dataPathFolder;
        this.tClass = tClass;
        data = new ArrayList<>(0);

        initialize();
    }

    private void initialize() {
        File folder = new File(dataPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] dataFiles = folder.listFiles();

            if (dataFiles != null) {
                for (var dataFile : dataFiles) {
                    data.add(new FileDataGetter<T>(dataFile.getAbsolutePath(), tClass).getData());
                }
            }
        }
    }

    public List<T> getList() {
        return data;
    }

}
