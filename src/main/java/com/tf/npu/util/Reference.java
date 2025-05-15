package com.tf.npu.util;

import java.util.Map;

public class Reference {
    // Define mod id in gray_brick.json common place for everything to reference
    public static final String MODID = "npu";
    public static final String MODNAME = "Build The World For NPU";
    public static final String VERSION = "2.24";

    public static final Map<PathType, String> PATH = Map.of(
            PathType.BLOCK, "block",
            PathType.ITEM, "item",
            PathType.CREATIVEMODETAB, "creativemodetab"
    );

    public enum PathType{
        BLOCK, ITEM, CREATIVEMODETAB
    }
    // Create gray_brick.json Deferred Register to hold Blocks which will all be registered under the "npu" namespace
}
