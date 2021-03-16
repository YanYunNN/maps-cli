package com.cloume.maps.commons.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yxiao
 * @date
 * @description
 */
public class JsonArrayReaderUtils {

    private static JsonArrayReaderUtils instance = null;

    private static Map<String, List<Map<String, Object>>> jsonMap = new HashMap<>();

    private JsonArrayReaderUtils(Map<String, String> jsonFiles) {
        jsonFiles.entrySet().forEach(entry -> {
            InputStream userInputStream = getClass().getClassLoader().getResourceAsStream(entry.getValue());
            if (userInputStream != null){
                ObjectMapper objectMapper = new ObjectMapper();
                List<Map<String, Object>> map = new ArrayList<>();
                try {
                    map = objectMapper.readValue(userInputStream, List.class);
                    jsonMap.put(entry.getKey(), map);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static JsonArrayReaderUtils getInstance(Map<String, String> jsonFiles) {
        if (instance == null) {
            instance = new JsonArrayReaderUtils(jsonFiles);
        }

        return instance;
    }

    public List<Map<String, Object>> getJsonMapByName(String name){
        return jsonMap.getOrDefault(name, null);
    }

    public Map<String, List<Map<String, Object>>> getAll() {
        return jsonMap;
    }
}
