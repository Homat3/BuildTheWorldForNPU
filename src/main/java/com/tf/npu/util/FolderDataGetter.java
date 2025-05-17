package com.tf.npu.util;


import com.mojang.logging.LogUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FolderDataGetter<T> {
    public static final org.slf4j.Logger LOGGER = LogUtils.getLogger();

    private final String dataPath;
    private final Class<T> tClass;
    private final ArrayList<T> data;


    public FolderDataGetter(String dataPathFolder, Class<T> tClass) {
        this.dataPath = Reference.PATH.get(Reference.PathType.LOADER) + dataPathFolder;
        this.tClass = tClass;
        data = new ArrayList<>(0);

        initialize();
    }

    private void initialize() {
        LOGGER.info("Preparing to get data from folder: {}", dataPath);
        URL url = FolderDataGetter.class.getClassLoader().getResource(dataPath);
        if (url == null) return;
        try{
            LOGGER.info("Getting file from folder: {})", dataPath);
            Path path = Paths.get(url.toURI());
            try (Stream<Path> paths = Files.walk(path)){
                paths.filter(Files::isRegularFile).forEach((file) -> {data.add(new FileDataGetter<>(file, tClass).getData());});
            }
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<T> getList() {
        return data;
    }

}
