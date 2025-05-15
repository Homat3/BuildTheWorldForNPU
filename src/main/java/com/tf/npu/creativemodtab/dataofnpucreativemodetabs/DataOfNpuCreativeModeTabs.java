package com.tf.npu.creativemodtab.dataofnpucreativemodetabs;

public class DataOfNpuCreativeModeTabs {
    public String ID;
    public String ENUM_NAME;
    public Icon icon;

    public String getIconType() {
        return icon.type;
    }
    public int getIconIndex() {
        return icon.index;
    }

    private static class Icon{
        public String type;
        public int index;
    }
}
