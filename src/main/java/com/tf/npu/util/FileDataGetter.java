package com.tf.npu.util;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;

public class FileDataGetter<T> {
    private static final Gson gson = new Gson();

    private final String dataPath;
    private final Class<T> tClass;

    public FileDataGetter(String dataPathFolder, Class<T> tClass) {
        this.dataPath = dataPathFolder;
        this.tClass = tClass;
    }

    public T getData() {
        try {
            JsonReader reader = new JsonReader(new FileReader(dataPath));
            T data = gson.fromJson(reader, tClass);
            reader.close();
            return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
