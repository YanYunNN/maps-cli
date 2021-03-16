package com.cloume.maps.commons.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yxiao
 * @date
 * @description
 */
public class JsonMapReaderUtils {

    private static JsonMapReaderUtils instance = null;

    private static Map<String, Map<String, Object>> jsonMap = new HashMap<>();

    private JsonMapReaderUtils(Map<String, String> jsonFiles) {
        jsonFiles.entrySet().forEach(entry -> {
            InputStream userInputStream = getClass().getClassLoader().getResourceAsStream(entry.getValue());
            if (userInputStream != null){
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> map = new HashMap<>();
                try {
                    map = objectMapper.readValue(userInputStream, Map.class);
                    jsonMap.put(entry.getKey(), map);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static JsonMapReaderUtils getInstance(Map<String, String> jsonFiles) {
        if (instance == null) {
            instance = new JsonMapReaderUtils(jsonFiles);
        }

        return instance;
    }

    public Map<String, Object> getJsonMapByName(String name){
        return jsonMap.getOrDefault(name, null);
    }

    public Map<String, Map<String, Object>> getAll() {
        return jsonMap;
    }
}
