package com.tf.npu.blocks.dataofnpublocks;

import java.util.ArrayList;
import java.util.List;

public class ShapeData {
    public String loader;
    public Element[] elements;

    public Boolean loaderIsObj() {
        if (loader == null) return false;
        else return loader.equals("forge:obj");
    }

    public ArrayList<List<Double>> getShapeList() {
        if (loaderIsObj()) {
            return null;
        }

        ArrayList<List<Double>> shapeList = new ArrayList<>(0);

        for (Element element : elements) {
            element.transform();
            shapeList.add(List.of(element.from[0], element.from[1], element.from[2], element.to[0], element.to[1], element.to[2]));
        }

        return shapeList;
    }


    private static class Element {
        public Double[] from;
        public Double[] to;

        public void transform() {
            for (int i = 0; i < 3; i++) {
                from[i] /= 16.0;
                to[i] /= 16.0;
            }
        }
    }
}
