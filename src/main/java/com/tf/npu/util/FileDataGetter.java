package com.tf.npu.util;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.mojang.logging.LogUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileDataGetter<T> {
    private static final Gson gson = new Gson();
    public static final org.slf4j.Logger LOGGER = LogUtils.getLogger();

    private final Path dataPath;
    private final Class<T> tClass;

    private T data;

    public FileDataGetter(Path dataPath, Class<T> tClass) {
        this.dataPath = dataPath;
        this.tClass = tClass;


        initialize();
    }
    public FileDataGetter(String dataPath, Class<T> tClass) {
        try {
            this.dataPath = Paths.get(Objects.requireNonNull(FolderDataGetter.class.getClassLoader().getResource(dataPath)).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        this.tClass = tClass;

        initialize();
    }

    private void initialize() {
        LOGGER.info("Preparing to get data from file: {}", dataPath);
        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(dataPath.toUri().toURL().openStream()));
            boolean readerReady = fileReader.ready();
            LOGGER.info("Getting data from file: {} (Reader state:{})", dataPath, readerReady ? "Ready" : "Failed");
            if (readerReady){
                JsonReader reader = new JsonReader(fileReader);
                data = gson.fromJson(reader, tClass);
                reader.close();
                LOGGER.info("Succeed to get data from file: {}", dataPath);
            }
            else {
                LOGGER.info("Failed to get data from file: {}, Reason: File not exist", dataPath);
            }
        } catch (IOException e) {
            LOGGER.error("Failed to get data from file: {}, Reason: {}", dataPath, e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public T getData() {
        return data;
    }
}
