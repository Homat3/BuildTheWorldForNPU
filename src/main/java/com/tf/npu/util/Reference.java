package com.tf.npu.util;

import java.util.Map;

public class Reference {
    // 不可变动值
    public static final String MODID = "npu";
    public static final String MODNAME = "Build The World For NPU";
    public static final String VERSION = "2.24";

    public static final Map<PathType, String> PATH = Map.of(
            PathType.LOADER, "data/npu/",
            PathType.BLOCK, "block",
            PathType.ITEM, "item",
            PathType.MODEL, "assets/npu/models/",
            PathType.CREATIVEMODETAB, "creativemodetab"
    );

    public enum PathType{
        LOADER, BLOCK, ITEM, MODEL, CREATIVEMODETAB
    }
}
