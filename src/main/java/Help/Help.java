package Help;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Help {
    public static void main(String[] args) {
        deleteDuplicates();
    }
    public static void translationFilt() {
        // 配置路径
        String fileFolderPath = "E:\\Documents\\Java\\McMod\\BuildTheWorldForNPU\\src\\main\\resources\\assets\\npu\\blockstates";
        String inputFilePath = "E:\\Documents\\Java\\McMod\\BuildTheWorldForNPU\\src\\main\\resources\\assets\\npu\\lang\\en_us.json";
        String outputFilePath = "E:\\Documents\\Java\\McMod\\BuildTheWorldForNPU\\src\\main\\resources\\assets\\npu\\lang\\en_us_filtered.json";

        try {
            // 1. 获取文件夹下所有JSON文件的文件名（无后缀名）
            Set<String> nameList = getJsonFileNamesWithoutExtension(fileFolderPath);
            System.out.println("找到的JSON文件名列表: " + nameList);

            // 2. 读取翻译文件
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            Map<String, String> translationMap = objectMapper.readValue(
                    new File(inputFilePath),
                    new com.fasterxml.jackson.core.type.TypeReference<Map<String, String>>() {}
            );

            // 3. 过滤键值对
            Map<String, String> filteredMap = translationMap.entrySet().stream()
                    .filter(entry -> {
                        String key = entry.getKey();
                        // 如果不是item.npu.XXXXXX格式的键，保留
                        if (!key.startsWith("item.npu.")) {
                            return true;
                        }
                        // 提取XXXXXX部分
                        String suffix = key.substring("item.npu.".length());
                        // 检查是否在文件名列表中
                        return nameList.contains(suffix);
                    })
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            // 4. 保存过滤后的文件
            objectMapper.writeValue(new File(outputFilePath), filteredMap);

            System.out.println("Done! Save as: " + outputFilePath);
            System.out.println("Original number: " + translationMap.size());
            System.out.println("Filtered number: " + filteredMap.size());
            System.out.println("Del number: " + (translationMap.size() - filteredMap.size()));

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static Set<String> getJsonFileNamesWithoutExtension(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));

        if (files == null) {
            return Collections.emptySet();
        }

        return Arrays.stream(files)
                .map(File::getName)
                .map(name -> name.substring(0, name.lastIndexOf('.')))
                .collect(Collectors.toSet());
    }
    public static void deleteDuplicates() {
        // 输入和输出文件路径
        String inputFilePath = "E:\\Documents\\Java\\McMod\\BuildTheWorldForNPU\\src\\main\\resources\\assets\\npu\\lang\\zh_cn.json";
        String outputFilePath = "E:\\Documents\\Java\\McMod\\BuildTheWorldForNPU\\src\\main\\resources\\assets\\npu\\lang\\zh_cn_c.json";

        try {
            // 创建ObjectMapper实例
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            // 读取JSON文件到Map
            Map<String, String> translationMap = objectMapper.readValue(
                    new File(inputFilePath),
                    new com.fasterxml.jackson.core.type.TypeReference<Map<String, String>>() {}
            );

            // 使用TreeMap自动去重并按键排序
            Map<String, String> sortedMap = new TreeMap<>(translationMap);

            // 将排序后的Map写入新文件
            objectMapper.writeValue(new File(outputFilePath), sortedMap);

            System.out.println("Done, saved to: " + outputFilePath);
            System.out.println("Original number: " + translationMap.size());
            System.out.println("Done number: " + sortedMap.size());
            System.out.println("Del number: " + (translationMap.size() - sortedMap.size()));

        } catch (IOException e) {
            System.err.println("处理文件时出错: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
